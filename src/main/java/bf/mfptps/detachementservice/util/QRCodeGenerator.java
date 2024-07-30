/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public class QRCodeGenerator {

    public static Image createQRAsImage(String data, Map<?, ?> hashMap, int height, int width) throws WriterException, IOException {
        String charset = "UTF-8";
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(matrix, "PNG", stream);

        byte[] byteArray = stream.toByteArray();

        Image image = ImageIO.read(new ByteArrayInputStream(byteArray));

        return image;
    }
}
