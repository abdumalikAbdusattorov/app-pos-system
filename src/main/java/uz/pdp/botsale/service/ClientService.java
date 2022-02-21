package uz.pdp.botsale.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.botsale.entity.Client;
import uz.pdp.botsale.entity.Notification;
import uz.pdp.botsale.entity.enums.RoleName;
import uz.pdp.botsale.exception.ResourceNotFoundException;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ReqClient;
import uz.pdp.botsale.payload.ResPageable;
import uz.pdp.botsale.repository.ClientRepository;
import uz.pdp.botsale.repository.NotificationRepository;
import uz.pdp.botsale.utils.CommonUtils;

import java.awt.*;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    NotificationRepository notificationRepository;

    public ApiResponse saveOrEdit(ReqClient reqClient) {
        ApiResponse response = new ApiResponse();
        try {
            if (reqClient.getName().length() > 0 && reqClient.getPhoneNumber().length() == 13 && reqClient.getDebt() != null) {
                response.setMessage("Saved");
                response.setSuccess(true);
                response.setMessageType(TrayIcon.MessageType.INFO);
                Client client = new Client();
                if (reqClient.getId() != null) {
                    client = clientRepository.findById(reqClient.getId()).orElseThrow(() -> new ResourceNotFoundException("client", "id", reqClient.getId()));
                    response.setMessage("Edited");
                }
                client.setName(reqClient.getName());
                client.setPhoneNumber(reqClient.getPhoneNumber());
                client.setDebt(reqClient.getDebt());
                clientRepository.save(client);
                notificationRepository.save(new Notification("Qarzdorlar ro`yxatiga: " + client.getName() + "(" + client.getDebt() + ") sum qarz bilan qo`shildi!", true, RoleName.ROLE_DIRECTOR, TrayIcon.MessageType.INFO));
            } else if (reqClient.getName().length() == 0) {
                response.setMessage("must be name !");
                response.setSuccess(false);
                response.setMessageType(TrayIcon.MessageType.WARNING);
            } else if (reqClient.getPhoneNumber().length() != 13) {
                response.setMessage("must be phoneNumber length 13");
                response.setSuccess(false);
                response.setMessageType(TrayIcon.MessageType.WARNING);
            } else if (reqClient.getDebt() == null) {
                response.setMessage("must be  debt");
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
        Page<Client> clientPage = clientRepository.findAll(CommonUtils.getPageableById(page, size));
        if (!search.equals("all")) {
            clientPage = clientRepository.findAllByNameStartingWithIgnoreCase(search, CommonUtils.getPageableById(page, size));
        }
        return new ResPageable(clientPage.getContent(), clientPage.getTotalElements(), page);
    }

//   public ApiResponse changeActive(Long id, boolean active) {
//        try {
//            Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("client", "id", id));
//            client.s(active);
//            clientRepository.save(client);
//            return new ApiResponse(active ? "Activated" : "Blocked", true, TrayIcon.MessageType.INFO);
//        } catch (Exception e) {
//            return new ApiResponse("Error", false, TrayIcon.MessageType.ERROR);
//        }
//    }

   public ApiResponse removeClient(Long id) {
       try {
           clientRepository.deleteById(id);
           return new ApiResponse("Deleted", true, TrayIcon.MessageType.INFO);
       } catch (Exception e) {
           return new ApiResponse("Error", false, TrayIcon.MessageType.ERROR);
       }
   }
}
