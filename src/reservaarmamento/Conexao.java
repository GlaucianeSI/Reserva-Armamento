/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package reservaarmamento;
import java.sql.*;
import javax.swing.*;

public class Conexao {
    
    final private String driver = "org.gjt.mm.mysql.Driver";
    final private String url = "jdbc:mysql://localhost/reservaarmamento3";
    final private String usuario = "root";
    final private String senha = "";
    Connection con;
    public Statement statement;
    public ResultSet resultset;
    Statement stmt;
    ResultSet rs;
    
    //public Boolean conecta(){
    public Connection conecta(){
       boolean result = true;
       try
       {
           Class.forName(driver);
           con = DriverManager.getConnection(url,usuario,senha);
          // JOptionPane.showMessageDialog(null, "Concetou com o Banco");
       }
       catch(ClassNotFoundException Driver){
           JOptionPane.showMessageDialog(null, "Driver não localizado: "+Driver);
           result = false;
       }
       catch(SQLException Fonte){
           JOptionPane.showMessageDialog(null, "Erro na conexão com a fonte de dados: "+Fonte);
           result = false;
       }
       //return result 
       return con;
       }
    
    public void desconecta(){
        boolean result = true;
        try{
            
            con.close();
            //JOptionPane.showMessageDialog(null, "banco fechado");
        }
        catch(SQLException fecha){
            JOptionPane.showMessageDialog(null, "Nao foi possível fechar o banco de dados: "+ fecha);
            result = false;
        }
    }
    
    public void executeSQL(String sql){
        
        try{
            
            statement = con.createStatement();
            resultset = statement.executeQuery(sql);
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Nao foi possível executar o comando "+ex+" o comando foi: "+sql);
        }
    }

    void OpenConnectionMysql() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    }

