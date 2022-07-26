package bd.info.habib.personaldiarybackend.repository.specification;

import bd.info.habib.personaldiarybackend.model.CategoryModel;
import bd.info.habib.personaldiarybackend.model.NoteModel;
import bd.info.habib.personaldiarybackend.utils.AuthUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class NoteSpecification {

    // For Criteria Search of Notes
    public static Specification<NoteModel> search(String queryData, List<String> fields, CategoryModel categoryModel) {

        return (root, query, cb) -> {
            List<Predicate> orPredicates = new ArrayList<>();

            // Setting up the query and field name Predicate
            if (fields != null && !fields.isEmpty() && queryData != null && !queryData.isEmpty()) {
                for (String field : fields) {
                    orPredicates.add(cb.like(cb.lower(root.get(field)), "%" + queryData.toLowerCase() + "%"));
                }
            }

            Predicate[] arrayOrPredicates = new Predicate[orPredicates.size()];
            orPredicates.toArray(arrayOrPredicates);
            Predicate orPredicate = cb.or(arrayOrPredicates);

            // Setting up predicate for category search and user wise note finding
            List<Predicate> andPredicates = new ArrayList<>();
            String userName = AuthUtils.getUserName();
            if (userName != null) {
                andPredicates.add(cb.equal(root.get("createdBy"), userName));
            }
            if (categoryModel != null) {
                andPredicates.add(cb.equal(root.get("category"), categoryModel));
            }

            Predicate[] arrayAndPredicates = new Predicate[andPredicates.size()];
            andPredicates.toArray(arrayAndPredicates);
            Predicate andPredicate = cb.and(arrayAndPredicates);

            if (andPredicates.size() > 0 && orPredicates.size() > 0) {
                return cb.and(orPredicate, andPredicate); // return the combined  'and' 'or' predicate
            } else {
                return andPredicate; // searching with only category and username
            }

        };
    }

}
