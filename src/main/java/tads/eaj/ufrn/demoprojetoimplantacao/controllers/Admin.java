package tads.eaj.ufrn.demoprojetoimplantacao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tads.eaj.ufrn.demoprojetoimplantacao.domain.Produto;
import tads.eaj.ufrn.demoprojetoimplantacao.persistence.ProdutoDAO;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class Admin {

    ProdutoDAO pdao = new ProdutoDAO();

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProdutoDAO pdao = new ProdutoDAO();
        response.getWriter().println("<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <title>Cadastro de Produtos</title>" +
                "</head>" +
                "<body>" +
                "<form action=\"/cadastrar\" method=\"post\">" +
                "    Nome:<input type=\"text\" name=\"nome\"><br />" +
                "    Descricao:<input type=\"text\" name=\"descricao\"> <br />" +
                "    Tipo: <input type=\"text\" name=\"tipo\"> <br />" +
                "    Preco: <input type=\"number\" step=\"0.01\" min=\"0\" name=\"preco\"> <br />" +
                "    Quantidade: <input type=\"number\" name=\"quantidade\"> <br />" +
                "    <button type=\"submit\">Cadastar</button>" +
                "</form>" +
                "</body>" +
                "</html>");

        HttpSession secao = request.getSession();

        var data = new Date(secao.getCreationTime());

        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy--hh:mm:ss");
        Cookie c = new Cookie("acesso", formatoData.format(data));
        c.setMaxAge(60*60*24);
        response.addCookie(c);
        response.getWriter().println("Data do ultimo acesso:"+formatoData.format(data));
    }

    @RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
    public void RegisterProduct(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Produto p = new Produto(
                request.getParameter("nome"),
                request.getParameter("descricao"),
                Integer.parseInt(request.getParameter("quantidade")),
                request.getParameter("tipo"),
                Double.parseDouble(request.getParameter("preco"))
        );
        pdao.cadastrar(p);
        response.sendRedirect("/admin");
    }
}