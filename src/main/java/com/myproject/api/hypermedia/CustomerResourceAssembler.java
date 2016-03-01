package com.myproject.api.hypermedia;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.myproject.api.domain.CustomerResource;
import com.myproject.domain.Customer;

@Component
public class CustomerResourceAssembler extends ResourceAssemblerSupport<Customer, CustomerResource> {

	public CustomerResourceAssembler(Class<?> controllerClass, Class<CustomerResource> resourceType) {
		super(controllerClass, resourceType);
	}

	@Override
	public CustomerResource toResource(Customer entity) {

		CustomerResource resource = createResourceWithId(entity.getId(), entity);

		return resource;
	}

	@Override
	protected CustomerResource instantiateResource(Customer entity) {

		CustomerResource res = new CustomerResource();
		res.setCustomerId(entity.getId());
		res.setFirstName(entity.getFirstName());
		res.setLastName(entity.getLastName());
		res.setType(entity.getType());
		res.setSex(entity.getSex());
		return res;
	}

}
