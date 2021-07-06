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

    private String BUSCAR = "SELECT * FROM public.\"Produto\" WHERE id_produto = ?";

    private String CRIAR = "CREATE TABLE IF NOT EXISTS public.\"Produto\"\n" +
            "(\n" +
            "    id_produto integer NOT NULL DEFAULT nextval('\"Produto_id_produto_seq\"'::regclass),\n" +
            "    nome text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
            "    descricao text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
            "    preco double precision NOT NULL,\n" +
            "    quantidade integer NOT NULL,\n" +
            "    tipo text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
            "    CONSTRAINT pk_id_produto PRIMARY KEY (id_produto)\n" +
            ")\n" +
            "\n" +
            "TABLESPACE pg_default;";

    public ProdutoDAO(){
        minhaConexao = new Conexao(("jdbc:postgresql://"+System.getenv("DATABASE_HOST")+
                ":"+System.getenv("DATABASE_PORT")+"/"+System.getenv("DATABASE_NAME")+"?systemTimezone=UTC"),
        System.getenv("DATABASE_USERNAME"), System.getenv("DATABASE_PASSWORD"));


    }

    public void cadastrar(Produto p) {
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
            System.out.println("Erro na listagem:" + e.getMessage());
        }

        return lista;
    }

    public Produto buscar(int id){
        Produto p =null;
        try {
            minhaConexao.conectar();
            PreparedStatement instrucao = minhaConexao.getConexao().prepareStatement(BUSCAR);
            instrucao.setInt(1, id);
            var rs = instrucao.executeQuery();
            if(rs.next()) {
                p = new Produto(  rs.getInt("id_produto"),rs.getString("nome"), rs.getString("descricao"),
                        rs.getInt("quantidade"), rs.getString("tipo"), rs.getDouble("preco"));
            }
            minhaConexao.desconectar();
        }catch (SQLException e) {
            System.out.println("Erro na listagem:" + e.getMessage());
        }

        return p;
    }

    public void criar(){


        try{
            minhaConexao.conectar();
            PreparedStatement instrucao = minhaConexao.getConexao().prepareStatement(CRIAR);
            instrucao.execute();
            minhaConexao.desconectar();

        }catch (SQLException e) {
            System.out.println("Erro na criação do banco" + e.getMessage());

        }
    }
}
