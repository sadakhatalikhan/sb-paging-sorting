package com.sb.sdjpa.crud.repository;

import com.sb.sdjpa.crud.model.CustomerModel;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaginationRepository extends PagingAndSortingRepository<CustomerModel, Long> {
}
