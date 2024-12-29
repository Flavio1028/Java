package br.com.codeup.social.exceptions;

import java.io.Serial;
import java.io.Serializable;

public class ForbiddenFollowsExcepetion extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public ForbiddenFollowsExcepetion(String message) {
        super(message);
    }

}
