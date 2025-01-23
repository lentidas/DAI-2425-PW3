# Endpoints summary

This table summarizes the accepted HTTP request methods by endpoint.

| Endpoint URL         |     GET      |     POST     |     PUT      |    DELETE    |
|:---------------------|:------------:|:------------:|:------------:|:------------:|
| `/users`             | $\checkmark$ |              |              |              |
| `/users/{username}`  | $\checkmark$ | $\checkmark$ | $\checkmark$ | $\checkmark$ |              
|                      |              |              |              |              |
| `/gpg/list`          | $\checkmark$ |              |              |              |
| `/gpg/by-email`      | $\checkmark$ | $\checkmark$ |              | $\checkmark$ |
| `/gpg/by-username`   | $\checkmark$ |              |              |              |
| `/emails`            | $\checkmark$ |              |              |              |
| `/email/by-username` | $\checkmark$ | $\checkmark$ |              | $\checkmark$ |
