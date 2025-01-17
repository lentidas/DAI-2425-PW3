## `GET /users`

Allows a client to obtain the list of users that are known by the user.

### Parameters

This endpoint does not take any parameters

### Responses

The server will always return `200`. It returns the list of users with their username and full name in a JSON list format.

### Example usage

**Request**:

`GET /users`

**Response**:

`200 OK`

```json
[
  {
    "username": "john.doe",
    "name": "John Doe"
  },
  {
    "username": "jane.doe",
    "name": "Jane Doe"
  }
]
```