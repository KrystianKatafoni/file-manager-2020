package com.katafoni.filemanager.file.extension;


import com.katafoni.filemanager.exception.FileHasIncorrectExtensionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Validation if extension of file for conversion is allowed
 */
@Component
@Slf4j
public class ExtensionValidatorImpl implements ExtensionValidator {
    AcceptableExtensionRepository acceptableExtensionRepository;

    public ExtensionValidatorImpl(AcceptableExtensionRepository acceptableExtensionRepository) {
        this.acceptableExtensionRepository = acceptableExtensionRepository;
    }

    /**
     * Method check in repository if extension of file exsist as acceptable
     * @param extension
     * @return acceptableExtension as optional
     * @throws FileHasIncorrectExtensionException wrong extension of file or it's not allowed
     */
    @Override
    public AcceptableExtension validate(String extension) throws FileHasIncorrectExtensionException {
        AcceptableExtension acceptableExtension = acceptableExtensionRepository.findByExtension(extension)
                .orElseThrow(()-> new FileHasIncorrectExtensionException(extension));
        return acceptableExtension;
    }

}
