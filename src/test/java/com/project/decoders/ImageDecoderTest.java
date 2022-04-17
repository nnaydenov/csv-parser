package com.project.decoders;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

class ImageDecoderTest {

    private static final String testString = "dGVzdCBzdHJpbmc=";

    @BeforeEach
    void setup() throws IOException {
        Files.createDirectories(Path.of("src/test/resources"));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of("src/test/resources/test.png"));
    }

    @Test
    void decode() throws IOException {
        ImageDecoder.decode(testString, "src/test/resources/", "test.png");
        try (FileInputStream fileInputStream = new FileInputStream("src/test/resources/test.png")) {
            byte[] bytes = fileInputStream.readAllBytes();
            String result = Base64.getEncoder().encodeToString(bytes);
            Assertions.assertEquals(testString, result);
        }
    }
}