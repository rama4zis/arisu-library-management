# Category API Spec

## Create Category
Endpoint: POST /api/categories

Request Header:
- X-API-TOKEN: Token (Mandatory)

Request Body:
```json
{
  "name": "Fiction",
  "description": "Fictional books category"
}
```

Response success:
```json
{
    "status": "success",
    "data": {
        "id": "1",
        "name": "Fiction",
        "description": "Fictional books category"
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

## Update Category
Endpoint: PUT /api/categories/{id}

Request Header:
- X-API-TOKEN: Token (Mandatory)

Request Body:
```json
{
  "name": "Fiction", // Put if only you want to update
  "description": "Fictional books category" // Put if only you want to update
}
```

Response success:
```json
{
    "status": "success",
    "data": {
        "id": "1",
        "name": "Fiction",
        "description": "Fictional books category"
    }
}
```