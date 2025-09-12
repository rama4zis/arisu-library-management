# Author API Spec

## Create Author

Endpoint: POST /api/authors

Request Header:

- X-API-TOKEN: Token (Mandatory)

Request Body:

```json
{
  "name": "Author Name",
  "bio": "Author Bio",
  "dob": "1970-01-01",
  "country": "Country", // Optional
  "website": "https://authorwebsite.com" // Optional
}
```

Response success:

```json
{
  "status": "success",
  "data": {
    "id": "1",
    "name": "Author Name",
    "bio": "Author Bio",
    "dob": "1970-01-01",
    "country": "Country",
    "website": "https://authorwebsite.com"
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

## Update Author

Request Header:

- X-API-TOKEN: Token (Mandatory)

Request Body:

```json
{
  "name": "Author Name",
  "bio": "Author Bio",
  "dob": "1970-01-01",
  "country": "Country", // Optional
  "website": "https://authorwebsite.com" // Optional
}
```

Response success:

```json
{
  "status": "success",
  "data": {
    "id": "1",
    "name": "Author Name",
    "bio": "Author Bio",
    "dob": "1970-01-01",
    "country": "Country",
    "website": "https://authorwebsite.com"
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
