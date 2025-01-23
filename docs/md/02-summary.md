# Endpoints summary

This table summarizes the accepted HTTP request methods by endpoint.

| Endpoint URL              |     GET      |     POST     |     PUT      |    DELETE    |
|:--------------------------|:------------:|:------------:|:------------:|:------------:|
| `/users`                  | $\checkmark$ | $\checkmark$ |              |              |
| `/users/{username}`       | $\checkmark$ |              | $\checkmark$ | $\checkmark$ |              
| `/gpg-keys`               | $\checkmark$ | $\checkmark$ |              |              |
| `/gpg-keys/{fingerprint}` | $\checkmark$ |              | $\checkmark$ | $\checkmark$ |
| `/emails`                 | $\checkmark$ |              |              |              |
| `/emails/{username}`      | $\checkmark$ | $\checkmark$ |              | $\checkmark$ |
