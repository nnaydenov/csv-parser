package com.project.decoders;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class ImageDecoder {

    private static final String OUTPUT_PATH = "src/main/resources/generated/";

    public static void decode(String imageString, String fileName) {
        byte[] decodedImage = Base64.getDecoder().decode(imageString);
        try {
            Files.write(Path.of(OUTPUT_PATH + fileName), decodedImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
