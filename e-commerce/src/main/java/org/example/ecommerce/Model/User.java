package org.example.ecommerce.Model;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty(message = "User ID can not be empty !")
    private String userID;

    @NotEmpty(message = "User name can not be empty !")
    @Size(min = 5, message = "User Name have to be at least 5 characters !")
    private String userName;

    @NotEmpty(message = "User password can not be empty ! ")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "User password must have at least one character and one digits !")
    @Size(min = 6, message = "User password must be at least 6 characters !")
    private String userPassword;

    @NotEmpty(message = "user email can not be empty !")
    @Email(message = "please enter a valid email !")
    private String userEmail;

    @NotEmpty(message = "User role can not be empty !")
    @Pattern(regexp = "^(admin|Admin|customer|Customer)$", message = "User role must be either customer or admin !")
    private String userRole;

    @NotNull(message = "Balance can not be Empty !")
    @Min(value = 0 , message = "User Balance can not be less than zero !")
    private double userBalance ;


}
