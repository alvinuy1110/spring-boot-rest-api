package com.myproject.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.exception.GenericJDBCException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.RepositoryConfig;
import com.myproject.TestConfig;
import com.myproject.TestObjectFactory;
import com.myproject.WebServiceConfig;
import com.myproject.api.domain.CustomerResource;
import com.myproject.api.hypermedia.CustomerResourceAssembler;
import com.myproject.domain.Customer;
import com.myproject.repository.CustomerRepository;
import com.myproject.service.ICustomerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {WebServiceConfig.class, RepositoryConfig.class, TestConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
  DirtiesContextTestExecutionListener.class,
  TransactionalTestExecutionListener.class})
@WebAppConfiguration
@Transactional
public class CustomerControllerTest {
	private static final String GET_CUSTOMER_URL = "/api/v1/customers/{custId}";
	private static final String GET_CUSTOMERS_URL = "/api/v1/customers";
	private static final String CREATE_CUSTOMERS_URL = "/api/v1/customers";

  @Mock
	private ICustomerService custService;
  // @Autowired
  @Mock
	private CustomerResourceAssembler custResourceAssembler;
  @Autowired
  private PagedResourcesAssembler pagedAssembler;
  @Mock
	private CustomerRepository custRepo;

  @Autowired
  private ObjectMapper objectMapper;

  @InjectMocks
	private CustomerController resource = new CustomerController();
  // @Autowired
  // private WebApplicationContext applicationContext;
  MockHttpServletRequestBuilder request;
  private HttpHeaders headers;
  private MockMvc mockMvc;

  @Before
  public void setup() throws Exception {
    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    // if there are custom/ other annotations, you have to use the full mode and do something like an integration test. You need to supply
    // db unit testing
    // Setup Spring test in full mode. Use this for negative test cases
    // mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();

    // test only the resource. Combine with mocks
    // mockMvc = MockMvcBuilders.standaloneSetup(resource).build();
    mockMvc = MockMvcBuilders.standaloneSetup(resource)
      // add the pagination support
      .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
      // add the exception handler
      .setHandlerExceptionResolvers(createExceptionResolver()).build();

    // Sets headers...
    headers = new HttpHeaders();

    headers.put(HttpHeaders.ACCEPT, Arrays.asList(new String[] {MediaType.APPLICATION_JSON_VALUE}));


    // Inject actual beans
		resource.setCustomerResourceAssembler(custResourceAssembler);
    resource.setPagedAssembler(pagedAssembler);
		resource.setCustomerRepo(custRepo);
		resource.setCustService(custService);


  }

  // set up the controllerAdvice
  private ExceptionHandlerExceptionResolver createExceptionResolver() {
    ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {

      @Override
      protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
        Method method = new ExceptionHandlerMethodResolver(AppExceptionHandler.class).resolveMethod(exception);
        return new ServletInvocableHandlerMethod(new AppExceptionHandler(), method);
      }

    };
    exceptionResolver.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    exceptionResolver.afterPropertiesSet();
    return exceptionResolver;
  }
  @Test
	public void testGetCustomer() throws Exception {


		Customer customer = mock(Customer.class);
		when(custRepo.findOne(any(Integer.class))).thenReturn(customer);

		CustomerResource custResource = new CustomerResource();
		custResource.setFirstName("Name1");
		custResource.setType("some_type");

		when(custResourceAssembler.toResource(customer)).thenReturn(custResource);

    request = get(GET_CUSTOMER_URL, "1").headers(headers);

    ResultActions resultActions = this.mockMvc.perform(request).andDo(print()).andExpect(status().isOk());

		resultActions.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.firstName", is("Name1")))
      .andExpect(jsonPath("$.type", is("some_type")));
      // String json = resultActions.andReturn().getResponse().getContentAsString();

		Mockito.verify(custRepo, Mockito.times(1)).findOne(any(Integer.class));


  }

  @Test
	public void testGetCustomer_NotFound() throws Exception {

		Customer customer = null;
		when(custRepo.findOne(any(Integer.class))).thenReturn(customer);

		CustomerResource custResource = new CustomerResource();
		when(custResourceAssembler.toResource(customer)).thenReturn(custResource);

    request = get(GET_CUSTOMER_URL, "1").headers(headers);

    ResultActions resultActions = this.mockMvc.perform(request).andDo(print()).andExpect(status().isNotFound());

    // no payload
    assertEquals(resultActions.andReturn().getResponse().getContentLength(), 0);

		Mockito.verify(custRepo, Mockito.times(1)).findOne(any(Integer.class));

  }
  @Test
	public void testGetCustomer_Error() throws Exception {

		when(custRepo.findOne(any(Integer.class))).thenThrow(new RuntimeException("test"));


    request = get(GET_CUSTOMER_URL, "1").headers(headers);

    ResultActions resultActions = this.mockMvc.perform(request).andDo(print()).andExpect(status().is5xxServerError());

    resultActions.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$.code", is("ERR_SYSTEM")))
      .andExpect(jsonPath("$.message", is("ERROR SYSTEM")));

		Mockito.verify(custRepo, Mockito.times(1)).findOne(any(Integer.class));

  }

  @Test
	public void testGetCustomer_DBError() throws Exception {

		when(custRepo.findOne(any(Integer.class)))
				.thenThrow(new GenericJDBCException("test", mock(SQLException.class)));

    request = get(GET_CUSTOMER_URL, "1").headers(headers);

    ResultActions resultActions = this.mockMvc.perform(request).andDo(print()).andExpect(status().is5xxServerError());

    resultActions.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$.code", is("ERR_SYSTEM")))
      .andExpect(jsonPath("$.message", is("ERROR SYSTEM")));

		Mockito.verify(custRepo, Mockito.times(1)).findOne(any(Integer.class));

  }

  @Test
	public void testGetCustomers() throws Exception {

		Customer customer = mock(Customer.class);
		List<Customer> list = new ArrayList<>();
		list.add(customer);
		Page<Customer> page = new PageImpl<>(list);
		when(custRepo.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

    request = get(GET_CUSTOMERS_URL).headers(headers);

    ResultActions resultActions = this.mockMvc.perform(request).andDo(print()).andExpect(status().isOk());

    // no payload
    assertEquals(resultActions.andReturn().getResponse().getContentLength(), 0);

		Mockito.verify(custRepo, Mockito.times(1)).findAll(any(Specification.class), any(Pageable.class));

  }

  @Test
	public void testGetCustomers_NotFound() throws Exception {

		Page<Customer> page = null;
		when(custRepo.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

    request = get(GET_CUSTOMERS_URL).headers(headers);

    ResultActions resultActions = this.mockMvc.perform(request).andDo(print()).andExpect(status().isNotFound());

    // no payload
    assertEquals(resultActions.andReturn().getResponse().getContentLength(), 0);

		Mockito.verify(custRepo, Mockito.times(1)).findAll(any(Specification.class), any(Pageable.class));

  }


	// private DocPackageResource getDocPackageResource(DocPackageRequest req) {
	// DocPackageResource res = new DocPackageResource();
	//
	// res.setType(req.getSource());
	// res.setReferenceId(req.getApplicationId());
	// res.setOriginatorId(req.getUserId());
	//
	// // TODO
	// res.setStatus("OPEN");
	// res.setAutoClose(false);
	//
	// // TODO
	// // res.setLabels(labels);
	//
	// res.setCustomer(toCustomer(req));
	// return res;
	// }

	// private Customer toCustomer(DocPackageRequest req) {
	// Integer custId = req.getCustomerId();
	// String fname = req.getFirstName();
	// String lname = req.getLastName();
	// Customer cust = null;
	// if (custId != null || fname != null || lname != null) {
	// cust = new Customer();
	// cust.setFirstName(fname);
	// cust.setLastName(lname);
	// cust.setId(custId);
	// }
	// return cust;
	// }
	@Test
	public void testCreateCustomers() throws Exception {
		CustomerResource req = TestObjectFactory.getCustomerResource();

		Customer cust = TestObjectFactory.getCustomer();
		// DocPackageResource docPackRes = getDocPackageResource(req);
		// mock response
		when(custService.createCustomer(any(CustomerResource.class))).thenReturn(cust);

		when(custResourceAssembler.toResource(cust)).thenReturn(req);

		String jsonPayload = objectMapper.writeValueAsString(req);
		request = post(CREATE_CUSTOMERS_URL).headers(headers).content(jsonPayload)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		ResultActions resultActions = this.mockMvc.perform(request).andDo(print()).andExpect(status().isOk());

		// no payload
		//
		assertEquals(resultActions.andReturn().getResponse().getContentLength(), 0);

		Mockito.verify(custService, Mockito.times(1)).createCustomer(any(CustomerResource.class));
		Mockito.verify(custResourceAssembler, Mockito.times(1)).toResource(any(Customer.class));
	}
	//
	// @Test
	// public void testCreateDocPackage_InvalidPayload() throws Exception {
	//
	// String jsonPayload = "aavxcvxv";
	// request =
	// post(CREATE_DOC_PACKAGE_URL).headers(headers).content(jsonPayload).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
	//
	// ResultActions resultActions =
	// this.mockMvc.perform(request).andDo(print()).andExpect(status().isBadRequest());
	//
	// resultActions.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$.code",
	// is("ERR_SYSTEM")))
	// .andExpect(jsonPath("$.message", is("ERROR SYSTEM")));
	//
	// assertTrue(resultActions.andReturn().getResolvedException() instanceof
	// HttpMessageNotReadableException);
	//
	// }
	//
	// @Test
	// public void testCreateDocPackage_MissingPayload() throws Exception {
	//
	// String jsonPayload = null;
	// request =
	// post(CREATE_DOC_PACKAGE_URL).headers(headers).accept(MediaType.APPLICATION_JSON);
	//
	// ResultActions resultActions =
	// this.mockMvc.perform(request).andDo(print()).andExpect(status().isBadRequest());
	//
	// resultActions.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$.code",
	// is("ERR_SYSTEM")))
	// .andExpect(jsonPath("$.message", is("ERROR SYSTEM")));
	//
	// assertTrue(resultActions.andReturn().getResolvedException() instanceof
	// HttpMessageNotReadableException);
	//
	// }
}
