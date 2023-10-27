package com.example.userservice.media.utils;

import com.example.userservice.exception.FeignResponseException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThrowableUtils {

    /** The logger. */
    private static Logger logger = LoggerFactory.getLogger(ThrowableUtils.class);

    public static String getExceptionExplain(Exception e){
        return String.format("%s | %s | %s", e.toString(), e.getMessage(), ExceptionUtils.getStackTrace(e));
    }

    public static void printError(Exception ex) {
        String message = ex instanceof FeignResponseException
                ? ((FeignResponseException) ex).getErrorModel().toString() : ex.getLocalizedMessage();
        logger.error(message, ex);
    }

}
