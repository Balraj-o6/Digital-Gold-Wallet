package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author Anant
 */

@Data
public class AddressDTO {
    private Integer addressId;
    @NotBlank(message="Street is required")
    private String street;
    @NotBlank(message="City is required")
    private String city;
    @NotBlank(message = "State is required")
    private String state;
    @NotBlank(message = "Postal code is required")
    @Size(min=5, max=10, message="Postal code must be between 5 and 10 characters")
    private String postalCode;
    @NotBlank(message="Country is required")
    private String country;
}
