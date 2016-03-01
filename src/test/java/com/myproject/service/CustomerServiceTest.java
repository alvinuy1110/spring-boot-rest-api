package com.myproject.service;

import org.junit.After;
import org.junit.Before;

public class CustomerServiceTest {

	CustomerService custService;
  @Before
  public void setUp() throws Exception {
		custService = new CustomerService();
  }

  @After
  public void tearDown() throws Exception {
  }

	// @Test
	// public void testCreateCustomerResource() {
	//
	// DocPackageRequest req = TestObjectFactory.getDocPackageRequest();
	// DocPackageResource docPackRes = custService.getDocPackageResource(req);
	//
	// validate(req, docPackRes);
	// }
	//
	// private void validate(DocPackageRequest req, DocPackageResource
	// docPackRes) {
	// assertNotNull("DocPackageRequest is null", req);
	// assertNotNull("DocPackageResource is null", docPackRes);
	//
	// assertEquals(req.getSource(), docPackRes.getType());
	// assertEquals(req.getApplicationId(), docPackRes.getReferenceId());
	// assertEquals(req.getUserId(), docPackRes.getOriginatorId());
	//
	// // TODO
	// assertNotNull("AutoClose is null", docPackRes.getAutoClose());
	// assertNotNull("Status is null", docPackRes.getStatus());
	//
	// // TODO
	// // assertEquals(req.getUserId(), docPackRes.getLabels());
	// Customer cust = docPackRes.getCustomer();
	// assertNotNull("customer is null", cust);
	//
	// assertEquals(req.getCustomerId(), cust.getId());
	// assertEquals(req.getFirstName(), cust.getFirstName());
	// assertEquals(req.getLastName(), cust.getLastName());
	//
	// }

}
