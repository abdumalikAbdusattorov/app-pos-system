package uz.pdp.botsale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.botsale.entity.enums.DashboardEnumType;
import uz.pdp.botsale.payload.ApiResponseModel;
import uz.pdp.botsale.payload.ResPageable;
import uz.pdp.botsale.repository.ClientRepository;
import uz.pdp.botsale.repository.CompanyRepository;
import uz.pdp.botsale.repository.PurchaseElementsRepository;
import uz.pdp.botsale.repository.SellRepository;

import java.awt.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    PurchaseElementsRepository purchaseElementsRepository;

    @Autowired
    SellRepository sellRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CompanyRepository companyRepository;

/*    public ApiResponseModel getAllInformation() {
        Double income = purchaseElementsRepository.findAllByCountAndProduct(startDate, endDate);
        Double outLay = sellRepository.findAllByTotal(startDate, endDate);
        Double profit = sellRepository.findAllByTotalAndCashAmAndCash(startDate, endDate);
        Double visitors = sellRepository.findAllByTotalAndCash(startDate, endDate);
        Double debt = clientRepository.findAllByDebt(startDate, endDate);
        Double company = companyRepository.findAllByName(startDate, endDate);
        List<Double> doubles = new ArrayList<>();
        doubles.add(income);
        doubles.add(outLay);
        doubles.add(profit);
        doubles.add(visitors);
        doubles.add(debt);
        doubles.add(company);
        return new ApiResponseModel(true, "Ok ^_~", doubles);
    }*/

    public ApiResponseModel getInformation(DashboardEnumType type, Timestamp startDate, Timestamp endDate) {
        try {
            ApiResponseModel response = new ApiResponseModel();
            switch (type) {
                case INCOME:
                    Double income = purchaseElementsRepository.findAllByCountAndProduct(startDate, endDate);
                    response.setSuccess(true);
                    response.setMessage("sizning " + startDate.toString().substring(0, 10) + "dan " + endDate.toString().substring(0, 10) + "gacha bo`lgan maxsulotlar uchun chiqimlar ");
                    response.setObject(income);
                    break;
                case OUTLAY:
                    Double outLay = sellRepository.findAllByTotal(startDate, endDate);
                    response.setSuccess(true);
                    response.setMessage("sizning " + startDate.toString().substring(0, 10) + "dan " + endDate.toString().substring(0, 10) + "gacha bo`lgan vaqtdagi maxsulotlar kirim ");
                    response.setObject(outLay);
                    break;
                case PROFIT:
                    Double profit = sellRepository.findAllByTotalAndCashAmAndCash(startDate, endDate);
                    response.setSuccess(true);
                    response.setObject(profit);
                    break;
                case VISITORS:
                    Double visitors = sellRepository.findAllByTotalAndCash(startDate, endDate);
                    response.setSuccess(true);
                    response.setMessage(startDate.toString().substring(0, 10) + "dan " + endDate.toString().substring(0, 10) + "gacha tashrif buyuruvchilar soni ");
                    response.setObject(visitors);
                    break;
                case DEBT:
                    Double debt = clientRepository.findAllByDebt(startDate, endDate);
                    response.setSuccess(true);
                    response.setMessage(startDate.toString().substring(0, 10) + "dan " + endDate.toString().substring(0, 10) + "gacha bo`lgan jami qarz miqdori ");
                    response.setObject(debt);
                    break;
                case COMPANY:
                    Double company = companyRepository.findAllByName(startDate, endDate);
                    response.setSuccess(true);
                    response.setMessage(startDate.toString().substring(0, 10) + "dan " + endDate.toString().substring(0, 10) + "gacha bo`lgan hamkorlar ro`yhati");
                    response.setObject(company);
                    break;
            }
            return response;
        } catch (Exception e) {
            return new ApiResponseModel(false, "Error", TrayIcon.MessageType.WARNING);
        }
    }

}
