package tads.eaj.ufrn.demoprojetoimplantacao.persistence;

import tads.eaj.ufrn.demoprojetoimplantacao.domain.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class ProdutoDAO {

    private Conexao minhaConexao;

    private String CADASTRAR = "INSERT INTO public.\"Produto\"(" +
            " nome, descricao, preco, quantidade, tipo)" +
            "VALUES (?, ?, ?, ?, ?, ?);";
    private String RELATORIO = "SELECT id_produto, nome, descricao, preco, quantidade, tipo FROM public.\"Produto\"";

    public ProdutoDAO(){
        minhaConexao = new Conexao(("jdbc:postgresql://"+System.getenv("DATABASE_HOST")+
                ":"+System.getenv("DATABASE_PORT")+"/"+System.getenv("DATABASE_NAME")+"?systemTimezone=UTC"),
        System.getenv("DATABASE_USERNAME"), System.getenv("DATABASE_PASSWORD"));


    }

    public void cadastrarCorretor(Produto p) {
        try {
            minhaConexao.conectar();
            PreparedStatement instrucao =
                    minhaConexao.getConexao().prepareStatement(CADASTRAR);
            instrucao.setString(1, p.getNome());
            instrucao.setString(2, p.getDescricao());
            instrucao.setDouble(3, p.getPreco());
            instrucao.setInt(4, p.getQuantidade());
            instrucao.setString(5, p.getTipo());
            instrucao.execute();
            minhaConexao.desconectar();

        }catch (SQLException e) {
            System.out.println("Erro no cadastro:" +e.getMessage());
        }
    }

    public ArrayList<Produto> relatorio(){
        ArrayList<Produto> lista = new ArrayList<>();
        Produto p;
        try {
            minhaConexao.conectar();
            Statement instrucao = minhaConexao.getConexao().createStatement();
            ResultSet rs = instrucao.executeQuery(RELATORIO);
            while(rs.next()) {
                p = new Produto(  rs.getInt("id_produto"),rs.getString("nome"), rs.getString("descricao"),
                        rs.getInt("quantidade"), rs.getString("tipo"), rs.getDouble("preco"));
                lista.add(p);
            }
            minhaConexao.desconectar();
        }catch (SQLException e) {
            System.out.println("Erro no relat�rio geral:" + e.getMessage());
        }

        return lista;
    }
}
