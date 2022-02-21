package uz.pdp.botsale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.botsale.entity.Company;
import uz.pdp.botsale.entity.Notification;
import uz.pdp.botsale.entity.enums.RoleName;
import uz.pdp.botsale.exception.ResourceNotFoundException;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ReqCompany;
import uz.pdp.botsale.payload.ResPageable;
import uz.pdp.botsale.repository.CompanyRepository;
import uz.pdp.botsale.repository.NotificationRepository;
import uz.pdp.botsale.utils.CommonUtils;

import java.awt.*;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    NotificationRepository notificationRepository;

    public ApiResponse saveOrEdit(ReqCompany reqCompany) {
        ApiResponse response = new ApiResponse();
        try {
            if (!reqCompany.getName().equals(companyRepository.findByName(reqCompany.getName())) || reqCompany.getId() != null) {
                if (reqCompany.getName().length() > 0 && reqCompany.getPhoneNumber().length() == 13 && reqCompany.getAgentName().length() > 0) {
                    response.setMessage("Saved");
                    response.setSuccess(true);
                    response.setMessageType(TrayIcon.MessageType.INFO);
                    Company company = new Company();
                    if (reqCompany.getId() != null) {
                        company = companyRepository.findById(reqCompany.getId()).orElseThrow(() -> new ResourceNotFoundException("Company", "id", reqCompany.getId()));
                        response.setMessage("Edited");
                    }
                    company.setName(reqCompany.getName());
                    company.setAgentName(reqCompany.getAgentName());
                    company.setPhoneNumber(reqCompany.getPhoneNumber());
                    notificationRepository.save(new Notification("Sizga yangi kompanya (" + company.getName() + ") qo`shildi!", true, RoleName.ROLE_DIRECTOR, TrayIcon.MessageType.INFO));
                    companyRepository.save(company);
                } else if (reqCompany.getName().length() == 0) {
                    response.setMessage("Must be name");
                    response.setSuccess(false);
                    response.setMessageType(TrayIcon.MessageType.WARNING);
                } else if (reqCompany.getAgentName().length() != 13) {
                    response.setMessage("Must be phone number length 13!");
                    response.setSuccess(false);
                    response.setMessageType(TrayIcon.MessageType.WARNING);
                } else if (reqCompany.getAgentName().length() == 0) {
                    response.setMessage("Must be agent name");
                    response.setSuccess(false);
                    response.setMessageType(TrayIcon.MessageType.WARNING);
                }
            } else if (reqCompany.getName().equals(companyRepository.findByName(reqCompany.getName()))) {
                response.setMessage("This massage already exist");
                response.setSuccess(false);
                response.setMessageType(TrayIcon.MessageType.WARNING);
            }
        } catch (Exception e) {
            response.setMessage("Error");
            response.setSuccess(false);
            response.setMessageType(TrayIcon.MessageType.ERROR);
        }
        return response;
    }

    public ResPageable pageable(Integer page, Integer size, String search) {
        Page<Company> companies = companyRepository.findAllByActive(true, CommonUtils.getPageableById(page, size));
        if (!search.equals("all")) {
            companies = companyRepository.findAllByNameStartingWithIgnoreCaseOrAgentNameStartingWithIgnoreCase(search, search, CommonUtils.getPageableById(page, size));
        }
        return new ResPageable(companies.getContent(), companies.getTotalElements(), page);
    }

    public ApiResponse changeActive(Long id, boolean active) {
        try {
            Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("company", "id", id));
            company.setActive(active);
            companyRepository.save(company);
            return new ApiResponse(active ? "Activated" : "Blocked", true, TrayIcon.MessageType.INFO);
        } catch (Exception e) {
            return new ApiResponse("Error", false, TrayIcon.MessageType.ERROR);
        }
    }

    public ApiResponse removeCompany(Long id) {
        try {
            companyRepository.deleteById(id);
            return new ApiResponse("Deleted", true, TrayIcon.MessageType.INFO);
        } catch (Exception e) {
            return new ApiResponse("Error", false, TrayIcon.MessageType.ERROR);
        }
    }
}
