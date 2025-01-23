## `GET /gpg/list`

Shows a list of all stored public GPG keys, along with the user and email they belong to.

### Parameters

This endpoint does not take any parameters.

### Responses

Endpoint may only return `200` to confirm the request was successful.

Response is in a JSON format. Example request and response is:

**Request**:

`GET /gpg/list`

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
    "username": "jane.doe",
    "user": "Jane Doe",
    "email": "jane.doe@example.com",
    "key": "-----BEGIN PGP PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
  }
]
```
