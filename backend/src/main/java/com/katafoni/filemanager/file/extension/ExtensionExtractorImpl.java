package com.katafoni.filemanager.file.extension;


import com.google.common.io.Files;
import com.katafoni.filemanager.exception.FileHasIncorrectExtensionException;
import org.springframework.stereotype.Component;

@Component
public class ExtensionExtractorImpl implements ExtensionExtractor {
    /**
     * Extract extension from file name
     * @param filename filename with extension fe. test.pdf
     * @return extension as string fe. pdf, String could be empty
     * @throws FileHasIncorrectExtensionException wrong extension of file, it's not recognized
     */
    @Override
    public String extract(String filename) throws FileHasIncorrectExtensionException {
        String extension = Files.getFileExtension(filename);
        if(extension.isEmpty()) throw new FileHasIncorrectExtensionException(extension);
        return extension;
    }
}
