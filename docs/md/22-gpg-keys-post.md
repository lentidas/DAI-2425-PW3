
## `POST /gpg-keys`

Adds a new key to the database.

### Parameters

No HTTP path or query parameters are needed.

### Request

The request body must contain a JSON object with the following fields:

- `fingerprint`: the fingerprint of the key in hexadecimal format
- `key`: the key itself in ASCII-armored format

### Responses

- `201`: request was successful and the key was created
- `400`: either the fingerprint or the key is invalid
- `409`: the fingerprint is already present in the database

If the creation is successful, a JSON object with the same fields as the request is returned.
Otherwise, an appropriate HTTP status code is returned.

### Example with valid key

**Request**:

```
POST /gpg-keys
[...]

{
    "fingerprint": "AABBCCDDEEFFGGHHIIJJKKLLMMNNOOPPQQRRSSTT",
    "key": "-----BEGIN PGP PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
}
```

**Response**:

`201 Created`

```
{
    "fingerprint": "AABBCCDDEEFFGGHHIIJJKKLLMMNNOOPPQQRRSSTT",
    "key": "-----BEGIN PG PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
}
```

### Example with invalid key

**Request**:

```
POST /gpg-keys
[...]

{
    "fingerprint": "AABBCCDDEEFFGGHHIIJJKKLLMMNNOOPPQQRRSSTT",
    "key": "toto--titi-tata"
}
```

**Response**:

```
400 Bad Request
```

### Example with key that's already present

**Request**:

```
POST /gpg-keys
[...]

{
    "fingerprint": "AABBCCDDEEFFGGHHIIJJKKLLMMNNOOPPQQRRSSTT",
    "key": "-----BEGIN PG PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
}
```

**Response**:

```
409 Conflict
```
