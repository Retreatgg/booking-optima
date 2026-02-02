package com.optima.test_task.services;

import com.optima.test_task.dtos.CreateResourceInput;
import com.optima.test_task.dtos.ResourceResponse;
import com.optima.test_task.dtos.SetIsActiveInput;
import com.optima.test_task.dtos.UpdateResourceInput;
import com.optima.test_task.models.Resource;

import java.util.List;

public interface ResourceService {
    Resource findModelById(Long id);
    List<ResourceResponse> getAll();
    ResourceResponse createResource(CreateResourceInput input);
    ResourceResponse delete(Long id);
    ResourceResponse setIsActive(SetIsActiveInput input);
    ResourceResponse update(UpdateResourceInput input);
}
