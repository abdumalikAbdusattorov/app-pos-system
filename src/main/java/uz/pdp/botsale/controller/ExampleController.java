/*
package uz.pdp.botsale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import uz.pdp.botsale.excample.ExampleService;

import java.awt.image.BufferedImage;

@RestController
@RequestMapping("/api/example")
public class ExampleController {

    @Autowired
    ExampleService exampleService;

    @DeleteMapping(value = "/barcode3",produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> barcode(@RequestParam String name) throws Exception {
        return successResponse(ExampleService.generateQRCode(name));
    }

    @GetMapping(value = "/barcode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> example(@RequestParam String barCode) throws Exception{
        return successResponse(ExampleService.barCode(barCode));
    }

    @GetMapping(value = "/barcode2", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> exampleBarcode2(@RequestParam String barCode){
        return successResponse(ExampleService.exampleBarcode2(barCode));
    }

    @PostMapping(value = "/qrCode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> zxing(@RequestParam String qrText) throws Exception{
        return successResponse(ExampleService.generateQRCode(qrText));
    }

    private ResponseEntity<BufferedImage> successResponse(BufferedImage image) {
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }
}
*/
