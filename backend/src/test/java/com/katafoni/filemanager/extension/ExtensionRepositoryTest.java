package com.katafoni.filemanager.extension;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class ExtensionRepositoryTest {

    @Autowired
    private ExtensionRepository extensionRepository;


    @Test
    public void whenFindExtensionAsPdf_itShouldReturnExtension() {
        //given
        String extensionAsString = "pdf";

        //when
        Optional<ExtensionEntity> foundedExtension = extensionRepository.findByExtension(extensionAsString);

        //then
        assertThat(foundedExtension.get().getExtension()).isEqualTo(extensionAsString);
    }

    @Test
    public void whenFindExtensionAsGif_itShouldReturnEmptyOptional() {
        //given
        String extensionAsString = "zip";
        //when
        Optional<ExtensionEntity> foundedExtension = extensionRepository.findByExtension(extensionAsString);

        //then
        assertFalse(foundedExtension.isPresent());
    }

}
