package com.HelloSpring.dto.request;

import com.HelloSpring.model.DocumentType;

import java.time.LocalDate;

public class DocumentClassDTO
{

    private DocumentType documentType;

    private String path;

    private LocalDate CreatedDate;
    private LocalDate ModifiedDate;

}
