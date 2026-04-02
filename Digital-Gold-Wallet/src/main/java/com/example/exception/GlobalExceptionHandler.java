package com.example.exception;

import com.example.dto.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ResponseBody
	@ExceptionHandler(VendorNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorDTO handleVendorNotFound(VendorNotFoundException e, HttpServletRequest request) {
		return new ErrorDTO(e.getMessage(), LocalDate.now(), request.getRequestURI());
	}

	@ResponseBody
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorDTO handleUserNotFound(UserNotFoundException e, HttpServletRequest request) {
		return new ErrorDTO(e.getMessage(), LocalDate.now(), request.getRequestURI());
	}
	
	@ResponseBody
	@ExceptionHandler(TransactionNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorDTO handleTransactionNotFound(TransactionNotFoundException e, HttpServletRequest request) {
		return new ErrorDTO(e.getMessage(), LocalDate.now(), request.getRequestURI());
	}

	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Map<String, ErrorDTO> handleValidationException(MethodArgumentNotValidException e,
			HttpServletRequest request) {
		List<ObjectError> errors = e.getBindingResult().getAllErrors();
		Map<String, ErrorDTO> mp = new HashMap<>();
		for (ObjectError err : errors) {
			String fieldName = ((FieldError) err).getField();
			mp.put(fieldName, new ErrorDTO(err.getDefaultMessage(), LocalDate.now(), request.getRequestURI()));
		}
		return mp;
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorDTO handleGenericException(Exception e, HttpServletRequest request) {
		return new ErrorDTO(e.getMessage(), LocalDate.now(), request.getRequestURI());
	}
}
