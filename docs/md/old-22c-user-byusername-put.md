## `PUT /user/by-username/{username}`

Updates data about a specific user

### Parameters

| Parameter   | Optional?    | Description                       |
|-------------|--------------|-----------------------------------|
| `username`  |              | Username to be used for this user |
| `firstname` |              | Updated user's first name         |
| `lastname`  |              | Updated user's last name          |

### Responses

The server returns the following status codes:

- `200`: User was edited
- `400`: Username is malformed
- `404`: User does not exist

Always returns an empty JSON list: `[]`

### Example with valid data

**Request**:

```
PUT /user/by-username/john.doe
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
PUT /user/by-username/malf:or$med
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

### Example with user that does not exist
```
PUT /user/by-username/unknown.user
[...]

{
    "firstname": "John",
    "lastname": "Doe"
}
```

**Response**:

```
404 NOT FOUND

[]
```