package uz.pdp.botsale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.botsale.collections.BrandCol;
import uz.pdp.botsale.entity.Brand;
import uz.pdp.botsale.entity.Notification;
import uz.pdp.botsale.exception.ResourceNotFoundException;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ApiResponseModel;
import uz.pdp.botsale.payload.ReqBrand;
import uz.pdp.botsale.payload.ResPageable;
import uz.pdp.botsale.repository.AttachmentRepository;
import uz.pdp.botsale.repository.BrandRepository;
import uz.pdp.botsale.repository.NotificationRepository;
import uz.pdp.botsale.utils.CommonUtils;

import java.awt.*;
import java.util.Optional;

@Service
public class BrandService {

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    NotificationRepository notificationRepository;

    public ApiResponse saveOrEdit(ReqBrand reqBrand) {
        ApiResponse response = new ApiResponse();
        try {
            if (!
                    reqBrand.getName().equals(brandRepository.findByName(reqBrand.getName())) /*&& reqBrand.getBrandIcon() != null*/) {
                response.setMessage("Saved");
                response.setSuccess(true);
                response.setMessageType(TrayIcon.MessageType.INFO);
                Brand brand = new Brand();
                if (reqBrand.getId() != null) {
                    brand = brandRepository.findById(reqBrand.getId()).orElseThrow(() -> new ResourceNotFoundException("Brand", "id", reqBrand.getId()));
                    response.setMessage("Edited");
                }
//                brand.setBrandIcon(attachmentRepository.findById(reqBrand.getBrandIcon()).orElseThrow(() -> new ResourceNotFoundException("BranIcon", "id", reqBrand.getBrandIcon())));
                brand.setName(reqBrand.getName());
                notificationRepository.save(new Notification("sizga yangi Brand:" + brand.getName() + " qo`shildi!", true, TrayIcon.MessageType.INFO));
                brandRepository.save(brand);
            } else if (reqBrand.getName().equals(brandRepository.findByName(reqBrand.getName()))) {
                response.setMessage("This massage already exist");
                response.setSuccess(false);
                response.setMessageType(TrayIcon.MessageType.WARNING);
            }
//            else if (reqBrand.getBrandIcon() == null) {
//                response.setMessage("siz rasm tanlang");
//                response.setSuccess(false);
//                response.setMessageType(TrayIcon.MessageType.WARNING);
//            }
            else if (reqBrand.getName() == null) {
                response.setMessage("Brand nomini yozing!");
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
        Page<Brand> brandPage = brandRepository.findAll(CommonUtils.getPageableById(page, size));
        if (!search.equals("all")) {
            brandPage = brandRepository.findAllByNameStartingWithIgnoreCase(search, CommonUtils.getPageableById(page, size));
        }
        return new ResPageable(brandPage.getContent(), brandPage.getTotalElements(), page);
    }

    public ApiResponse changeActive(Integer id, boolean active) {
        try {
            Brand brand = brandRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("brand", "id", id));
            brand.setActive(active);
            brandRepository.save(brand);
            return new ApiResponse(active ? "Activated" : "Blocked", true, TrayIcon.MessageType.INFO);
        } catch (Exception e) {
            return new ApiResponse("Error", false, TrayIcon.MessageType.ERROR);
        }
    }

    public ApiResponseModel getBrandCol(Integer id) {
        try {
            return new ApiResponseModel(true, "OK ;)",  brandRepository.findBrandById(id).get());
        } catch (Exception e) {
            return new ApiResponseModel(false, "Error", TrayIcon.MessageType.ERROR);
        }
    }

    public ApiResponse removeBrand(Integer id) {
        try {
            brandRepository.deleteById(id);
            return new ApiResponse("Deleted", true, TrayIcon.MessageType.INFO);
        } catch (Exception e) {
            return new ApiResponse("Error", false, TrayIcon.MessageType.ERROR);
        }
    }
}
