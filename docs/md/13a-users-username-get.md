## `GET /users/{username}`

Allows a client to obtain information about the provided user.

### Parameters

| Parameter  | Optional?    | Description                             |
|------------|--------------|-----------------------------------------|
| `username` |              | Shows information related to this user. |

### Responses

The server returns the following status codes:

- `200`: user was found
- `400`: username is malformed
- `404`: user does not exist

When successful, it returns a JSON object containing the user's information:

```json
{
    "username": "john.doe",
    "firstName": "John",
    "lastName": "Doe"
}
```

### Example with valid username

**Request**:

```
GET /user/john.doe
```

**Response**:

`200 OK`

```json
{
  "username": "john.doe",
  "firstName": "John",
  "lastName": "Doe"
}
```

### Example with malformed username

```
GET /users/malf:or$med
```

**Response**:

```
400 Bad Request
```

### Example with username that doesn't exist

```
GET /users/unknown.user
```

**Response**:

```
404 Not Found
```
