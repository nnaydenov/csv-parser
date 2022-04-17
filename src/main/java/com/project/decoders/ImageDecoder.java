package com.project.decoders;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageDecoder {

    public static void decode(String imageString, String destination, String fileName) {
        byte[] decodedImage = Base64.getDecoder().decode(imageString);
        try (FileOutputStream outputStream = new FileOutputStream(destination + fileName)) {
            outputStream.write(decodedImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
