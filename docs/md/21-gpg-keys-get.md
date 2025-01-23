## `GET /gpg-keys`

Shows a list of all stored public GPG keys, along with the respective fingerprint.

### Parameters

No HTTP path or query parameters are needed.

### Responses

Endpoint may only return `200` to confirm the request was successful.

Response is in a JSON format. Example request and response is:

**Request**:

`GET /gpg-keys`

**Response**:

`200 OK`

```json
[
  {
    "fingerprint": "AABBCCDDEEFFGGHHIIJJKKLLMMNNOOPPQQRRSSTT",
    "key": "-----BEGIN PGP PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
  },
  {
    "fingerprint": "UUVVWWXXYYZZ00112233445566778899AABBCCDD",
    "key": "-----BEGIN PGP PUBLIC KEY BLOCK-----\n\n[...]\n-----END PGP PUBLIC KEY BLOCK-----"
  }
]
```
