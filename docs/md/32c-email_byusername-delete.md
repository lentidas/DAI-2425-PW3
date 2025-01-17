## `DELETE /email/by-username/{username}`

Detaches an email address to a user. This also removes any key - email pairs for keys that are no longer attached to any email address.

### Parameters

| Parameter  | Optional?    | Description                      |
|------------|--------------|----------------------------------|
| `username` |              | Username to detach an email from |
| `email`    |              | Email to detach from user        |

### Responses

- `200`: Request was successful
- `400`: Invalid username (malformed username)
- `404`: Unknown username or email address is not attached to user

Response is in the form of a JSON dictionary. The dictionary is empty on successful requests. It contains the error message in case of an unsuccessful request:

```json
{
  "error": "Error message"
}
```


### Example with a valid username and email

**Request**:

```DELETE /email/by-username/john.doe
[...]

email:john.doe@example.com
```

**Response**:

```
200 OK

{}
```

### Example with an invalid username

**Request**:

`DELETE /email/by-username/malf:or$med
[...]

email:john.doe@example.com
`

**Response**:

```
400 BAD

{
    "error": "Invalid username"
}
```

### Example with an invalid email address

**Request**:

`DELETE /email/by-username/john.doe
[...]

email:john.doe@
`

**Response**:

```
400 BAD

{
    "error": "Invalid email address"
}
```

### Example with an unknown user

**Request**:

`DELETE /email/by-username/unknown.user
[...]

email:john.doe@example.com
`

**Response**:

```
404 NOT FOUND

{
    "error": "Invalid username"
}
```

### Example with an email that's not attached to the user

**Request**:

`DELETE /email/by-username/john.doe
[...]

email:jane.doe@example.com
`

**Response**:

```
404 NOT FOUND

{
    "error": "Email is not attached to user"
}
```
