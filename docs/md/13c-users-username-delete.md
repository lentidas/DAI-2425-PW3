## `DELETE /users/{username}`

Deletes a specific user.

Deleting a user also deletes all emails associated to this email, as well as any key - email pairs
that were exclusively used attributed to this user's email addresses.

### Parameters

| Parameter  | Optional? | Description                         |
|------------|-----------|-------------------------------------|
| `username` |           | Username of the user to be deleted. |

### Responses

The server returns the following status codes:

- `200`: user was deleted
- `400`: username is malformed
- `404`: user does not exist

Always returns an empty JSON list: `[]`

### Example with valid username

**Request**:

```
DELETE /users/john.doe
```

**Response**:

`200 OK`

```json
{
    "username": "john.doe",
    "firstname": "John",
    "lastname": "Doe"
}
```

### Example with malformed username

```
DELETE /users/malf:or$med
```

**Response**:

```
400 Bad Request
```

### Example with user that does not exist

```
DELETE /users/unknown.user
```

**Response**:

```
404 Not Found
```