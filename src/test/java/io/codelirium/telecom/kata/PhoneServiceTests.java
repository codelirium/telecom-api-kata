package io.codelirium.telecom.kata;

import io.codelirium.telecom.kata.domain.dto.PhoneDTO;
import io.codelirium.telecom.kata.domain.entity.PhoneEntity;
import io.codelirium.telecom.kata.repository.PhoneRepository;
import io.codelirium.telecom.kata.service.PhoneService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.Random;

import static ch.qos.logback.core.CoreConstants.EMPTY_STRING;
import static io.codelirium.telecom.kata.util.DomainUtil.getPhoneEntities;
import static io.codelirium.telecom.kata.util.DomainUtil.getPhoneEntity;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static net.bytebuddy.utility.RandomString.make;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;


public class PhoneServiceTests {

	@Rule
	public ExpectedException expectedException = none();

	@Mock
	private PhoneRepository phoneRepository;

	private PhoneService phoneService;


	{

		initMocks(this);

	}


	@Before
	public void setUp() {

		reset(phoneRepository);

		phoneService = new PhoneService(new ModelMapper(), phoneRepository);

	}


	@Test
	public void testThatAllPhonesComeFromTheRepository() {

		final List<PhoneEntity> thePhoneEntities = getPhoneEntities(10);


		when(phoneRepository.findAll()).thenReturn(thePhoneEntities);


		final List<PhoneDTO> outputPhoneDTOs = phoneService.readAll();


		assertEquals(thePhoneEntities.size(), outputPhoneDTOs.size());

		verify(phoneRepository, times(1)).findAll();
	}


	@Test
	public void testThatSearchedPhonesComeFromTheRepository() {

		final List<PhoneEntity> thePhoneEntities = getPhoneEntities(10);


		when(phoneRepository.findByCustomerId(anyString())).thenReturn(thePhoneEntities);


		final List<PhoneDTO> outputPhoneDTOs = phoneService.readByCustomerId(make(10));


		assertEquals(thePhoneEntities.size(), outputPhoneDTOs.size());

		verify(phoneRepository, times(1)).findByCustomerId(anyString());
	}


	@Test
	public void testThatBlankSearchTermResultsToEmptyListResult() {

		final List<PhoneEntity> thePhoneEntities = getPhoneEntities(10);


		when(phoneRepository.findByCustomerId(anyString())).thenReturn(thePhoneEntities);


		final List<PhoneDTO> outputPhoneDTOs = phoneService.readByCustomerId(EMPTY_STRING);


		assertThat(outputPhoneDTOs.size(), equalTo(0));

		verify(phoneRepository, times(0)).findByCustomerId(anyString());
	}


	@Test
	public void testThatActivatedPhoneComesFromTheRepository() {

		final PhoneEntity thePhoneEntity = getPhoneEntity(new Random().nextLong());


		when(phoneRepository.findByNumber(anyString())).thenReturn(of(thePhoneEntity));

		when(phoneRepository.saveAndFlush(any(PhoneEntity.class))).thenReturn(thePhoneEntity);


		final PhoneDTO outputPhoneDTO = phoneService.activate(make(10));


		assertTrue(outputPhoneDTO.getIsActive());

		verify(phoneRepository, times(1)).saveAndFlush(any(PhoneEntity.class));
	}


	@Test
	public void testThatNonExistentPhoneCannotBeActivated() {

		when(phoneRepository.findByNumber(anyString())).thenReturn(empty());


		expectedException.expect(RuntimeException.class);

		phoneService.activate(make(10));


		verify(phoneRepository, times(0)).saveAndFlush(any(PhoneEntity.class));
	}
}
