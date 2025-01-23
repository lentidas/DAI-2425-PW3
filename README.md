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
    - [Build Docker image](#build-docker-image)
    - [Print the help message](#print-the-help-message)
    - [Start the server](#start-the-server)
    - [Start the client](#start-the-client)
    - [Using the JAR](#using-the-jar)
  - [Documentation](#documentation)
  - [Contributing](#contributing)
    - [Clone and build the project](#clone-and-build-the-project)

## Usage



The full API specification is available in [this document](./docs/api_doc.pdf).

> [!NOTE]
> For compatibility through multiple operating systems, we recommend you run this program using Docker. You can still run the JAR we release, but ensure you have Java 21 or later installed on your machine.

### Build Docker image

The image to run the program is available alongside the releases of this repository:

```shell
# Pull the image from GitHub Container Registry.
docker pull ghcr.io/lentidas/dai-2425-pw3:latest
```

You can also build the image yourself using the provided Dockerfile:

```shell
# Clone the repository.
git clone https://github.com/lentidas/DAI-2425-PW3.git

# Change to the project directory.
cd DAI-2425-PW3

# Build the Docker image.
docker build -t quickgpg:latest .
```

> [!NOTE]
> For the following sections, adapt the commands to use the image you built or pulled from the GitHub Container Registry.

### Print the help message

To print the help message, you can use the following commands:

```shell
# Print the help message for the server.
docker run ghcr.io/lentidas/dai-2425-pw3:latest server --help

# Starting the container without arguments defaults to printing the help message for the server.
docker run ghcr.io/lentidas/dai-2425-pw3:latest
```

### Start the HTTP server

To start the server, you can use the following commands:

> [!NOTE]
> The `Dockerfile` exposes the port `7070` by default. As you noted from the examples above, if you want to change the port of the program using the `-p` or `-b` flags, you must also expose the same port when running the container.

> [!IMPORTANT]
> Since we are using Docker, the server will not be accessible with the default listening address of `127.0.0.1`. You must ALWAYS override and use a *any* address like `0.0.0.0` or `::` to allow connections from outside the container.

### Start the PostgresSQL database

To start the client, you can use the following commands:

> [!NOTE]
> The `Dockerfile` exposes the port `5432` by default. As you noted from the examples above, if you want to change the port of the program using the `-p` or `-b` flags, you must also expose the same port when running the container.

### Using the JAR

Using the JAR directly is also possible, as long as you have a database deployed on a locally-running PostgresSQL database. Default connection parameters are as follows:

- Database: `gpg_keyserver_db`
- Schema: `gpg_keyserver`
- Username: `gpg_keyserver`
- Password: `gpg_keyserver`
- Port: 5432

You can download the JAR from the [releases page](https://github.com/lentidas/DAI-2425-PW3/releases).


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
