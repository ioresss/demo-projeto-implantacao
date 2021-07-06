package tads.eaj.ufrn.demoprojetoimplantacao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tads.eaj.ufrn.demoprojetoimplantacao.domain.Produto;
import tads.eaj.ufrn.demoprojetoimplantacao.persistence.ProdutoDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Controller
@RequestMapping(value = "/config")
public class Config {

    @GetMapping
    public void config(HttpServletRequest request, HttpServletResponse response) throws IOException{

        try{
            ProdutoDAO pDAO = new ProdutoDAO();
            if(pDAO.verificar()){
                pDAO.criar();
                Produto p;
                pDAO.cadastrar(new Produto("Toalha de banho FOFA", "Toalha fofa e boa de secar", 10, "banho", 10.10));
                pDAO.cadastrar(new Produto("Toalha de rosto FOFA", "Toalha fofa e boa de secar", 10, "banho", 5.05));
                pDAO.cadastrar(new Produto("Edredon durma com os anjos", "Edredon que faz vc sentir q foi pro céu", 10, "cama", 55.50));
                pDAO.cadastrar(new Produto("Jogo de cama casal queen size SHINGEKI NO KYOJIN", "pra vc que é um otaku", 10, "cama", 169.99));
                pDAO.cadastrar(new Produto("Toalha de mesa ELEGANZA", "Toalha de mesa cheia de carisma, nervo e talento!", 10, "mesa", 29.99));

                response.getWriter().println("OK");
            }
            else{
                response.getWriter().println("Configuração ja feita");
            }


        }catch (IOException | SQLException e) {
            response.getWriter().println("Erro ao criar o banco de dados!");


        }
    }
}