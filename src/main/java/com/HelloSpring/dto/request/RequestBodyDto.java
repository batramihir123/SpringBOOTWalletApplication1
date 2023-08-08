package com.HelloSpring.dto.request;

import com.HelloSpring.aop.ValidateCustomerId;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestBodyDto
{
    @ValidateCustomerId
    private int customerId;

    @NotNull(message = "Frequency should not be null")
    private String frequency;

    private int day;
}
