package com.myproject.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.myproject.TestObjectFactory;


public class CustomerTest {

	Customer customer;

  @Before
  public void setUp() throws Exception {
		customer = new Customer();
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testGetId() {

		customer.setId(111);
		assertEquals(customer.getId(), 111);
  }

  @Test
  public void testGetType() {
    String val = "acbbbcddsfdf";
		customer.setType(val);
		assertEquals(customer.getType(), val);
  }


  @Test
	public void testGetFirstName() {
    String val = "some name";
		customer.setFirstName(val);
		assertEquals(customer.getFirstName(), val);
  }

  @Test
	public void testGetLastName() {
		String val = "some name";
		customer.setLastName(val);
		assertEquals(customer.getLastName(), val);
  }

  @Test
  public void testEquals() {
		Customer cust1 = new Customer();
		Customer cust2 = new Customer();

		assertTrue("Initial state should be same", cust1.equals(cust2));

  }

  @Test
  public void testNotEquals() {
		Customer cust1 = TestObjectFactory.getCustomer();
		Customer cust2 = new Customer();

		assertTrue("should not be same", !cust1.equals(cust2));

  }

  @Test
  public void testHashCode() {
		Customer cust1 = new Customer();
		Customer cust2 = new Customer();

		assertNotEquals("should not be same", cust1.hashCode(), cust2.hashCode());

		cust1 = TestObjectFactory.getCustomer();
		cust2 = TestObjectFactory.getCustomer();
		assertNotEquals("should not be same", cust1.hashCode(), cust2.hashCode());
  }
}
