package bd.info.habib.personaldiarybackend.repository;

import bd.info.habib.personaldiarybackend.model.CategoryModel;
import bd.info.habib.personaldiarybackend.model.NoteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<NoteModel, Long>, JpaSpecificationExecutor<NoteModel> {

    List<NoteModel> findAllByCreatedBy(String createdBy);

    Optional<NoteModel> findByIdAndCreatedBy(Long id, String userName);

    List<NoteModel> findAllByCategoryAndCreatedBy(CategoryModel categoryModel, String userName);
}
