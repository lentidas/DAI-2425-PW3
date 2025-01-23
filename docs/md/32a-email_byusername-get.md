## `GET /emails/{username}`

Shows a list of all the emails addresses associated with a specific user.

### Parameters

| Parameter  | Optional?    | Description                                             |
|------------|--------------|---------------------------------------------------------|
| `username` |              | Username to use to retrieve all emails associated with. |

### Responses

- `200`: request was successful
- `400`: invalid username (malformed username)
- `404`: unknown username

Only a status code with an empty body is returned.

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

`GET /emails/john.doe`

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

`GET /emails/empty.user`

**Response**:

`200 OK`

```json
[]
```

### Example with malformed username

**Request**:

`GET /emails/malf:or$med`

**Response**:

`400 BAD`

```
Bad Request
```


### Example with an unknown user specified

**Request**:

`GET /emails/unknown.user`

**Response**:

`404 NOT FOUND`

```
Not Found
```
