package br.com.codeup.social.exceptions;

import br.com.codeup.social.rest.dto.FieldError;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {

        if (exception instanceof ParametersException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new FieldError(exception.getMessage()))
                    .build();
        }

        if (exception instanceof ForbiddenFollowsExcepetion) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new FieldError(exception.getMessage()))
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Internal server error")
                .build();
    }

}
