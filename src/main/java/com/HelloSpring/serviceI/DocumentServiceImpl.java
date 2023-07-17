package com.HelloSpring.serviceI;

import com.HelloSpring.GlobalException.ResourceNotFoundException;
import com.HelloSpring.model.Customer;
import com.HelloSpring.model.CustomerDocuments;
import com.HelloSpring.model.DocumentType;
import com.HelloSpring.repo.CustomerRepo;
import com.HelloSpring.repo.DocumentRepoJpaRepository;
import com.HelloSpring.service.DocumentService;
import com.HelloSpring.util.ImageUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepoJpaRepository dr;

    @Autowired
    private CustomerRepo cs;

    private final String Folder_path = "C:/images/";
    @Override
    public String uploadFileSystem(MultipartFile file, int customerId, DocumentType type) throws IOException {

        String filepath = Folder_path + file.getOriginalFilename();
        String format = file.getOriginalFilename();
        String like = format.substring(format.lastIndexOf(".")+1);
        log.info(like);
        System.out.println(like+",,,,,,.....................");

        System.out.println(type);

        System.out.println(customerId+"................");


        if (!like.equals("png") && !like.equals("jpeg") && !like.equals("jpg")) {
            throw new ResourceNotFoundException("Format Is Unsupported");
        }

        Optional<Customer> cus = cs.findById(customerId);
        if(cus.isEmpty())
        {
            throw new ResourceNotFoundException("User DoesNot Exist");
        }
        Optional<CustomerDocuments> op = dr.findByCustomerCustomerIdAndDocumentType(customerId, type);
        System.out.println(op+"...................");
        if(op.isPresent())
        {
            throw new ResourceNotFoundException("User has already this document ");
        }
        CustomerDocuments cr = dr.save(CustomerDocuments.builder()
                .documentType(type)
                .customer(cus.get())
                .path(filepath)
                .CreatedDate(LocalDate.now()).ModifiedDate(LocalDate.now()).build());

        file.transferTo(new File(filepath));

        if(cr != null)
        {
            return "file Upload Successfully" + filepath;
        }

        return null;
    }

    @Override
    public byte[] downloadImage(int customerId, DocumentType Doc) throws IOException {

        CustomerDocuments customerDocuments = dr.findByCustomerCustomerIdAndDocumentType(customerId,Doc).orElseThrow(()->new ResourceNotFoundException("User DoesNot Exist"));
//        if(dbImageData.isEmpty()){
//            throw new ResourceNotFoundException("User DoesNot Exist");
//        }

//        CustomerDocuments customerDocuments=dbImageData.get();
        String imagePath=customerDocuments.getPath();
        Path omg= Paths.get(imagePath);


        byte[] images = Files.readAllBytes(omg);
         return images;
    }



    }


