package com.example.converter;

import com.example.enums.TransactionType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TransactionTypeConverter implements AttributeConverter<TransactionType, String> {

	@Override
	public String convertToDatabaseColumn(TransactionType attribute) {
		return attribute.getValue(); // Enum → DB
	}

	@Override
	public TransactionType convertToEntityAttribute(String dbData) {
		return TransactionType.fromValue(dbData); // DB → Enum
	}
}