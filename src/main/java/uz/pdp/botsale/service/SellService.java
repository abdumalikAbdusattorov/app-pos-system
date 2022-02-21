package uz.pdp.botsale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.botsale.entity.*;
import uz.pdp.botsale.entity.enums.StatusSell;
import uz.pdp.botsale.exception.ResourceNotFoundException;
import uz.pdp.botsale.payload.*;
import uz.pdp.botsale.repository.*;
import uz.pdp.botsale.utils.CommonUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class SellService {

    @Autowired
    SellRepository sellRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    MarketRepository marketRepository;

    @Autowired
    CashRepository cashRepository;

    @Autowired
    SellDetailsRepository sellDetailsRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SellDetailCountRepository sellDetailsCountRepository;

    @Autowired
    PurchaseElementsRepository purchaseElementsRepository;

    @Autowired
    ProfitRepository profitRepository;

    public ApiResponseModel saveOrEdit(ReqSell reqSell) {
        ApiResponseModel response = new ApiResponseModel();
        try {
            if (reqSell.getStatusSell() != null && reqSell.getCashId() != null && reqSell.getMarketId() != null && reqSell.getReqSellDetailsList().size() > 0) {
                response.setMessage("Saved");
                response.setSuccess(true);
                Sell sell = new Sell();
                List<SellDetails> sellDetailsList = new ArrayList<>();
                for (ReqSellDetails reqSellDetails : reqSell.getReqSellDetailsList()) {
                    SellDetails sellDetails = new SellDetails();
                    sellDetails.setProduct(productRepository.findByBarCode(reqSellDetails.getProductBarcode()).orElseThrow(() -> new ResourceNotFoundException("Product", "id", reqSellDetails.getProductBarcode())));
                    sellDetails.setCount(reqSellDetails.getCount());
                    sellDetails.setPrice(reqSellDetails.getCount() * purchaseElementsRepository.findByProductBarCode(reqSellDetails.getProductBarcode()).get().getPresentPrice());
                    sellDetailsList.add(sellDetails);
                    sellDetailsRepository.save(sellDetails);
                }
                sell.setSellDetails(sellDetailsList);
                double totalPrice = 0.0;
                sell.setMarket(marketRepository.findById(reqSell.getMarketId()).orElseThrow(() -> new ResourceNotFoundException("Market", "id", reqSell.getMarketId())));
                sell.setCash(cashRepository.findById(reqSell.getCashId()).orElseThrow(() -> new ResourceNotFoundException("Cash", "id", reqSell.getCashId())));
                Cash cash = cashRepository.findById(reqSell.getCashId()).get();
                List<Sell> sells = new ArrayList<>();
                sells.add(sell);
                cash.setSellList(sells);
                if (reqSell.getStatusSell().equals(StatusSell.PAID)) {
                    sell.setStatusSell(StatusSell.PAID);
                } else if (reqSell.getStatusSell().equals(StatusSell.UNPAID) || reqSell.getStatusSell().equals(StatusSell.PARTLYPAID)) {
                    sell.setClient(reqSell.getClient());
                    sell.setStatusSell(reqSell.getStatusSell());
                    sell.getClient().setDebt(totalPrice - reqSell.getClient().getPayed());
                    if (reqSell.getClient().getId() != null) {
                        Client client = clientRepository.findById(reqSell.getClient().getId()).orElseThrow(() -> new ResourceNotFoundException("Clien", "id", reqSell.getClient().getId()));
                        client.setDebt(client.getDebt() + reqSell.getClient().getDebt());
                        clientRepository.save(client);
                        sell.setClient(client);
                    } else {
                        sell.setClient(reqSell.getClient());
                        clientRepository.save(reqSell.getClient());
                    }
                }
                sell.setTotal(totalPrice);
                sellRepository.save(sell);
                for (SellDetails sellDetail : sell.getSellDetails()) {
                    PurchaseElements repository = purchaseElementsRepository.findByProductId(sellDetail.getProduct().getId());
                    PurchaseElements repository2 = purchaseElementsRepository.findPurchaseElementsByProductId(sellDetail.getProduct().getId());
                    Profit profits = new Profit();
                    if (sellDetail.getCount() > repository.getPresentCount()) {
                        double leftCount = sellDetail.getCount() - repository.getPresentCount();
                        Double profit1 = (repository.getPresentPrice() - repository.getIncomePrice()) * repository.getPresentCount();
                        Double profit2 = (repository2.getPresentPrice() - repository2.getIncomePrice()) * leftCount;
                        profits.setTotalProfit(profit1 + profit2);
                        profitRepository.save(profits);
                        repository.setPresentCount(0);
                        repository2.setPresentCount((int) (repository2.getPresentCount() - leftCount));
                    } else {
                        double leftCount = repository.getPresentCount() - sellDetail.getCount();
                        Double profit = (repository.getPresentPrice() - repository.getIncomePrice()) * sellDetail.getCount();
                        repository.setPresentCount((int) leftCount);
                        profits.setTotalProfit(profit);
                        profitRepository.save(profits);
                    }
                }
            } else {
                response.setMessage("must be reqSellDetailsList client marketId cashId statusSell");
                response.setSuccess(false);
                response.setObject(TrayIcon.MessageType.WARNING);
            }
        } catch (Exception e) {
            response.setMessage("Error");
            response.setSuccess(false);
            response.setObject(TrayIcon.MessageType.ERROR);
        }
        return response;
    }

    public ResPageable pageable(Integer page, Integer size) {
        Page<Sell> sells = sellRepository.findAll(CommonUtils.getPageableById(page, size));
        return new ResPageable(sells.getContent(), sells.getTotalElements(), page);
    }

    public ApiResponseModel getSellCol(Long id) {
        try {
            return new ApiResponseModel(true, "Ok", sellRepository.findSellById(id).orElseThrow(() -> new ResourceNotFoundException("Sell", "id", "Error")));
        } catch (Exception e) {
            return new ApiResponseModel(false, "Error", TrayIcon.MessageType.ERROR);
        }
    }
}
