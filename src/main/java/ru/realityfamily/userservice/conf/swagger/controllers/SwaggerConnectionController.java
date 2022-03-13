package ru.realityfamily.userservice.conf.swagger.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
public class SwaggerConnectionController {
    @GetMapping(value = "/")
    public void root(HttpServletResponse rsp) throws IOException {
        rsp.sendRedirect("/userservice/swagger-ui/index.html");
    }
}