package br.com.codeup.social.exceptions;

import java.io.Serial;
import java.io.Serializable;

public class ParametersException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public ParametersException(String message) {
        super(message);
    }

}
