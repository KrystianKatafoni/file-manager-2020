package com.katafoni.filemanager.extension.util;


import com.katafoni.filemanager.extension.ExtensionEntity;

public interface ExtensionValidator {
    ExtensionEntity validate(String extension);
}
