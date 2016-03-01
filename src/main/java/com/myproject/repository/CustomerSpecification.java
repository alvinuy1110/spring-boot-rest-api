package com.myproject.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.myproject.domain.Customer;

public class CustomerSpecification implements Specification<Customer> {
	private String sex;
	private String type;

	private static final String ATTR_SEX = "sex";
	private static final String ATTR_TYPE = "type";

	/**
	 * @param sex
	 * @param type
	 */
	public CustomerSpecification(String sex, String type) {
		super();
		this.sex = sex;
		this.type = type;
	}

	@Override
	public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate p = cb.conjunction();
		if (sex != null) {
			p = cb.and(cb.equal(root.get(ATTR_SEX), sex));
		}
		if (type != null) {
			p = cb.and(cb.equal(root.get(ATTR_TYPE), type));
		}

		return p;

	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

}
