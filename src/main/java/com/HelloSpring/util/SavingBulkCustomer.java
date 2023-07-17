package com.HelloSpring.util;

import com.HelloSpring.dto.request.CustomerRequestDto;
import com.HelloSpring.model.Address;
import com.HelloSpring.model.Customer;

import java.util.List;

public class SavingBulkCustomer {

    public void Saveit(List<Customer> cmr, List<Address> list1 , List<CustomerRequestDto> valid)
    {
        for(CustomerRequestDto cr : valid)
        {
            Address add = Address.builder().addressLine1(cr.getAddressLine1()).addressLine2(cr.getAddressLine1())
                    .state(cr.getState()).city(cr.getCity()).pincode(cr.getPincode()).build();


            list1.add(add);


            Customer customer = Customer.builder().firstName(cr.getFirstName())
                    .lastName(cr.getLastName()).emailId(cr.getEmailId()).contactNo(cr.getContactNo()).gender(cr.getGender())
                    .password(cr.getPassword()).address(add).build();
            cmr.add(customer);
        }

    }

}
