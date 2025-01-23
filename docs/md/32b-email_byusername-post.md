## `POST /emails/{username}`

Associates an email address to a user

### Parameters

| Parameter  | Optional?    | Description                       |
|------------|--------------|-----------------------------------|
| `username` |              | Username to associate email to    |
| `email`    |              | Email to associate to user        |

### Responses

- `201`: Association created
- `400`: Invalid username (malformed username)
- `404`: Unknown username
- `410`: Email address has already been associated to a user

No response is returned when successful. When an error occurs, HTTP status code message is returned.


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

`
200 OK
`

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

`
400 BAD
`

```
Bad Request
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

`
400 BAD
`

```
Bad Request
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

`
404 NOT FOUND
`

```
Not Found
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

`
410 CONFLICT
`

```
Conflict
```
