package bd.info.habib.personaldiarybackend.repository;

import bd.info.habib.personaldiarybackend.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {

    List<CategoryModel> findAllByCreatedBy(String createdBy);


}
