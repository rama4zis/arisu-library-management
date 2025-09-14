package com.arisu_library.arisu_library_management.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.arisu_library.arisu_library_management.model.enums.BookStatus;
import com.arisu_library.arisu_library_management.model.request.BookRequestRecord;
import com.arisu_library.arisu_library_management.model.response.BaseResponse;
import com.arisu_library.arisu_library_management.service.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<?> create(
        @RequestPart(name = "coverImage", required = false) MultipartFile coverImage,
        @RequestPart(name = "book") BookRequestRecord req
        ) {
        return BaseResponse.ok("success", bookService.create(req, coverImage));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<?> update(
        @RequestPart(name = "coverImage", required = false) MultipartFile coverImage,
        @RequestPart(name = "book") BookRequestRecord req,
        @PathVariable String id
    ) {
        var updateBook = new BookRequestRecord(
            id,
            req.title(),
            req.summary(),
            req.coverImage(),
            req.authorId(),
            req.categoryId(),
            req.pages(),
            req.language(),
            req.publishedDate(),
            req.status()
        );
        return BaseResponse.ok("success", bookService.update(updateBook, coverImage));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BaseResponse<?> findById(@PathVariable String id) {
        return BaseResponse.ok("success", bookService.findById(id));
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BaseResponse<?> list(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) BookStatus status,
            @ParameterObject @PageableDefault Pageable pageable) {
        return BaseResponse.ok("", bookService.findAll(title, authorName, categoryName, status, pageable));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<?> delete(@PathVariable String id) {
        bookService.deleteById(id);
        return BaseResponse.ok("deleted", null);
    }
}
