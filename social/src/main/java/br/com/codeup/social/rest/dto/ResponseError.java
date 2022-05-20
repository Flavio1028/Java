package br.com.codeup.social.rest.dto;
import javax.validation.ConstraintViolation;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ResponseError {

    private String message;
    private Collection<FieldError> erros;

    public ResponseError(String message, Collection<FieldError> erros) {
        this.message = message;
        this.erros = erros;
    }

    public static <T> ResponseError createFormValidation(Set<ConstraintViolation<T>> violations) {
        List<FieldError> error = violations
                .stream()
                .map(cv -> new FieldError(cv.getPropertyPath().toString(), cv.getMessage()))
                .collect(Collectors.toList());

        String message = "Validation Error";
        return new ResponseError(message, error);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Collection<FieldError> getErros() {
        return erros;
    }

    public void setErros(Collection<FieldError> erros) {
        this.erros = erros;
    }
}
