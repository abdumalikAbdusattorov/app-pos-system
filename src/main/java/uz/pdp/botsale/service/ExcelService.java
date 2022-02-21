package uz.pdp.botsale.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.botsale.entity.*;
import uz.pdp.botsale.repository.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ExcelService {
    //**********************************************CLIENT EXCEL***************************
    @Autowired
    ClientRepository clientRepository;

    private static String[] columnClients = {"→→", "t/r", "Name", "phoneNumber", "debt", "←←"};//excelni ustunlarini yasab olish

    public ResponseEntity<byte[]> getClient() {
        Workbook workbook = new XSSFWorkbook();

        CreationHelper creationHelper = workbook.getCreationHelper(); //workbookni xamma formatlarini qo'llab quvvatlaydi

        Sheet sheet = workbook.createSheet("↑↑↑Client↑↑↑");

        //Font stylelari
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.BLACK1.getIndex());
        font.setFontHeightInPoints((short) 14);
        font.setItalic(true);
        font.setFontName("Arial Black");

        //bitta yacheyka stil ko'rinishi
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        //qator yasamoqchiman Create row
        Row row = sheet.createRow(0);

        //Create cell ustun yasayapti
        for (int i = 0; i < columnClients.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(columnClients[i]);
        }

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        int rowNum = 1;
        List<Client> clientList = clientRepository.findAll();

        int number = 1;

        CellStyle tr = workbook.createCellStyle();
        tr.setBorderTop(BorderStyle.THIN);
        tr.setBorderBottom(BorderStyle.THIN);
        tr.setBorderLeft(BorderStyle.THIN);
        tr.setBorderRight(BorderStyle.THIN);
        tr.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        for (Client client : clientList) {
            Row row1 = sheet.createRow(rowNum++);

            Cell cell = row1.createCell(0);
            cell.setCellValue("↑↓");
            cell.setCellStyle(tr);

            cell = row1.createCell(1);
            cell.setCellValue(number++);
            cell.setCellStyle(tr);

            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(client.getName());
            cel2.setCellStyle(style);

            cel2 = row1.createCell(3);
            cel2.setCellValue(client.getPhoneNumber());
            cel2.setCellStyle(style);

            cel2 = row1.createCell(4);
            cel2.setCellValue(client.getDebt());
            cel2.setCellStyle(style);

            cell = row1.createCell(5);
            cell.setCellValue("↑↓\n");
            cell.setCellStyle(tr);
        }

        for (int i = 0; i < columnClients.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            workbook.write(byteArrayOutputStream);
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openXmlFormats-officeDocument.spreadSheetMl.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\" CLIENT :" + currentDateTime + ".xlsx")
                .body(bytes);
    }

    //**********************************************MARKET EXCEL***************************

    @Autowired
    MarketRepository marketRepository;

    private static String[] columnMarkets = {"→→", "t/r", "Name", "Address", "purchaseList", "users", "cash", "←←"};//excelni ustunlarini yasab olish

    public ResponseEntity<byte[]> getMarket() {
        Workbook workbook = new XSSFWorkbook();

        CreationHelper creationHelper = workbook.getCreationHelper(); //workbookni xamma formatlarini qo'llab quvvatlaydi

        Sheet sheet = workbook.createSheet("↑↑↑Market↑↑↑");

        //Font stylelari
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.BLACK1.getIndex());
        font.setFontHeightInPoints((short) 14);
        font.setItalic(true);
        font.setFontName("Arial Black");

        //bitta yacheyka stil ko'rinishi
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        //qator yasamoqchiman Create row
        Row row = sheet.createRow(0);

        //Create cell ustun yasayapti
        for (int i = 0; i < columnMarkets.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(columnMarkets[i]);
        }

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        int rowNum = 1;
        List<Market> marketList = marketRepository.findAll();

        int number = 1;

        CellStyle tr = workbook.createCellStyle();
        tr.setBorderTop(BorderStyle.THIN);
        tr.setBorderBottom(BorderStyle.THIN);
        tr.setBorderLeft(BorderStyle.THIN);
        tr.setBorderRight(BorderStyle.THIN);
        tr.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        for (Market market : marketList) {
            Row row1 = sheet.createRow(rowNum++);

            Cell cell = row1.createCell(0);
            cell.setCellValue("↑↓");
            cell.setCellStyle(tr);

            cell = row1.createCell(1);
            cell.setCellValue(number++);
            cell.setCellStyle(tr);

            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(market.getName());
            cel2.setCellStyle(style);

            cel2 = row1.createCell(3);
            cel2.setCellValue(market.getAddress());
            cel2.setCellStyle(style);

/*            for (Purchase purchase : market.getPurchaseList()) {
                purchase
            }

            cel2 = row1.createCell(4);
            cel2.setCellValue(market.getPurchaseList().get);
            cel2.setCellStyle(style);*/

            cell = row1.createCell(5);
            cell.setCellValue("↑↓\n");
            cell.setCellStyle(tr);
        }

        for (int i = 0; i < columnMarkets.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            workbook.write(byteArrayOutputStream);
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openXmlFormats-officeDocument.spreadSheetMl.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\" CLIENT :" + currentDateTime + ".xlsx")
                .body(bytes);
    }

    //**********************************************COMPANY EXCEL***************************

    @Autowired
    CompanyRepository companyRepository;

    private static String[] columnCompanys = {"→→", "t/r", "Name", "phoneNumber", "agentName", "active", "←←"};//excelni ustunlarini yasab olish

    public ResponseEntity<byte[]> getCompany() {
        Workbook workbook = new XSSFWorkbook();
        CreationHelper creationHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("↑↑↑Company↑↑↑");

        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.BLACK1.getIndex());
        font.setFontHeightInPoints((short) 14);
        font.setItalic(true);
        font.setFontName("Arial Black");

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        Row row = sheet.createRow(0);

        for (int i = 0; i < columnCompanys.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(columnCompanys[i]);
        }

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        int rowNum = 1;
        List<Company> companyList = companyRepository.findAll();

        int number = 1;

        CellStyle tr = workbook.createCellStyle();
        tr.setBorderTop(BorderStyle.THIN);
        tr.setBorderBottom(BorderStyle.THIN);
        tr.setBorderLeft(BorderStyle.THIN);
        tr.setBorderRight(BorderStyle.THIN);
        tr.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        for (Company company : companyList) {
            Row row1 = sheet.createRow(rowNum++);

            Cell cell = row1.createCell(0);
            cell.setCellValue("↑↓");
            cell.setCellStyle(tr);

            cell = row1.createCell(1);
            cell.setCellValue(number++);
            cell.setCellStyle(tr);

            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(company.getName());
            cel2.setCellStyle(style);

            cel2 = row1.createCell(3);
            cel2.setCellValue(company.getPhoneNumber());
            cel2.setCellStyle(style);

            cel2 = row1.createCell(3);
            cel2.setCellValue(company.getAgentName());
            cel2.setCellStyle(style);

            cell = row1.createCell(4);
            cell.setCellValue(company.isActive() ? "Activated" : "Blocked");
//            Hamkorlik qilinmoqcha yoki vaqtincha toxtatilgan
            cell.setCellStyle(tr);

            cell = row1.createCell(5);
            cell.setCellValue("↑↓\n");
            cell.setCellStyle(tr);
        }

        for (int i = 0; i < columnCompanys.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            workbook.write(byteArrayOutputStream);
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openXmlFormats-officeDocument.spreadSheetMl.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\" COMPANY :" + currentDateTime + ".xlsx")
                .body(bytes);
    }

    //**********************************************CASH EXCEL***************************

    @Autowired
    CashRepository cashRepository;

    private static String[] columnCash = {"→→", "t/r", "Name", "phoneNumber", "agentName", "active", "←←"};//excelni ustunlarini yasab olish

    public ResponseEntity<byte[]> getCash() {
        Workbook workbook = new XSSFWorkbook();
        CreationHelper creationHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("↑↑↑Company↑↑↑");

        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.BLACK1.getIndex());
        font.setFontHeightInPoints((short) 14);
        font.setItalic(true);
        font.setFontName("Arial Black");

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        Row row = sheet.createRow(0);

        for (int i = 0; i < columnCompanys.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(columnCompanys[i]);
        }

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        int rowNum = 1;
        List<Company> companyList = companyRepository.findAll();

        int number = 1;

        CellStyle tr = workbook.createCellStyle();
        tr.setBorderTop(BorderStyle.THIN);
        tr.setBorderBottom(BorderStyle.THIN);
        tr.setBorderLeft(BorderStyle.THIN);
        tr.setBorderRight(BorderStyle.THIN);
        tr.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        for (Company company : companyList) {
            Row row1 = sheet.createRow(rowNum++);

            Cell cell = row1.createCell(0);
            cell.setCellValue("↑↓");
            cell.setCellStyle(tr);

            cell = row1.createCell(1);
            cell.setCellValue(number++);
            cell.setCellStyle(tr);

            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(company.getName());
            cel2.setCellStyle(style);

            cel2 = row1.createCell(3);
            cel2.setCellValue(company.getPhoneNumber());
            cel2.setCellStyle(style);

            cel2 = row1.createCell(3);
            cel2.setCellValue(company.getAgentName());
            cel2.setCellStyle(style);

            cell = row1.createCell(4);
            cell.setCellValue(company.isActive() ? "Activated" : "Blocked");
//            Hamkorlik qilinmoqcha yoki vaqtincha toxtatilgan
            cell.setCellStyle(tr);

            cell = row1.createCell(5);
            cell.setCellValue("↑↓\n");
            cell.setCellStyle(tr);
        }

        for (int i = 0; i < columnCompanys.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            workbook.write(byteArrayOutputStream);
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openXmlFormats-officeDocument.spreadSheetMl.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\" COMPANY :" + currentDateTime + ".xlsx")
                .body(bytes);
    }

    //**********************************************CASH EXCEL***************************

    @Autowired
    BrandRepository brandRepository;

    private static String[] columnBrand = {"→→", "t/r", "Name", "phoneNumber", "agentName", "active", "←←"};

    public ResponseEntity<byte[]> getBrand() {
        Workbook workbook = new XSSFWorkbook();
        CreationHelper creationHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("↑↑↑Brand↑↑↑");

        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.BLACK1.getIndex());
        font.setFontHeightInPoints((short) 14);
        font.setItalic(true);
        font.setFontName("Arial Black");

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        Row row = sheet.createRow(0);

        for (int i = 0; i < columnBrand.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(columnBrand[i]);
        }

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        int rowNum = 1;
        List<Brand> brandList = brandRepository.findAll();

        int number = 1;

        CellStyle tr = workbook.createCellStyle();
        tr.setBorderTop(BorderStyle.THIN);
        tr.setBorderBottom(BorderStyle.THIN);
        tr.setBorderLeft(BorderStyle.THIN);
        tr.setBorderRight(BorderStyle.THIN);
        tr.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        for (Brand brand : brandList) {
            Row row1 = sheet.createRow(rowNum++);

            Cell cell = row1.createCell(0);
            cell.setCellValue("↑↓");
            cell.setCellStyle(tr);

            cell = row1.createCell(1);
            cell.setCellValue(number++);
            cell.setCellStyle(tr);

            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(brand.getName());
            cel2.setCellStyle(style);

            cell = row1.createCell(3);
            cell.setCellValue(brand.isActive() ? "Activated" : "Blocked");
            cell.setCellStyle(tr);

            cell = row1.createCell(4);
            cell.setCellValue("↑↓\n");
            cell.setCellStyle(tr);
        }

        for (int i = 0; i < columnBrand.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            workbook.write(byteArrayOutputStream);
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openXmlFormats-officeDocument.spreadSheetMl.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\" Brand " + currentDateTime + ".xlsx")
                .body(bytes);
    }

    //**********************************************CA EXCEL***************************

    @Autowired
    CategoryRepository categoryRepository;

    private static String[] columnCategory = {"→→", "t/r", "Name", "child Categories", "active", "←←"};

    public ResponseEntity<byte[]> getCategory() {
        Workbook workbook = new XSSFWorkbook();
        CreationHelper creationHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("↑↑↑Category↑↑↑");

        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.BLACK1.getIndex());
        font.setFontHeightInPoints((short) 14);
        font.setItalic(true);
        font.setFontName("Arial Black");

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        Row row = sheet.createRow(0);

        for (int i = 0; i < columnCategory.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(columnCategory[i]);
        }

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        CellStyle tr = workbook.createCellStyle();
        tr.setBorderTop(BorderStyle.THIN);
        tr.setBorderBottom(BorderStyle.THIN);
        tr.setBorderLeft(BorderStyle.THIN);
        tr.setBorderRight(BorderStyle.THIN);
        tr.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        int rowNum = 1;
        List<Category> categoryList = categoryRepository.findAll();

        int number = 1;

        for (Category category : categoryList) {
            Row row1 = sheet.createRow(rowNum++);

            Cell cell = row1.createCell(0);
            cell.setCellValue("↑↓");
            cell.setCellStyle(tr);

            cell = row1.createCell(1);
            cell.setCellValue(number++);
            cell.setCellStyle(tr);

            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(category.getName());
            cel2.setCellStyle(style);

            cell = row1.createCell(3);
            cell.setCellValue(category.getName());
            cell.setCellStyle(tr);

            cell = row1.createCell(4);
            cell.setCellValue(category.isActive() ? "Activated" : "Blocked");
            cell.setCellStyle(tr);

            cell = row1.createCell(5);
            cell.setCellValue("↑↓\n");
            cell.setCellStyle(tr);
        }

        for (int i = 0; i < columnCategory.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            workbook.write(byteArrayOutputStream);
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openXmlFormats-officeDocument.spreadSheetMl.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\" Brand " + currentDateTime + ".xlsx")
                .body(bytes);
    }

    //**********************************************CASH EXCEL***************************

    @Autowired
    ProductRepository productRepository;

    private static String[] columnProduct = {"→→", "t/r", "Name", "category", "brand", "Barcode", "active", "←←"};

    public ResponseEntity<byte[]> getProduct() {
        Workbook workbook = new XSSFWorkbook();
        CreationHelper creationHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("↑↑↑Category↑↑↑");

        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.BLACK1.getIndex());
        font.setFontHeightInPoints((short) 14);
        font.setItalic(true);
        font.setFontName("Arial Black");

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        Row row = sheet.createRow(0);

        for (int i = 0; i < columnProduct.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(columnProduct[i]);
        }

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        CellStyle tr = workbook.createCellStyle();
        tr.setBorderTop(BorderStyle.THIN);
        tr.setBorderBottom(BorderStyle.THIN);
        tr.setBorderLeft(BorderStyle.THIN);
        tr.setBorderRight(BorderStyle.THIN);
        tr.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        int rowNum = 1;
        List<Product> productList = productRepository.findAll();

        int number = 1;

        for (Product product : productList) {
            Row row1 = sheet.createRow(rowNum++);

            Cell cell = row1.createCell(0);
            cell.setCellValue("↑↓");
            cell.setCellStyle(tr);

            cell = row1.createCell(1);
            cell.setCellValue(number++);
            cell.setCellStyle(tr);

            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(product.getName());
            cel2.setCellStyle(style);

            cell = row1.createCell(3);
            cell.setCellValue(product.getCategory().getName());
            cell.setCellStyle(tr);

            cell = row1.createCell(4);
            cell.setCellValue(product.getBrand().getName());
            cell.setCellStyle(tr);

            cell = row1.createCell(5);
            cell.setCellValue(product.getBarCode());
            cell.setCellStyle(tr);

            cell = row1.createCell(6);
            cell.setCellValue(product.isActive() ? "Activated" : "Blocked");
            cell.setCellStyle(tr);

            cell = row1.createCell(7);
            cell.setCellValue("↑↓\n");
            cell.setCellStyle(tr);
        }

        for (int i = 0; i < columnProduct.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            workbook.write(byteArrayOutputStream);
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openXmlFormats-officeDocument.spreadSheetMl.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\" Product " + currentDateTime + ".xlsx")
                .body(bytes);
    }

    //**********************************************CASH EXCEL***************************

    @Autowired
    PurchaseElementsRepository purchaseElementsRepository;

    private static String[] columnPurchase = {"→→", "t/r", "Product", "income price", "sell Price", "present Price", "count", "present Count", "income time", "←←"};

    public ResponseEntity<byte[]> getPurchaseElements() {
        Workbook workbook = new XSSFWorkbook();
        CreationHelper creationHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("↑↑↑Purchase Elements↑↑↑");

        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.BLACK1.getIndex());
        font.setFontHeightInPoints((short) 14);
        font.setItalic(true);
        font.setFontName("Arial Black");

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        Row row = sheet.createRow(0);

        for (int i = 0; i < columnPurchase.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(columnPurchase[i]);
        }

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        CellStyle tr = workbook.createCellStyle();
        tr.setBorderTop(BorderStyle.THIN);
        tr.setBorderBottom(BorderStyle.THIN);
        tr.setBorderLeft(BorderStyle.THIN);
        tr.setBorderRight(BorderStyle.THIN);
        tr.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        int rowNum = 1;
        List<PurchaseElements> purchaseElementsList = purchaseElementsRepository.findAll();

        int number = 1;

        for (PurchaseElements purchaseElements : purchaseElementsList) {
            Row row1 = sheet.createRow(rowNum++);

            Cell cell = row1.createCell(0);
            cell.setCellValue("↑↓");
            cell.setCellStyle(tr);

            cell = row1.createCell(1);
            cell.setCellValue(number++);
            cell.setCellStyle(tr);

            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(purchaseElements.getProduct().getName());
            cel2.setCellStyle(style);

            cell = row1.createCell(3);
            cell.setCellValue(purchaseElements.getIncomePrice());
            cell.setCellStyle(tr);

            cell = row1.createCell(4);
            cell.setCellValue(purchaseElements.getSellPrice());
            cell.setCellStyle(tr);

            cell = row1.createCell(5);
            cell.setCellValue(purchaseElements.getPresentPrice());
            cell.setCellStyle(tr);

            cell = row1.createCell(6);
            cell.setCellValue(purchaseElements.getCount());
            cell.setCellStyle(tr);

            cell = row1.createCell(7);
            cell.setCellValue(purchaseElements.getPresentCount());
            cell.setCellStyle(tr);

            cell = row1.createCell(7);
            cell.setCellValue(purchaseElements.getCreatedAt());
            cell.setCellStyle(tr);

            cell = row1.createCell(8);
            cell.setCellValue("↑↓\n");
            cell.setCellStyle(tr);
        }

        for (int i = 0; i < columnPurchase.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            workbook.write(byteArrayOutputStream);
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openXmlFormats-officeDocument.spreadSheetMl.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\" Purchase " + currentDateTime + ".xlsx")
                .body(bytes);
    }

    //**********************************************Selldatails EXCEL***************************

    @Autowired
    SellDetailsRepository sellDetailsRepository;

    private static String[] columnSell = {"→→", "t/r", "Product", "Price", "Count", "Sold time", "←←"};

    public ResponseEntity<byte[]> getSellDetails(Timestamp startDate, Timestamp endDate) {
        Workbook workbook = new XSSFWorkbook();
        CreationHelper creationHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("↑↑↑Sell details↑↑↑");

        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.BLACK1.getIndex());
        font.setFontHeightInPoints((short) 14);
        font.setItalic(true);
        font.setFontName("Arial Black");

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        Row row = sheet.createRow(0);

        for (int i = 0; i < columnSell.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(columnSell[i]);
        }

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        CellStyle tr = workbook.createCellStyle();
        tr.setBorderTop(BorderStyle.THIN);
        tr.setBorderBottom(BorderStyle.THIN);
        tr.setBorderLeft(BorderStyle.THIN);
        tr.setBorderRight(BorderStyle.THIN);
        tr.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        int rowNum = 1;
        List<SellDetails> sellDetailsList = sellDetailsRepository.findAllByDate(startDate, endDate);

        int number = 1;

        for (SellDetails sellDetails : sellDetailsList) {
            Row row1 = sheet.createRow(rowNum++);

            Cell cell = row1.createCell(0);
            cell.setCellValue("↑↓");
            cell.setCellStyle(tr);

            cell = row1.createCell(1);
            cell.setCellValue(number++);
            cell.setCellStyle(tr);

            Cell cel2 = row1.createCell(2);
            cel2.setCellValue(sellDetails.getProduct().getName());
            cel2.setCellStyle(style);

            cell = row1.createCell(3);
            cell.setCellValue(sellDetails.getPrice());
            cell.setCellStyle(tr);

            cell = row1.createCell(4);
            cell.setCellValue(sellDetails.getCount());
            cell.setCellStyle(tr);

            cell = row1.createCell(5);
            cell.setCellValue(sellDetails.getCreatedAt());
            cell.setCellStyle(tr);

            cell = row1.createCell(5);
            cell.setCellValue("↑↓\n");
            cell.setCellStyle(tr);
        }

        for (int i = 0; i < columnSell.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            workbook.write(byteArrayOutputStream);
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openXmlFormats-officeDocument.spreadSheetMl.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\" Sell Details " + currentDateTime + ".xlsx")
                .body(bytes);
    }
}
