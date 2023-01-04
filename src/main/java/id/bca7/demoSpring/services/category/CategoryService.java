package id.bca7.demoSpring.services.category;

import id.bca7.demoSpring.models.dto.request.CategoryRequest;
import id.bca7.demoSpring.models.dto.response.ResponseData;

public interface CategoryService {
    // kerangka method untuk CRUD Category

    // Create
    ResponseData createCategory(CategoryRequest request) throws Exception;

    // Read
    // all
    ResponseData readCategory(Boolean status);
    
    // by ID
    ResponseData readCategory(Integer id) throws Exception;

    // Update
    ResponseData updateCategory(Integer id, CategoryRequest request) throws Exception;

    // Delete
    ResponseData deleteCategory(Integer id) throws Exception;
}
