# Book API Spec

## Create Book

Endpoint: POST /api/books

Request Header:

- X-API-TOKEN: Token (Mandatory)

Request Body:

```json
{
  "title": "Book Title",
  "summary": "Book Summary",
  "coverImage": "Path or URL to cover image", // Optional
  "authorId": "1",
  "category": "Fiction", // or you can use id
  "pages": 300,
  "language": "English",
  "publishedDate": "2023-10-01",
  "status": "AVAILABLE" // AVAILABLE, BORROWED, LOST, DAMAGED
}
```

Response success:

```json
{
  "status": "success",
  "data": {
    "id": "1",
    "title": "Book Title",
    "summary": "Book Summary",
    "coverImage": "Path or URL to cover image",
    "author": {
      "id": "1",
      "name": "Author Name"
    },
    "category": {
      "id": "1",
      "name": "Fiction"
    },
    "pages": 300,
    "language": "English",
    "publishedDate": "2023-10-01",
    "status": "AVAILABLE"
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

## Update Book

Endpoint: PUT /api/books/{bookId}

Request Header:

- X-API-TOKEN: Token (Mandatory)

Request Body:

```json
{
  "title": "New Book Title", // Put if only you want to update
  "summary": "New Book Summary", // Put if only you want to update
  "coverImage": "Path or URL to cover image",
  "authorId": "1", // Put if only you want to update
  "category": "Fiction", // or you can use id - Put if only you want to update
  "pages": 350, // Put if only you want to update
  "language": "English", // Put if only you want to update
  "publishedDate": "2023-10-01", // Put if only you want to update
  "status": "AVAILABLE" // AVAILABLE, BORROWED, LOST, DAMAGED - Put if only you want to update
}
```

Response success:

```json
{
  "status": "success",
  "data": {
    "id": "1",
    "title": "New Book Title",
    "summary": "New Book Summary",
    "coverImage": "Path or URL to cover image",
    "author": {
      "id": "1",
      "name": "Author Name"
    },
    "category": {
      "id": "1", // or you can use name
      "name": "Fiction" // or you can use id
    },
    "pages": 350,
    "language": "English",
    "publishedDate": "2023-10-01",
    "status": "AVAILABLE"
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

## Get Book by ID

Endpoint: GET /api/books/{bookId}

Response success:

```json
{
  "status": "success",
  "data": {
    "id": "1",
    "title": "Book Title",
    "summary": "Book Summary",
    "coverImage": "Path or URL to cover image",
    "author": {
      "id": "1",
      "name": "Author Name"
    },
    "category": {
      "id": "1", // or you can use name
      "name": "Fiction" // or you can use id
    },
    "pages": 300,
    "language": "English",
    "publishedDate": "2023-10-01",
    "status": "AVAILABLE"
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

## Search Books

Endpoint: GET /api/books

Request Query Parameters (Optional):

- authorId: Filter by author ID
- name: Filter by book title
- category: Filter by category name

Response success:

```json
{
  "status": "success",
  "data": [
    {
      "id": "1",
      "title": "Book Title",
      "summary": "Book Summary",
      "coverImage": "Path or URL to cover image",
      "author": {
        "id": "1",
        "name": "Author Name"
      },
      "category": {
        "id": "1", // or you can use name
        "name": "Fiction" // or you can use id
      },
      "pages": 300,
      "language": "English",
      "publishedDate": "2023-10-01",
      "status": "AVAILABLE"
    },
    {
      "id": "2",
      "title": "Another Book Title",
      "summary": "Another Book Summary",
      "coverImage": "Path or URL to cover image",
      "author": {
        "id": "1",
        "name": "Author Name"
      },
      "category": {
        "id": "1", // or you can use name
        "name": "Fiction" // or you can use id
      },
      "pages": 250,
      "language": "English",
      "publishedDate": "2022-05-15",
      "status": "BORROWED"
    }
  ],
  "paging": {
    "page": 0,
    "totalPage": 1,
    "size": 10
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
