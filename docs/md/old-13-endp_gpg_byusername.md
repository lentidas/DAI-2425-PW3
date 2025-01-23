
## `GET /gpg/by-username/{username}`

Shows a list of all the stored public GPG keys belonging to a specific email address.

### Parameters

| Parameter  | Optional?    | Description                                |
|------------|--------------|--------------------------------------------|
| `username` |              | Only shows GPG keys belonging to this user |

### Responses

- `200`: Request was successful
- `400`: Invalid username (malformed username)

Response is in a JSON format.

### Example with valid user

**Request**:

```GET /gpg/by-username/john.doe```

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
    "email": "john.doe@home-email.com",
    "key": "-----BEGIN PGP PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
  }
]
```

### Example with valid user, no emails

If the provided username is valid, but user has no emails, an empty JSON list is returned:

**Request**:

```GET /gpg/by-username/unknown.user```

**Response**:

`200 OK`

```json
[]
```

### Example with invalid username

As described, if the username is malformed, a specific HTTP status code is returned. Furthermore, and empty JSON list returned:

**Request**:

```GET /gpg/by-username/malf:or$med```

**Response**:

`400 BAD`

```json
[]
```