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
  "birthDate": "1970-01-01",
  "country": "Country", // Optional
  "website": "https://authorwebsite.com", // Optional
  "socialMedia": { // Optional
    "twitter": "https://twitter.com/author", // Optional
    "facebook": "https://facebook.com/author", // Optional
    "instagram": "https://instagram.com/author" // Optional
  }
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
    "birthDate": "1970-01-01",
    "country": "Country",
    "website": "https://authorwebsite.com",
    "socialMedia": {
      "twitter": "https://twitter.com/author",
      "facebook": "https://facebook.com/author",
      "instagram": "https://instagram.com/author"
    }
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
  "birthDate": "1970-01-01",
  "country": "Country", // Optional
  "website": "https://authorwebsite.com", // Optional
  "socialMedia": { // Optional
    "twitter": "https://twitter.com/author", // Optional
    "facebook": "https://facebook.com/author", // Optional
    "instagram": "https://instagram.com/author" // Optional
  }
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
    "birthDate": "1970-01-01",
    "country": "Country",
    "website": "https://authorwebsite.com",
    "socialMedia": {
      "twitter": "https://twitter.com/author",
      "facebook": "https://facebook.com/author",
      "instagram": "https://instagram.com/author"
    }
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