package com.arisu_library.arisu_library_management.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arisu_library.arisu_library_management.model.enums.LoanStatus;
import com.arisu_library.arisu_library_management.model.request.LoanRequestRecord;
import com.arisu_library.arisu_library_management.model.response.BaseResponse;
import com.arisu_library.arisu_library_management.service.LoanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    // Create loan: validation enforces profile presence and max 7-day duration
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BaseResponse<?> create(@RequestBody LoanRequestRecord req) {
        return BaseResponse.ok("success", loanService.create(req));
    }

    // Mark as returned
    @PutMapping("/{id}/return")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BaseResponse<?> markReturned(@PathVariable String id) {
        return BaseResponse.ok("success", loanService.markReturned(id));
    }

    // Get by id
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BaseResponse<?> findById(@PathVariable String id) {
        return BaseResponse.ok("success", loanService.findById(id));
    }

    // List with filters
    @PostMapping("/list")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BaseResponse<?> list(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) LoanStatus status,
            @ParameterObject @PageableDefault Pageable pageable) {
        return BaseResponse.ok("", loanService.findAll(username, title, status, pageable));
    }
}
