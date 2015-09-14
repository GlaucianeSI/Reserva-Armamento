package reservaarmamento;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author administrador
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class conectaMysql {  
    public static String status = "Não conectado...";
    
    public static Connection getConexao() throws SQLException {  
    Connection con=null;  
    String local = System.getProperty("user.dir");
          try {  
         Class.forName("com.mysql.jdbc.Driver");  
         con =  
            DriverManager.getConnection("jdbc:mysql://localhost/reservaarmamento3?user=root&password="); 
         /*
         +TelaAdministrador.host+"/seminfoicet?user="
            +TelaAdministrador.usuario+"&password="
            +TelaAdministrador.senha);
         */
         
            Statement stm = (Statement) con.createStatement();  
      } catch (Exception e) {  
         //System.out.println("Não foi possível conectar ao banco: " + e.getMessage());  
         JOptionPane.showMessageDialog(null,"Não foi possível conectar ao banco: "+ e.getMessage(),"Erro",0);
         status = "Não conectado";
      }  
          System.out.println("Conectado: "); 
          status = "Conectado!";
            return con; 
   }  
    public static void main(String args[]) throws SQLException {  
        Connection conexao = new conectaMysql().getConexao();  
    }
    
    public static String statusConnection(){
        return status;
    }
}  
