package com.katafoni.filemanager.extension;

import com.katafoni.filemanager.extension.exception.FileHasIncorrectExtensionException;
import com.katafoni.filemanager.extension.util.ExtensionExtractor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class ExtensionExtractorTest {

    @Autowired
    private ExtensionExtractor extensionExtractor;

    @Test
    public void whenFileNameHasValidExtension_thenReturnExtension() {
        //given
        String validFileName = "file.pdf";
        //when
        String extension = extensionExtractor.extract(validFileName);
        //then
        assertThat(extension).isEqualToIgnoringWhitespace("pdf");
    }
    @Test
    public void whenFileNameHasNotName_thenReturnExtension() {
        //given
        String validFileName = ".pdf";
        //when
        String extension = extensionExtractor.extract(validFileName);
        //then
        assertThat(extension).isEqualToIgnoringWhitespace("pdf");
    }
    @Test
    public void whenFileNameHasDotsAndExtension_thenReturnExtension() {
        //given
        String validFileName = "...pdf";
        //when
        String extension = extensionExtractor.extract(validFileName);
        //then
        assertThat(extension).isEqualToIgnoringWhitespace("pdf");
    }
    @Test
    public void whenFileNameHasCommasAndExtension_thenReturnExtension() {
        //given
        String validFileName = ",,.pdf";
        //when
        String extension = extensionExtractor.extract(validFileName);
        //then
        assertThat(extension).isEqualToIgnoringWhitespace("pdf");
    }
    @Test
    public void whenFileNameHasNotDotBeforeExtension_thenThrowException() {
        //given
        String invalidFileName = "filepdf";

        //then
        FileHasIncorrectExtensionException ex = assertThrows(FileHasIncorrectExtensionException.class,
                ()->extensionExtractor.extract(invalidFileName));
    }
    @Test
    public void whenFileNameIsEmpty_thenThrowException() {
        //given
        String invalidFileName = "";

        //then
        FileHasIncorrectExtensionException ex = assertThrows(FileHasIncorrectExtensionException.class,
                ()->extensionExtractor.extract(invalidFileName));
    }
    @Test
    public void whenFileNameHasDotOnEnd_thenThrowException() {
        //given
        String invalidFileName = "filepdf.";

        //then
        FileHasIncorrectExtensionException ex = assertThrows(FileHasIncorrectExtensionException.class,
                ()->extensionExtractor.extract(invalidFileName));
    }
    @Test
    public void whenFileNameHasOnlyDots_thenThrowException() {
        //given
        String invalidFileName = "...";

        //then
        FileHasIncorrectExtensionException ex = assertThrows(FileHasIncorrectExtensionException.class,
                ()->extensionExtractor.extract(invalidFileName));
    }
}
