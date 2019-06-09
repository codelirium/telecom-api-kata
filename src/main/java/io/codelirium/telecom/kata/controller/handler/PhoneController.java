package io.codelirium.telecom.kata.controller.handler;

import io.codelirium.telecom.kata.domain.dto.PhoneDTO;
import io.codelirium.telecom.kata.domain.dto.response.RESTSuccessResponseBody;
import io.codelirium.telecom.kata.service.PhoneService;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.inject.Inject;
import java.util.List;

import static io.codelirium.telecom.kata.controller.mapping.UrlMappings.*;
import static io.codelirium.telecom.kata.domain.dto.response.builder.RESTResponseBodyBuilder.success;
import static java.util.Collections.singletonList;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(API_PATH_ROOT)
public class PhoneController {

	private static final Logger LOGGER = getLogger(PhoneController.class);


	private PhoneService phoneService;


	@Inject
	public PhoneController(final PhoneService phoneService) {

		this.phoneService = phoneService;

	}


	@ResponseStatus(OK)
	@GetMapping(value = API_CATALOG_PHONES, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<RESTSuccessResponseBody<PhoneDTO>> readAll() {

		final List<PhoneDTO> allPhones = phoneService.readAll();


		LOGGER.debug("Building response for all phones retrieval ...");

		final RESTSuccessResponseBody<PhoneDTO> body = success(PhoneDTO.class.getSimpleName(), allPhones);

		LOGGER.debug("Response for all phones retrieval was built successfully.");


		return new ResponseEntity<>(body, OK);
	}


	@ResponseStatus(OK)
	@GetMapping(value = API_CATALOG_PHONES, params = REQ_PARAM_CUSTOMER_ID, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<RESTSuccessResponseBody<PhoneDTO>> readByCustomerId(@RequestParam(REQ_PARAM_CUSTOMER_ID) final String customerId) {

		final List<PhoneDTO> phoneDTOs = phoneService.readByCustomerId(customerId);


		LOGGER.debug("Building response for customer phones retrieval ...");

		final RESTSuccessResponseBody<PhoneDTO> body = success(PhoneDTO.class.getSimpleName(), phoneDTOs);

		LOGGER.debug("Response for customer phones retrieval was built successfully.");


		return new ResponseEntity<>(body, OK);
	}


	@ResponseStatus(OK)
	@PostMapping(value = API_CATALOG_PHONES + "/{" + PATH_PARAM_NUMBER + "}" + API_CATALOG_ACTIVATIONS, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<RESTSuccessResponseBody<PhoneDTO>> activate(@PathVariable final String number) {

		final PhoneDTO activatedPhoneDTO = phoneService.activate(number);


		LOGGER.debug("Building response for activated phone ...");

		final RESTSuccessResponseBody<PhoneDTO> body = success(PhoneDTO.class.getSimpleName(), singletonList(activatedPhoneDTO));

		LOGGER.debug("Response for activated phone was built successfully.");


		return new ResponseEntity<>(body, OK);
	}
}
