package com.myproject.controller;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResourceAccessException;

import com.myproject.api.domain.APIError;

/**
 * Application Exception Handler which will be used to translate exception
 * content to valid HTTP responses.
 * 
 *
 */
@ControllerAdvice
public class AppExceptionHandler {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AppExceptionHandler.class);

  private APIError errSystemAPIError;
  private APIError errInvalidDataAPIError;
	public AppExceptionHandler() {
		errSystemAPIError = getSystemAPIError();
    errInvalidDataAPIError = getInvalidDataAPIError();
	}

  private APIError getSystemAPIError() {
    APIError rs = new APIError();
    // TODO
    rs.setCode("ERR_SYSTEM");
    rs.setMessage("ERROR SYSTEM");
    return rs;
	}

  private APIError getInvalidDataAPIError() {
    APIError rs = new APIError();
    // TODO
    rs.setCode("ERR_SYSTEM");
    rs.setMessage("ERROR SYSTEM");
    return rs;
  }


	@ExceptionHandler(ResourceAccessException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
  public APIError processRestResourceUnavailableException(ResourceAccessException rae, HttpServletRequest request) {

		LOGGER.error("An error occured while trying to access a REST resource. Error message: [" + rae.getMostSpecificCause().getMessage() + "]", rae);

    APIError err = errSystemAPIError;

		return err;

	}



	@ExceptionHandler(GenericJDBCException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
  public APIError processDBSystemError(GenericJDBCException gjdbce, HttpServletRequest request) {

		LOGGER.error("A DB-related error occured while attempting to connect to the database !", gjdbce);

    APIError err = errSystemAPIError;

		return err;

	}




	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
  public APIError processValidationError(MissingServletRequestParameterException msre, HttpServletRequest request) {

		LOGGER.error(msre.getMessage() + msre.getParameterName());

		if (msre != null) {
			LOGGER.error("Error encountered when processing InvalidMessagePayload", msre);
		} else {
			LOGGER.error("Error encountered when processing InvalidMessagePayload");
		}

    APIError err = errInvalidDataAPIError;

    LOGGER.error("An error occured. ErrorCode = [" + err.getCode() + "], Error Details = [" + err.getMessage() + "]", msre);

		return err;

	}

	/**
	 * To handle invalid/missing payloads
	 * 
	 * @param hmre
	 *            a http message not readable exception
	 * @param request
	 *            the request that caused the error
	 * @return a bad request status code with an invalid value validation error
	 * @see org.springframework.http.HttpStatus#BAD_REQUEST
	 * @see ValidationErrorConstants#INVALID_VALUE
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
  public APIError processInvalidMessagePayload(HttpMessageNotReadableException hmre, HttpServletRequest request) {

		if (hmre != null) {
			LOGGER.error("Error encountered when processing InvalidMessagePayload", hmre);
		} else {
			LOGGER.error("Error encountered when processing InvalidMessagePayload");
		}

    APIError err = errInvalidDataAPIError;

    LOGGER.error("An error occured. ErrorCode = [" + err.getCode() + "], Error Details = [" + err.getMessage() + "]", hmre);

		return err;

	}

	/**
	 * To handle invalid/missing payloads
	 * 
	 * @param hmre
	 *            a http message not readable exception
	 * @param request
	 *            the request that caused the error
	 * @return a bad request status code with an invalid value validation error
	 * @see org.springframework.http.HttpStatus#BAD_REQUEST
	 * @see ValidationErrorConstants#INVALID_VALUE
	 */
	@ExceptionHandler(TypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
  public APIError processInvalidType(TypeMismatchException tme, HttpServletRequest request) {

		if (tme != null) {
			LOGGER.error("Error encountered when processing InvalidType", tme);
		} else {
			LOGGER.error("Error encountered when processing InvalidType");
		}

    APIError err = errInvalidDataAPIError;

    LOGGER.error("An error occured. ErrorCode = [" + err.getCode() + "], Error Details = [" + err.getMessage() + "]", tme);

		return err;

	}

	/**
	 * To handle invalid/missing payloads
	 * 
	 * @param hmre
	 *            a http message not readable exception
	 * @param request
	 *            the request that caused the error
	 * @return a bad request status code with an invalid value validation error
	 * @see org.springframework.http.HttpStatus#BAD_REQUEST
	 * @see ValidationErrorConstants#INVALID_VALUE
	 */
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
  public APIError processInvalidType(HttpMediaTypeNotSupportedException me, HttpServletRequest request) {

		if (me != null) {
			LOGGER.debug("Error encountered when processing InvalidType", me);
		} else {
			LOGGER.debug("Error encountered when processing InvalidType");
		}

    APIError err = errInvalidDataAPIError;

		// LOGGER.error("An error occured. ErrorCode = [" + err.getCode() + "],
		// Error Details = [" + err.getDescription() + "]", tme);

		return err;

	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
  public APIError processGenericSystemError(Throwable t, HttpServletRequest request) {

		LOGGER.error("A generic error occured!", t);

    APIError err = errSystemAPIError;

		return err;

	}
}