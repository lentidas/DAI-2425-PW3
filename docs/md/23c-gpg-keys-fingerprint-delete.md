## `DELETE /gpg-keys/{fingerprint}`

Deletes a key from the server.

### Parameters

| Parameter     | Optional? | Description                           |
|---------------|-----------|---------------------------------------|
| `fingerprint` |           | Fingerprint of the GPG key to delete. |

### Responses

- `200`: key was deleted
- `400`: fingerprint is invalid
- `404`: key does not exist

### Example with valid GPG key

**Request**:

```
DELETE /gpg-keys/AABBCCDDEEFFGGHHIIJJKKLLMMNNOOPPQQRRSSTT
```

**Response**:

`200 OK`

```json
{
  "fingerprint": "AABBCCDDEEFFGGHHIIJJKKLLMMNNOOPPQQRRSSTT",
  "key": "-----BEGIN PGPPUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
}
```

### Example with invalid fingerprint

**Request**:

```
DELETE /gpg-keys/invalid-fingerprint
```

**Response**:

```
400 Bad Request
```

### Example with key that doesn't exist

**Request**:

```
DELETE /gpg-keys/unknown-fingerprint
```

**Response**:

```
404 Not Found
```
