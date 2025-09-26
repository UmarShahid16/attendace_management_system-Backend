package com.ams.AMS.util.imageUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

public class ImageUtil {

    public static String saveBase64Image(String base64Str) {
        try {
            String extension = "";
            if (base64Str.contains(",")) {
                // extract prefix before comma
                String prefix = base64Str.split(",")[0];
                base64Str = base64Str.split(",")[1];

                if (prefix.contains("image/png")) {
                    extension = "png";
                } else if (prefix.contains("image/jpeg")) {
                    extension = "jpg";
                } else if (prefix.contains("image/gif")) {
                    extension = "gif";
                } else if (prefix.contains("image/webp")) {
                    extension = "webp";
                }
            }

            byte[] decodedBytes = Base64.getDecoder().decode(base64Str);
            String fileName = UUID.randomUUID() + "." + extension;
            String folderPath = "D:/My Data/MyProjects/AMS-images/";

            File directory = new File(folderPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filePath = folderPath + fileName;
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(decodedBytes);
            }

            return "http://localhost:8081/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }
}
