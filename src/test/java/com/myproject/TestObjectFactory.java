package com.myproject;

import java.util.concurrent.ThreadLocalRandom;

import com.myproject.api.domain.CustomerResource;
import com.myproject.domain.Customer;

/**
 * Test objects 
 *
 */
public class TestObjectFactory {

  /**Generate a random string
   * @return
   */
  public static String generateRandomString() {
    return Long.toHexString(ThreadLocalRandom.current().nextLong());
  }

	public static CustomerResource getCustomerResource() {

		CustomerResource cust = new CustomerResource();
		cust.setFirstName("Some fname");
		cust.setLastName("LName aaa");
		cust.setSex("M");
		cust.setCustomerId(3478);
		cust.setType("C");
		return cust;
	}

  public static Customer getCustomer() {

    Customer cust = new Customer();
    cust.setFirstName("Some fname");
    cust.setLastName("LName aaa");
		cust.setSex("M");
		cust.setType("C");
    cust.setId(3478);
    return cust;
  }

}
