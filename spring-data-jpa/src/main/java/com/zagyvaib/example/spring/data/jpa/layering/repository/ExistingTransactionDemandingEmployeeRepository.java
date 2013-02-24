package com.zagyvaib.example.spring.data.jpa.layering.repository;

import com.zagyvaib.example.spring.data.jpa.common.entity.Employee;
import com.zagyvaib.example.spring.data.jpa.layering.infrastructure.ExistingTransactionDemandingRepository;
import com.zagyvaib.example.spring.data.jpa.layering.infrastructure.ReadOnlyAndRequiresExistingTransaction;

// @ReadOnlyAndRequiresExistingTransaction declares that methods on _this_ interface (as opposed to methods inherited
// from super-interfaces) are read-only and demand an existing transaction when they are invoked.
@ReadOnlyAndRequiresExistingTransaction
public interface ExistingTransactionDemandingEmployeeRepository
        extends ExistingTransactionDemandingRepository<Employee, Long> {

    Employee findByName(String name);
}