## `PUT /users/{username}`

Allows updating the `firstName` or `lastName` attributes of specific user.

The username is not allowed to be changed and if required, instead a new user should be created,
followed by moving the emails from the old user to the new one, then deleting the old user.

### Parameters

| Parameter   | Optional? | Description                       |
|-------------|-----------|-----------------------------------|
| `username`  |           | Username to be used for this user |

### Request

The request body must contain a JSON object with the following fields:

- `firstname`: user's first name
- `lastname`: user's last name

### Responses

The server returns the following status codes:

- `200`: user was edited
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

```
200 OK

{
    "username": "john.doe",
    "firstName": "John",
    "lastName": "DoeNew"
}
```

### Example with malformed username

```
PUT /usersmalf:or$med
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
