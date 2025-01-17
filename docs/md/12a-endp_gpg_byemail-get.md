
## `GET /gpg/by-email`

Shows a list of all the stored public GPG keys belonging to a specific email address.

### Parameters

| Parameter | Optional?    | Description                                                                             |
|-----------|--------------|-----------------------------------------------------------------------------------------|
| `email`   |              | Only shows GPG keys belonging to this email when retrieving the keys associated with it |

### Responses

- `200`: Request was successful
- `400`: Invalid email (malformed email)

Response is in a JSON format.

### Example with a valid email

**Request**:

```GET /gpg/by-email?email=john.doe@example.com```

**Response**:

`200 OK`

```json
[
  {
    "username": "john.doe",
    "user": "John Doe",
    "email": "john.doe@example.com",
    "key": "-----BEGIN PGP PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
  },
  {
    "username": "john.doe",
    "user": "John Doe",
    "email": "john.doe@example.com",
    "key": "-----BEGIN PGP PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
  }
]
```

### Example with email that has no keys attached to it

If the provided email address is valid, but has no GPG keys attributed to it, an empty JSON list is returned:

**Request**:

```GET /gpg/by-email/?email=unknown.user@example.com```

**Response**:

`200 OK`

```json
[]
```

### Example with malformed email


**Request**:

```GET /gpg/by-email/?email=unknown.user@```

**Response**:

`400 BAD`

```json
[]
```