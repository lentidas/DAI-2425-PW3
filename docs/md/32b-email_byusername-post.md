## `POST /emails/{username}`

Associates an email address to a user.

### Parameters

| Parameter  | Optional?    | Description                     |
|------------|--------------|---------------------------------|
| `username` |              | Username to associate email to. |

### Request

The request body must contain a JSON object with the following fields:

- `email`: user's email address

### Responses

- `201`: association created
- `400`: invalid username (malformed username)
- `404`: unknown username
- `409`: email address has already been associated to a user

Only a status code with an empty body is returned.

### Example with a valid username and email

**Request**:

```
POST /emails/john.doe
[...]

{
  "email": "john.doe@example.com"
}
```

**Response**:

```
200 OK
```

### Example with an invalid username

**Request**:

```
POST /emails/malf:or$med
[...]

{
  "email": "john.doe@example.com"
}
```

**Response**:

```
400 Bad Request
```

### Example with an invalid email address

**Request**:

```
POST /emails/john.doe
[...]

{
  "email": "john.doe@"
}
```

**Response**:

```
400 Bad Request
```

### Example with an unknown user

**Request**:

```
POST /emails/unknown.user
[...]

{
  "email": "john.doe@example.com"
}
```

**Response**:

```
404 Not Found
```

### Example with an email that's already been attached to another user

**Request**:

```
POST /emails/john.doe
[...]

{
  "email": "john.doe@example.com"
}
```

**Response**:

```
409 Conflict
```
