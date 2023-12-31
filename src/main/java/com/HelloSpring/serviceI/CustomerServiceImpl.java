package com.HelloSpring.serviceI;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import com.HelloSpring.GlobalException.*;
import com.HelloSpring.GlobalException.ResourceNotFoundException;
import com.HelloSpring.apiresponse.ApiResponse1;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.HelloSpring.apiresponse.AuthenticationResponse;
import com.HelloSpring.model.*;
import com.HelloSpring.util.SearchingHelper;
import com.HelloSpring.service.TransactionService;
import com.HelloSpring.util.PdfHelper;
import com.HelloSpring.util.SavingBulkCustomer;
import com.HelloSpring.util.ValidationsExcelCustomer;
import com.lowagie.text.*;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.HelloSpring.dto.request.CustomerFnmLnmGenderDTO;
import com.HelloSpring.dto.request.CustomerLoginDTO;
import com.HelloSpring.dto.request.CustomerRequestDto;
import com.HelloSpring.repo.AddressRepo;
import com.HelloSpring.repo.CustomerRepo;
import com.HelloSpring.service.CustomerService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired(required = false)
	CustomerRepo customerRepo;
	@Autowired(required = false)
	AddressRepo addressRepo;

	@Autowired
	TransactionService transactionService;
	@Autowired
	PasswordEncoder passwordEncoder;


	@Autowired
	private JavaMailSender message;

	@Value("${ExpiryDays}")
    Long days;


	/*
	Here the customer is creating using the builder function
	 */
	@Override
	public Integer createCustomer(@Valid CustomerRequestDto crDto) {
		log.info("Inside save customer of service with given request"+crDto);
			System.out.println("inside save customer of service with given request "+ crDto);
			 Optional<Customer> optCust= customerRepo.findByEmailIdAndPassword(crDto.getEmailId(),crDto.getPassword());
		  if(optCust.isPresent())
		  {
			  throw new EntityAlreadyExistException("Customer email id is already existing ");
		  }
		  Address add=Address.builder().addressLine1(crDto.getAddressLine1())
		    .addressLine2(crDto.getAddressLine2())
		    .city(crDto.getCity())
		    .state(crDto.getState())
		    .pincode(crDto.getPincode())
		    .build();
		 
		  Address addCreated= addressRepo.save(add);
		  
		  
		  Customer cust=Customer.builder().firstName(crDto.getFirstName()).lastName(crDto.getLastName()).emailId(crDto.getEmailId())
		    .contactNo(crDto.getContactNo()).address(addCreated).gender(crDto.getGender()).password(passwordEncoder.encode(crDto.getPassword()))
				  .registerationDate(LocalDate.now()).expiryDate(LocalDate.now().plusDays(days))
		    
		    .build();
		  System.out.println("cust entity is now from builder : "+ cust);
		  log.info("Cust enttity saved",cust);;
		  Customer custCreated= customerRepo.save(cust);
		  
		  
		  
		  return custCreated.getCustomerId();
	}

	@Override
	public Customer getCustomerByCustId(int customerid) {
		return customerRepo.findById(customerid).get();
	}


	/*
	Function for finding out the valid user with his mail id and password
	 */
	@Override
	public boolean isValidCustByEmailidAndPwd(@Valid CustomerLoginDTO customerLogin) {
		Optional<Customer> opt= customerRepo.findByEmailIdAndPassword(customerLogin.getEmailId(), customerLogin.getPassword());
		if(opt.isEmpty())
		  {
			  throw new  ResourceNotFoundException("Invalid Userr");
		  }
		
		return true;
	}
	/*
	Function to find the customer by his mail id
	 */

	@Override
	public Customer getCustomerByCustId1(String CustEmailID) {
		System.out.println("............."+CustEmailID);
		return customerRepo.findByEmailId(CustEmailID).get();
	}

	@Override
	public Customer saveCustomer(Customer cust) {
		return customerRepo.save(cust);
	}

	/*
	Here the customer is getting finded by his firstname
	 */
	@Override
	public List<Customer> findByFirstNameLike(String fn) {
		List<Customer> cs = customerRepo.findAll();
		if(cs.isEmpty())
		{
			throw new  ResourceNotFoundException("Invalid Userr");
			
		}
		List<Customer> fs = new ArrayList<>();
		System.out.println(".................................................."+cs);
		for(Customer ls:cs)
		{
			if(ls.getFirstName().contains(fn))
			{
				fs.add(ls);
			}
		}
		System.out.println("............................"+fs);
		return fs;
		
	}
	@Override
	public List<Customer> findByFirstNameContaining(String fnm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> findByfirstNameContains(String fnm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> findByfirstNameIsContaining(String fnm) {

		return null;
	}

	@Override
	public List<Customer> findByFirstNameIgnoreCase(String fn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean existsByEmailIdAndPassword(String email, String password) {
		Optional<Customer> opt= customerRepo.findByEmailIdAndPassword(email, password);
		if(opt.isEmpty())
		  {
			  throw new  ResourceNotFoundException("Invalid Userr");
		  }
		
		return true;
	}


	@Override
	public List<CustomerFnmLnmGenderDTO> findByLastName(String lnm) {
		List<Customer> cs = customerRepo.findAll();
		List<CustomerFnmLnmGenderDTO> f = new ArrayList<>();
		for(Customer c : cs)
		{
			String firstName = c.getFirstName();
			String lastName = c.getLastName();
			Gender gender = c.getGender();
			
			CustomerFnmLnmGenderimpl d = new CustomerFnmLnmGenderimpl();
			d.setFirstName(firstName);
			d.setLastName(lastName);
			d.setGender(gender);
			
			f.add(d);
		}
		
		return f;
	}

	/*
	Pageable is the Pagenation object which in terms of Two Integer which is PageNo and PageSize or offset and limjt takes two Integer
	And other is the String based Sort object which provides two parameters that are SortField and SortDirection like on what basic to
	Sort and THe Direction here is Ascending and Descending refers whereas Other parameters are Searching Based where we have used the
	Simple Repo inbuilt Functions ..............
	 */
	
	public ApiResponse1 display(Integer PageNo , Integer PageSize,String sortBy, String sortDirection,String firstName,
								String lastName , String emailId) {
		Sort sort = sortDirection.equalsIgnoreCase("asc") || sortDirection.equalsIgnoreCase("ascending") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable p = PageRequest.of(PageNo, PageSize, sort);
		Page<Customer> allCustomer ;
		if (firstName != null || lastName != null || emailId != null) {
			allCustomer = customerRepo.findByFirstNameIsContainingOrLastNameIsContainingOrEmailIdIsContaining(firstName, lastName, emailId, p);
		}
		else { allCustomer = customerRepo.findAll(p);}
		List<Customer> list = allCustomer.getContent();
		ApiResponse1 apiresp = new ApiResponse1();
		apiresp.setContent(list);
		apiresp.setPageNumber(allCustomer.getNumber());
		apiresp.setPageSize(allCustomer.getSize());
		apiresp.setLastPage(allCustomer.isLast());
		apiresp.setTotalElement(allCustomer.getNumberOfElements());
		apiresp.setTotalPage(allCustomer.getTotalPages());
		return apiresp;
}/*
	Here the Scheduler Annotation is applied along with the cron expression to sent the mail
	after Cetrain period of interval
	Cron Expression --> * ->seconds->
	-->*->Minutes->0-59
	--->*->hours->12
	-->*->>Day of the month
	-->*-->Month-->
	---->*-->Day of the week
	-->*->>year
	 */
	@Scheduled(cron = "0 0 0 * * *")
	public void SendExpiryDate()
	{
		log.info("Here is the Expiry Mail Scheduler");
		List<Customer> cus = customerRepo.findByExpiryDateOrExpiryDate(LocalDate.now(),LocalDate.now().plusDays(days));
		if(cus.isEmpty())
		{
			throw new  ResourceNotFoundException("Invalid Userr");
		}
		for(Customer C : cus)
		{
			String toEmail = C.getEmailId();
			sendmail(toEmail,"Wallet Account is Expired","Dear Customer" + ""+"Your Account will get Expired on " + C.getExpiryDate()+
					"Please Get Your Premium Recharge" );
		}
	}/*
	Here its main java class inbuilt package use to sent the mail along with the functions
	and the object of mailsender is created to send the particular message
	 */
	@Override
	public void sendmail(String toMail, String Subject, String Body) {
		SimpleMailMessage msg= new SimpleMailMessage();

		msg.setFrom("mihirbatra97@gmail.com");
		msg.setTo(toMail);
		msg.setSubject(Subject);
		msg.setText(Body);
		message.send(msg);
		log.info("Mail Sent SuccessFully");
	}

	@Override
	public Workbook AddingUserToExcel() {
		XSSFWorkbook workbook = new XSSFWorkbook();

		Sheet sheet = workbook.createSheet("ExcelSheetForWallet");
		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("First Name");
		headerRow.createCell(1).setCellValue("Last Name");
		headerRow.createCell(2).setCellValue("EmailId");
		headerRow.createCell(3).setCellValue("Contact Number");
		headerRow.createCell(4).setCellValue("Gender");
		headerRow.createCell(5).setCellValue("Password");
		headerRow.createCell(6).setCellValue("Address1");
		headerRow.createCell(7).setCellValue("Address2");
		headerRow.createCell(8).setCellValue("City");
		headerRow.createCell(9).setCellValue("State");
		headerRow.createCell(10).setCellValue("Pin code");
		return workbook;
	}

	@Override
	public List<CustomerRequestDto> convertExcelTOCustomerList(MultipartFile file) throws Exception{
		List<CustomerRequestDto> cs = new ArrayList<>();
		List<CustomerRequestDto> valid = new ArrayList<>();
		List<CustomerRequestDto> Invalid = new ArrayList<>();
		Workbook  workbook = new XSSFWorkbook(file.getInputStream());
		Sheet sheet = workbook.getSheetAt(0);
		int rowRum  =0;
		Iterator<Row> rowit = sheet.iterator();
		while(rowit.hasNext()){
			Row row = rowit.next();
			if(rowRum == 0)
			{rowRum++; continue;
			}
			Iterator<Cell> cells = row.iterator();
			CustomerRequestDto C = new CustomerRequestDto();
			while(cells.hasNext()) {
				Cell cell = cells.next();
				switch (cell.getColumnIndex()) {
					case 0:
						C.setFirstName((String) cell.getStringCellValue());
                    break;
					case 1:
						C.setLastName((String) cell.getStringCellValue());
						break;
					case 2:
						C.setEmailId((String) cell.getStringCellValue());
						break;
					case 3:
						C.setContactNo(String.valueOf(cell.getNumericCellValue()));
						break;
					case 4:
						String genderCellValue = cell.getStringCellValue();
						C.setGender(Gender.valueOf(genderCellValue.toUpperCase()));
					    break;
					case 5:
						C.setPassword((String) cell.getStringCellValue());
						break;
					case 6:
						C.setAddressLine1((String) cell.getStringCellValue());
					break;
					case 7:
						C.setAddressLine2((String) cell.getStringCellValue());
					break;
					case 8:
						C.setCity((String) cell.getStringCellValue());
					break;
					case 9:
						C.setState((String) cell.getStringCellValue());
					break;
					case 10:
						C.setPincode(String.valueOf(cell.getNumericCellValue()));
					break;
				}
			}
			cs.add(C);
		}
		ValidationsExcelCustomer validity = new ValidationsExcelCustomer(customerRepo);
		validity.ValidCustomer(valid,Invalid,cs);
		List<Customer> cmr = new ArrayList<>();
		List<Address> list1= new ArrayList<>();
		SavingBulkCustomer obj = new SavingBulkCustomer();
		obj.Saveit(cmr,list1,valid);
		List<Address> saved= addressRepo.saveAll(list1);
		List<Customer> savedCustomers = customerRepo.saveAll(cmr);
		return Invalid;
	}

	@Override
	public ByteArrayInputStream CreatePdf(int customerId) {
		log.info("Here the Pdf is being Created");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4);
		try {
			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.open();
// Here to add the  Customer General Details Function is Called
			PdfHelper pdfHelper = new PdfHelper();
			pdfHelper.helper1(customerId,customerRepo,document);
// Here the function to add the Address Details Called
			PdfHelper pdfHelper1 = new PdfHelper();
			pdfHelper1.helper2(customerId,customerRepo,document);
// Here is the table getting Created
			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100f);
			table.setWidths(new float[]{1, 1, 1, 1, 1});
			table.setSpacingBefore(5);

// Here Function Called to Add the Customer Account Details Table
			List<Integer> Accoutnum = new ArrayList<>();
			PdfHelper pdfHelper3 = new PdfHelper();
			pdfHelper3.helper3(customerId, Accoutnum, table, customerRepo);

// Here Transaction table is getting created by calling the funcy
			PdfHelper pdfHelper2 = new PdfHelper();
			pdfHelper2.helper4(Accoutnum,table,transactionService);

			document.add(table);
		} catch (DocumentException e) {
			String errorMessage = "Error occurred while creating the PDF document: " + e.getMessage();
			log.error(errorMessage);
			throw new RuntimeException(errorMessage, e);
		} finally {
			document.close();
		}
		return new ByteArrayInputStream(out.toByteArray());

	}
}
