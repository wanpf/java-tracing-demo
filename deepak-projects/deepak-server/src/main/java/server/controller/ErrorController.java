package server.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration

public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController{
    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error() {
        return "{error:\"server error\"}";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
