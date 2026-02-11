package com.url.shortner.records;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCustomerRequest(

        @NotBlank(message = "Name is mandatory")
        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
        String name,

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email format is invalid")
        String email,

        @NotBlank(message = "City is mandatory")
        @Size(min = 2, max = 30, message = "City must be between 2 and 30 characters")
        String city
) {}
