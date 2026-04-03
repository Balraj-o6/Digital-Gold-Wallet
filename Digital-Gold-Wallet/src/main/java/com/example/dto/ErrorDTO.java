package com.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Anant
 */

@Getter
@Setter
public class ErrorDTO {
	private String errorMessage;
	@JsonFormat(pattern = "dd-MMM-yyyy")
	private LocalDate now;
	private String uri;

	public ErrorDTO(String errorMessage, LocalDate now, String uri) {
		this.errorMessage = errorMessage;
		this.now = now;
		this.uri = uri;
	}
}
