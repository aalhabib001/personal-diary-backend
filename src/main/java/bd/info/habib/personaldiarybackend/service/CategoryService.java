package bd.info.habib.personaldiarybackend.service;

import bd.info.habib.personaldiarybackend.dto.ApiDataResponse;
import bd.info.habib.personaldiarybackend.dto.ApiMessageResponse;
import bd.info.habib.personaldiarybackend.dto.request.CategoryRequest;
import bd.info.habib.personaldiarybackend.dto.response.CategoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    ResponseEntity<ApiMessageResponse> createCategory(CategoryRequest categoryRequest);

    ResponseEntity<ApiDataResponse<List<CategoryResponse>>> getAllCategories();

    ResponseEntity<ApiMessageResponse> updateCategory(Long id, CategoryRequest categoryRequest);

    ResponseEntity<ApiMessageResponse> deleteCategory(Long id);
}
