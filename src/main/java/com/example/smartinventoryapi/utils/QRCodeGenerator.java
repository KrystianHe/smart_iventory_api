package com.example.smartinventoryapi.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.nio.file.Path;

public class QRCodeGenerator {
    private static final String QR_CODES_DIR = "qrcodes/";

    public String generateAndSaveQRCode(String data, String fileName) {
        try {
            File dir = new File(QR_CODES_DIR);
            if (!dir.exists()) dir.mkdirs();

            String filePath = QR_CODES_DIR + fileName + ".png";
            BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 300, 300);
            Path path = new File(filePath).toPath();
            MatrixToImageWriter.writeToPath(matrix, "PNG", path);

            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
