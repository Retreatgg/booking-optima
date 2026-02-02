package com.optima.test_task.services.impls;

import com.optima.test_task.dtos.CreateResourceInput;
import com.optima.test_task.dtos.ResourceResponse;
import com.optima.test_task.dtos.SetIsActiveInput;
import com.optima.test_task.dtos.UpdateResourceInput;
import com.optima.test_task.exceptions.ResourceNotFoundException;
import com.optima.test_task.mappers.ResourceMapper;
import com.optima.test_task.models.Resource;
import com.optima.test_task.repositories.ResourceRepository;
import com.optima.test_task.services.ResourceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;

    @Override
    public Resource findModelById(Long id) {
        return resourceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ресурс с ID: " + id + " не найден"));
    }

    @Override
    public List<ResourceResponse> getAll() {
        List<Resource> resources = resourceRepository.findAll();
        return resourceMapper.toResponseList(resources);
    }

    @Override
    @Transactional
    public ResourceResponse createResource(CreateResourceInput input) {
        Resource resource = toResource(input);
        resourceRepository.save(resource);
        return resourceMapper.toResponse(resource);
    }

    @Override
    @Transactional
    public ResourceResponse delete(Long id) {
        Resource resource = findModelById(id);
        resourceRepository.delete(resource);
        return resourceMapper.toResponse(resource);
    }

    @Override
    @Transactional
    public ResourceResponse setIsActive(SetIsActiveInput input) {
        Resource resource = findModelById(input.id());
        resource.setIsActive(input.isActive());
        resourceRepository.save(resource);
        return resourceMapper.toResponse(resource);
    }

    @Override
    @Transactional
    public ResourceResponse update(UpdateResourceInput input) {
        Resource resource = findModelById(input.id());
        resource.setName(input.name());
        resource.setDescription(input.description());
        resource.setAmount(input.amount());
        resourceRepository.save(resource);
        return resourceMapper.toResponse(resource);
    }

    private Resource toResource(CreateResourceInput input) {
        return Resource.builder()
                .name(input.name())
                .description(input.description())
                .isActive(input.isActive())
                .amount(input.amount())
                .isDeleted(false)
                .build();
    }
}
