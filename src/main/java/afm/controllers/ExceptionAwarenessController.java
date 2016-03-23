package afm.controllers;

import afm.exceptions.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by rchen on 3/23/16.
 */
public class ExceptionAwarenessController {

    private static final Logger logger = LoggerFactory
            .getLogger(ExceptionAwarenessController.class);

    @ExceptionHandler
    void handleDataException(DataException e,
                                        HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.toString());
        error(e.toString());
    }

    void info(String message) {
        logger.info(message);
    }

    void error(String message) {
        logger.error(message);
    }
}
