package bd.info.habib.personaldiarybackend.repository.specification;

import bd.info.habib.personaldiarybackend.model.CategoryModel;
import bd.info.habib.personaldiarybackend.model.NoteModel;
import bd.info.habib.personaldiarybackend.utils.AuthUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class NoteSpecification {

    public static Specification<NoteModel> search(String queryData, List<String> fields, CategoryModel categoryModel) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (fields != null && !fields.isEmpty()){
                for (String field : fields) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(field)), "%" + queryData.toLowerCase() + "%"));
                }
            }

            String userName = AuthUtils.getUserName();

            if (userName != null) {
                predicates.add(criteriaBuilder.equal(root.get("createdBy"), userName));
            }

            if(categoryModel != null){
                predicates.add(criteriaBuilder.equal(root.get("category"), categoryModel));
            }

            // arraylist to array
            Predicate[] arrayPredicates = new Predicate[predicates.size()];
            predicates.toArray(arrayPredicates);
            return criteriaBuilder.and(arrayPredicates);

        };
    }

}
