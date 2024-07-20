package vn.nguyendong.laptopshop.service.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import vn.nguyendong.laptopshop.domain.Product;
import vn.nguyendong.laptopshop.domain.Product_;

// Filter Query with JPA Specifications -> JPA Criteria MetaModel
public class ProductSpecification {
    // case 1
    public static Specification<Product> nameLike(String name) {
        // @Override 'toPredicate' method trong interface Specification (-> là return)
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.NAME), "%" + name + "%");
    }

    // case 2
    public static Specification<Product> minPrice(double min) {
        // ge: greater than or equal
        return (root, query, criteriaBuilder) -> criteriaBuilder.ge(root.get(Product_.PRICE), min);
    }

    // case 3
    public static Specification<Product> maxPrice(double max) {
        // le: less than or equal
        return (root, query, criteriaBuilder) -> criteriaBuilder.le(root.get(Product_.PRICE), max);
    }

    // case 4
    public static Specification<Product> equalFactory(String factory) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Product_.PRICE), factory);
    }

    // case 5
    public static Specification<Product> matchListFactory(List<String> factories) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(Product_.FACTORY)).value(factories);
    }

    // case4
    public static Specification<Product> matchListTarget(List<String> target) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(Product_.TARGET)).value(target);
    }

    // case5
    public static Specification<Product> matchPrice(double min, double max) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.gt(root.get(Product_.PRICE), min),
                criteriaBuilder.le(root.get(Product_.PRICE), max));
    }

    // case6
    public static Specification<Product> matchMultiplePrice(double min, double max) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(
                root.get(Product_.PRICE), min, max);
    }

}
