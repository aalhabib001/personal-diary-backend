package bd.info.habib.personaldiarybackend.controller;

import bd.info.habib.personaldiarybackend.dto.ApiDataResponse;
import bd.info.habib.personaldiarybackend.dto.ApiMessageResponse;
import bd.info.habib.personaldiarybackend.dto.request.CategoryRequest;
import bd.info.habib.personaldiarybackend.dto.response.CategoryResponse;
import bd.info.habib.personaldiarybackend.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    // For creating new category
    @PostMapping
    public ResponseEntity<ApiMessageResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }

    // For getting all categories for a user
    @GetMapping
    public ResponseEntity<ApiDataResponse<List<CategoryResponse>>> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // Editing category Data
    @PutMapping("/{id}")
    public ResponseEntity<ApiMessageResponse> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        return categoryService.updateCategory(id, categoryRequest);
    }

    // Deleting category Data
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiMessageResponse> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }
}
