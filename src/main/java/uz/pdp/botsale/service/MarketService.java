package uz.pdp.botsale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.botsale.collections.MarketCol;
import uz.pdp.botsale.entity.*;
import uz.pdp.botsale.entity.enums.RoleName;
import uz.pdp.botsale.exception.ResourceNotFoundException;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ApiResponseModel;
import uz.pdp.botsale.payload.ReqMarket;
import uz.pdp.botsale.payload.ResPageable;
import uz.pdp.botsale.repository.*;
import uz.pdp.botsale.utils.CommonUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MarketService {

    @Autowired
    MarketRepository marketRepository;

    @Autowired
    CashRepository cashRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationRepository notificationRepository;

    public ApiResponse saveOrEdit(ReqMarket reqMarket) {
        ApiResponse response = new ApiResponse();
        try {
            if (!reqMarket.getName().equals(marketRepository.findByName(reqMarket.getName())) || reqMarket.getId() != null) {
                if (reqMarket.getName().length() > 0 && reqMarket.getAddress().length() > 0 && reqMarket.getUsers().size() > 0) {
                    response.setSuccess(true);
                    response.setMessage("Saved");
                    response.setMessageType(TrayIcon.MessageType.INFO);
                    Market market = new Market();
                    if (reqMarket.getId() != null) {
                        market = marketRepository.findById(reqMarket.getId()).orElseThrow(() -> new ResourceNotFoundException("Market", "Id", reqMarket.getId()));
                        response.setMessage("Edited");
                    }
                    market.setLan(reqMarket.getLan());
                    market.setLat(reqMarket.getLat());
                    market.setAddress(reqMarket.getAddress());
                    market.setName(reqMarket.getName());
                    List<Purchase> purchaseList = new ArrayList<>();
                    for (Long id : reqMarket.getPurchaseList()) {
                        purchaseList.add(purchaseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Purchase", "id", id)));
                    }
                    market.setPurchaseList(purchaseList);

                    List<User> userList = new ArrayList<>();
                    for (UUID id : reqMarket.getUsers()) {
                        userList.add(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id)));
                    }
                    market.setUsers(userList);

                    List<Cash> cashList = new ArrayList<>();
                    if (reqMarket.getCashList() == null) {
                        Cash cash = new Cash();
                        cash.setName("Kassa 1");
                        cashRepository.save(cash);
                        cashList.add(cash);
                        notificationRepository.save(new Notification("Sizga market " + market.getName() + " qo`shildi! va yangi kassa ham qo`shildi market manzili: " + market.getAddress(), true, RoleName.ROLE_DIRECTOR, TrayIcon.MessageType.INFO));
                    } else {
                        for (Integer id : reqMarket.getCashList()) {
                            cashList.add(cashRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cash", "Id", id)));
                        }
                    }
                    market.setCashList(cashList);
                    marketRepository.save(market);
                }
            } else {
                response.setMessage("This market already exist");
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
        Page<Market> marketPage = marketRepository.findAll(CommonUtils.getPageableById(page, size));
        if (!search.equals("all")) {
            marketPage = marketRepository.findByNameStartingWithIgnoreCase(search, CommonUtils.getPageableById(page, size));
        }
        return new ResPageable(marketPage.getContent(), marketPage.getTotalElements(), page);
    }

    public ApiResponse changeActive(Integer id, boolean active) {
        try {
            Market market = marketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Market", "Id", id));
            market.setActive(active);
            return new ApiResponse(active ? "Activated" : "Blocked", true, TrayIcon.MessageType.INFO);
        } catch (Exception e) {
            return new ApiResponse("Error", false, TrayIcon.MessageType.ERROR);
        }
    }

    public ApiResponse removeMarket(Integer id) {
        try {
            marketRepository.deleteById(id);
            return new ApiResponse("Deleted", true, TrayIcon.MessageType.INFO);
        } catch (Exception e) {
            return new ApiResponse("Error", false, TrayIcon.MessageType.ERROR);
        }
    }

    public ApiResponseModel getMarketCol(Integer id) {
        try {
            Optional<MarketCol> marketById = marketRepository.findMarketById(id);
            return new ApiResponseModel(true, "Ok", marketById.get());
        } catch (Exception e) {
            return new ApiResponseModel(false, "Error", TrayIcon.MessageType.ERROR);
        }
    }
}
