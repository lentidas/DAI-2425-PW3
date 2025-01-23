## `GET /user/{username}`

Allows a client to obtain information about the provided user.

### Parameters

| Parameter  | Optional?    | Description                             |
|------------|--------------|-----------------------------------------|
| `username` |              | Shows information related to this user. |

### Responses

The server returns the following status codes:

- `200`: user was found
- `404`: user does not exist

When successful, it returns a JSON object containing the user's information:

```json
{
    "username": "john.doe",
    "firstName": "John",
    "lastName": "Doe"
}
```

### Example with valid username

**Request**:

```
GET /user/john.doe
```

**Response**:

`200 OK`

```json
{
  "username": "john.doe",
  "firstName": "John",
  "lastName": "Doe"
}
```


### Example with username that doesn't exist

```
GET /user/by-username/unknown.user
```

**Response**:

`404 Not Found`
