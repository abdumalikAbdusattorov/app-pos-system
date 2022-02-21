package uz.pdp.botsale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.botsale.collections.ProductCol;
import uz.pdp.botsale.entity.Notification;
import uz.pdp.botsale.entity.Product;
import uz.pdp.botsale.entity.enums.RoleName;
import uz.pdp.botsale.exception.ResourceNotFoundException;
import uz.pdp.botsale.payload.ApiResponse;
import uz.pdp.botsale.payload.ApiResponseModel;
import uz.pdp.botsale.payload.ReqProduct;
import uz.pdp.botsale.payload.ResPageable;
import uz.pdp.botsale.repository.BrandRepository;
import uz.pdp.botsale.repository.CategoryRepository;
import uz.pdp.botsale.repository.NotificationRepository;
import uz.pdp.botsale.repository.ProductRepository;
import uz.pdp.botsale.utils.CommonUtils;

import java.awt.*;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    NotificationRepository notificationRepository;

    public ApiResponse saveOrEdit(ReqProduct reqProduct) {
        ApiResponse response = new ApiResponse();
        try {
            if (!reqProduct.getName().equals(productRepository.findByName(reqProduct.getName())) || reqProduct.getId() != null) {
                if (reqProduct.getName().length() > 0 && reqProduct.getBarCode().length() == 13 && reqProduct.getCategoryId() != null && reqProduct.getBrandId() != null || reqProduct.getId() != null) {
                    response.setSuccess(true);
                    response.setMessage("Saved");
                    Product product = new Product();
                    response.setMessageType(TrayIcon.MessageType.INFO);
                    if (reqProduct.getId() != null) {
                        product = productRepository.findById(reqProduct.getId()).orElseThrow(() -> new ResourceNotFoundException("Product", "Id", reqProduct.getId()));
                        response.setMessage("Edit");
                    }
                    product.setCategory(categoryRepository.findById(reqProduct.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", reqProduct.getCategoryId())));
                    product.setBrand(brandRepository.findById(reqProduct.getBrandId()).orElseThrow(() -> new ResourceNotFoundException("Brand", "id", reqProduct.getBrandId())));
                    product.setName(reqProduct.getName());
                    product.setBarCode(reqProduct.getBarCode());
                    notificationRepository.save(new Notification("Sizga product " + product.getName() + " qo`shildi sanasi(" + product.getCreatedAt() + ")", true, RoleName.ROLE_WAREHOUSE_MANAGER, TrayIcon.MessageType.INFO));
                    productRepository.save(product);
                } else if (reqProduct.getName().length() == 0) {
                    response.setMessage("must be name");
                    response.setSuccess(false);
                    response.setMessageType(TrayIcon.MessageType.WARNING);
                } else if (reqProduct.getBarCode().length() != 13) {
                    response.setMessage("must Barcode length be 13");
                    response.setSuccess(false);
                    response.setMessageType(TrayIcon.MessageType.WARNING);
                } else if (reqProduct.getCategoryId() == null) {
                    response.setMessage("must be Category");
                    response.setSuccess(false);
                    response.setMessageType(TrayIcon.MessageType.WARNING);
                } else if (reqProduct.getBrandId() == null) {
                    response.setMessage("must be Brand");
                    response.setSuccess(false);
                    response.setMessageType(TrayIcon.MessageType.WARNING);
                }
            } else if (reqProduct.getName().equals(productRepository.findByName(reqProduct.getName()))) {
                response.setMessage("this product already exist");
                response.setSuccess(false);
                response.setMessageType(TrayIcon.MessageType.WARNING);
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error");
            response.setMessageType(TrayIcon.MessageType.ERROR);
        }
        return response;
    }

    public ResPageable pageable(Integer page, Integer size, String search) {
        Page<Product> productPage = productRepository.findAll(CommonUtils.getPageableById(page, size));
        if (!search.equals("all")) {
            productPage = productRepository.findAllByNameStartingWithIgnoreCase(search, CommonUtils.getPageableById(page, size));
        }
        return new ResPageable(productPage.getContent(), productPage.getTotalElements(), page);
    }

    public ApiResponse changeActive(Long id, boolean active) {
        try {
            Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
            product.setActive(active);
            productRepository.save(product);
            return new ApiResponse(active ? "Activated" : "Blocked", true, TrayIcon.MessageType.INFO);
        } catch (Exception e) {
            return new ApiResponse("Error", false, TrayIcon.MessageType.ERROR);
        }
    }

    public ApiResponse removeProduct(Long id) {
        try {
            productRepository.deleteById(id);
            return new ApiResponse("Deleted", true, TrayIcon.MessageType.INFO);
        } catch (Exception e) {
            return new ApiResponse("Error", false, TrayIcon.MessageType.ERROR);
        }
    }

    public ApiResponseModel getProductCol(String barcode) {
        try {
            Optional<Product> byProductId = productRepository.findByBarCode(barcode);
            return new ApiResponseModel(true, "Ok ^_^", byProductId.get());
        } catch (Exception e) {
            return new ApiResponseModel(false, "Error", TrayIcon.MessageType.ERROR);
        }
    }
}
