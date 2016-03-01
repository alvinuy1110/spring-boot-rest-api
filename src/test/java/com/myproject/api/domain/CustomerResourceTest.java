package com.myproject.api.domain;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CustomerResourceTest {

	CustomerResource res;

  @Before
  public void setUp() throws Exception {
		res = new CustomerResource();
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testGetType() {
    String val = "acbbbcd";
    res.setType(val);
    assertEquals(res.getType(), val);
  }

  @Test
	public void testGetFirstName() {
    String val = "acbbbcd";
		res.setFirstName(val);
		assertEquals(res.getFirstName(), val);
  }
  @Test
	public void testGetLastName() {
    String val = "acbbbcd";
		res.setLastName(val);
		assertEquals(res.getLastName(), val);
  }



  @Test
	public void testGetSex() {
    String val = "acbbbcd";
		res.setSex(val);
		assertEquals(res.getSex(), val);
  }




}
