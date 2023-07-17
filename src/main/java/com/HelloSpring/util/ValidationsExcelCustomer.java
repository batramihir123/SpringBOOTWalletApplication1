package com.HelloSpring.util;
import com.HelloSpring.dto.request.CustomerRequestDto;
import com.HelloSpring.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ValidationsExcelCustomer
{

    @Autowired
    CustomerRepo customerRepo;


    public ValidationsExcelCustomer(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public void  ValidCustomer(List<CustomerRequestDto> Valid , List<CustomerRequestDto> Invalid , List<CustomerRequestDto> cs)
    {
        for(CustomerRequestDto c :cs)
        {
            boolean isValid = true;
            StringBuilder errorMessage = new StringBuilder();

            if (c.getFirstName() == null) {
                errorMessage.append("First Name is null.");
                isValid = false;
            }
            if (c.getLastName() == null) {
                errorMessage.append("Last Name is null. ");
                isValid = false;
            }
            if (c.getEmailId() == null) {
                errorMessage.append("Email Id is null. ");
                isValid = false;
            }
            if (c.getContactNo() == null) {
                errorMessage.append("Contact No is null. ");
                isValid = false;
            }
            if (c.getGender() == null) {
                errorMessage.append("Gender is null. ");
                isValid = false;
            }
            if (c.getPassword() == null) {
                errorMessage.append("Password is null. ");
                isValid = false;
            }
            if (c.getAddressLine1() == null) {
                errorMessage.append("Address Line 1 is null. ");
                isValid = false;
            }
            if (c.getAddressLine2() == null) {
                errorMessage.append("Address Line 2 is null. ");
                isValid = false;
            }
            if (c.getCity() == null) {
                errorMessage.append("City is null. ");
                isValid = false;
            }
            if (c.getState() == null) {
                errorMessage.append("State is null. ");
                isValid = false;
            }
            if (c.getPincode() == null) {
                errorMessage.append("Pincode is null. ");
                isValid = false;
            }

            if (isValid) {
                // Check for duplicate email before adding to valid list
                if (customerRepo.findByEmailId(c.getEmailId()).isPresent()) {
                    isValid = false;
                    System.out.println(".................................................................."+ customerRepo.findByEmailId(c.getEmailId()));
                    errorMessage.append("User Already Exists");
                } else {
                    System.out.println("add valid customer");
                    Valid.add(c);
                }
            }

            if (!isValid) {
                c.setValidationMessage(errorMessage.toString());
                Invalid.add(c);
            }
        }

    }

}
