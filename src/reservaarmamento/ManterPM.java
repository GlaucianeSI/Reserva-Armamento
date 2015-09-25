/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package reservaarmamento;

import java.awt.Component;
import java.awt.print.PrinterException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author glaucia
 */
public class ManterPM extends javax.swing.JFrame {
 /**
     * Creates new form ManterPM
     */
    static int codexec = 0;
    Conexao con ;
  
    public ManterPM() {
       con = new Conexao();
       initComponents();
        
    }
    public static int pegaCodigo(){
        int codigo = 0;
        int linha;
        linha = tbPart.getSelectedRow();
        
        codigo = Integer.parseInt(tbPart.getValueAt(linha, 0).toString());
        
        return codigo;
    }
    public void limparCampos(){
    nome.setText("");
    rg.setText("");
    mat.setText("");
    grad.setText("");
    }  

    public void atualizaTabela(){
         con.conecta();
         String sql = "select * from usuario where nome_policial LIKE '"+tvNome.getText().toString()+"%'";

         PreparedStatement cnd;
         try {
                cnd = con.conecta().prepareStatement(sql);
                ResultSet rs;
                rs = cnd.executeQuery();


                DefaultTableModel model = (DefaultTableModel) tbPart.getModel();
                model.setNumRows(0);
        
                tbPart.getColumnModel().getColumn(0).setMinWidth(0);  
                tbPart.getColumnModel().getColumn(0).setMaxWidth(0); 
               /* tbPart.getColumnModel().getColumn(2).setMinWidth(0); 
                tbPart.getColumnModel().getColumn(3).setMaxWidth(50);
                tbPart.getColumnModel().getColumn(4).setMaxWidth(50);
                tbPart.getColumnModel().getColumn(5).setMaxWidth(50);
            //    tbPart.getColumnModel().getColumn(5).setMaxWidth(150);*/


         while(rs.next()){
          //System.out.println("Nome: " + rs.getString("nome"));
        model.addRow(
                      new Object[]{
                      rs.getString("cod_policial"),
                      rs.getString("posto_graduacao"),
                      rs.getString("nome_policial"),
                      rs.getString("rg_policial"),
                      rs.getString("matricula_policial"),
                      rs.getString("tipo_usu")
                }
             );
         }
        } catch (SQLException ex) {
            Logger.getLogger(ManterPM.class.getName()).log(Level.SEVERE, null, ex);
            
            JOptionPane.showMessageDialog(rootPane,"erro "+ex.getMessage());
        }
        
       }
    
    public void upDatePM(){
        if (tbPart.getSelectedRow() != -1) {
            int resposta = JOptionPane.showConfirmDialog(this, "Confirma atualização?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                 int codigo = pegaCodigo();
        String sql = "UPDATE usuario SET posto_graduacao = ?,"
                + " nome_policial = ?,"
                + " rg_policial = ? , "
                + "matricula_policial = ? , "
                + "tipo_usu = ?"
                + "WHERE cod_policial = "+codigo+" ";
        
        try{
            
           con.conecta();
           
           PreparedStatement cnd;
           
           cnd = con.conecta().prepareStatement(sql);
           cnd.setString(1, grad.getText());
           cnd.setString(2, nome.getText());
           cnd.setString(3,rg.getText());
           cnd.setString(4,mat.getText());
           cnd.setString(5,funcao.getText());
           cnd.executeUpdate();
           JOptionPane.showMessageDialog(null, "Informação atualizada com sucesso!");
           atualizarTabela();
            
        }catch(SQLException erro){
            JOptionPane.showMessageDialog(null, erro);
        }
    }      
        }
    }

     public void mostrarPM(){
       
        int seleciona = tbPart.getSelectedRow();
        
        grad.setText(tbPart.getValueAt(seleciona, 1).toString());
        nome.setText(tbPart.getValueAt(seleciona, 2).toString());
        rg.setText(tbPart.getValueAt(seleciona, 3).toString());
        mat.setText(tbPart.getValueAt(seleciona, 4).toString());
        funcao.setText(tbPart.getValueAt(seleciona, 5).toString());
        
        con.conecta();
         String sql = "select * from usuario where cod_policial = "+tbPart.getValueAt(seleciona, 0).toString();

         PreparedStatement cnd;
         try {
                cnd = con.conecta().prepareStatement(sql);
                ResultSet rs;
                rs = cnd.executeQuery();
                rs.next();
                
                
                      if(rs.getString("digital_policial") == null){
                          //Icon ic = new ImageIcon("fechar.png");
                          bio.setText("Biometria Não Cadastrada");
                          bio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/icones/fechar.png")));
                          
                      }
                      else{
                          bio.setText("Biometria Cadastrada");
                          bio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/icones/confirmar3.png")));
                      }
             }catch(Exception erro){
                 JOptionPane.showMessageDialog(null, erro+"  Erro ao buscar dados do PM");
             }
     
    }
     
     public void atualizarTabela() {
       
         con.conecta();
         String sql = "select * from usuario;";

         PreparedStatement cnd;
         try {
               cnd = con.conecta().prepareStatement(sql);
               ResultSet rs;
               rs = cnd.executeQuery();
        
               DefaultTableModel model = (DefaultTableModel) tbPart.getModel();
               model.setNumRows(0);
           
        while(rs.next()){
          //System.out.println("Nome: " + rs.getString("nome"));
               model.addRow(
                  new Object[]{
                      rs.getString("cod_policial"), 
                      rs.getString("posto_graduacao"),
                      rs.getString("nome_policial"),
                      rs.getString("rg_policial"),  
                      rs.getString("matricula_policial"), 
                      rs.getString("usuario"),
                      rs.getString("senhar"),
                      rs.getString("tipo_usu"),
                  }
          );
        }
                   
        } catch (SQLException ex) {
            Logger.getLogger(ManterPM.class.getName()).log(Level.SEVERE, null, ex);
            
            JOptionPane.showMessageDialog(rootPane,"erro "+ex.getMessage());
        }
      }
    
    public void excluirPM(){
        if (tbPart.getSelectedRow() != -1) {
            int resposta = JOptionPane.showConfirmDialog(this, "Confirma exclusão do Policial Militar?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {

                // System.out.println("Exclui!!!");

                int codigo = pegaCodigo();

                try {
                     con.conecta();
                   
                    //String query = ();

                    PreparedStatement cnd;
                    cnd = con.conecta().prepareStatement("delete from usuario where cod_policial=?");
                    cnd.setInt(1,codigo);
                    cnd.executeUpdate();
                    con.desconecta();
                    JOptionPane.showMessageDialog(null, "registro excluído com sucesso!");
                    atualizaTabela();
                }catch (SQLException ex) {
                    Logger.getLogger(ManterPM.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um nome da lista!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pesquisa = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tvNome = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbPart = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        grad = new javax.swing.JTextField();
        mat = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        nome = new javax.swing.JTextField();
        rg = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        funcao = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        bio = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btNovo = new javax.swing.JButton();
        btEditar = new javax.swing.JButton();
        btImprimir = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        btSalvar = new javax.swing.JButton();
        btSair = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("MANTER POLICIAL MILITAR");
        setBackground(new java.awt.Color(0, 255, 255));
        setResizable(false);

        pesquisa.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        pesquisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/icones/pesq.png"))); // NOI18N
        pesquisa.setText("Pesquisar");
        pesquisa.setPreferredSize(new java.awt.Dimension(181, 39));
        pesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesquisaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel1.setText("Nome:");

        tvNome.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados Policial Militar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 14), new java.awt.Color(51, 51, 51))); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(1200, 365));

        tbPart.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tbPart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Post./Grad.", "Nome", "RG", "Matricula", "Cod.Função"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbPart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPartMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbPart);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Atualiza Informações", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 18), new java.awt.Color(51, 51, 51))); // NOI18N
        jPanel1.setToolTipText("");
        jPanel1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        grad.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        mat.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel8.setText("Matricula:");

        nome.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        rg.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setText("Nome:");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel4.setText("Post./Grad.:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setText("RG:");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel5.setText("Cod. Função:");

        funcao.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jButton1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jButton1.setText("Cadastrar Biometria");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        bio.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        bio.setText("Status");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nome, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mat, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(grad)))
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(14, 14, 14)
                        .addComponent(rg, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(funcao)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(bio)
                        .addGap(77, 77, 77)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(rg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(mat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(grad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(funcao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(bio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1272, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(83, Short.MAX_VALUE))
        );

        jPanel1.getAccessibleContext().setAccessibleName("");

        btNovo.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/icones/policial2.png"))); // NOI18N
        btNovo.setText("Novo");
        btNovo.setPreferredSize(new java.awt.Dimension(133, 40));
        btNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoActionPerformed(evt);
            }
        });

        btEditar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/icones/editar.png"))); // NOI18N
        btEditar.setText("Editar");
        btEditar.setPreferredSize(new java.awt.Dimension(133, 40));
        btEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditarActionPerformed(evt);
            }
        });

        btImprimir.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/icones/imagemimpr.png"))); // NOI18N
        btImprimir.setText("Imprimir");
        btImprimir.setPreferredSize(new java.awt.Dimension(133, 40));
        btImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btImprimirActionPerformed(evt);
            }
        });

        btExcluir.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/icones/deletar.png"))); // NOI18N
        btExcluir.setText("Excluir");
        btExcluir.setPreferredSize(new java.awt.Dimension(133, 40));
        btExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirActionPerformed(evt);
            }
        });

        btSalvar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/icones/salvar.png"))); // NOI18N
        btSalvar.setText("Salvar");
        btSalvar.setPreferredSize(new java.awt.Dimension(133, 40));
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
            }
        });

        btSair.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/icones/fechar.png"))); // NOI18N
        btSair.setText("Sair");
        btSair.setPreferredSize(new java.awt.Dimension(133, 40));
        btSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(btNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(btExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(btSair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSair, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(134, 134, 134))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tvNome, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1294, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(tvNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(81, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoActionPerformed
    // TODO add your handling code here:
        btNovo.setEnabled(true); 
        btEditar.setEnabled(true);
        btImprimir.setEnabled(true);
        btExcluir.setEnabled(true);
        btSalvar.setEnabled(true);
        btSair.setEnabled(true);
        tbPart.setEnabled(true);
               
        for (Component com : jPanel1.getComponents()){
            com.setEnabled(true);
        }
        
        
 //   new CadastrarPM().setVisible(true);
        int op = Integer.parseInt(JOptionPane.showInputDialog("Informe a sua opção:\n1 - Cadastrar com Briometria\n2 - Cadastrar sem Biometria"));
        if(op == 1){
            new CadastrarPM("-1").setVisible(true);
        }
        else if(op == 2){
            new SemBiometria().setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(null,"Opção Inválida");
        }
         //  codexec = 1;
    }//GEN-LAST:event_btNovoActionPerformed

    private void pesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pesquisaActionPerformed
     
        atualizaTabela();
        btNovo.setEnabled(true); 
        btEditar.setEnabled(true);
        btImprimir.setEnabled(true);
        btExcluir.setEnabled(true);
        btSalvar.setEnabled(true);
        btSair.setEnabled(true);
        tbPart.setEnabled(true);
        jPanel1.setEnabled(true);
             
         
// TODO add your handling code here:
    }//GEN-LAST:event_pesquisaActionPerformed

    private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed
        // TODO add your handling code here:
            excluirPM();
        btNovo.setEnabled(false); 
        btEditar.setEnabled(false);
        btImprimir.setEnabled(false);
        btExcluir.setEnabled(true);
        btSalvar.setEnabled(true);
        btSair.setEnabled(true);
        tbPart.setEnabled(true);
    
    }//GEN-LAST:event_btExcluirActionPerformed

    private void btSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSairActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btSairActionPerformed

    private void btEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditarActionPerformed
        btNovo.setEnabled(false); 
        btEditar.setEnabled(true);
        btImprimir.setEnabled(false);
        btExcluir.setEnabled(false);
        btSalvar.setEnabled(true);
        btSair.setEnabled(true);
        tbPart.setEnabled(false);
        
        codexec = 2;
    }//GEN-LAST:event_btEditarActionPerformed

    private void btImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btImprimirActionPerformed
        // TODO add your handling code here:
       
        btNovo.setEnabled(false); 
        btEditar.setEnabled(true);
        btImprimir.setEnabled(true);
        btExcluir.setEnabled(false);
        btSalvar.setEnabled(true);
        btSair.setEnabled(true);
        tbPart.setEnabled(false);
        
        try {  
        tbPart.print();  
    } catch (PrinterException ex) {  
        JOptionPane.showMessageDialog(this, "Não foi possível imprimir\n" + ex.getMessage());  
    }  
    }//GEN-LAST:event_btImprimirActionPerformed

    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
        // TODO add your handling code here:
         upDatePM();
    }//GEN-LAST:event_btSalvarActionPerformed

    private void tbPartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPartMouseClicked
        // TODO add your handling code here:
        mostrarPM();
    }//GEN-LAST:event_tbPartMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new CadastrarPM(tbPart.getValueAt(tbPart.getSelectedRow(), 0).toString()).setVisible(true);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ManterPM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManterPM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManterPM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManterPM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManterPM().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bio;
    private javax.swing.JButton btEditar;
    private javax.swing.JButton btExcluir;
    private javax.swing.JButton btImprimir;
    private javax.swing.JButton btNovo;
    private javax.swing.JButton btSair;
    private javax.swing.JButton btSalvar;
    private javax.swing.JTextField funcao;
    private javax.swing.JTextField grad;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField mat;
    private javax.swing.JTextField nome;
    private javax.swing.JButton pesquisa;
    private javax.swing.JTextField rg;
    private static javax.swing.JTable tbPart;
    private javax.swing.JTextField tvNome;
    // End of variables declaration//GEN-END:variables
}
