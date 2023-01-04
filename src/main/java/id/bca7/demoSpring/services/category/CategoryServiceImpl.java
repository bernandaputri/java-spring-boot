package id.bca7.demoSpring.services.category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import id.bca7.demoSpring.models.dto.request.CategoryRequest;
import id.bca7.demoSpring.models.dto.response.ResponseData;
import id.bca7.demoSpring.models.entity.Category;
import id.bca7.demoSpring.repositories.CategoryRepository;
import id.bca7.demoSpring.validators.AppValidator;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AppValidator appValidator;

    private Category category;
    private ResponseData responseData;
    private List<Category> categories;

    @Override
    public ResponseData createCategory(CategoryRequest request) throws Exception {
        category = new Category();

        category.setName(request.getCategoryName());

        categoryRepository.save(category);
        responseData = new ResponseData(HttpStatus.CREATED.value(), "success", category);
        return responseData;
    }

    @Override
    public ResponseData readCategory(Boolean status) {
        if (status == null) {
            categories = categoryRepository.findAll();
        } else {
            categories = categoryRepository.findByIsDeleted(status);
        }
        responseData = new ResponseData(200, "success", categories);
        return responseData;
    }

    @Override
    public ResponseData readCategory(Integer id) throws Exception {
        Optional<Category> categoryById = categoryRepository.findById(id);
        appValidator.validateCategoryNotFound(categoryById);

        category = categoryById.get();
        responseData = new ResponseData(200, "success", category);
        return responseData;
    }

    @Override
    public ResponseData updateCategory(Integer id, CategoryRequest request) throws Exception {
        Optional<Category> categoryById = categoryRepository.findById(id);
        appValidator.validateCategoryNotFound(categoryById);

        category = categoryById.get();
        category.setName(request.getCategoryName());
        categoryRepository.save(category);
        responseData = new ResponseData(200, "Category successfully updated.", category);
        return responseData;
    }

    @Override
    public ResponseData deleteCategory(Integer id) throws Exception {
        Optional<Category> categoryById = categoryRepository.findById(id);
        appValidator.validateCategoryNotFound(categoryById);

        category = categoryById.get();
        appValidator.validateCategoryIsAlreadyDeleted(category);
        category.setIsDeleted(true);
        categoryRepository.save(category);
        responseData = new ResponseData(200, "Category successfully deleted.", category);

        return responseData;
    }
}
