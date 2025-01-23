
## `DELETE /gpg/by-email`

Deletes a key - email pair from the server.

### Parameters

| Parameter | Optional? | Description                |
|-----------|-----------|----------------------------|
| `email`   |           | Email to detach a key from |
| `key`     |           | Key to detach              |

### Responses

- `200`: Request was successful
- `404`: Key does not exist, or is not attributed to the provided email

### Example with valid GPG key

**Request**:

```
DELETE /gpg/by-email
[...]

{
"email": "john.doe@example.com",
"key": "-----BEGIN PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
}
```

**Response**:

`200 OK`

`[]`

### Example with invalid email - key pair

**Request**:

```
DELETE /gpg/by-email
[...]

{
"email": "john.doe@example.com",
"key": "-----BEGIN GPG PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
}
```

**Response**:

`400 BAD`

```
{
    "error": "GPG key not attributed to provided email"
}
```
