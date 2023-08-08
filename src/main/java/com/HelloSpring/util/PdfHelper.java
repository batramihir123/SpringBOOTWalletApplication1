package com.HelloSpring.util;

import com.HelloSpring.GlobalException.ResourceNotFoundException;
import com.HelloSpring.model.Account;
import com.HelloSpring.model.Address;
import com.HelloSpring.model.Customer;
import com.HelloSpring.model.Transaction;
import com.HelloSpring.repo.CustomerRepo;
import com.HelloSpring.service.TransactionService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import java.util.List;

import static com.HelloSpring.constants.StringConstants.CUSTOMER_DETAILS_TITLE;

public class PdfHelper
{
    public   void helper1(int customerId ,CustomerRepo customerRepo,Document document)
    {
        String title = "Customer ";
        Customer cust = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Not Exist"));

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
        Paragraph titlePara = new Paragraph(title, titleFont);
        titlePara.setAlignment(Element.ALIGN_CENTER);
        document.add(titlePara);


        String customerDetails = CUSTOMER_DETAILS_TITLE;
        customerDetails += "Customer ID: " + customerId + "      " +  "First Name :" + cust.getFirstName()+ "      " +"Last Name:" + cust.getLastName() + "     " + "EmailId:" + cust.getEmailId()  + "  "+ "Gender: " + cust.getGender().toString();

        Font detailsFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        Paragraph detailsPara = new Paragraph(customerDetails, detailsFont);
        detailsPara.setAlignment(Element.ALIGN_LEFT);
        document.add(detailsPara);

    }

    public  void helper2(int customerId, CustomerRepo customerRepo,Document document)
    {
        String title1 = "Address ";
        String title3 = "\n";


        Customer cust = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Not Exist"));
        Font titleFont1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
        Paragraph titlePara1 = new Paragraph(title1, titleFont1);
        titlePara1.setAlignment(Element.ALIGN_CENTER);
        document.add(titlePara1);

        Address as = cust.getAddress();

        String AddressDetals = "Address Details:\n";
        AddressDetals += "AddressLine1: " + as.getAddressLine1() + "      " +  "AddressLine2:" +as.getAddressLine2()+ "      " +"City:" + as.getCity()+ "     " + "State:" + as.getCity()  + "  "+ "Pincode: " + String.valueOf(as.getPincode()) +"\n";
        // Add more customer details as needed
        Font detailsFont1 = FontFactory.getFont(FontFactory.HELVETICA, 12);
        Paragraph detailsPara1= new Paragraph(AddressDetals, detailsFont1);
        detailsPara1.setAlignment(Element.ALIGN_LEFT);
        document.add(detailsPara1);

        Font titleFont2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
        Paragraph titlePara2 = new Paragraph(title3, titleFont2);
        titlePara2.setAlignment(Element.ALIGN_LEFT);
        document.add(titlePara2);


    }

    public void helper3(int customerId , List<Integer> Accoutnum, PdfPTable table ,CustomerRepo customerRepo)
    {
        Customer cust = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Not Exist"));

      table.setSpacingBefore(5);
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(CMYKColor.GRAY);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);

        List<Account> am = cust.getAccoutns();
        font.setColor(CMYKColor.WHITE);
        cell.setPhrase(new Phrase("AccountNumber", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("AccountType", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Description", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("OpeningBalance", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("OpeningDate", font));
        table.addCell(cell);

        for(Account aj :am)
        {
            table.addCell(String.valueOf(aj.getAccountNumber()));
            table.addCell(aj.getAccountType().toString());
            table.addCell(aj.getDescription());
            table.addCell(String.valueOf(aj.getOpeningBalance()));
            table.addCell(aj.getOpeningDate().toString());

            Accoutnum.add(aj.getAccountNumber());
        }

    }

    public void helper4(List<Integer>Accoutnum,PdfPTable table ,TransactionService transactionService )
    {
        table.setSpacingBefore(5);
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(CMYKColor.GRAY);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);

        font.setColor(CMYKColor.WHITE);
        cell.setPhrase(new Phrase("Description", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("TransactionType", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("TO_Account", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("From_Account", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Amount", font));
        table.addCell(cell);

        for (Integer ac : Accoutnum) {

            List<Transaction> transactions = transactionService.getTransactionsByAccountNumber(ac);

            for (Transaction transaction : transactions) {

                table.addCell(transaction.getDescription());
                table.addCell(transaction.getTransactiontype().toString());
                if (transaction.getToAccount() != null) {
                    table.addCell(transaction.getToAccount().toString());
                } else {
                    table.addCell("0");
                }

                if (transaction.getFromAccount() != null) {
                    table.addCell(transaction.getFromAccount().toString());
                } else {
                    table.addCell("0");
                }
                table.addCell(String.valueOf(transaction.getAmount()));

            }



        }

    }



}
