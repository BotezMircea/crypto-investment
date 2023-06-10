package com.botez.mircea.cryptoinvestment.model;

import com.botez.mircea.cryptoinvestment.model.entity.Currency;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

public class CurrencySpecification {

    public static Specification<Currency> timestampInBetween(Long startDate, Long endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("timestamp"), startDate, endDate);
    }

    public static Specification<Currency> startDateEquals(Long startDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("timestamp"), startDate);
    }

    public static Specification<Currency> symbolEquals(String symbol) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("symbol"), symbol);
    }
}
