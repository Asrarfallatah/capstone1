package org.example.fazzaa.Service;


import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Model.Company;
import org.example.fazzaa.Model.EventRequest;
import org.example.fazzaa.Model.Feedback;
import org.example.fazzaa.Model.WithdrawRequest;
import org.example.fazzaa.Repository.CompanyRepository;
import org.example.fazzaa.Repository.EventRequestRepository;
import org.example.fazzaa.Repository.FeedbackRepository;
import org.example.fazzaa.Repository.WithdrawRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    // connect to the database
    private final CompanyRepository companyRepository;
    private final EventRequestRepository eventRequestRepository;
    private final FeedbackRepository feedbackRepository;
    private final WithdrawRequestRepository withdrawRequestRepository;
    private final AiService aiService;


    // get all company info
    public List<Company> getAllCompanyInfo() {
        return companyRepository.findAll();
    }

    // add company info to the database
    public void addCompanyInfo(Company company) {
        companyRepository.save(company);
    }

   // update company info
    public boolean updateCompanyInfo(Integer infoId, Company company) {

        // check if the company exists
        Company oldcompany = companyRepository.findCompanyByCompanyId(infoId);
        if (oldcompany == null) {
            return false;
        }

        // update
        oldcompany.setCompanyName(company.getCompanyName());
        oldcompany.setCompanyVision(company.getCompanyVision());
        oldcompany.setAboutUs(company.getAboutUs());
        oldcompany.setCompanyPolicy(company.getCompanyPolicy());
        oldcompany.setCompanyGoals(company.getCompanyGoals());
        oldcompany.setCompanyValues(company.getCompanyValues());

        // save it
        companyRepository.save(oldcompany);
        return  true;
    }

   // delete company info
    public boolean deleteCompanyInfo(Integer infoId) {

        // check if the company exists
        Company company = companyRepository.findCompanyByCompanyId(infoId);
        if (company == null) {
            return false;
        }

        // delete it
        companyRepository.delete(company);
        return true;
    }

    // 23- get company improvement advice from Ai
    public String getAiCompanyImprovementAdvice(Integer infoId) {

        Company company = companyRepository.findCompanyByCompanyId(infoId);
        if (company == null) {
            return "error no company with this information is found  in the database to show it !";
        }

        List<EventRequest> requests = eventRequestRepository.findAll();
        List<Feedback> feedbacks = feedbackRepository.findAll();
        List<WithdrawRequest> withdraws = withdrawRequestRepository.findAll();

        StringBuilder sb = new StringBuilder();

        sb.append("Analyze this company information:\n\n")
                .append("Company Name: ").append(company.getCompanyName()).append("\n")
                .append("Vision: ").append(company.getCompanyVision()).append("\n")
                .append("About Us: ").append(company.getAboutUs()).append("\n")
                .append("Policy: ").append(company.getCompanyPolicy()).append("\n")
                .append("Goals: ").append(company.getCompanyGoals()).append("\n")
                .append("Values: ").append(company.getCompanyValues()).append("\n\n");


        sb.append("Here are event request trends:\n");
        for (EventRequest r : requests) {
            sb.append("- ").append(r.getEventType())
                    .append(" | Budget: ").append(r.getBudget())
                    .append("\n");
        }
        sb.append("\n");


        sb.append("Here is feedback from clients:\n");
        for (Feedback f : feedbacks) {
            sb.append("- Rating: ").append(f.getRating())
                    .append(" | Comment: ").append(f.getComment())
                    .append("\n");
        }
        sb.append("\n");


        sb.append("Here are withdrawal reasons:\n");
        for (WithdrawRequest w : withdraws) {
            sb.append("- Reason: ").append(w.getReason())
                    .append(" | Fee Paid: ").append(w.getFeePaid())
                    .append("\n");
        }
        sb.append("\n");


        sb.append("Please provide professional recommendations on:\n")
                .append("- how to improve company policy\n")
                .append("- how to attract more clients\n")
                .append("- how to increase trust and satisfaction\n")
                .append("- how to enhance brand identity\n")
                .append("- how to strengthen workflow\n")
                .append("- how to create fun and unique client experiences\n");

        return aiService.generateResponse(sb.toString());
    }
}
