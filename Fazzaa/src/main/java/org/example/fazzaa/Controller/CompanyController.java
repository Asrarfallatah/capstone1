package org.example.fazzaa.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.fazzaa.ApiResponse.ApiResponse;
import org.example.fazzaa.Model.Company;
import org.example.fazzaa.Service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    // get
    @GetMapping("/get")
    public ResponseEntity<?> getCompanyInfo() {

        if (companyService.getAllCompanyInfo().isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("no company information yet in the database to show its information !"));
        }

        return ResponseEntity.status(200).body(companyService.getAllCompanyInfo());
    }

    //add
    @PostMapping("/add")
    public ResponseEntity<?> addCompanyInfo(@Valid @RequestBody Company company, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        companyService.addCompanyInfo(company);
        return ResponseEntity.status(200).body(new ApiResponse("company information has been added successfully to the database!"));
    }

    // update
    @PutMapping("/update/{companyId}")
    public ResponseEntity<?> update(@PathVariable Integer companyId, @Valid @RequestBody Company company, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        boolean result = companyService.updateCompanyInfo(companyId, company);

        if (!result) {
            return ResponseEntity.status(400).body(new ApiResponse("error no company with that id is found to update its information!"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("company information has been updated successfully !"));
    }

    //delete
    @DeleteMapping("/delete/{companyId}")
    public ResponseEntity<?> delete(@PathVariable Integer companyId) {

        boolean result = companyService.deleteCompanyInfo(companyId);

        if (!result) {
            return ResponseEntity.status(400).body(new ApiResponse("error no company with that id is found to update its information!"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("company information deleted successfully !"));
    }

    // 23- get company improvement advice from Ai
    @GetMapping("/ai-improvement/{infoId}")
    public ResponseEntity<?> getAiCompanyImprovementAdvice(@PathVariable Integer infoId) {

        String response = companyService.getAiCompanyImprovementAdvice(infoId);
        return ResponseEntity.status(200).body(response);
    }




}
