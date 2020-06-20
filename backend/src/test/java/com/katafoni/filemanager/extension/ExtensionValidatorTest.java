package com.katafoni.filemanager.extension;


import com.katafoni.filemanager.extension.exception.FileHasIncorrectExtensionException;
import com.katafoni.filemanager.extension.util.ExtensionValidator;
import com.katafoni.filemanager.extension.util.ExtensionValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ExtensionValidatorTest {

    @MockBean
    ExtensionRepository extensionRepository;
    ExtensionValidator extensionValidator;
    private final static String MESSAGE_EXTENSION_EXCEPTION = "Please set proper extension for the file";
    @BeforeEach
    public void setUp() {
        extensionValidator = new ExtensionValidatorImpl(extensionRepository);
    }
    @Test
    public void whenValidExtension_thenReturnExtension() {

        //given
        String extensionAsString = ".pdf";
        ExtensionEntity extension = new ExtensionEntity(1L, extensionAsString, true);

        //when
        given(extensionRepository.findByExtension(extensionAsString))
                .willReturn(Optional.ofNullable(extension));
        ExtensionEntity result = extensionValidator.validate(extensionAsString);
        //then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getExtension()).isEqualToIgnoringWhitespace(extensionAsString);
        verify(extensionRepository,times(1)).findByExtension(extensionAsString);
    }
    @Test
    public void whenNotValidExtension_thenReturnApiError() throws Exception {

        Assertions.assertThrows(FileHasIncorrectExtensionException.class,
                () -> {extensionValidator.validate("");});
        Assertions.assertThrows(FileHasIncorrectExtensionException.class,
                () -> {extensionValidator.validate("sh");});

    }
}
