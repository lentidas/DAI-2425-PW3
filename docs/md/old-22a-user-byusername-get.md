## `GET /user/by-username/{username}`

Allows a client to obtain information about the provided user.

### Parameters

| Parameter  | Optional?    | Description                            |
|------------|--------------|----------------------------------------|
| `username` |              | Shows information related to this user |

### Responses

The server returns the following status codes:

- `200`: User was found
- `400`: Username is malformed
- `404`: User does not exist

When successful, it returns a JSON dictionary containing the user's information:

```json
{
    "username": "john.doe",
    "name": "John Doe"
}
```

### Example with valid username

**Request**:

```
GET /user/by-username/john.doe
```

**Response**:

`200 OK`

```json
{
    "username": "john.doe",
    "name": "John Doe"
}
```


### Example with username that doesn't exist

```
GET /user/by-username/unknown.user
```

**Response**:

`404 NOT FOUND`

```json
{}
```

### Example with malformed email
```
GET /user/by-username/malf:or$med
```

**Response**:

`400 BAD`

```json
{}
```