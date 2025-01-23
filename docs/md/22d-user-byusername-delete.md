## `DELETE /user/by-username/{username}`

Deletes a specific user.

Deleting a user also deletes all emails associated to this email, as well as any key - email pairs that were exclusively used attributed to this user's email addresses.

### Parameters

| Parameter   | Optional?    | Description            |
|-------------|--------------|------------------------|
| `username`  |              | Username to be deleted |

### Responses

The server returns the following status codes:

- `200`: User was deleted
- `400`: Username is malformed
- `404`: User does not exist

Always returns an empty JSON list: `[]`

### Example with valid data

**Request**:

```
DELETE /user/by-username/john.doe
```

**Response**:

```
200 OK

[]
```


### Example with malformed username

```
DELETE /user/by-username/malf:or$med
```

**Response**:

```
400 BAD

[]
```

### Example with user that does not exist
```
DELETE /user/by-username/unknown.user
```

**Response**:

```
404 NOT FOUND

[]
```