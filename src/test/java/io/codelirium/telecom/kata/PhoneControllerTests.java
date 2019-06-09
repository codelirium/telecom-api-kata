package io.codelirium.telecom.kata;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.codelirium.telecom.kata.controller.error.TelecomAPIErrorHandler;
import io.codelirium.telecom.kata.controller.handler.PhoneController;
import io.codelirium.telecom.kata.domain.dto.PhoneDTO;
import io.codelirium.telecom.kata.service.PhoneService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static io.codelirium.telecom.kata.controller.mapping.UrlMappings.*;
import static io.codelirium.telecom.kata.domain.dto.response.builder.RESTResponseBodyBuilder.success;
import static io.codelirium.telecom.kata.util.DomainUtil.getPhoneDTO;
import static io.codelirium.telecom.kata.util.DomainUtil.getPhoneDTOs;
import static java.util.Collections.singletonList;
import static net.bytebuddy.utility.RandomString.make;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@RunWith(MockitoJUnitRunner.class)
public class PhoneControllerTests {

	@Mock
	private PhoneService phoneService;

	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper();


	{

		initMocks(this);

	}


	@Before
	public void setUp() {

		reset(phoneService);

		mockMvc = standaloneSetup(new PhoneController(phoneService))
								.setControllerAdvice(new TelecomAPIErrorHandler(objectMapper))
								.build();
	}


	@Test
	public void testThatGetAllPhonesIsOkWithContent() throws Exception {

		final List<PhoneDTO> thePhoneDTOs = getPhoneDTOs(10);

		when(phoneService.readAll()).thenReturn(thePhoneDTOs);

		mockMvc.perform(get(API_PATH_ROOT + API_CATALOG_PHONES))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(content().json(objectMapper.writeValueAsString(success(PhoneDTO.class.getSimpleName(), thePhoneDTOs))));

		verify(phoneService, times(1)).readAll();
	}


	@Test
	public void testThatGetPhonesByCustomerIdIsOkWithContent() throws Exception {

		final List<PhoneDTO> thePhoneDTOs = getPhoneDTOs(10);

		when(phoneService.readByCustomerId(anyString())).thenReturn(thePhoneDTOs);

		mockMvc.perform(get(API_PATH_ROOT + API_CATALOG_PHONES)
							.param(REQ_PARAM_CUSTOMER_ID, make(10)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(content().json(objectMapper.writeValueAsString(success(PhoneDTO.class.getSimpleName(), thePhoneDTOs))));

		verify(phoneService, times(1)).readByCustomerId(anyString());
	}


	@Test
	public void testThatPostPhoneIsActivatedWithContent() throws Exception {

		final PhoneDTO thePhoneDTO = getPhoneDTO(0L);

		when(phoneService.activate(anyString())).thenReturn(thePhoneDTO);

		mockMvc.perform(post(API_PATH_ROOT + API_CATALOG_PHONES + "/{" + PATH_PARAM_NUMBER + "}" + API_CATALOG_ACTIVATIONS, make(10))
							.content(objectMapper.writeValueAsString(thePhoneDTO))
							.contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(content().json(objectMapper.writeValueAsString(success(PhoneDTO.class.getSimpleName(), singletonList(thePhoneDTO)))));

		verify(phoneService, times(1)).activate(anyString());
	}
}
