package com.epam.aurora.cucumberserver.controllers.advice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public String handleException(Throwable exception) {
        LOG.error(exception.getMessage(), exception);

        return ExceptionUtils.getStackTrace(exception);
    }

}
