package client.util;

import client.bean.Error;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.RestClientException;

//TODO improvement - implement error codes
public class ErrorHelper {
    public static Error getGenericError() {
        Error error = new Error(0, "A system error occurred");
        return error;
    }

    public static Error getError(final RestClientException restException) {
        Error error = new Error(1, restException.getMessage());
        return error;
    }

    public static Error getSaveError() {
        Error error = new Error(2, "Address could not be saved to database");
        return error;
    }

    public static Error getErrorWithMessage(final String message) {
        Error error = new Error(3, message);
        return error;
    }

    public static Error getError(final Exception exception) {
        Error error = new Error(5, exception.getMessage());
        return error;
    }

    public static Error getError(final HttpMessageNotReadableException exception) {
        Error error = new Error(4, exception.getMessage());
        return error;
    }
}
