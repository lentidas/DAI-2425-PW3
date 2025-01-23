## `PUT /users/{username}`

Allows updating the `firstName` or `lastName` attributes of specific user.

The username is not allowed to be changed and if required, instead a new user should be created,
followed by moving the emails from the old user to the new one, then deleting the old user.

### Parameters

| Parameter   | Optional? | Description                        |
|-------------|-----------|------------------------------------|
| `username`  |           | Username of the user to be edited. |

### Request

The request body must contain a JSON object with the following fields:

- `firstName`: user's first name
- `lastName`: user's last name

### Responses

The server returns the following status codes:

- `200`: user was edited
- `400`: username is malformed
- `404`: user does not exist

Only a status code with an empty body is returned.

### Example with valid data

**Request**:

```
PUT /users/john.doe
[...]

{
    "firstName": "John",
    "lastName": "DoeNew"
}
```

**Response**:

`200 OK`

```json
{
    "username": "john.doe",
    "firstName": "John",
    "lastName": "DoeNew"
}
```

### Example with malformed username

```
PUT /users/malf:or$med
[...]

{
    "firstName": "John",
    "lastName": "Doe"
}
```

**Response**:

```
400 Bad Request
```

### Example with user that does not exist

```
PUT /users/unknown.user
[...]

{
    "firstName": "John",
    "lastName": "Doe"
}
```

**Response**:

```
404 Not Found
```
