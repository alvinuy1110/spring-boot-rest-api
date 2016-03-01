package com.myproject.service;

import com.myproject.api.domain.CustomerResource;
import com.myproject.domain.Customer;

public interface ICustomerService {
  	/**
	 * Process the request create the customer;
	 * 
	 * @param req
	 * @return null if not able to process
	 */
	public Customer createCustomer(CustomerResource req);

}
