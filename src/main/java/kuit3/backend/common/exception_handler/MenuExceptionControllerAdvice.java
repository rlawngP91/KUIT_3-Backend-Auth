package kuit3.backend.common.exception_handler;

import jakarta.annotation.Priority;
import kuit3.backend.common.exception.MenuException;
import kuit3.backend.common.response.BaseErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Priority(0)
@RestControllerAdvice
public class MenuExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MenuException.class)
    public BaseErrorResponse handle_MenuException(MenuException e) {
        log.error("[handle_MenuException]", e);
        return new BaseErrorResponse(e.getExceptionStatus(), e.getMessage());
    }
}