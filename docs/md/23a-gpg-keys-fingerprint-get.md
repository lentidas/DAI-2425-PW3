## `GET /gpg-keys/{fingerprint}`

Allows a client to obtain the private key associated with the provided fingerprint.

### Parameters

| Parameter     | Optional?    | Description                                           |
|---------------|--------------|-------------------------------------------------------|
| `fingerprint` |              | Fingerprint of the GPG key the client want to obtain. |

### Responses

- `200`: request was successful
- `400`: fingerprint is invalid
- `404`: fingerprint does not exist

When successful, it returns a JSON object containing the key's information:

```json
{
    "fingerprint": "AABBCCDDEEFFGGHHIIJJKKLLMMNNOOPPQQRRSSTT",
    "key": "-----BEGIN PGP PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
}
```

### Example with a valid fingerprint

**Request**:

```
GET /gpg-keys/AABBCCDDEEFFGGHHIIJJKKLLMMNNOOPPQQRRSSTT
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

**Request**:

```
GET /gpg-keys/invalid-fingerprint
```

**Response**:

```
400 Bad Request
```

### Example with fingerprint that doesn't exist

**Request**:

```
GET /gpg-keys/unknown-fingerprint
```

**Response**:

```
404 Not Found
```
