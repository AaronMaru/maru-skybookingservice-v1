package com.skybooking.skypointservice.httpstatus;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


//    @Autowired
//    private LocalizationBean localization;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        BindingResult result = ex.getBindingResult();

        List<FieldError> fieldErrors = result.getFieldErrors();
        String validation = "";

        for (FieldError fieldError: fieldErrors) {
            validation = fieldError.getDefaultMessage();
        }

        body.put("error", status.getReasonPhrase());
        body.put("message", validation);

        return new ResponseEntity<>(body, headers, status);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Permanent redirect
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Status 308
     * @Return Errors
     */
    @ExceptionHandler(PermanentException.class)
    public ResponseEntity<CustomErrorResponse> handlePermanentRedirect(PermanentException ex) {
        CustomErrorResponse errors = this.setError(ex.getMessage(), ex.getData(), HttpStatus.PERMANENT_REDIRECT.value());
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
    public ResponseEntity<CustomErrorResponse> handleUnauthorized(UnauthorizedException ex) {
        CustomErrorResponse errors = this.setError(ex.getMessage(), ex.getData(), HttpStatus.UNAUTHORIZED.value());
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
    public ResponseEntity<CustomErrorResponse> handleInternalError(InternalServerError ex) {
        CustomErrorResponse errors = this.setError(ex.getMessage(), ex.getData(), HttpStatus.INTERNAL_SERVER_ERROR.value());
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
    public ResponseEntity<CustomErrorResponse> handleMethod(MethodNotAllowException ex) {
        CustomErrorResponse errors = this.setError(ex.getMessage(), ex.getData(), HttpStatus.METHOD_NOT_ALLOWED.value());
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
    public ResponseEntity<CustomErrorResponse> handleBadRequest(BadRequestException ex) {
        CustomErrorResponse errors = this.setError(ex.getMessage(), ex.getData(), HttpStatus.BAD_REQUEST.value());
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
    public ResponseEntity<CustomErrorResponse> handleNotFound(NotFoundException ex) {
        CustomErrorResponse errors = this.setError(ex.getMessage(), ex.getData(), HttpStatus.NOT_FOUND.value());
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
    public ResponseEntity<CustomErrorResponse> handleTemporary(TemporaryException ex) {
        CustomErrorResponse errors = this.setError(ex.getMessage(), ex.getData(), HttpStatus.TEMPORARY_REDIRECT.value());
        return new ResponseEntity<>(errors, HttpStatus.TEMPORARY_REDIRECT);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Gone
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Status 410
     */
    @ExceptionHandler(GoneException.class)
    public ResponseEntity<CustomErrorResponse> handleGone(GoneException ex) {
        CustomErrorResponse errors = this.setError(ex.getMessage(), ex.getData(), HttpStatus.GONE.value());
        return new ResponseEntity<>(errors, HttpStatus.GONE);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Gone
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Status 409
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<CustomErrorResponse> handleConflict(ConflictException ex) {
        CustomErrorResponse errors = this.setError(ex.getMessage(), ex.getData(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<CustomErrorResponse> handleForbidden(ForbiddenException ex) {
        CustomErrorResponse errors = this.setError(ex.getMessage(), ex.getData(), HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
    }

    private CustomErrorResponse setError(String message, Object data, int httpstatusValue) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
//        errors.setMessage(localization.multiLanguageRes(ex.getMessage()));
        errors.setMessage(message);
        errors.setStatus(httpstatusValue);
        errors.setData(data);

        return errors;
    }
}
