package com.myproject.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.myproject.domain.Customer;

public class CustomerSpecificationTest {

	CustomerSpecification spec;

	private static final String DEFAULT_SEX = "defSex";
	private static final String DEFAULT_TYPE = "defType";

	CriteriaQuery<?> query;
	Root<Customer> root;
	CriteriaBuilder cb;

	Predicate predMock;

	@Before
	public void setUp() throws Exception {
		spec = new CustomerSpecification(DEFAULT_SEX, DEFAULT_TYPE);
		query = mock(CriteriaQuery.class);
		root = mock(Root.class);
		cb = mock(CriteriaBuilder.class);
		predMock = mock(Predicate.class);
		// mock response
		when(cb.conjunction()).thenReturn(predMock);

		when(cb.and(any(Predicate.class))).thenReturn(predMock);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCustomerSpecification() {
		spec = new CustomerSpecification(DEFAULT_SEX, DEFAULT_TYPE);

		assertEquals(spec.getSex(), DEFAULT_SEX);
		assertEquals(spec.getType(), DEFAULT_TYPE);
	}

	@Test
	public void testToPredicate_NullValues() {

		spec = new CustomerSpecification(null, null);
		Predicate pred = spec.toPredicate(root, query, cb);
		assertNotNull("Predicate is null", pred);

		Mockito.verify(cb, Mockito.times(1)).conjunction();
		Mockito.verify(cb, Mockito.times(0)).and(any(Predicate.class));
	}

	@Test
	public void testToPredicate_Sex() {

		spec = new CustomerSpecification(DEFAULT_SEX, null);
		Predicate pred = spec.toPredicate(root, query, cb);
		assertNotNull("Predicate is null", pred);

		Mockito.verify(cb, Mockito.times(1)).conjunction();
		Mockito.verify(cb, Mockito.times(1)).and(any(Predicate.class));
	}

	@Test
	public void testToPredicate_Type() {

		spec = new CustomerSpecification(null, DEFAULT_TYPE);
		Predicate pred = spec.toPredicate(root, query, cb);
		assertNotNull("Predicate is null", pred);

		Mockito.verify(cb, Mockito.times(1)).conjunction();
		Mockito.verify(cb, Mockito.times(1)).and(any(Predicate.class));
	}

	@Test
	public void testToPredicate_SexType() {

		spec = new CustomerSpecification(DEFAULT_SEX, DEFAULT_TYPE);
		Predicate pred = spec.toPredicate(root, query, cb);
		assertNotNull("Predicate is null", pred);

		Mockito.verify(cb, Mockito.times(1)).conjunction();
		Mockito.verify(cb, Mockito.times(2)).and(any(Predicate.class));
	}

}
