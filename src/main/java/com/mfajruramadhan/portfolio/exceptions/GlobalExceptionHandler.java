package com.mfajruramadhan.portfolio.exceptions;

import com.mfajruramadhan.portfolio.dto.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    // change default bad request response
    @ExceptionHandler
}
