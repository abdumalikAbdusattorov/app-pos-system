package uz.pdp.botsale.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.botsale.collections.StockCol;
import uz.pdp.botsale.entity.PurchaseElements;
import uz.pdp.botsale.entity.Stock;
import uz.pdp.botsale.entity.StockDetail;
import uz.pdp.botsale.exception.ResourceNotFoundException;
import uz.pdp.botsale.payload.*;
import uz.pdp.botsale.repository.MarketRepository;
import uz.pdp.botsale.repository.PurchaseElementsRepository;
import uz.pdp.botsale.repository.StockDetailRepository;
import uz.pdp.botsale.repository.StockRepository;
import uz.pdp.botsale.utils.CommonUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    MarketRepository marketRepository;

    @Autowired
    StockDetailRepository stockDetailRepository;

    @Autowired
    PurchaseElementsRepository purchaseElementsRepository;

    public ApiResponse saveOrEdit(ReqStock reqStock) {
        ApiResponse response = new ApiResponse();
        try {
            if (reqStock.getName().length() > 0 && reqStock.getPercent() > 0 && reqStock.getStartData() != null && reqStock.getEndData() != null && reqStock.getReqStockDetails().size() > 0) {
                response.setMessage("Saved");
                response.setSuccess(true);
                response.setMessageType(TrayIcon.MessageType.INFO);
                Stock stock = new Stock();
                if (reqStock.getId() != null) {
                    stock = stockRepository.findById(reqStock.getId()).orElseThrow(() -> new ResourceNotFoundException("Stoke", "id", reqStock.getId()));
                    response.setMessage("Edited");
                }
                stock.setName(reqStock.getName());
                stock.setPercent(reqStock.getPercent());
                stock.setStartData(reqStock.getStartData());
                stock.setEndData(reqStock.getEndData());
                stock.setMarket(marketRepository.findById(reqStock.getMarketId()).orElseThrow(() -> new ResourceNotFoundException("Market", "id", reqStock.getMarketId())));
                Stock save = stockRepository.save(stock);
                for (ReqStockDetail reqStockDetail : reqStock.getReqStockDetails()) {
                    StockDetail stockDetail = new StockDetail();
                    if (reqStockDetail.getId() != null) {
                        stockDetail = stockDetailRepository.findById(reqStockDetail.getId()).orElseThrow(() -> new ResourceNotFoundException("stockProduct", "id", reqStockDetail.getId()));
                    }
                    PurchaseElements purchaseElements = purchaseElementsRepository.findById(reqStockDetail.getProductId()).orElseThrow(() -> new ResourceNotFoundException("product", "id", reqStockDetail.getProductId()));
                    stockDetail.setStock(save);
                    stockDetail.setPurchaseElements(purchaseElements);
                    stockDetail.setBeforePrice(purchaseElements.getSellPrice());
                    stockDetail.setAfterPrice(purchaseElements.getSellPrice() * (100 - reqStock.getPercent()) / 100);
                    purchaseElements.setPresentPrice(stockDetail.getAfterPrice());
                    purchaseElementsRepository.save(purchaseElements);
                    stockDetailRepository.save(stockDetail);
//                    ****************************************************************************
                    URL urlApi = new URL("http://worldtimeapi.org/api/timezone/asia/tashkent");
                    URLConnection connection = urlApi.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    JSONObject jsonObject = new JSONObject(reader.readLine());
                    String thisTime=jsonObject.getString("datetime");
                    if (reqStock.getEndData().toString().substring(0 - 10).equals(thisTime.substring(0 - 10))) {
                        PurchaseElements purchaseElements1 = purchaseElementsRepository.findById(reqStockDetail.getProductId()).get();
                        purchaseElements1.setPresentPrice(purchaseElements.getPresentPrice() * (1 - reqStock.getPercent()) / 100);
                    }
//                    *****************************************************************************************************************
                }
            } else {
                response.setMessage("must be name percent startData endData reqStockDetails marketId");
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

    public ResPageable getPageable(Integer page, Integer size, String search) {
        Page<Stock> stockPage = stockRepository.findAll(CommonUtils.getPageable(page, size));
        if (!search.equals("all")) {
            stockPage = stockRepository.findAllByNameStartingWithIgnoreCase(search, CommonUtils.getPageable(page, size));
        }
        return new ResPageable(stockPage.getContent(), stockPage.getTotalElements(), page);
    }

    public ApiResponse changeActive(Integer id, boolean active) {
        try {
            Stock stock = stockRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Stock", "Id", id));
            stock.setActive(active);
            stockRepository.save(stock);
            return new ApiResponse(active ? "Activated" : "Blocked", true, TrayIcon.MessageType.INFO);
        } catch (Exception e) {
            return new ApiResponse("Error", false, TrayIcon.MessageType.ERROR);
        }
    }

    public ApiResponseModel getStockCol(Integer id) {
        try {
            return new ApiResponseModel(true, "Ok ^_^", stockRepository.findStockById(id).get());
        } catch (Exception e) {
            return new ApiResponseModel(false, "Error", TrayIcon.MessageType.ERROR);
        }
    }
}
