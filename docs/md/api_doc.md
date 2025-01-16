# Endpoint template

This section contains the template that is used in the endpoints list section. It describes what each part does, and how to fill it.

## `/example_endpoint`

A description of this endpoint will be available here. The requirements are listed in this description, as well as what its goal is.


### Accepted methods

This table allows readers to quickly understand what the accepted HTTP request methods are.

|               |     GET      |     POST     |     PUT      |    DELETE    |
|:--------------|:------------:|:------------:|:------------:|:------------:|
| **Accepted?** |              | $\checkmark$ |              | $\checkmark$ |


### Parameters

The table below lists all the mandatory and optional requirements, with a brief description of each one of them

| Parameter  | Optional?    | Description                                                        |
|------------|--------------|--------------------------------------------------------------------|
| `username` | $\checkmark$ | Username to be used for this dummy request. Does not affect output |


### Responses

This section lists the possible HTTP status codes the clients may receive when using this endpoint. Server-side status codes (`5xx`) are excluded from the list.

When using both the `POST` and `DELETE` methods:

- `200`: Call was successful
- `400`: Invalid parameters
- `401`: User not logged


# Endpoints summary

This table summarizes the accepted HTTP request methods by endpoint.

| Endpoint URL        |     GET      |     POST     |     PUT      |    DELETE    |
|:--------------------|:------------:|:------------:|:------------:|:------------:|
| `/example_endpoint` |              | $\checkmark$ |              | $\checkmark$ |


# GPG keys

This section lists and describes the endpoints that allows a client to interact with the GPG keys stored in the server.


## `/gpg/list`

Shows a list of all stored public GPG keys, along with the user and email they belong to.

### Accepted methods


|               |      GET      |     POST     |     PUT      |    DELETE    |
|:--------------|:-------------:|:------------:|:------------:|:------------:|
| **Accepted?** | $\checkmark$  |              |              |              |


### Parameters

This endpoint does not take any parameters.

### Responses

Endpoint may only return `200` to confirm the request was successful.

Response is in a JSON format. Example request and response is:

**Request**:

`GET /gpg/list`

**Response**:

```json
[
  {
    "user": "John Doe",
    "email": "john.doe@example.com",
    "key": "-----BEGIN PGP PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
  },
  {
    "user": "Jane Doe",
    "email": "jane.doe@example.com",
    "key": "-----BEGIN PGP PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
  }
]
```


## `/gpg/by-email`

Shows a list of all the stored public GPG keys belonging to a specific email address.

### Accepted methods


|               |      GET      |     POST     |     PUT      |    DELETE    |
|:--------------|:-------------:|:------------:|:------------:|:------------:|
| **Accepted?** | $\checkmark$  |              |              |              |


### Parameters

| Parameter | Optional?    | Description                                 |
|-----------|--------------|---------------------------------------------|
| `email`   |              | Only shows GPG keys belonging to this email |

### Responses

- `200`: Request was successful
- `400`: Invalid email (malformed email)

Response is in a JSON format. Example request and response:

**Request**:

```GET /gpg/by-email/?email=john.doe@example.com```

**Response**:

```json
[
  {
    "user": "John Doe",
    "email": "john.doe@example.com",
    "key": "-----BEGIN PGP PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
  },
  {
    "user": "John Doe",
    "email": "john.doe@example.com",
    "key": "-----BEGIN PGP PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
  }
]
```

If the provided email address is valid, but has no GPG keys attributed to it, an empty JSON list is returned:

**Request**:

```GET /gpg/by-email/?email=unknown.user@example.com```

**Response**:

```json
[]
```


## `/gpg/manage`

Allows clients to post or delete GPG keys for a specific user.

### Accepted methods


|               |      GET      |     POST     |     PUT      |    DELETE    |
|:--------------|:-------------:|:------------:|:------------:|:------------:|
| **Accepted?** |               | $\checkmark$ |              | $\checkmark$ |


### Parameters

Parameters are to be sent as `application/json` data, in the form of a dictionary.

| Parameter | Optional?    | Description                                            |
|-----------|--------------|--------------------------------------------------------|
| `email`   |              | Email to attach GPG key, or to which it is attached to |
| `key`     |              | GPG key to post or delete                              |

### Responses

For the `POST` method:

- `200`: Request was successful
- `400`: Invalid email (malformed email) or GPG key (malformed key)
- `409`: GPG key has already been attributed to an email address

For the `DELETE` method:

- `200`: Request was successful
- `404`: Key does not exist, or is not attributed to the provided email

For both `POST` and `DELETE` methods, response is an empty JSON list in case no error has occurred, or a JSON containing the error message.

### Valid key post

**Request**:

```
POST /gpg/manage
Content-Type: application/json
[...]
Content-Length: 204
 
{
"email": "john.doe@example.com",
"key": "-----BEGIN PGP PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
}
```

**Response**:

`[]`

### Invalid email on POST

**Request**:

```
POST /gpg/manage
Content-Type: application/json
[...]
Content-Length: 292
 
{
"email": "john.doe@",
"key": "-----BEGIN PGP PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
}
```

**Response**:

```
{
    "error": "Invalid email address"
}
```

### Invalid key on POST

**Request**:

```
POST /gpg/manage
Content-Type: application/json
[...]
Content-Length: 200
 
{
"email": "john.doe@example.com",
"key": "-----BEGIN PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
}
```

**Response**:

```
{
    "error": "Invalid GPG key"
}
```

### Valid GPG key deletion

**Request**:

```
DELETE /gpg/manage
Content-Type: application/json
[...]
Content-Length: 200
 
{
"email": "john.doe@example.com",
"key": "-----BEGIN PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
}
```

**Response**:

`[]`

### Invalid email - key pair to be deleted

**Request**:

```
DELETE /gpg/manage
Content-Type: application/json
[...]
Content-Length: 204
 
{
"email": "john.doe@example.com",
"key": "-----BEGIN GPG PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
}
```

**Response**:

```
{
    "error": "GPG key not attributed to provided email"
}
```

# Users
