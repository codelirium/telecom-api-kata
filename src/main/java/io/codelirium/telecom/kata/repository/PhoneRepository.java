package io.codelirium.telecom.kata.repository;

import io.codelirium.telecom.kata.domain.entity.PhoneEntity;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static io.codelirium.telecom.kata.util.DomainUtil.getPhoneEntities;
import static io.codelirium.telecom.kata.util.DomainUtil.getPhoneEntity;
import static java.util.Collections.emptyList;
import static java.util.Optional.empty;


@Component
public class PhoneRepository {


	public Optional<PhoneEntity> findByNumber(final String number){

		if (new Random().nextInt(10) % 2 == 0) {

			return empty();

		}


		return Optional.of(getPhoneEntity(new Random().nextLong()));
	}


	public List<PhoneEntity> findByCustomerId(final String customerId) {

		if (new Random().nextInt(10) % 2 == 0) {

			return emptyList();

		}


		return getPhoneEntities(new Random().nextInt(5));
	}


	public List<PhoneEntity> findAll() {

		return getPhoneEntities(new Random().nextInt(200));

	}


	public PhoneEntity saveAndFlush(final PhoneEntity phoneEntity) {

		return phoneEntity;

	}
}
