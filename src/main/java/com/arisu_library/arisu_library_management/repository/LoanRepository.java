package com.arisu_library.arisu_library_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.arisu_library.arisu_library_management.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, String>, JpaSpecificationExecutor<Loan> {}