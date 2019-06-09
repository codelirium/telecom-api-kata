package io.codelirium.telecom.kata.service;

import io.codelirium.telecom.kata.domain.dto.PhoneDTO;
import io.codelirium.telecom.kata.domain.entity.PhoneEntity;
import io.codelirium.telecom.kata.repository.PhoneRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.TRUE;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.Assert.notNull;


@Service
@Transactional
public class PhoneService {

	private ModelMapper modelMapper;

	private PhoneRepository phoneRepository;


	@Inject
	public PhoneService(final ModelMapper modelMapper, final PhoneRepository phoneRepository) {

		this.modelMapper = modelMapper;

		this.phoneRepository = phoneRepository;

	}


	public List<PhoneDTO> readAll() {

		return phoneRepository.findAll()
								.parallelStream()
									.map(phoneEntity -> modelMapper.map(phoneEntity, PhoneDTO.class))
									.collect(toList());
	}


	public List<PhoneDTO> readByCustomerId(final String customerId) {

		notNull(customerId, "The customer id cannot be null.");


		return !customerId.isEmpty() ? phoneRepository.findByCustomerId(customerId)
												.parallelStream()
													.map(phoneEntity -> modelMapper.map(phoneEntity, PhoneDTO.class))
													.collect(toList())
									: emptyList();
	}


	public PhoneDTO activate(final String number) {

		notNull(number, "The number cannot be null.");


		final Optional<PhoneEntity> optionalPhoneEntity = phoneRepository.findByNumber(number);

		if (optionalPhoneEntity.isPresent()) {

			optionalPhoneEntity.get().setIsActive(TRUE);

			final PhoneEntity persistedPhoneEntity = phoneRepository.saveAndFlush(optionalPhoneEntity.get());


			return modelMapper.map(persistedPhoneEntity, PhoneDTO.class);
		}


		throw new RuntimeException("The number does not exist.");
	}
}
