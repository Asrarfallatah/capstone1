package org.example.fazzaa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;

    /// /////////////////////////////////////////////////////////////////

    @NotEmpty(message = "Company name can not be empty !")
    @Column(columnDefinition = "VARCHAR(60) NOT NULL")
    private String companyName;

    /// /////////////////////////////////////////////////////////////////

    @NotEmpty(message = "Vision can not be empty !")
    @Column(columnDefinition = "TEXT NOT NULL")
    private String companyVision;

    /// /////////////////////////////////////////////////////////////////

    @NotEmpty(message = "About us can not be empty !")
    @Column(columnDefinition = "TEXT NOT NULL")
    private String aboutUs;

    /// /////////////////////////////////////////////////////////////////

    @NotEmpty(message = "Company policy can not be empty !")
    @Column(columnDefinition = "TEXT NOT NULL")
    private String companyPolicy;

    /// /////////////////////////////////////////////////////////////////

    @NotEmpty(message = "Company goals can not be empty !")
    @Column(columnDefinition = "TEXT NOT NULL")
    private String companyGoals;

    /// /////////////////////////////////////////////////////////////////

    @NotEmpty(message = "Company values can not be empty !")
    @Column(columnDefinition = "TEXT NOT NULL")
    private String companyValues;
}
