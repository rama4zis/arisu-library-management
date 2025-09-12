# Loan API Spec

## Create Loan
Endpoint: POST /api/loans

Request Header:
- X-API-TOKEN: Token (Mandatory)

Request Body:
```json
{
  "userId": "1",
  "bookId": "1",
  "loanDate": "2023-10-01",
  "dueDate": "2023-10-15"
}
```

Response success:
```json
{
    "status": "success",
    "data": {
        "id": "1",
        "userId": "1",
        "bookId": "1",
        "loanDate": "2023-10-01",
        "dueDate": "2023-10-15",
        "returnDate": null,
        "status": "ONGOING" // ONGOING, RETURNED, OVERDUE
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

## Update Loan
Endpoint: PUT /api/loans/{loanId}

Request Header:
- X-API-TOKEN: Token (Mandatory)

Request Body:
```json
{
  "returnDate": "2023-10-10", // Put if only you want to update
  "status": "RETURNED" // ONGOING, RETURNED, OVERDUE - Put if only you want to update
}
```

Response success:
```json
{
    "status": "success",
    "data": {
        "id": "1",
        "userId": "1",
        "bookId": "1",
        "loanDate": "2023-10-01",
        "dueDate": "2023-10-15",
        "returnDate": "2023-10-10",
        "status": "RETURNED"
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