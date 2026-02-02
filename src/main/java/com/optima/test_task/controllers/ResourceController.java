package com.optima.test_task.controllers;

import com.optima.test_task.dtos.CreateResourceInput;
import com.optima.test_task.dtos.ResourceResponse;
import com.optima.test_task.dtos.SetIsActiveInput;
import com.optima.test_task.dtos.UpdateResourceInput;
import com.optima.test_task.services.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @QueryMapping
    public List<ResourceResponse> resources() {
        return resourceService.getAll();
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResourceResponse createResource(@Argument CreateResourceInput input) {
        return resourceService.createResource(input);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResourceResponse deleteResource(@Argument Long id) {
        return resourceService.delete(id);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResourceResponse setIsActiveResource(@Argument SetIsActiveInput input) {
        return resourceService.setIsActive(input);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResourceResponse updateResource(@Argument UpdateResourceInput input) {
        return resourceService.update(input);
    }

}
