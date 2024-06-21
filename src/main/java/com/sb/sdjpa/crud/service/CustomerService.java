package com.sb.sdjpa.crud.service;

import com.sb.sdjpa.crud.request.CustomerRequest;
import com.sb.sdjpa.crud.response.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface CustomerService {
    ResponseEntity<APIResponse> createCustomer(CustomerRequest request);
    ResponseEntity<APIResponse> getAllCustomers();
    ResponseEntity<APIResponse> getByCustomerId(long customerId);
    ResponseEntity<APIResponse> deleteByCustomerId(long customerId);
    ResponseEntity<APIResponse> updateCustomerDetails(long customerId, CustomerRequest request);
    ResponseEntity<APIResponse> getCustomersUsingPagination(int pageNo, int pageSize);
    ResponseEntity<APIResponse> getCustomersUsingPagingAndSorting(int pageNo, int pageSize, String sortBy);
    ResponseEntity<APIResponse> getCustomersUsingSorting(String sortBy);
}
