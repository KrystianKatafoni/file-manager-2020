package com.katafoni.filemanager.extension.util;


import com.katafoni.filemanager.extension.ExtensionEntity;
import com.katafoni.filemanager.extension.ExtensionRepository;
import com.katafoni.filemanager.extension.exception.FileHasIncorrectExtensionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Validation if extension of file for conversion is allowed
 */
@Component
@Slf4j
public class ExtensionValidatorImpl implements ExtensionValidator {
    ExtensionRepository extensionRepository;

    public ExtensionValidatorImpl(ExtensionRepository extensionRepository) {
        this.extensionRepository = extensionRepository;
    }

    /**
     * Method check in repository if extension of file exsist as acceptable
     * @param extension
     * @return acceptableExtension as optional
     * @throws FileHasIncorrectExtensionException wrong extension of file or it's not allowed
     */
    @Override
    public ExtensionEntity validate(String extension) throws FileHasIncorrectExtensionException {
        ExtensionEntity extensionEntity = extensionRepository.findByExtension(extension.toLowerCase())
                .orElseThrow(()-> new FileHasIncorrectExtensionException(extension));
        if(!extensionEntity.isEnabled()) throw new FileHasIncorrectExtensionException(extension);
        return extensionEntity;
    }

}
