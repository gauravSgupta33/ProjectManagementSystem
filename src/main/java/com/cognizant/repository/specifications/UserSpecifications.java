package com.cognizant.repository.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.cognizant.entities.User;
import com.cognizant.entities.User_;

public class UserSpecifications {
	/**
     * Creates a specification used to find persons whose last name begins with
     * the given search term. This search is case insensitive.
     * @param searchTerm
     * @return
     */
    public static Specification<User> lastNameIsLike(final String searchTerm) {
        
        return new Specification<User>() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Predicate toPredicate(Root<User> userRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                String likePattern = getLikePattern(searchTerm);
                return cb.like(cb.lower(userRoot.<String>get(User_.lastName)), likePattern);
            }
            
            private String getLikePattern(final String searchTerm) {
                StringBuilder pattern = new StringBuilder();
                pattern.append(searchTerm.toLowerCase());
                pattern.append("%");
                return pattern.toString();
            }
        };
    }
}
