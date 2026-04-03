package com.example.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Anant
 */

@Data
public class UserDTO {
    private Integer userId;
    @NotBlank(message="Name is required")
    private String name;
    @NotBlank(message="Email is required")
    @Email(message="Invalid email format")
    private String email;
    @PositiveOrZero(message="Balance cannot be negative")
    private BigDecimal balance;
    @Valid
    private AddressDTO address;

}
