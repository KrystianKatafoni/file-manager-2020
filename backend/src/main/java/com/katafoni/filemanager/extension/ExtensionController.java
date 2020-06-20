package com.katafoni.filemanager.extension;

import com.katafoni.filemanager.extension.exception.ExtensionAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("api/extension")
public class ExtensionController {

    ExtensionRepository extensionRepository;
    ExtensionMapper mapper;

    public ExtensionController(ExtensionRepository extensionRepository,
                               ExtensionMapper extensionMapper) {
        this.extensionRepository = extensionRepository;
        this.mapper = extensionMapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('extension:read')")
    public List<ExtensionDto> getExtensions() {
        List<ExtensionDto> extensions =
                this.mapper.acceptableExtensionEntitiesToDtos(this.extensionRepository.findAll());
        extensions.sort(Comparator.comparing(ExtensionDto::getExtension));
        return extensions;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('extension:write')")
    @ResponseStatus(HttpStatus.CREATED)
    public ExtensionDto saveExtension(@NotNull @RequestBody ExtensionDto extensionDto) {

        this.extensionRepository.findByExtension(extensionDto.getExtension())
                .ifPresent(x -> {
                    throw new ExtensionAlreadyExistException("Extension already exist");
                });
        ExtensionEntity savedExtension = this.extensionRepository
                .save(this.mapper.acceptableExtensionDtoToEntity(extensionDto));

        return this.mapper.acceptableExtensionEntityToDto(savedExtension);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('extension:delete')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteExtension(@NotNull @PathVariable("id") Long id) {
        this.extensionRepository.deleteById(id);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('extension:write')")
    @ResponseStatus(HttpStatus.OK)
    public ExtensionDto updateExtension(@NotNull @RequestBody ExtensionDto extensionDto) {

        ExtensionEntity savedExtension = this.extensionRepository
                .save(this.mapper.acceptableExtensionDtoToEntity(extensionDto));

        return this.mapper.acceptableExtensionEntityToDto(savedExtension);
    }
}
