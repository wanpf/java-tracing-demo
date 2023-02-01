package client.controller;

import client.util.ErrorHelper;
import client.util.JsonHelper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration

public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController{
    public static final String ERROR_PATH = "/error";
    public static final String SYSTEM_ERROR_TEXT = "We are sorry, a system error occurred, please contact system admins or check logs.";

    @RequestMapping(value = ERROR_PATH)
    public String error() {
        return JsonHelper.asJsonStringOrBlank(ErrorHelper.getErrorWithMessage(SYSTEM_ERROR_TEXT));
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
