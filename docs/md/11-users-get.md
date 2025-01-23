## `GET /users`

Allows a client to obtain the list of users that are known by the server.

### Parameters

| Parameter   | Optional?    | Description                                    |
|-------------|--------------|------------------------------------------------|
| `firstName` | $\checkmark$ | Filters the list of users by their first name. |
| `lastName`  | $\checkmark$ | Filters the list of users by their last name.  |

The search is case-insensitive and partial matches are allowed. If both `firstName` and `lastName`
are provided, the server will return only the users that match both criteria.

### Responses

The server will always return `200`. It returns the list of users with their username and names in a
JSON list format. An empty list is returned if no users are found.

### Example usage

**Request**:

`GET /users`

**Response**:

`200 OK`

```json
[
  {
    "username": "john.doe",
    "firstName": "John",
    "lastName": "Doe"
  },
  {
    "username": "jane.doe",
    "firstName": "Jane",
    "lastName": "Doe"
  }
]
```

**Request**:

`GET /users?firstName=John`

**Response**:

`200 OK`

```json
[
  {
    "username": "john.doe",
    "firstName": "John",
    "lastName": "Doe"
  }
]
```