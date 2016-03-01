package com.myproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myproject.api.domain.CustomerResource;
import com.myproject.api.hypermedia.CustomerResourceAssembler;
import com.myproject.domain.Customer;
import com.myproject.repository.CustomerRepository;
import com.myproject.repository.CustomerSpecification;
import com.myproject.service.ICustomerService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

// to be accessible
@CrossOrigin
@Controller
public class CustomerController {

	@SuppressWarnings("rawtypes")
	@Autowired
	private PagedResourcesAssembler pagedAssembler;

	@Autowired
	private CustomerResourceAssembler customerResourceAssembler;

	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private ICustomerService custService;

	@ApiOperation(value = "getCustomer", nickname = "getCustomer")
	@RequestMapping(value = "api/v1/customers/{custId}", method = RequestMethod.GET)
	// @ApiImplicitParams({
	// @ApiImplicitParam(name = "name", value = "User's name", required = false,
	// dataType = "string", paramType = "query", defaultValue = "Niklas") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = CustomerResource.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })

	@ResponseBody
	public HttpEntity<CustomerResource> getCustomer(@PathVariable(value = "custId") String customerId) {

		Integer custId;
		try {
			custId = Integer.valueOf(customerId);
		} catch (NumberFormatException nfe) {
			return new ResponseEntity<CustomerResource>(HttpStatus.NOT_FOUND);
		}
		// real one using repo
		Customer customer = customerRepo.findOne(custId);

		if (customer == null) {
			return new ResponseEntity<CustomerResource>(HttpStatus.NOT_FOUND);
		}

		CustomerResource customerResource = customerResourceAssembler.toResource(customer);

		return new ResponseEntity<CustomerResource>(customerResource, HttpStatus.OK);
	}

	@RequestMapping(value = "api/v1/customers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody

	public HttpEntity<PagedResources<CustomerResource>> getCustomers(@PageableDefault Pageable p,
			@RequestParam(value = "sex", required = false) String sex,
			@RequestParam(value = "type", required = false) String type) {

		Page<Customer> page = null;
		Specification<Customer> spec = new CustomerSpecification(sex, type);
		page = customerRepo.findAll(spec, p);

		if (page == null) {
			return new ResponseEntity<PagedResources<CustomerResource>>(HttpStatus.NOT_FOUND);
		}

		// TODO
		// Given the fields, you can modify the resource assembler to modify the
		// fields to be returned!

		PagedResources<CustomerResource> pagedResources = pagedAssembler.toResource(page, customerResourceAssembler);

		// return standard json
		return new ResponseEntity<PagedResources<CustomerResource>>(pagedResources, HttpStatus.OK);
	}

	@RequestMapping(value = "api/v1/customers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody

	public HttpEntity<CustomerResource> createCustomer(@RequestBody CustomerResource request) {

		Customer customer = custService.createCustomer(request);
		CustomerResource customerResource = customerResourceAssembler.toResource(customer);
		// CustomerResource custResource =
		// custService.createCustomerResource(request);

		return new ResponseEntity<CustomerResource>(customerResource, HttpStatus.OK);
	}

	/**
	 * @param pagedAssembler
	 *            the pagedAssembler to set
	 */
	@SuppressWarnings("rawtypes")
	public void setPagedAssembler(PagedResourcesAssembler pagedAssembler) {
		this.pagedAssembler = pagedAssembler;
	}

	/**
	 * @param custService
	 *            the custService to set
	 */
	public void setCustService(ICustomerService custService) {
		this.custService = custService;
	}

	/**
	 * @param customerRepo
	 *            the customerRepo to set
	 */
	public void setCustomerRepo(CustomerRepository customerRepo) {
		this.customerRepo = customerRepo;
	}

	/**
	 * @param customerResourceAssembler
	 *            the customerResourceAssembler to set
	 */
	public void setCustomerResourceAssembler(CustomerResourceAssembler customerResourceAssembler) {
		this.customerResourceAssembler = customerResourceAssembler;
	}

}