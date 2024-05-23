package kuit3.backend.common.exception.jwt;

import kuit3.backend.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class JwtMalformedTokenException extends RuntimeException {

    private final ResponseStatus exceptionStatus;

    public JwtMalformedTokenException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}