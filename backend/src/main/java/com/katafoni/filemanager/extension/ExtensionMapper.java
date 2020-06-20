package com.katafoni.filemanager.extension;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExtensionMapper {

    ExtensionDto acceptableExtensionEntityToDto(ExtensionEntity entity);
    ExtensionEntity acceptableExtensionDtoToEntity(ExtensionDto dto);
    List<ExtensionEntity> acceptableExtensionDtosToEnties(List<ExtensionDto> dtos);
    List<ExtensionDto> acceptableExtensionEntitiesToDtos(List<ExtensionEntity> entities);

}
