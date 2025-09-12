# User API Spec

## Register User
Endpoint: POST /api/users

Request Body:
```json
{
    "username": "rama",
    "password": "secret😂",
    "email": "rama@example.com",
    "role": "ADMIN", // Optional, default is USER. Other value is ADMIN
    "status": "ACTIVE" // Optional, default is ACTIVE. Other value is INACTIVE
}
```

Response success:
```json
{
    "status": "success",
}
```

Response error:
```json
{
    "status": "error",
    "message": "?"
}
```

## Login User
Endpoint: POST /api/auth/login

Request Body:
```json
{
  "username": "rama",
  "password": "secret😂"
}
```

Response success:
```json
{
    "status": "success",
    "data": {
        "token": "TOKEN",
        "expiresIn": 3600 // in seconds
    }
}
```

Response error:
```json
{
    "status": "error",
    "message": "Username or password is incorrect"
}
```

## Get User
Endpoint: GET /api/users/{id}

Request Header:
- X-API-TOKEN: Token (Mandatory)

Response success:
```json
{
    "status": "success",
    "data": {
        "id": "1",
        "username": "rama",
        "email": "rama@example.com",
        "role": "ADMIN",
        "status": "ACTIVE"
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

## Update User
Endpoint: PUT /api/users/edit/{id}

Request Header:
- X-API-TOKEN: Token (Mandatory)

Request Body:
```json
{
  "username": "rama", // Put if only you want to update
  "password": "newsecret😂",  // Put if only you want to update
  "email": "rama@example.com", // Put if only you want to update
  "role": "ADMIN", // Put if only you want to update
  "status": "ACTIVE" // Put if only you want to update
}
```

Response success:
```json
{
    "status": "success",
    "data": {
        "id": "1",
        "username": "rama",
        "email": "rama@example.com",
        "role": "ADMIN",
        "status": "ACTIVE"
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

## Logout User
Endpoint: POST /api/auth/logout

Request Header:
- X-API-TOKEN: Token (Mandatory)

Response success:
```json
{
    "status": "success"
}
```

Response error:
```json
{
    "status": "error",
    "message": "?"
}
```

## Delete User
Endpoint: DELETE /api/users/{id}

Request Header:
- X-API-TOKEN: Token (Mandatory)

Response success:
```json
{
    "status": "success"
}
```

Response error:
```json
{
    "status": "error",
    "message": "?"
}
```