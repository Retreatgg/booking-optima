package com.optima.test_task.mappers;

import com.optima.test_task.dtos.ResourceResponse;
import com.optima.test_task.models.Resource;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResourceMapper {

    ResourceResponse toResponse(Resource resource);
    List<ResourceResponse> toResponseList(List<Resource> resources);

}
