package com.skybooking.skyflightservice.exception.httpstatus;

import com.skybooking.skyflightservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private Localization localization;

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Temporary redirect
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Status 307
     */
    @ExceptionHandler(TemporaryException.class)
    public ResponseEntity<CustomErrorResponse> handleTemporary(TemporaryException ex, WebRequest request) {

        CustomErrorResponse error = new CustomErrorResponse();
        error.setTimestamp(dateFormat.format(new Date()));
        error.setMessage(localization.multiLanguageRes(ex.getMessage()));
        error.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
        error.setError(ex.getError());

        return new ResponseEntity<>(error, HttpStatus.TEMPORARY_REDIRECT);

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
    public ResponseEntity<CustomErrorResponse> handlePermanentRedirect(PermanentException ex, WebRequest request) {

        CustomErrorResponse error = new CustomErrorResponse();
        error.setTimestamp(dateFormat.format(new Date()));
        error.setMessage(localization.multiLanguageRes(ex.getMessage()));
        error.setStatus(HttpStatus.PERMANENT_REDIRECT.value());
        error.setError(ex.getError());

        return new ResponseEntity<>(error, HttpStatus.PERMANENT_REDIRECT);

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

        CustomErrorResponse error = new CustomErrorResponse();
        error.setTimestamp(dateFormat.format(new Date()));
        error.setMessage(localization.multiLanguageRes(ex.getMessage()));
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError(ex.getError());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

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

        CustomErrorResponse error = new CustomErrorResponse();
        error.setTimestamp(dateFormat.format(new Date()));
        error.setMessage(localization.multiLanguageRes(ex.getMessage()));
        error.setStatus(HttpStatus.UNAUTHORIZED.value());
        error.setError(ex.getError());

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);

    }



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * FORBIDDEN
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return Error
     * @Status 403
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<CustomErrorResponse> handleForbidden(ForbiddenException ex, WebRequest request) {

        CustomErrorResponse error = new CustomErrorResponse();
        error.setTimestamp(dateFormat.format(new Date()));
        error.setMessage(localization.multiLanguageRes(ex.getMessage()));
        error.setStatus(HttpStatus.FORBIDDEN.value());
        error.setError(ex.getError());

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);

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

        CustomErrorResponse error = new CustomErrorResponse();
        error.setTimestamp(dateFormat.format(new Date()));
        error.setMessage(localization.multiLanguageRes(ex.getMessage()));
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError(ex.getError());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }




    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Gone
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Status 410
     */
    @ExceptionHandler(GoneException.class)
    public ResponseEntity<CustomErrorResponse> handleGone(GoneException ex, WebRequest request) {

        CustomErrorResponse error = new CustomErrorResponse();
        error.setTimestamp(dateFormat.format(new Date()));
        error.setMessage(localization.multiLanguageRes(ex.getMessage()));
        error.setStatus(HttpStatus.GONE.value());
        error.setError(ex.getError());

        return new ResponseEntity<>(error, HttpStatus.GONE);

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

        CustomErrorResponse error = new CustomErrorResponse();
        error.setTimestamp(dateFormat.format(new Date()));
        error.setMessage(localization.multiLanguageRes(ex.getMessage()));
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setError(ex.getError());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
