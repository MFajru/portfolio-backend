package com.mfajruramadhan.portfolio.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mfajruramadhan.portfolio.dto.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.server.WebServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LogManager.getLogger();

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response<NotFoundException> handleNotFoundException(NotFoundException exception) {
        logger.warn(exception.getMessage());

        Response<NotFoundException> res = new Response<>();
        res.setData(null);
        res.setMessage(exception.getMessage());
        return res;
    }

    @ExceptionHandler({BadRequestException.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<RuntimeException> handleBadRequestException(RuntimeException exception) {

        logger.warn(exception.getMessage());

        Response<RuntimeException> res = new Response<>();
        res.setData(null);
        res.setMessage(exception.getMessage());
        return res;

    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<HttpMessageNotReadableException> handleInvalidRequestValueTypeException(HttpMessageNotReadableException exception) {
        logger.warn(exception.getMessage());

        String fName = "";
        String expectedVal = "";
        if (exception.getCause() instanceof InvalidFormatException e) {
            fName = e.getPath().get(0).getFieldName();
            expectedVal = e.getTargetType().getSimpleName();
        }
        Response<HttpMessageNotReadableException> res = new Response<>();
        res.setData(null);
        res.setMessage(String.format("%s expected value of %s in field %s", exception.getMessage().substring(0,17), expectedVal, fName));
        return res;

    }
}
