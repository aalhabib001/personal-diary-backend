package bd.info.habib.personaldiarybackend.service.impl;

import bd.info.habib.personaldiarybackend.dto.ApiDataResponse;
import bd.info.habib.personaldiarybackend.dto.ApiMessageResponse;
import bd.info.habib.personaldiarybackend.dto.request.CategoryRequest;
import bd.info.habib.personaldiarybackend.dto.response.CategoryResponse;
import bd.info.habib.personaldiarybackend.model.CategoryModel;
import bd.info.habib.personaldiarybackend.repository.CategoryRepository;
import bd.info.habib.personaldiarybackend.service.CategoryService;
import bd.info.habib.personaldiarybackend.utils.AuthUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<ApiMessageResponse> createCategory(CategoryRequest categoryRequest) {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setCategoryName(categoryRequest.getCategoryName());

        categoryRepository.save(categoryModel);

        return new ResponseEntity<>(new ApiMessageResponse(201, "Category created successfully"), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiDataResponse<List<CategoryResponse>>> getAllCategories() {
        String userName = AuthUtils.getUserName();

        List<CategoryModel> categoryModels = categoryRepository.findAllByCreatedBy(userName);

        // Converting model to response dto
        List<CategoryResponse> categoryResponses = categoryModels.stream()
                .map(categoryModel -> new CategoryResponse(categoryModel.getId(), categoryModel.getCategoryName()))
                .collect(Collectors.toList());

        if (categoryResponses.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Category Found");
        else
            return new ResponseEntity<>(new ApiDataResponse<>(200, "Categories fetched successfully", categoryResponses), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<ApiMessageResponse> updateCategory(Long id, CategoryRequest categoryRequest) {
        CategoryModel categoryModel = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + id));

        // Checking if the category belongs to the user
        String userName = AuthUtils.getUserName();
        if (!categoryModel.getCreatedBy().equals(userName))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to update this category");

        categoryModel.setCategoryName(categoryRequest.getCategoryName());
        categoryRepository.save(categoryModel);

        return new ResponseEntity<>(new ApiMessageResponse(200, "Category updated successfully"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiMessageResponse> deleteCategory(Long id) {
        CategoryModel categoryModel = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + id));

        // Checking if the category belongs to the user
        String userName = AuthUtils.getUserName();
        if (!categoryModel.getCreatedBy().equals(userName))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to delete this category");

        categoryRepository.delete(categoryModel);

        return new ResponseEntity<>(new ApiMessageResponse(200, "Category with notes deleted successfully"), HttpStatus.OK);
    }

}
