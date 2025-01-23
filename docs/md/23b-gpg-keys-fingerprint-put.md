## `PUT /gpg-keys/{fingerprint}`

Allows updating the `key` attributes of a specific key.

### Parameters

| Parameter     | Optional? | Description                                           |
|---------------|-----------|-------------------------------------------------------|
| `fingerprint` |           | Fingerprint of the GPG key the client want to obtain. |

### Request

The request body must contain a JSON object with the following fields:

- `key`: the new key

### Responses

The server returns the following status codes:

- `200`: key was edited
- `400`: fingerprint is malformed
- `404`: key does not exist

When successful, it returns a JSON object containing the user's information:

```json
{
  "fingerprint": "AABBCCDDEEFFGGHHIIJJKKLLMMNNOOPPQQRRSSTT",
  "key": "-----BEGIN PGP PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
}
```

### Example with valid data

**Request**:

```
PUT /gpg-keys/AABBCCDDEEFFGGHHIIJJKKLLMMNNOOPPQQRRSSTT
[...]

{
    "key": "-----BEGIN PGP PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
}
```

**Response**:

`200 OK`

```json
{
  "fingerprint": "AABBCCDDEEFFGGHHIIJJKKLLMMNNOOPPQQRRSSTT",
  "key": "-----BEGIN PGP PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
}
```

### Example with invalid fingerprint

```
PUT /fingerprint/invalid-fingerprint
[...]

{
    "key": "-----BEGIN PGP PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
}
```

**Response**:

```
400 Bad Request
```

### Example with key that doesn't exist

```
PUT /gpg-keys/unknown-fingerprint
[...]

{
    "key": "-----BEGIN PGP PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
}
```

**Response**:

```
404 Not Found
```
