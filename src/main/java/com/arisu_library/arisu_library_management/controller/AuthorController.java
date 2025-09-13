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

import com.arisu_library.arisu_library_management.model.request.AuthorRequestRecord;
import com.arisu_library.arisu_library_management.model.response.BaseResponse;
import com.arisu_library.arisu_library_management.service.AuthorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<?> create(@RequestBody AuthorRequestRecord req) {
        return BaseResponse.ok("success", authorService.create(req));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<?> update(@PathVariable String id, @RequestBody AuthorRequestRecord req) {
        var fixed = new AuthorRequestRecord(id, req.name(), req.biography(), req.dob(), req.country(), req.website());
        return BaseResponse.ok("success", authorService.update(fixed));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BaseResponse<?> findById(@PathVariable String id) {
        return BaseResponse.ok("success", authorService.findById(id));
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BaseResponse<?> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String country,
            @ParameterObject @PageableDefault Pageable pageable) {
        return BaseResponse.ok("", authorService.findAll(name, country, pageable));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<?> delete(@PathVariable String id) {
        authorService.deleteById(id);
        return BaseResponse.ok("deleted", null);
    }
}
