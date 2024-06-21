package com.sb.sdjpa.crud.service.impl;

import com.sb.sdjpa.crud.model.CustomerModel;
import com.sb.sdjpa.crud.repository.PaginationRepository;
import com.sb.sdjpa.crud.repository.CustomerRepository;
import com.sb.sdjpa.crud.request.CustomerRequest;
import com.sb.sdjpa.crud.response.APIResponse;
import com.sb.sdjpa.crud.response.CustomerResponse;
import com.sb.sdjpa.crud.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sb.sdjpa.crud.constants.AppConstants.*;
import static com.sb.sdjpa.crud.mapper.CustomerMapper.modelToResponseMapper;
import static com.sb.sdjpa.crud.mapper.CustomerMapper.requestToModel;

/**
 * This class holds all the business logic, and it will be used as a interface between controller and DAO layer.
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PaginationRepository paginationRepository;

    /**
     * This method is used to store the customer details into the database.
     *
     * @param request customer request
     * @return responseEntity object
     */
    @Override
    public ResponseEntity<APIResponse> createCustomer(CustomerRequest request) {

        CustomerModel customerModel = customerRepository.save(requestToModel(request));

        return ResponseEntity.ok(
                APIResponse.builder()
                        .errorCode(SUCCESS_CODE)
                        .errorMessage(SUCCESSFULLY_STORED)
                        .data(modelToResponseMapper(customerModel))
                        .build()
        );
    }

    /**
     * This method is used to fetch all the customers from the database.
     *
     * @return responseEntity object
     */
    @Override
    public ResponseEntity<APIResponse> getAllCustomers() {
        List<CustomerModel> customerDetails = customerRepository.findAll();
        List<CustomerResponse> customers = customerDetails.stream()
                .map(customerModel -> modelToResponseMapper(customerModel))
                .toList();

        return ResponseEntity.ok(
                APIResponse.builder()
                        .errorCode(SUCCESS_CODE)
                        .errorMessage(SUCCESSFULLY_RETRIEVED)
                        .data(customers)
                        .build()
        );
    }

    /**
     * Fetch customer based on the specific id.
     *
     * @param customerId customer id
     * @return responseEntity object
     */
    @Override
    public ResponseEntity<APIResponse> getByCustomerId(long customerId) {
        Optional<CustomerModel> modelOptional = customerRepository.findById(customerId);
        if (!modelOptional.isPresent()) {
            return ResponseEntity.ok(
                    APIResponse.builder()
                            .errorCode(CUSTOMER_NOT_EXISTS_CODE)
                            .errorMessage(CUSTOMER_NOT_EXISTS)
                            .data(List.of())
                            .build()
            );
        }

        CustomerModel model = modelOptional.get();
        CustomerResponse response = modelToResponseMapper(model);
        return ResponseEntity.ok(
                APIResponse.builder()
                        .errorCode(SUCCESS_CODE)
                        .errorMessage(SUCCESSFULLY_RETRIEVED)
                        .data(response)
                        .build()
        );
    }

    /**
     * This method is used to delete the customer from the database.
     *
     * @param customerId customer id
     * @return responseEntity object
     */
    @Override
    public ResponseEntity<APIResponse> deleteByCustomerId(long customerId) {

        Optional<CustomerModel> modelOptional = customerRepository.findById(customerId);

        if (!modelOptional.isPresent()) {
            return ResponseEntity.ok(
                    APIResponse.builder()
                            .errorCode(CUSTOMER_NOT_EXISTS_CODE)
                            .errorMessage(CUSTOMER_NOT_EXISTS)
                            .data(List.of())
                            .build()
            );
        }
        customerRepository.deleteById(customerId);
        return ResponseEntity.ok(
                APIResponse.builder()
                        .errorCode(SUCCESS_CODE)
                        .errorMessage(SUCCESSFULLY_DELETED)
                        .data(List.of())
                        .build()
        );
    }

    /**
     * This method is used to update the customer details into the database.
     *
     * @param customerId customer id
     * @param request customer request object
     * @return responseEntity object
     */
    @Override
    public ResponseEntity<APIResponse> updateCustomerDetails(long customerId, CustomerRequest request) {

        Optional<CustomerModel> modelOptional = customerRepository.findById(customerId);
        if (modelOptional.isPresent()) {
            CustomerModel model = modelOptional.get();
            model.setCustomerName(request.getCustomerName());
            model.setCustomerAge(request.getCustomerAge());
            model.setCustomerMobileNumber(request.getCustomerMobileNumber());
            model.setCustomerEmailAddress(request.getCustomerEmailAddress());
            model.setCustomerAddress(request.getCustomerAddress());
            model = customerRepository.save(model);

            return ResponseEntity.ok(
                    APIResponse.builder()
                            .errorCode(SUCCESS_CODE)
                            .errorMessage(SUCCESSFULLY_UPDATED)
                            .data(modelToResponseMapper(model))
                            .build()
            );
        } else {
            return ResponseEntity.ok(
                    APIResponse.builder()
                            .errorCode(CUSTOMER_NOT_EXISTS_CODE)
                            .errorMessage(CUSTOMER_NOT_EXISTS)
                            .data(List.of())
                            .build()
            );
        }
    }

    /**
     * Load the customer using paging algorithm.
     *
     * @param pageNo page number
     * @param pageSize page size
     * @return response entity object
     */
    @Override
    public ResponseEntity<APIResponse> getCustomersUsingPagination(int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<CustomerModel> pagedResult = paginationRepository.findAll(pageRequest);
        return ResponseEntity.ok(
                APIResponse.builder()
                        .errorCode(SUCCESS_CODE)
                        .errorMessage(SUCCESSFULLY_UPDATED)
                        .data(pagedResult.stream().map(customerModel -> modelToResponseMapper(customerModel)).toList())
                        .build()
        );
    }

    /**
     * Load the customer using paging and sorting algorithm.
     *
     * @param pageNo page number
     * @param pageSize page size
     * @param sortBy sorting order by
     * @return response entity object
     */
    @Override
    public ResponseEntity<APIResponse> getCustomersUsingPagingAndSorting(int pageNo, int pageSize, String sortBy) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<CustomerModel> pagedResult = paginationRepository.findAll(pageRequest);
        return ResponseEntity.ok(
                APIResponse.builder()
                        .errorCode(SUCCESS_CODE)
                        .errorMessage(SUCCESSFULLY_UPDATED)
                        .data(pagedResult.stream().map(customerModel -> modelToResponseMapper(customerModel)).toList())
                        .build()
        );
    }

    /**
     * Load the customer using paging and sorting algorithm.
     *
     * @param sortBy sorting order by
     * @return response entity object
     */
    @Override
    public ResponseEntity<APIResponse> getCustomersUsingSorting(String sortBy) {
        Sort sortOrder = Sort.by(sortBy);
        Iterable<CustomerModel> pagedResult = paginationRepository.findAll(sortOrder);
        List<CustomerModel> customerModels = new ArrayList<>();
        pagedResult.forEach(customerModels::add);
        return ResponseEntity.ok(
                APIResponse.builder()
                        .errorCode(SUCCESS_CODE)
                        .errorMessage(SUCCESSFULLY_UPDATED)
                        .data(customerModels.stream().map(customerModel -> modelToResponseMapper(customerModel)).toList())
                        .build()
        );
    }
}
