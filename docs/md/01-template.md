# Endpoint template

This section contains the template that is used in the endpoints list section. It describes what each part does, and how to fill it.

## `POST /example_endpoint`

A description of this endpoint will be available here. The requirements are listed in this description, as well as what its goal is.

### Parameters

The table below lists all the mandatory and optional requirements, with a brief description of each one of them. **Typically, a path parameter is mandatory, while query parameters are optional.**

| Parameter  | Optional?    | Description                                                         |
|------------|--------------|---------------------------------------------------------------------|
| `username` | $\checkmark$ | Username to be used for this dummy request. Does not affect output. |


### Responses

This section lists the possible HTTP status codes the clients may receive when using this endpoint. Server-side status codes (`5xx`) are excluded from the list.

When using both the `POST`:

- `200`: Call was successful
- `400`: Invalid parameters
- `401`: User not logged
