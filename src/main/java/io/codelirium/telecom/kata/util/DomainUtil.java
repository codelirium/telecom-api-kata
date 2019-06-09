package io.codelirium.telecom.kata.util;

import io.codelirium.telecom.kata.domain.dto.PhoneDTO;
import io.codelirium.telecom.kata.domain.entity.PhoneEntity;
import java.util.List;

import static com.google.common.collect.Lists.newArrayListWithExpectedSize;
import static java.lang.Boolean.FALSE;
import static java.lang.Integer.toUnsignedLong;
import static java.util.stream.IntStream.range;
import static net.bytebuddy.utility.RandomString.make;


public class DomainUtil {

	private DomainUtil() { }


	public static PhoneDTO getPhoneDTO(final Long id) {

		return new PhoneDTO(id, make(10), make(10), FALSE);

	}


	public static List<PhoneDTO> getPhoneDTOs(final int size) {

		final List<PhoneDTO> phoneDTOs = newArrayListWithExpectedSize(size);

		range(0, size).forEach(i -> phoneDTOs.add(getPhoneDTO(toUnsignedLong(i))));


		return phoneDTOs;
	}


	public static PhoneEntity getPhoneEntity(final Long id) {

		final PhoneEntity phoneEntity = new PhoneEntity(make(10), make(10), FALSE);

		phoneEntity.setId(id);


		return phoneEntity;
	}


	public static List<PhoneEntity> getPhoneEntities(final int size) {

		final List<PhoneEntity> phoneEntities = newArrayListWithExpectedSize(size);

		range(0, size).forEach(i -> phoneEntities.add(getPhoneEntity(toUnsignedLong(i))));


		return phoneEntities;
	}
}
