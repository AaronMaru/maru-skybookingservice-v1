package com.skybooking.staffservice.exception.httpstatus;

import com.skybooking.staffservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @Autowired
    private LocalizationBean localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Permanent redirect
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Status 308
     * @Return Errors
     */
    @ExceptionHandler(PermanentException.class)
    public ResponseEntity<CustomErrorResponse> handlePermanentRedirect(PermanentException ex, WebRequest request) {

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setMessage(localization.multiLanguageRes(ex.getMessage()));
        errors.setStatus(HttpStatus.PERMANENT_REDIRECT.value());
        errors.setData(ex.getData());

        return new ResponseEntity<>(errors, HttpStatus.PERMANENT_REDIRECT);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Unauthorized
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Status 401
     * @Return Errors
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CustomErrorResponse> handleUnauthorized(UnauthorizedException ex, WebRequest request) {

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setMessage(localization.multiLanguageRes(ex.getMessage()));
        errors.setStatus(HttpStatus.UNAUTHORIZED.value());
        errors.setData(ex.getData());

        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Internal server error
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Status 500
     * @Return Errors
     */
    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<CustomErrorResponse> handleInternalError(InternalServerError ex, WebRequest request) {

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setMessage(localization.multiLanguageRes(ex.getMessage()));
        errors.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errors.setData(ex.getData());

        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);

    }



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Method not allow
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Status 405
     * @Return Errors
     */
    @ExceptionHandler(MethodNotAllowException.class)
    public ResponseEntity<CustomErrorResponse> handleMethod(MethodNotAllowException ex, WebRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setMessage(localization.multiLanguageRes(ex.getMessage()));
        errors.setStatus(HttpStatus.BAD_REQUEST.value());
        errors.setData(ex.getData());

        return new ResponseEntity<>(errors, HttpStatus.METHOD_NOT_ALLOWED);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Bad Request
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Status 400
     * @Return Errors
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomErrorResponse> handleBadRequest(BadRequestException ex, WebRequest request) {

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setMessage(localization.multiLanguageRes(ex.getMessage()));
        errors.setStatus(HttpStatus.BAD_REQUEST.value());
        errors.setData(ex.getData());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Not Found
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return Error
     * @Status 404
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleNotFound(NotFoundException ex, WebRequest request) {

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setMessage(localization.multiLanguageRes(ex.getMessage()));
        errors.setStatus(HttpStatus.NOT_FOUND.value());
        errors.setData(ex.getData());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Temporary redirect
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Status 307
     */
    @ExceptionHandler(TemporaryException.class)
    public ResponseEntity<CustomErrorResponse> handleTemporary(TemporaryException ex, WebRequest request) {

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setMessage(localization.multiLanguageRes(ex.getMessage()));
        errors.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
        errors.setData(ex.getData());

        return new ResponseEntity<>(errors, HttpStatus.TEMPORARY_REDIRECT);

    }
}
