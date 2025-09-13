package com.arisu_library.arisu_library_management.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<?> create(@RequestBody BookRequestRecord req) {
        return BaseResponse.ok("success", bookService.create(req));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<?> update(@PathVariable String id, @RequestBody BookRequestRecord req) {
        var fixed = new BookRequestRecord(
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
        return BaseResponse.ok("success", bookService.update(fixed));
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
