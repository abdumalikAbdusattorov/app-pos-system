/*
package uz.pdp.botsale.excample;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.EAN13Writer;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;

@Service
public class ExampleService {

    public static BufferedImage barcode2(String name) throws Exception {
        Barcode barcode=  BarcodeFactory.createCode128(name);
        barcode.setBarHeight(60);
        barcode.setBarWidth(2);
        File image = new File("code.png");
        BarcodeImageHandler.savePNG(barcode, image);

        return BarcodeImageHandler.getImage(barcode);
    }


    public static BufferedImage barCode(String barCode) throws Exception {
        Barcode barcode = BarcodeFactory.createCode128B(barCode);
        barcode.setBarHeight(60);
        barcode.setBarWidth(2);

        File imgFile = new File("testSize.png");

        BarcodeImageHandler.savePNG(barcode, imgFile);

        return BarcodeImageHandler.getImage(barcode);
    }

    public static BufferedImage exampleBarcode2(String barCode) {
        EAN13Bean barcodeGenerator = new EAN13Bean();
        BitmapCanvasProvider canvas =
                new BitmapCanvasProvider(160, BufferedImage.TYPE_BYTE_BINARY, false, 0);

        barcodeGenerator.generateBarcode(canvas, barCode);
        return canvas.getBufferedImage();
    }

    public static BufferedImage generateEAN13(String qrText) throws Exception {
        EAN13Writer barcodeWriter = new EAN13Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(qrText, BarcodeFormat.EAN_13, 300, 150);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public static BufferedImage generateCode128(String qrText) throws Exception {
        Code128Writer writer = new Code128Writer();
        BitMatrix bitMatrix = writer.encode(qrText, BarcodeFormat.CODE_128, 300, 150);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public static BufferedImage generateQRCode(String qrText) throws Exception {
        EAN13Writer asdf= new EAN13Writer();
        BitMatrix bitMatrix = asdf.encode(qrText, BarcodeFormat.EAN_13, 300, 150);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

}
*/
