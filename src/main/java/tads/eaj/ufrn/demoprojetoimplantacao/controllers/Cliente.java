package tads.eaj.ufrn.demoprojetoimplantacao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tads.eaj.ufrn.demoprojetoimplantacao.domain.Produto;
import tads.eaj.ufrn.demoprojetoimplantacao.persistence.ProdutoDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@Controller

public class Cliente {

    ProdutoDAO pdao = new ProdutoDAO();
    @RequestMapping(value = "/cliente", method = RequestMethod.GET)
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ArrayList<Produto> lista = pdao.relatorio();


        response.getWriter().println("<table width=100%  border=\"1\">" +
                " <thead> " +
                "<tr> " +
                "<td> Nome </td>" +
                "<td> Descricao </td>" +
                "<td> Quantidade </td>" +
                "<td> Tipo </td>" +
                "<td> Preço </td>" +
                "<td>  </td>" +
                "</tr>" +
                " </thead>"
        );
        response.getWriter().println("<tbody>");
        for(Produto produto:lista){
            response.getWriter().println("<tr text-align=\"center\">" +
                    "<td>"+produto.getNome() + "</td>" +
                    "<td>"+produto.getDescricao() + "</td>" +
                    "<td>"+produto.getQuantidade()+ "</td>" +
                    "<td>"+produto.getTipo()+ "</td>" +
                    "<td>"+produto.getPreco()+ "</td>" +
                    "<td><a href=\"/adicionarCarrinho?id=" +produto.getId()+ "\">Adicionar</a></td>"+
                    "</tr>");
        }
        response.getWriter().println("</tbody");
        response.getWriter().println("</table>");
        response.getWriter().println("<a href=\"/verCarrinho\">Ver Carrinho </a>");

    }


    @RequestMapping(value="verCarrinho", method = RequestMethod.GET)
    public void verCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession secao = request.getSession();


        if(secao.getAttribute("carrinho")==null){
            response.sendRedirect("/cliente");
        }else{

            ArrayList<Produto> lista = (ArrayList<Produto>) secao.getAttribute("carrinho");
            response.getWriter().println("<header>\n" +
                    "    <ul>\n" +
                    "      <li> <a href=\"/cliente\"> Voltar para a loja</a></li>\n" +
                    "      <li> <a href=\"/finalizarCompra\">Finalizar Compra </a></li>\n" +
                    "    </ul>\n" +
                    "  </header>");

            response.getWriter().println("<table width=100%  border=\"1\">" +
                    " <thead> " +
                    "<tr> " +
                    "<td> Nome </td>" +
                    "<td> Descricao </td>" +
                    "<td> Quantidade </td>" +
                    "<td> Tipo </td>" +
                    "<td> Preço </td>" +
                    "<td>  </td>" +
                    "</tr>" +
                    " </thead>"
            );
            response.getWriter().println("<tbody>");
            for(var produto:lista){
                response.getWriter().println("<tr text-align=\"center\">" +
                        "<td>"+produto.getNome() + "</td>" +
                        "<td>"+produto.getDescricao() + "</td>" +
                        "<td>"+produto.getQuantidade()+ "</td>" +
                        "<td>"+produto.getTipo()+ "</td>" +
                        "<td>"+produto.getPreco()+ "</td>" +
                        "<td><a href=\"/adicionarCarrinho?id=" +produto.getId()+ "\">Adicionar</a></td>"+
                        "</tr>");
            }
            response.getWriter().println("</tbody");
            response.getWriter().println("</table>");

        }

    }
    @RequestMapping(value = "finalizarCompra", method = RequestMethod.GET)
    public void finalizarCompra(HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.getSession().invalidate();
        response.sendRedirect("/");

    }

    @RequestMapping(value="adicionarCarrinho", method = RequestMethod.GET)
    public  void adicionarCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Produto p;

        HttpSession session = request.getSession();
        if(session.getAttribute("carrinho")==null){
            session.setAttribute("carrinho", new ArrayList<Produto>());
        }

        ArrayList<Produto> produtosCarrinho = (ArrayList<Produto>) session.getAttribute("carrinho");
        Integer id = Integer.parseInt(request.getParameter("id"));
        response.getWriter().println(id);
        p= pdao.buscar(id);
        response.getWriter().println(p.getNome());
        produtosCarrinho.add(p);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cliente");
        dispatcher.forward(request,response);
    }
}
