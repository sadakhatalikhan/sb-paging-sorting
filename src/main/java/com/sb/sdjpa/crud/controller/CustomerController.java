package com.sb.sdjpa.crud.controller;

import com.sb.sdjpa.crud.request.CustomerRequest;
import com.sb.sdjpa.crud.response.APIResponse;
import com.sb.sdjpa.crud.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    /**
     * create a customer, in case of post mapping this method will get invoked.
     *
     * @param request customer request
     * @return response entity object
     */
    @PostMapping("/create")
    public ResponseEntity<APIResponse> createCustomer(@RequestBody CustomerRequest request) {
        return customerService.createCustomer(request);
    }

    /**
     * get all the customer from the database, in case of get mapping with /getAll endpoint this method will get invoked.
     *
     * @return response entity object
     */
    @GetMapping("/getAll")
    public ResponseEntity<APIResponse> getAllCustomer() {
        return customerService.getAllCustomers();
    }

    /**
     * get a specific customer from the database, in case of get mapping with /getById endpoint this method will get invoked.
     *
     * @param customerId customer id
     * @return response entity object
     */
    @GetMapping("/getById/{customerId}")
    public ResponseEntity<APIResponse> getByCustomerId(@PathVariable long customerId) {
        return customerService.getByCustomerId(customerId);
    }

    /**
     * delete a specific customer from the database, in case of delete mapping this method will get invoked.
     *
     * @param customerId customer id
     * @return response entity object
     */
    @DeleteMapping("/deleteById/{customerId}")
    public ResponseEntity<APIResponse> deleteByCustomerId(@PathVariable long customerId) {
        return customerService.deleteByCustomerId(customerId);
    }

    /**
     * update a specific customer, in case of put mapping this method will get invoked.
     *
     * @param customerId customer id
     * @param request customer request object
     * @return response entity object
     */
    @PutMapping("/update/{customerId}")
    public ResponseEntity<APIResponse> updateCustomer(@PathVariable long customerId, @RequestBody CustomerRequest request) {
        return customerService.updateCustomerDetails(customerId, request);
    }

    /**
     * Load the customer using paging and sorting algorithm.
     *
     * @param pageNo page number
     * @param pageSize page size
     * @return response entity object
     */
    @GetMapping("/getCustomer/paging")
    public ResponseEntity<APIResponse> getCustomersUsingPagination(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize
    ) {
        return customerService.getCustomersUsingPagination(pageNo, pageSize);
    }

    /**
     * Load the customer using paging and sorting algorithm.
     *
     * @param pageNo page number
     * @param pageSize page size
     * @param sortBy sorting order by
     * @return response entity object
     */
    @GetMapping("/getCustomer/paging/sorting")
    public ResponseEntity<APIResponse> getCustomersUsingPagingAndSorting(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "customerId") String sortBy
    ) {
        return customerService.getCustomersUsingPagingAndSorting(pageNo, pageSize, sortBy);
    }

    /**
     * Load the customer using paging and sorting algorithm.
     *
     * @param sortBy sorting order by
     * @return response entity object
     */
    @GetMapping("/getCustomer/sorting")
    public ResponseEntity<APIResponse> getCustomersUsingSorting(
            @RequestParam(defaultValue = "customerId") String sortBy
    ) {
        return customerService.getCustomersUsingSorting(sortBy);
    }
}
