package com.katafoni.filemanager.file;

import com.katafoni.filemanager.file.dto.FileInfoDto;
import com.katafoni.filemanager.file.exception.FileHasIncorrectFormatException;
import com.katafoni.filemanager.file.search.SearchCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("api/files")
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('fileinfo:read')")
    public Page<FileInfoDto> getFilesInformation(@RequestParam(required = false) String name,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(defaultValue = "name,asc") String sort) {

        String[] sortParams = sort.split(",");
        Sort sortObj = sortParams.length == 2
                ? Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]) : Sort.by(Sort.Order.asc("name"));

        Pageable paging = PageRequest.of(page, size, sortObj);
        SearchCriteria search = new SearchCriteria("name","like", name);
        return this.fileService.getFilesInfo(search, paging);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('file:read')")
    public ResponseEntity<byte[]> getFile(@NotNull @PathVariable Long id) {

        byte[] fileBytes = this.fileService.getFile(id);
        return ResponseEntity.ok()
                .contentLength(fileBytes.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileBytes);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('file:write')")
    public FileInfoDto uploadFile(@NotNull @RequestParam("file") MultipartFile file) {
        try {
            if(file.getBytes().length == 0) throw new FileHasIncorrectFormatException(file.getOriginalFilename());
        } catch (IOException e) {
            log.error("Cannot read file size");
        }
        return this.fileService.uploadFile(file);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('file:delete')")
    public void deleteFile(@NotNull @PathVariable Long id) {
        this.fileService.deleteFile(id);
    }
}
