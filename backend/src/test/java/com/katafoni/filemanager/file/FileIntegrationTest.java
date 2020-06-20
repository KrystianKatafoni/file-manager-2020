package com.katafoni.filemanager.file;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class FileIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    MockMultipartFile file;
    @BeforeEach
    public void setUpBeforeEach() throws IOException {
        byte[] bytes = new byte[1024 * 1024 * 10];
        file = new MockMultipartFile("file",
                "test.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                bytes);

    }
    @Test
    @WithUserDetails("standard@gmail.com")
    public void whenDocumentUploaded_thenCheckAllLayers_AndReturn201() throws Exception {

        //when
        mockMvc.perform(
                multipart("/api/files")
                        .file(file)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }
}
