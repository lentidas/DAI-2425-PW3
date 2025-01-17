## `POST /user/by-username/{username}`

Creates a user so emails can be attached to it.

### Parameters

| Parameter   | Optional?    | Description                       |
|-------------|--------------|-----------------------------------|
| `username`  |              | Username to be used for this user |
| `firstname` |              | User's first name                 |
| `lastname`  |              | User's last name                  |

### Responses

The server returns the following status codes:

- `201`: User created successfully
- `400`: Username is malformed
- `409`: Username has already been taken

It always returns an empty JSON list: `[]`

### Example with valid data

**Request**:

```
POST /user/by-username/john.doe
[...]

{
    "firstname": "John",
    "lastname": "Doe"
}
```

**Response**:

```
200 OK

[]
```


### Example with malformed username

```
POST /user/by-username/malf:or$med
[...]

{
    "firstname": "John",
    "lastname": "Doe"
}
```

**Response**:

```
400 BAD

[]
```

### Example with username that has already been taken (attempt to recreate)
```
POST /user/by-username/jane.doe
[...]

{
    "firstname": "John",
    "lastname": "Doe"
}
```

**Response**:

```
409 CONFLICT

[]
```