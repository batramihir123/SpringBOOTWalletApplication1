package com.HelloSpring.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.HelloSpring.GlobalException.UnAuthorizedUser;
import com.HelloSpring.aop.ValidFrequencyWRTDays;
import com.HelloSpring.aop.ValidateCustomerId;
import com.HelloSpring.apiresponse.AuthenticationResponse;
import com.HelloSpring.dto.request.*;

import com.HelloSpring.repo.CustomerRepo;
import com.HelloSpring.security.config.JwtService;
import com.HelloSpring.serviceI.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.HelloSpring.apiresponse.ApiResponse;
import com.HelloSpring.apiresponse.ApiResponse1;
import com.HelloSpring.dto.request.CustomerLoginDTO;
import com.HelloSpring.dto.request.CustomerRequestDto;
import com.HelloSpring.model.Customer;
import com.HelloSpring.service.AddressService;
import com.HelloSpring.service.CustomerService;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/customer")
@Slf4j
@Validated
public class CustomerCer {
	@Autowired(required = false)
	CustomerService customerservice;

	@Autowired
	AuthService authService;

	@Autowired
	JwtService jwtService;
	@Autowired
	CustomerRepo customerRepo;

	@Autowired(required = false)
	AddressService addressservice;
	
	 @PostMapping(value = "/create")
	 public ResponseEntity<ApiResponse> createCustomer(@RequestBody @Valid CustomerRequestDto customerRequest) 
	 {
	  log.info("inside create method of cusgtomer controller");
		  Integer generatedCustomerId= customerservice.createCustomer(customerRequest);
		  System.out.println(generatedCustomerId);
	   ApiResponse apiResponse = new ApiResponse(HttpStatus.OK.value(), "Customer Created Successfully",  generatedCustomerId);
	  return new ResponseEntity<ApiResponse> (apiResponse,HttpStatus.OK);
	 }
	 
	 
	 @PostMapping(value="/isValidUser")
		public ResponseEntity<ApiResponse> isValidUser(@RequestBody  @Valid CustomerLoginDTO customerLogin)
		{
			log.info("authenticating user - valid or not ");
			//customerLogin.
			System.out.println(customerLogin);
			boolean res= customerservice.isValidCustByEmailidAndPwd(customerLogin);
			ApiResponse apiresponse=new ApiResponse(HttpStatus.OK.value(), "user is validated", res);
			
			return new ResponseEntity<ApiResponse>	(apiresponse,HttpStatus.OK);
		}
	 
	
    @GetMapping(value = "/findByFirstNameLike/{FirstName}")
     public ResponseEntity<ApiResponse> findByFirstNameLike(@PathVariable String FirstName) 
     {
    	 log.info("Finding the Customer By its FirstName");
    	 List<Customer> custom = customerservice.findByFirstNameLike(FirstName);
    	 System.out.println(FirstName+"..........."+custom);
    	 ApiResponse apiresponse = new ApiResponse(HttpStatus.OK.value(),"FirstName user is there",custom);
    	 return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	 
     }
    
    @GetMapping(value = "findByLastName/{LastName}")
    public ResponseEntity<ApiResponse> findByLastName(@PathVariable String LastName) 
    {
   	 log.info("Finding the Customer By LastName");
   	 List<CustomerFnmLnmGenderDTO > custom = customerservice.findByLastName(LastName);
   	 
   	 ApiResponse apiresponse = new ApiResponse(HttpStatus.OK.value(),"LastName user is there",custom);
   	 return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	 
    }
    
    @PostMapping(value = "/display")
    public ResponseEntity<ApiResponse> display(
			@RequestParam (value="PageNo") Integer PageNo,
			@RequestParam (value="PageSize") Integer PageSize,
			@RequestParam(value = "sortBy", defaultValue = "customerId", required = false) String sortBy,
			@RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection,
			@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName,
			@RequestParam(value = "emailId", required = false) String emailId

	, HttpServletRequest request) throws ServletException, IOException {

		final String authHeader = request.getHeader("Authorization");
		if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
			throw new UnAuthorizedUser("UnAuthorizedUser");
		}

		ApiResponse1 custom = customerservice.display(PageNo,PageSize,sortBy,sortDirection,firstName,lastName,emailId);
		if (custom == null) {
			log.info("no record found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		 ApiResponse apiresponse = new ApiResponse(HttpStatus.OK.value(),"Here is the Page ",custom);
	   	 return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);
    }
	@GetMapping(value = "/download")
	public ResponseEntity<Resource> addingUser() throws IOException
	{
		Workbook workbook = customerservice.AddingUserToExcel();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		headers.setContentDisposition(ContentDisposition.attachment().filename("CustomerExcelData.xlsx").build());

		ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

		return ResponseEntity.ok()
			.headers(headers)
			.body(resource);
	}

	@PostMapping(value = "/GetExcelData")
	public ResponseEntity<ApiResponse> GetExcelData (@RequestParam ("file") MultipartFile file) throws Exception{

		 List<CustomerRequestDto> ls = customerservice.convertExcelTOCustomerList(file);
		ApiResponse apiresponse = new ApiResponse(HttpStatus.OK.value(),"Here its done",ls);
		return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);
	}


	@GetMapping("/createPdf/{customerId}")
	public ResponseEntity<byte[]> createPdf(@PathVariable @ValidateCustomerId Integer customerId,
											HttpServletRequest request) throws UnAuthorizedUser {
String header = request.getHeader("Authorization").substring(7);
String mail = jwtService.extractUsername(header);
Customer customer = customerRepo.findById(customerId).orElseThrow();
String email = customer.getEmailId();
if(mail.equals(email)){
	throw new UnAuthorizedUser("UnAuthorizedUser");
}
		log.info(".........................................................2");
		ByteArrayInputStream pdf = customerservice.CreatePdf(customerId);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Disposition","inline;filename=lcwd.pdf");
		byte[] pdfBytes;
		try {
			pdfBytes = IOUtils.toByteArray(pdf);
		} catch (IOException e) {
			// Handle the exception appropriately
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity
				.ok()
				.headers(httpHeaders)
				.contentType(MediaType.APPLICATION_PDF)
				.body(pdfBytes);
	}

	@PostMapping(value = "/authenticate")
	public ResponseEntity<ApiResponse> authenticate(
			@RequestBody CustomerLoginDTO customerLoginDTO
	) {

		ApiResponse apiResponse = new ApiResponse(HttpStatus.OK.value(), "Customer login Successfully", authService.authenticate(customerLoginDTO));
		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
	}

	@GetMapping(value = "/downloadPdf")
	public ResponseEntity<byte[]> downloadPdf(@RequestBody @ValidFrequencyWRTDays RequestBodyDto requestBodyDto)
	{
		ByteArrayInputStream pdf = customerservice.CreatePdf(requestBodyDto.getCustomerId());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Disposition","inline;filename=lcwd.pdf");
		byte[] pdfBytes;
		try {
			pdfBytes = IOUtils.toByteArray(pdf);
		} catch (IOException e) {
			// Handle the exception appropriately
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity
				.ok()
				.headers(httpHeaders)
				.contentType(MediaType.APPLICATION_PDF)
				.body(pdfBytes);
	}
}


