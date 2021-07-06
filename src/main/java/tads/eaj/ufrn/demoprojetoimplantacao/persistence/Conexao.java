package tads.eaj.ufrn.demoprojetoimplantacao.persistence;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Yuri e George >:(
 */
public class Conexao {
    
	private String caminho;
    private String usuario;
    private String senha;
    private Connection aPropriaConexao;
    
    public Conexao(String c, String u, String s){
        caminho = c;
        usuario = u;
        senha = s;
    }
    
    public void conectar(){
        try{
            Class.forName("org.postgresql.Driver"); //carregar o driver
            aPropriaConexao = DriverManager.getConnection(caminho, usuario, senha); //abrir a conex�o
        }catch(Exception e){
            System.out.println("Erro na conex�o: "+e.getMessage());
        }
    }
    public void desconectar(){
        try{
        	aPropriaConexao.close();
        }catch(Exception e){
            System.out.println("Erro na desconex�o: "+e.getMessage());
        }
    }
    public Connection getConexao(){
        return aPropriaConexao;
    }
}
