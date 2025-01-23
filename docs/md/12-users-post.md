## `POST /users`

Creates a new user in the server.

### Parameters

No HTTP path or query parameters are needed.

### Request

The request body must contain a JSON object with the following fields:

- `username`: username to be used for this user
- `firstName`: user's first name
- `lastName`: user's last name

### Responses

The server returns the following status codes:

- `201`: user created successfully
- `400`: username is malformed (must contain only alphanumeric characters, dots, and underscores)
- `409`: username has already been taken

Only a status code with an empty body is returned.

### Example with valid data

**Request**:

```
POST /users
[...]

{
    "username": "john.doe",
    "firstName": "John",
    "lastName": "Doe"
}
```

**Response**:

```
201 Created
```

### Example with malformed username

```
POST /users
[...]

{
    "username": "j$hn:doe",
    "firstName": "John",
    "lastName": "Doe"
}
```

**Response**:

```
400 Bad Request
```

### Example with username that has already been taken (attempt to recreate)

```
POST /users
[...]

{
    "username": "john.doe",
    "firstName": "John",
    "lastName": "Doe"
}
```

**Response**:

```
409 Conflict
```