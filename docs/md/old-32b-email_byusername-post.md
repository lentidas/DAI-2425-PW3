## `POST /email/by-username/{username}`

Associates an email address to a user

### Parameters

| Parameter  | Optional?    | Description                       |
|------------|--------------|-----------------------------------|
| `username` |              | Username to associate an email to |
| `email`    |              | Email to associate to user        |

### Responses

- `200`: Request was successful
- `400`: Invalid username (malformed username)
- `404`: Unknown username
- `410`: Email address has already been associated to a user

Response is in the form of a JSON dictionary. The dictionary is empty on successful requests. It contains the error message in case of an unsuccessful request:

```json
{
  "error": "Error message"
}
```


### Example with a valid username and email

**Request**:

```
POST /email/by-username/john.doe
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

```
POST /email/by-username/malf:or$med
[...]

email:john.doe@example.com
```

**Response**:

```
400 BAD

{
    "error": "Invalid username"
}
```

### Example with an invalid email address

**Request**:

```
POST /email/by-username/john.doe
[...]

email:john.doe@
```

**Response**:

```
400 BAD

{
    "error": "Invalid email address"
}
```

### Example with an unknown user

**Request**:

```
POST /email/by-username/unknown.user
[...]

email:john.doe@example.com
```

**Response**:

```
404 NOT FOUND

{
    "error": "Invalid username"
}
```

### Example with an email that's already been attached to another user

**Request**:

```
POST /email/by-username/john.doe
[...]

email:jane.doe@example.com
```

**Response**:

```
410 CONFLICT

{
    "error": "Email address has already been attached to another user"
}
```