package com.katafoni.filemanager.file.search;

import com.katafoni.filemanager.file.FileEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class FileSpecification implements Specification<FileEntity> {
    private SearchCriteria criteria;
    private List<Predicate> predicates = new ArrayList<>();

    public FileSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<FileEntity> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        if (criteria.getOwnerId() != null) {
            predicates.add(cb.equal(root.get("owner").get("id"), criteria.getOwnerId()));
        }
        if (criteria.getOperation().equalsIgnoreCase("like") && !criteria.getValue().isBlank()) {
            predicates.add(cb.like(root.get(criteria.getKey()), criteria.getValue() + "%"));
        }

        return cq.where(cb.and(predicates.toArray(new Predicate[0]))).getRestriction();
    }
}
