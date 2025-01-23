<h1 align="center" style="margin-top: 0px;">QuickGPG</h1>

<div align="center">
  
![Release](https://img.shields.io/github/v/release/lentidas/DAI-2425-PW3?style=for-the-badge) ![License](https://img.shields.io/github/license/lentidas/DAI-2425-PW3?style=for-the-badge)
 ![Docker Build](https://img.shields.io/github/actions/workflow/status/lentidas/DAI-2425-PW3/builder.yaml?style=for-the-badge&logo=docker&label=Docker%20Build)

</div>

> [!NOTE]
> This program was written as our third and last practical work during the [DAI course](https://github.com/heig-vd-dai-course/heig-vd-dai-course/tree/main) of 2024 at the [HEIG-VD](https://heig-vd.ch).

## Authors

- Pedro Alves da Silva ([@PedroAS7](https://github.com/PedroAS7))
- Gonçalo Carvalheiro Heleno ([@lentidas](https://github.com/lentidas))

# Table of Contents

- [Table of Contents](#table-of-contents)
  - [Usage](#usage)
    - [Running the Docker Compose file](#running-the-docker-compose-file)
      - [Running the `docker-compose.yaml` file](#running-the-docker-composeyaml-file)
  - [Using cURL against our API](#using-curl-against-our-api)
    - [Getting the list of all users](#getting-the-list-of-all-users)
    - [Get a user by their first name](#get-a-user-by-their-first-name)
    - [Adding a new user](#adding-a-new-user)
    - [Error when adding a user with a conflicting username](#error-when-adding-a-user-with-a-conflicting-username)
  - [Infrastructure](#infrastructure)
  - [Documentation](#documentation)
  - [Contributing](#contributing)
    - [Clone and build the project](#clone-and-build-the-project)

## Usage

The full API specification is available in [this document](./docs/api_doc.pdf).

> [!NOTE]
> For compatibility through multiple operating systems, we recommend you run this program using Docker.

### Running the Docker Compose file

We provide 2 Docker Compose files that allow you to test the application:

- `docker-compose.yaml`: this file is the one used to deploy the application in a production environment, on Microsoft Azure;
- `docker-compose-local.yaml`: this file is the one used to deploy the application in a local environment, on your machine.

Both files deploy the following:
- a PostgreSQL database container;
- a Traefik reverse proxy;
- a GPG Keyserver container.

> [!IMPORTANT]
> The `docker-compose.yaml` file is configured to deploy the application on Microsoft Azure. You will need to change the `"traefik.http.routers.website.rule=Host(`gpg-keyserver.westeurope.cloudapp.azure.com`)"` label in the `gpg-keyserver` service to match your domain.

> [!NOTE]
> The `docker-compose-local.yaml` file deploys the containers on your local machine, as such, the domain `localhost` is used. Also, no SSL is configured in this file, so port 80 is the only endpoint available.

> [!IMPORTANT]
> For some reason, we had issues with the GitHub Actions workflow that builds the Docker image, and we were unable to publish the image to the GitHub Container Registry. We decided, in a hail mary, to set our `docker-compose.yaml` file **to also build the image when running the services**, similarly to the `docker-compose-local.yaml`. This means that the image is not available in the GitHub Container Registry. We apologize for the inconvenience.

> [!NOTE]
> An init script to create the database schema is used by the PostgreSQL container. This script is located in the `db` folder. An important note, is that this script only runs on a fresh database, **so if you need to run it again, you will need to delete the database volume**.

#### Running the `docker-compose.yaml` file

To run the `docker-compose-local.yaml` file on your machine, you can use the following commands:

```shell
# Clone the repository.
git clone https://github.com/lentidas/DAI-2425-PW3.git

## Change to the project directory.
cd DAI-2425-PW3

### Start the services.
docker-compose -f docker-compose-local.yaml up -d

### Check that the service is running and provides data.
curl -i http://localhost/users
```

## Using cURL against our API

cURL can easily be used to interact with our HTTP server. A few examples are:

### Getting the list of all users

```curl -v https://gpg-keyserver.westeurope.cloudapp.azure.com/users
* Host gpg-keyserver.westeurope.cloudapp.azure.com:443 was resolved.
* IPv6: (none)
* IPv4: 108.143.27.98
*   Trying 108.143.27.98:443...
* Connected to gpg-keyserver.westeurope.cloudapp.azure.com (108.143.27.98) port 443
* ALPN: curl offers h2,http/1.1
* TLSv1.3 (OUT), TLS handshake, Client hello (1):
* [...]
> GET /users HTTP/2
> Host: gpg-keyserver.westeurope.cloudapp.azure.com
> User-Agent: curl/8.5.0
> Accept: */*
>
< HTTP/2 200
< content-type: application/json
< date: Thu, 23 Jan 2025 22:41:52 GMT
< content-length: 140
<
* Connection #0 to host gpg-keyserver.westeurope.cloudapp.azure.com left intact
[{"username":"PedroAS7","firstName":"Pedro","lastName":"Alves da Silva"},{"username":"lentidas","firstName":"Gonçalo","lastName":"Heleno"}]
```

### Get a user by their first name

```bash
$ curl -v https://gpg-keyserver.westeurope.cloudapp.azure.com/users?firstName=gonçalo
* Host gpg-keyserver.westeurope.cloudapp.azure.com:443 was resolved.
* IPv6: (none)
* IPv4: 108.143.27.98
*   Trying 108.143.27.98:443...
* Connected to gpg-keyserver.westeurope.cloudapp.azure.com (108.143.27.98) port 443
* ALPN: curl offers h2,http/1.1
* TLSv1.3 (OUT), TLS handshake, Client hello (1):
* [...]
> GET /users?firstName=gonçalo HTTP/2
> Host: gpg-keyserver.westeurope.cloudapp.azure.com
> User-Agent: curl/8.5.0
> Accept: */*
>
< HTTP/2 200
< content-type: application/json
< date: Thu, 23 Jan 2025 22:43:06 GMT
< content-length: 68
<
* Connection #0 to host gpg-keyserver.westeurope.cloudapp.azure.com left intact
[{"username":"lentidas","firstName":"Gonçalo","lastName":"Heleno"}]
```

### Adding a new user

```bash
$ curl -v --header "Content-Type: application/json" --data '{"username": "john.doe", "firstName": "john", "lastName": "doe"}' https://gpg-keyserver.westeurope.cloudapp.azure
.com/users
* Host gpg-keyserver.westeurope.cloudapp.azure.com:443 was resolved.
* IPv6: (none)
* IPv4: 108.143.27.98
*   Trying 108.143.27.98:443...
* Connected to gpg-keyserver.westeurope.cloudapp.azure.com (108.143.27.98) port 443
* ALPN: curl offers h2,http/1.1
* TLSv1.3 (OUT), TLS handshake, Client hello (1):
* [...]
> POST /users HTTP/2
> Host: gpg-keyserver.westeurope.cloudapp.azure.com
> User-Agent: curl/8.5.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 64
>
< HTTP/2 201
< content-type: application/json
< date: Thu, 23 Jan 2025 22:44:13 GMT
< content-length: 59
<
* Connection #0 to host gpg-keyserver.westeurope.cloudapp.azure.com left intact
{"username":"john.doe","firstName":"john","lastName":"doe"}
```

### Error when adding a user with a conflicting username

```bash
$ curl -v --header "Content-Type: application/json" --data '{"username": "john.doe", "firstName": "johnny", "lastName": "doe"}' https://gpg-keyserver.westeurope.cloudapp.azu
re.com/users
* Host gpg-keyserver.westeurope.cloudapp.azure.com:443 was resolved.
* IPv6: (none)
* IPv4: 108.143.27.98
*   Trying 108.143.27.98:443...
* Connected to gpg-keyserver.westeurope.cloudapp.azure.com (108.143.27.98) port 443
* ALPN: curl offers h2,http/1.1
* TLSv1.3 (OUT), TLS handshake, Client hello (1):
* [...]
> POST /users HTTP/2
> Host: gpg-keyserver.westeurope.cloudapp.azure.com
> User-Agent: curl/8.5.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 66
>
< HTTP/2 409
< content-type: text/plain;charset=utf-8
< date: Thu, 23 Jan 2025 22:45:27 GMT
< content-length: 8
<
* Connection #0 to host gpg-keyserver.westeurope.cloudapp.azure.com left intact
Conflict
```

### Pushing a new key & fingerprint

```bash
$ curl -v --header "Content-Type: application/json" --data '{"fingerprint":"123456789ABCDEF123456789ABCDEF1234567890","key":"-----BEGIN PGP PUBLIC KEY BLOCK-----[...]-----END PGP PUBLIC KEY BLOCK-----"}' https://gpg-keyserver.westeurope.cloudapp.azure.com/gpg-keys
* Host gpg-keyserver.westeurope.cloudapp.azure.com:443 was resolved.
* IPv6: (none)
* IPv4: 108.143.27.98
*   Trying 108.143.27.98:443...
* Connected to gpg-keyserver.westeurope.cloudapp.azure.com (108.143.27.98) port 443
* ALPN: curl offers h2,http/1.1
* TLSv1.3 (OUT), TLS handshake, Client hello (1):
* [...]
> POST /gpg-keys HTTP/2
> Host: gpg-keyserver.westeurope.cloudapp.azure.com
> User-Agent: curl/8.5.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 14029
>
< HTTP/2 201
< content-type: application/json
< date: Thu, 23 Jan 2025 22:50:42 GMT
<
{"fingerprint":"123456789ABCDEF123456789ABCDEF1234567890","key":"-----BEGIN PGP PUBLIC KEY BLOCK-----[...]-----END PGP PUBLIC KEY BLOCK-----"}
* Connection #0 to host gpg-keyserver.westeurope.cloudapp.azure.com left intact
```

## Infrastructure

The infrastructure for this project is deployed on Microsoft Azure. We tried to follow a Infrastructure as Code approach, using Terraform to deploy the resources and Ansible to bootstrap the VM with the necessary configurations.

The infrastructure code is located in the `infra` directory. The code is executed in a GitHub Actions workflow with the required secrets to authenticate with Azure.

A `README.md` file is available in the `infra/terraform` directory with instructions on how to deploy the infrastructure. We think a configuration step is missing, but please open an issue if you find this useful for you. We did not make one for the Ansible part.

## Documentation

The entirety of our Java code has been documented using Javadoc-style comments.

The full protocol specification is available in [this document](./docs/api_doc.pdf).

The usage instructions are available in the [Usage](#usage) section of this README.

## Contributing

You are welcome to contribute and improve this project. Below you will find some guidelines to help you get started.

- We try to follow the Semantic Versioning guidelines as much as possible. You can find more information about it [here](https://semver.org/).
- The releases for this project are automated using [Release Please](https://github.com/googleapis/release-please-action).
- As a consequence of the previous 2 points, we use the Conventional Commits specification for our commit messages. You can find more information about it [here](https://www.conventionalcommits.org/).
- We license our code under the GNU General Public License v3.0. You can find it [here](./LICENSE.txt). A copyright header template for the source files is provide [here](./.idea/copyright/DAI_PW2_GNUv3.xml).

### Clone and build the project

We use [Maven](https://maven.apache.org/) to manage our project. The Maven wrapper is versioned alongside with the rest of the code, as well as some project configurations for [IntelliJ IDEA from Jetbrains](https://www.jetbrains.com/idea/). These project files contain run configurations that you can use to run the program from the IDE.

To clone and build the project on the command line, you can use the following commands:

```shell
# Clone the repository.
git clone https://github.com/lentidas/DAI-2425-PW3.git

# Change to the project directory.
cd DAI-2425-PW3

# Modify the code as you wish.

# Check that the code is well formatted...
./mvnw spotless:check

# ...and eventually format it.
./mvnw spotless:apply

# Build the project with the dependencies.
./mvnw dependency:go-offline clean compile package

# Run the program (do not forget to adjust the version accordingly).
java -jar target/quickgpg-1.0.0.jar --help
```
