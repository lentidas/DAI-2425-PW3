resource "tls_private_key" "ssh_key" {
  algorithm = "RSA"
  rsa_bits  = 4096
}

resource "azurerm_resource_group" "resource_group" {
  name     = "${local.app_name}-rg"
  location = "West Europe"

  tags = local.default_tags
}

resource "azurerm_virtual_network" "virtual_network" {
  name                = "${local.app_name}-vnet"
  address_space       = ["10.0.0.0/16"]
  location            = resource.azurerm_resource_group.resource_group.location
  resource_group_name = resource.azurerm_resource_group.resource_group.name

  tags = local.default_tags
}

resource "azurerm_subnet" "private_subnet" {
  name                 = "${local.app_name}-subnet"
  resource_group_name  = resource.azurerm_resource_group.resource_group.name
  virtual_network_name = resource.azurerm_virtual_network.virtual_network.name
  address_prefixes     = ["10.0.1.0/24"]
}

resource "azurerm_public_ip" "public_ip" {
  name                = "${local.app_name}-public-ip"
  location            = resource.azurerm_resource_group.resource_group.location
  resource_group_name = resource.azurerm_resource_group.resource_group.name
  allocation_method   = "Static"
  domain_name_label   = local.app_name

  tags = local.default_tags

  lifecycle {
    create_before_destroy = true
  }
}

resource "azurerm_network_security_group" "network_security_group" {
  name                = "${local.app_name}-nsg"
  location            = resource.azurerm_resource_group.resource_group.location
  resource_group_name = resource.azurerm_resource_group.resource_group.name

  # The following security rules are based on what Azure creates by default when creating a VM following the guidelines of the HEIG-VD course.

  security_rule {
    name                       = "ssh"
    priority                   = 300
    direction                  = "Inbound"
    access                     = "Allow"
    protocol                   = "Tcp"
    source_port_range          = "*"
    destination_port_range     = "*"
    source_address_prefix      = "*"
    destination_address_prefix = "*"
  }

  security_rule {
    name                       = "http"
    priority                   = 320
    direction                  = "Inbound"
    access                     = "Allow"
    protocol                   = "Tcp"
    source_port_range          = "*"
    destination_port_range     = "*"
    source_address_prefix      = "*"
    destination_address_prefix = "*"
  }

  security_rule {
    name                       = "https"
    priority                   = 340
    direction                  = "Inbound"
    access                     = "Allow"
    protocol                   = "Tcp"
    source_port_range          = "*"
    destination_port_range     = "*"
    source_address_prefix      = "*"
    destination_address_prefix = "*"
  }

  tags = local.default_tags
}

resource "azurerm_network_interface" "network_interface" {
  name                = "${local.app_name}-nic"
  location            = resource.azurerm_resource_group.resource_group.location
  resource_group_name = resource.azurerm_resource_group.resource_group.name

  ip_configuration {
    name                          = "${local.app_name}-nic-ip"
    subnet_id                     = resource.azurerm_subnet.private_subnet.id
    private_ip_address_allocation = "Dynamic"
    public_ip_address_id          = resource.azurerm_public_ip.public_ip.id
  }

  tags = local.default_tags
}

resource "azurerm_network_interface_security_group_association" "nic_nsg_association" {
  network_interface_id      = resource.azurerm_network_interface.network_interface.id
  network_security_group_id = resource.azurerm_network_security_group.network_security_group.id
}

resource "azurerm_linux_virtual_machine" "ubuntu_vm" {
  name                = "${local.app_name}-vm"
  resource_group_name = resource.azurerm_resource_group.resource_group.name
  location            = resource.azurerm_resource_group.resource_group.location
  size                = "Standard_B1s"
  admin_username      = "ubuntu"
  network_interface_ids = [
    resource.azurerm_network_interface.network_interface.id,
  ]

  admin_ssh_key {
    username   = "ubuntu"
    public_key = resource.tls_private_key.ssh_key.public_key_openssh
  }

  os_disk {
    caching              = "ReadWrite"
    storage_account_type = "Standard_LRS"
  }

  source_image_reference {
    publisher = "canonical"
    offer     = "ubuntu-24_04-lts"
    sku       = "server"
    version   = "latest"
  }

  tags = local.default_tags
}

# We create this file to store the private key in a file so that we can use it with ansible-playbook.
# This is a workaround, because while we could pass the key directly in the command, since it it a sensitive value,
# Terraform would then hide the output from Ansible.
resource "null_resource" "create_secret_key_file" {
  triggers = {
    always_run = timestamp() # Trigger a run on every Terraform apply.
  }

  provisioner "local-exec" {
    working_dir = path.module
    command     = <<-EOT
      echo "${resource.tls_private_key.ssh_key.private_key_openssh}" > ./id_rsa.key && chmod 600 ./id_rsa.key
    EOT
  }

  depends_on = [resource.azurerm_linux_virtual_machine.ubuntu_vm]
}

resource "null_resource" "run_ansible_playbook" {
  triggers = {
    secret_key_file = null_resource.create_secret_key_file.id
  }

  provisioner "local-exec" {
    working_dir = path.module
    command     = <<-EOT
      ANSIBLE_HOST_KEY_CHECKING=False \
      ANSIBLE_PYTHON_INTERPRETER=/usr/bin/python3 \
      ansible-playbook \
        -u ubuntu \
        -i '${resource.azurerm_public_ip.public_ip.fqdn},' \
        --key-file ./id_rsa.key \
        ./ansible-playbook.yml
    EOT
  }

  depends_on = [resource.azurerm_linux_virtual_machine.ubuntu_vm]
}

# Delete the secret key file after running the Ansible Playbook.
resource "null_resource" "delete_secret_key_file" {
  triggers = {
    secret_key_file  = null_resource.create_secret_key_file.id
    ansible_playbook = null_resource.run_ansible_playbook.id
  }

  provisioner "local-exec" {
    working_dir = path.module
    command     = "rm ./id_rsa.key"
  }

  depends_on = [resource.azurerm_linux_virtual_machine.ubuntu_vm]
}
