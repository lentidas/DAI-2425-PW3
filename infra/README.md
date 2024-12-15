# Instructions

The `infra` directory contains the infrastructure code for the project. This includes the Terraform code for creating the Microsoft Azure resources required and the Ansible code for configuring the virtual machine.

## How to use

First, make sure you have the following tools installed:

- [Terraform](https://www.terraform.io/downloads.html)
- [Ansible](https://docs.ansible.com/ansible/latest/installation_guide/intro_installation.html)
- [Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli)

[Homebrew](https://brew.sh/) can be used to install these tools on macOS or Linux with the following command:

```bash
brew tap hashicorp/tap
brew install hashicorp/tap/terraform ansible azure-cli
```

When running locally, you will need to authenticate with Azure using the Azure CLI. More information can be found [here](https://registry.terraform.io/providers/hashicorp/azurerm/latest/docs/guides/azure_cli).

When running in a CI/CD pipeline, you will need to authenticate with Azure using a service principal. More information can be found [here](https://registry.terraform.io/providers/hashicorp/azurerm/latest/docs/guides/service_principal_client_secret). This guide shows you how to create a service principal and then pass it to Terraform using environment variables.

The `secrets.yml` file can be used with `summon` and `gopass` to store secrets securely.
