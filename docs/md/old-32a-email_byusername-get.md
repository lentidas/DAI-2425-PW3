## `GET /email/by-username/{username}`

Shows a list of all the emails addresses associated with a specific user

### Parameters

| Parameter  | Optional?    | Description                                            |
|------------|--------------|--------------------------------------------------------|
| `username` |              | Username to use to retrieve all emails associated with |

### Responses

- `200`: Request was successful
- `400`: Invalid username (malformed username)
- `404`: Unknown username

Response, if an error occurred, is an empty JSON list: `[]`. If successful, response if in a JSON list format like the one below:

```json
[
  {
    "email": "john.doe@example.com",
    "username": "john.doe"
  },
  {
    "email": "john.doe@home-email.com",
    "username": "john.doe"
  }
]
```


### Example with a valid username

**Request**:

`GET /email/by-username/john.doe`

**Response**:

`200 OK`

```json
[
  {
    "email": "john.doe@example.com",
    "username": "john.doe"
  },
  {
    "email": "john.doe@home-email.com",
    "username": "john.doe"
  }
]
```

### Example with a username that has no emails attached to it

**Request**:

`GET /email/by-username/empty.user`

**Response**:

`200 OK`

```json
[]
```

### Example with malformed username

**Request**:

`GET /email/by-username/malf:or$med`

**Response**:

`400 BAD`

```json
[]
```


### Example with an unknown user specified

**Request**:

`GET /email/by-username/unknown.user`

**Response**:

`404 NOT FOUND`

```json
[]
```
