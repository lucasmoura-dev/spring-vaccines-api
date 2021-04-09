package br.com.vacinas.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends ResponseStatusException {
    private String message;

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
        this.message = message;
    }

    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST, "Dados inválidos");
        this.message = "Dados inválidos";
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
