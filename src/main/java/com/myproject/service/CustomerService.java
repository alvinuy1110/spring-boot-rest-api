package com.myproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myproject.api.domain.CustomerResource;
import com.myproject.domain.Customer;
import com.myproject.repository.CustomerRepository;

@Component
public class CustomerService implements ICustomerService {


	@Autowired
	private CustomerRepository customerRepo;

	@Override
	public Customer createCustomer(CustomerResource req) {

		Customer cust =toCustomer(req);
		customerRepo.save(cust);
		return cust;
		// CustomerResource res = req;
		// return res;
	}

	private Customer toCustomer(CustomerResource req) {
		Customer cust = new Customer();
		cust.setFirstName(req.getFirstName());
		cust.setLastName(req.getLastName());
		cust.setSex(req.getSex());
		cust.setType(req.getType());
		return cust;
	}
}
