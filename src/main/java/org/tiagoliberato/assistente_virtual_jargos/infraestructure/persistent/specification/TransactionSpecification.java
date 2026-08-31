package org.tiagoliberato.assistente_virtual_jargos.infraestructure.persistent.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.tiagoliberato.assistente_virtual_jargos.application.transactions.dto.TransactionQuery;
import org.tiagoliberato.assistente_virtual_jargos.infraestructure.persistent.entity.TransactionEntity;

import java.util.ArrayList;
import java.util.List;



public class TransactionSpecification {

    public static Specification<TransactionEntity> from(TransactionQuery query){
        return(root, criteriaQuery, criteriaBuilder) ->{
            List<Predicate> predicates = new ArrayList<>();

            if(query.startDate() != null){
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("date"), query.startDate())
                );
            }
            if(query.endDate() != null){
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(root.get("date"), query.endDate())
                );
            }
            if(query.Category() != null){
                predicates.add(
                        criteriaBuilder.equal(
                                criteriaBuilder.upper(root.get("category")), query.Category())
                );
            }
            if(query.minAmount() != null){
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), query.minAmount())
                );
            }
            if(query.maxAmount() != null){
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(root.get("amount"), query.maxAmount())
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
