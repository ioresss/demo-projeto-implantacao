package tads.eaj.ufrn.demoprojetoimplantacao.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "/hello")
public class HelloWorld {

    @GetMapping
    public void doGet(HttpServletResponse response) throws IOException {

        response.getWriter().println("este é um easter egg :O");
    }
}
