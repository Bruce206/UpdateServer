package de.eins.updateserver.common;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import de.eins.updateserver.common.converters.CustomLocalDateTimeDeserializer;
import de.eins.updateserver.common.converters.CustomLocalDateTimeSerializer;

/**
 * Adds a created- and a modified-date to all entities which extend this one.
 */
@MappedSuperclass
public abstract class AbstractTimestampEntity {

	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@Column(name = "created", nullable = false)
	private LocalDateTime created;

	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@Column(name = "updated", nullable = false)
	private LocalDateTime updated;

	@PrePersist
	protected void onCreate() {
		this.updated = this.created = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updated = LocalDateTime.now();
	}

	public LocalDateTime getCreated() {
		return this.created;
	}

	public LocalDateTime getUpdated() {
		return this.updated;
	}

}