package com.sb.sdjpa.crud.repository;

import com.sb.sdjpa.crud.model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {
}
