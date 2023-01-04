package id.bca7.demoSpring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.bca7.demoSpring.models.dto.request.CategoryRequest;
import id.bca7.demoSpring.models.dto.response.ResponseData;
import id.bca7.demoSpring.services.category.CategoryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    private ResponseData responseData;

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody @Valid CategoryRequest request) {
        try {
            responseData = categoryService.createCategory(request);
            return ResponseEntity.status(responseData.getStatus()).body(responseData);
        } catch (Exception e) {
            e.printStackTrace();
            responseData = new ResponseData(500, e.getMessage(), null);
            return ResponseEntity.status(responseData.getStatus()).body(responseData);
        }
    }

    @GetMapping
    public ResponseEntity<?> getCategories(@RequestParam(value = "status", defaultValue = "") Boolean status) {
        try {
            responseData = categoryService.readCategory(status);
            return ResponseEntity.ok().body(responseData);
        } catch (Exception e) {
            e.printStackTrace();
            responseData = new ResponseData(500, e.getMessage(), null);
            return ResponseEntity.status(responseData.getStatus()).body(responseData);
        }
    }

    @GetMapping("/{idCategory}")
    public ResponseEntity<?> getCategoryById(@PathVariable("idCategory") Integer id) throws Exception {
        responseData = categoryService.readCategory(id);
        return ResponseEntity.ok().body(responseData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody CategoryRequest request) throws Exception {
        responseData = categoryService.updateCategory(id, request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) throws Exception {
        responseData = categoryService.deleteCategory(id);
        return ResponseEntity.ok().body(responseData);
    }
}