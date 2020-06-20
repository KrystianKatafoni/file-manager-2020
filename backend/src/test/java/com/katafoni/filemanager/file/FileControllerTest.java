package com.katafoni.filemanager.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.katafoni.filemanager.common.error.ApiError;
import com.katafoni.filemanager.file.dto.FileInfoDto;
import com.katafoni.filemanager.file.exception.FileHasIncorrectFormatException;
import com.katafoni.filemanager.file.impl.FileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc

@ActiveProfiles("test")
public class FileControllerTest {

    @MockBean
    public FileServiceImpl fileServiceImpl;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    MockMultipartFile file;

    private FileInfoDto fileInfoDto;

    @BeforeEach
    public void setUpBeforeEach() {

        byte[] bytes = new byte[1024 * 1024 * 10];
        file = new MockMultipartFile("file",
                "test.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                bytes);
        fileInfoDto = new FileInfoDto().builder()
                .id(1L)
                .name("test.pdf")
                .size(12345)
                .createdBy("standard@gmail.com")
                .creationDate(LocalDateTime.now())
                .extension("pdf")
                .build();

    }

    @WithUserDetails("standard@gmail.com")
    @Test
    public void whenUploadingValidAndAllowedDocument_thenReturns201() throws Exception {

        //when
        given(fileServiceImpl.uploadFile(file)).willReturn(fileInfoDto);

        //then
        mockMvc.perform(
                multipart("/api/files")
                        .file(file)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(fileServiceImpl, times(1)).uploadFile(file);
    }

    @WithUserDetails("standard@gmail.com")
    @Test
    public void whenUploadedFileNull_thenReturns400() throws Exception {
        // given
        InputStream inputStream = InputStream.nullInputStream();

        MockMultipartFile file = new MockMultipartFile("file",
                "test.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                inputStream);

        //then
        mockMvc.perform(
                multipart("/api/files")
                        .file(file)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @WithUserDetails("standard@gmail.com")
    @Test
    public void whenValidInput_thenReturnDocumentDto() throws Exception {

        //when
        given(fileServiceImpl.uploadFile(file)).willReturn(fileInfoDto);

        //then
        MvcResult mvcResult = mockMvc.perform(
                multipart("/api/files")
                        .file(file)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String expectedResponseBody = objectMapper.writeValueAsString(fileInfoDto);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);

    }
}
