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

import com.arisu_library.arisu_library_management.model.request.CategoryRequestRecord;
import com.arisu_library.arisu_library_management.model.response.BaseResponse;
import com.arisu_library.arisu_library_management.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<?> create(@RequestBody CategoryRequestRecord req) {
        return BaseResponse.ok("success", categoryService.create(req));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<?> update(@PathVariable String id, @RequestBody CategoryRequestRecord req) {
        // ensure path id consistency
        var fixed = new CategoryRequestRecord(id, req.name(), req.description());
        return BaseResponse.ok("success", categoryService.update(fixed));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BaseResponse<?> findById(@PathVariable String id) {
        return BaseResponse.ok("success", categoryService.findById(id));
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BaseResponse<?> list(
            @RequestParam(required = false) String name,
            @ParameterObject @PageableDefault Pageable pageable) {
        return BaseResponse.ok("", categoryService.findAll(name, pageable));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<?> delete(@PathVariable String id) {
        categoryService.deleteById(id);
        return BaseResponse.ok("deleted", null);
    }
}
