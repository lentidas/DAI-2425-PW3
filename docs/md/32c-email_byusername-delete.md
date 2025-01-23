## `DELETE /emails/{username}`

Detaches an email address from a user. This also removes any key - email pairs for keys that are no longer attached to any email address.

### Parameters

| Parameter  | Optional?    | Description                       |
|------------|--------------|-----------------------------------|
| `username` |              | Username to detach an email from. |

### Request

The request body must contain a JSON object with the following fields:

- `email`: user's email address to be deleted

### Responses

- `204`: Email has been detached from user
- `400`: Invalid username (malformed username)
- `404`: Unknown username or email address is not attached to user

Only a status code with an empty body is returned.

### Example with a valid username and email

**Request**:

```
DELETE /emails/john.doe
[...]

{
  "email": "john.doe@example.com"
}
```

**Response**:

`204 NO CONTENT`

### Example with an invalid username

**Request**:

```
DELETE /emails/malf:or$med
[...]

{
  "email": "john.doe@example.com"
}
```

**Response**:

`400 BAD`

```
Bad Request
```

### Example with an invalid email address

**Request**:

```
DELETE /emails/john.doe
[...]

{
  "email": "john.doe@"
}
```

**Response**:

`400 BAD`

```
Bad Request
```

### Example with an unknown user

**Request**:

```
DELETE /emails/unknown.user
[...]

{
  "email": "john.doe@example.com"
}
```

**Response**:

`404 NOT FOUND`

```
Not Found
```

### Example with an email that's not attached to the user

**Request**:

```
DELETE /emails/john.doe
[...]

{
  "email": "jane.doe@example.com"
}
```

**Response**:

`404 NOT FOUND`

```
Bad Request
```
