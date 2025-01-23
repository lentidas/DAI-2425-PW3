
## `POST /gpg/by-email`

Creates a key - email pair.

### Parameters

| Parameter | Optional? | Description                    |
|-----------|-----------|--------------------------------|
| `email`   |           | The email to attach the key to |
| `key`     |           | Key to attach to the email     |

### Responses

- `201`: Request was successful, and pair was created
- `400`: Invalid email (malformed email) or GPG key (malformed key)
- `410`: GPG key cannot be attached to different users

Response is in the form of a JSON dictionary. The dictionary is empty on successful requests. It contains the error message in case of an unsuccessful request:

```json
{
  "error": "Error message"
}
```

### Example with valid key

**Request**:

```
POST /gpg/by-email
[...]

{
    "email": "john.doe@example.com",
    "key": "-----BEGIN PGP PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
}
```

**Response**:

`201 CREATED`

`{}`

### Example with invalid email

**Request**:

```
POST /gpg/by-email
[...]

{
    "email": "john.doe@",
    "key": "-----BEGIN PGP PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
}
```

**Response**:

`400 BAD`

```
{
    "error": "Invalid email address"
}
```

### Example with invalid key

**Request**:

```
POST /gpg/by-email
[...]

{
    "email": "john.doe@example.com",
    "key": "-----BEGIN PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
}
```

**Response**:

`400 BAD`

```
{
    "error": "Invalid GPG key"
}
```

### Example with key that's already been attached to another user

**Request**:

```
POST /gpg/by-email
[...]

{
    "email": "jane.doe@example.com",
    "key": "-----BEGIN PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
}
```

**Response**:

`410 CONFLICT`

```
{
    "error": "Key has already been attahced to another user"
}
```