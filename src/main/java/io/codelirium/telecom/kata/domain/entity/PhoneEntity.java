package io.codelirium.telecom.kata.domain.entity;

import io.codelirium.telecom.kata.domain.entity.base.IdentifiableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

import static io.codelirium.telecom.kata.domain.entity.base.IdentifiableEntity.COLUMN_NAME_ID;
import static io.codelirium.telecom.kata.domain.entity.base.IdentifiableEntity.FIELD_NAME_ID;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = PhoneEntity.TABLE_NAME)
@AttributeOverride(name = FIELD_NAME_ID, column = @Column(name = COLUMN_NAME_ID))
public class PhoneEntity extends IdentifiableEntity<Long> implements Serializable {

	private static final long serialVersionUID = -828276729279891177L;


	static final String TABLE_NAME = "PHONES";


	@Column(name = "CUSTOMER_ID", nullable = false)
	private String customerId;

	@Column(name = "NUMBER", nullable = false)
	private String number;

	@Column(name = "IS_ACTIVE", nullable = false)
	private Boolean isActive;

}
