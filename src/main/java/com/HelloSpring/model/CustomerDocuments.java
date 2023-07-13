package com.HelloSpring.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CustomerDocuments {

    @Id
    @GeneratedValue
    private int id;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    private String path;

    private LocalDate CreatedDate;
    private LocalDate ModifiedDate;

    @Column(name = "imagedata")
    private byte[] imageData;

}
