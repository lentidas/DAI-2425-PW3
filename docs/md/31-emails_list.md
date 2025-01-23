## `GET /emails`

Allows a client to obtain the list of all or part of all known emails

### Parameters

Endpoint does not take any parameters.

### Responses

Server always replies with `200` to indicate the list retrieved successfully, even if no emails are known.

Response if in a JSON list format like the one below:

```json
[
  {
    "email": "john.doe@example.com",
    "username": "john.doe"
  },
  {
    "email": "john.doe@home-email.com",
    "username": "john.doe"
  },
  {
    "email": "jane.doe@example.com",
    "username": "jane.doe"
  }
]
```

### Example usage

Retrieves all emails

**Request**:

`GET /emails`

**Response**:

`200 OK`

```json
[
  {
    "email": "john.doe@example.com",
    "username": "john.doe"
  },
  {
    "email": "john.doe@home-email.com",
    "username": "john.doe"
  },
  {
    "email": "jane.doe@example.com",
    "username": "jane.doe"
  }
]
```
