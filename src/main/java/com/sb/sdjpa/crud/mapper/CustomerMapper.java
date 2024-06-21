package com.sb.sdjpa.crud.mapper;

import com.sb.sdjpa.crud.enums.CustomerStatus;
import com.sb.sdjpa.crud.model.CustomerModel;
import com.sb.sdjpa.crud.request.CustomerRequest;
import com.sb.sdjpa.crud.response.CustomerResponse;

import static com.sb.sdjpa.crud.utils.AppUtils.generateOtp;
import static com.sb.sdjpa.crud.utils.AppUtils.generatePassword;

public class CustomerMapper {
    public static CustomerResponse modelToResponseMapper(CustomerModel customerModel) {
        return CustomerResponse.builder()
                .customerId(customerModel.getCustomerId())
                .customerName(customerModel.getCustomerName())
                .customerAge(customerModel.getCustomerAge())
                .customerMobileNumber(customerModel.getCustomerMobileNumber())
                .customerEmailAddress(customerModel.getCustomerEmailAddress())
                .customerAddress(customerModel.getCustomerAddress())
                .createdDate(customerModel.getCreatedDate())
                .status(customerModel.getStatus())
                .verified(customerModel.isVerified())
                .createdDate(customerModel.getCreatedDate())
                .updatedDate(customerModel.getUpdatedDate())
                .build();
    }

    public static CustomerModel requestToModel(CustomerRequest request) {
        return CustomerModel.builder()
                .customerName(request.getCustomerName())
                .customerPassword(generatePassword())
                .customerAge(request.getCustomerAge())
                .customerMobileNumber(request.getCustomerMobileNumber())
                .customerEmailAddress(request.getCustomerEmailAddress())
                .customerAddress(request.getCustomerAddress())
                .status(CustomerStatus.INACTIVE)
                .customerOtp(generateOtp())
                .verified(Boolean.FALSE)
                .build();
    }
}
