## `POST /users`

Creates a new user in the server.

### Parameters

No HTTP path or query parameters are needed.

### Request

The request body must contain a JSON object with the following fields:

- `username`: username to be used for this user
- `firstname`: user's first name
- `lastname`: user's last name

### Responses

The server returns the following status codes:

- `201`: user created successfully
- `400`: username is malformed (must contain only alphanumeric characters, dots, and underscores)
- `409`: username has already been taken

If the creation is successful, a JSON object with the same fields as the request is returned.
Otherwise, an appropriate HTTP status code is returned.

### Example with valid data

**Request**:

```
POST /users
[...]

{
    "username": "john.doe",
    "firstname": "John",
    "lastname": "Doe"
}
```

**Response**:

```
201 Created
    
{
    "username": "john.doe",
    "firstname": "John",
    "lastname": "Doe"
}
```

### Example with malformed username

```
POST /users
[...]

{
    "username": "j$hn:doe",
    "firstname": "John",
    "lastname": "Doe"
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