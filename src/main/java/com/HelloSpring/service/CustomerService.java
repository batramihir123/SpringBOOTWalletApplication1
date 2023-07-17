package com.HelloSpring.service;




import com.HelloSpring.apiresponse.ApiResponse1;
import com.HelloSpring.dto.request.CustomerFnmLnmGenderDTO;
import com.HelloSpring.dto.request.CustomerLoginDTO;
import com.HelloSpring.dto.request.CustomerRequestDto;

import com.HelloSpring.model.Customer;

import java.io.ByteArrayInputStream;
import java.util.*;

import jakarta.validation.Valid;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;


public interface CustomerService {

	
	public Integer createCustomer(@Valid CustomerRequestDto customerRequest);
   
	public Customer getCustomerByCustId(int customerid);
	public Customer getCustomerByCustId1(String CustEmailID);

	public boolean isValidCustByEmailidAndPwd(@Valid CustomerLoginDTO customerLogin);

	public Customer saveCustomer(Customer cust);
	public List<Customer> findByFirstNameLike(String fn);
	public List<Customer> findByFirstNameContaining(String fnm);
	public List<Customer> findByfirstNameContains(String fnm);
	public List<Customer> findByfirstNameIsContaining(String fnm);
	public List<Customer> findByFirstNameIgnoreCase(String fn);
	public List<CustomerFnmLnmGenderDTO> findByLastName(String lnm);
	Boolean existsByEmailIdAndPassword(String email, String password);
	public ApiResponse1 display(Integer PageNo,Integer pageSize);

	public void sendmail(String toMail, String Subject , String Body);

	public Workbook AddingUserToExcel();

	public List<CustomerRequestDto>  convertExcelTOCustomerList(MultipartFile file) throws Exception;

	public ByteArrayInputStream CreatePdf(int customerId);
}
