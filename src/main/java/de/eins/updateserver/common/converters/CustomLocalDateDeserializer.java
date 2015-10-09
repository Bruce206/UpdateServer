package de.eins.updateserver.common.converters;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomLocalDateDeserializer extends JsonDeserializer<LocalDate> {

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	@Override
	public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		System.out.println(jp.getValueAsString());
		LocalDate dateTime = LocalDate.parse(jp.getValueAsString(), formatter);
		
		return dateTime;
	}

}
