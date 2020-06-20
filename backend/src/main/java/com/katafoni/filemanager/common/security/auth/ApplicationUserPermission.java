package com.katafoni.filemanager.common.security.auth;

public enum ApplicationUserPermission {
    FILE_READ("file:read"),
    FILE_WRITE("file:write"),
    FILEINFO_READ("fileinfo:read"),
    FILE_DELETE("file:delete"),
    EXTENSION_ADD("extension:write"),
    EXTENSION_READ("extension:read"),
    EXTENSION_DELETE("extension:delete");
    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
