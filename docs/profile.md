# Profile API Spec

## Create Profile

Endpoint: POST /api/users/{userId}/profile

Request Header:

- X-API-TOKEN: Token (Mandatory)

Request Body:

```json
{
  "firstName": "Rama",
  "lastName": "Aditya",
  "dob": "1999-10-01",
  "address": "Jl Surabaya No 1",
  "city": "Surabaya",
  "phone": "+1234567890"
}
```

Response success:

```json
{
  "status": "success",
  "data": {
    "id": "1",
    "firstName": "Rama",
    "lastName": "Aditya",
    "dob": "1999-10-01",
    "address": "Jl Surabaya No 1",
    "phone": "+1234567890"
  }
}
```

Response error:

```json
{
  "status": "error",
  "message": "?"
}
```

## Update Profile

Endpoint: PUT /api/users/{userId}/profile

Request Header:

- X-API-TOKEN: Token (Mandatory)

Request Body:

```json
{
  "firstName": "Rama",
  "lastName": "Aditya",
  "dob": "1999-10-01",
  "address": "Jl Surabaya No 2",
  "phone": "+1234567890"
}
```

Response success:

```json
{
  "status": "success",
  "data": {
    "id": "1",
    "firstName": "Rama",
    "lastName": "Aditya",
    "dob": "1999-10-01",
    "address": "Jl Surabaya No 1",
    "phone": "+1234567890"
  }
}
```

Response error:

```json
{
  "status": "error",
  "message": "?"
}
```

## Get Profile by User ID

Endpoint: GET /api/users/{userId}/profile

Request Header:

- X-API-TOKEN: Token (Mandatory)

Response success:

```json
{
  "status": "success",
  "data": {
    "id": "1",
    "firstName": "Rama",
    "lastName": "Aditya",
    "dob": "1999-10-01",
    "address": "Jl Surabaya No 1",
    "phone": "+1234567890"
  }
}
```

Response error:

```json
{
  "status": "error",
  "message": "?"
}
```
