/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservaarmamento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author glaucia
 */
public class TelaDevolucaoEstoque extends javax.swing.JFrame {

    Conexao con;
    String codMateriais[] = new String[10];
    String codPolicial = "";

    /**
     * Creates new form TelaDevolicaoEstoque
     */
    public TelaDevolucaoEstoque() {
        con = new Conexao();
        con.conecta();
        initComponents();
        btDevolver.setEnabled(false);
        btSalvar.setEnabled(false);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbRetirada = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbHas = new javax.swing.JTable();
        btSalvar = new javax.swing.JButton();
        btSair = new javax.swing.JButton();
        btDevolver = new javax.swing.JButton();
        btBuscar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Retiradas do Policial Militar"));

        tbRetirada.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tbRetirada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Cod. Retirada", "dest. Mat. Dest.", "Nome", "data da Retirada"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbRetirada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRetiradaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbRetirada);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Materiais Retirados"));

        tbHas.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tbHas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Cod. Retirada", "Material Retirado", "Qtde retirada", "Qtde Devolvida"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbHas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbHasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbHas);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1218, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
        );

        btSalvar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/icones/salvar.png"))); // NOI18N
        btSalvar.setText("Salvar");
        btSalvar.setPreferredSize(new java.awt.Dimension(121, 50));
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
            }
        });

        btSair.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/icones/fechar.png"))); // NOI18N
        btSair.setText("Sair");
        btSair.setPreferredSize(new java.awt.Dimension(121, 50));
        btSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSairActionPerformed(evt);
            }
        });

        btDevolver.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btDevolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/icones/confirmar3.png"))); // NOI18N
        btDevolver.setText("Devolver Material");
        btDevolver.setPreferredSize(new java.awt.Dimension(134, 50));
        btDevolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDevolverActionPerformed(evt);
            }
        });

        btBuscar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btBuscar.setText("Busca Retirada");
        btBuscar.setPreferredSize(new java.awt.Dimension(200, 50));
        btBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(368, 483, Short.MAX_VALUE)
                .addComponent(btSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(btSair, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(589, 589, 589))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(572, 572, 572)
                        .addComponent(btDevolver, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btDevolver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBuscarActionPerformed
        // TODO add your handling code here:
        PreparedStatement cnd1;
        
        try {
            /* cnd1 = con.conecta().prepareStatement("select cod_policial from usuario");
             ResultSet rs1;
             rs1 = cnd1.executeQuery();
               
             rs1.next();
             codPolicial = rs1.getString("cod_policial");
               
             */

            VerificarBio cad = new VerificarBio();
            codPolicial = cad.codigo;
            atualizaTabela(codPolicial);
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "" + e);
        }
    }//GEN-LAST:event_btBuscarActionPerformed

    public void devolverMaterial() {
        String codRetirada = "";
        String nomeMaterial = "";
        if (tbRetirada.getSelectedRows() != null) {
            codRetirada = tbRetirada.getValueAt(tbRetirada.getSelectedRow(), 0).toString();

            String sql = "select * from retirada_material_has_material where retirada_material_cod_retirada = " + codRetirada;

            PreparedStatement cnd;

            try {
                cnd = con.conecta().prepareStatement(sql);
                ResultSet rs;
                rs = cnd.executeQuery();

                DefaultTableModel model = (DefaultTableModel) tbHas.getModel();
                model.setNumRows(0);

                tbHas.getColumnModel().getColumn(0).setMinWidth(0);
                tbHas.getColumnModel().getColumn(0).setMinWidth(0);

                tbHas.getColumnModel().getColumn(1).setMinWidth(40);
                tbHas.getColumnModel().getColumn(2).setMinWidth(40);
                tbHas.getColumnModel().getColumn(3).setMinWidth(40);
//                tbHas.getColumnModel().getColumn(4).setMinWidth(40);

                int i = 0;
                while (rs.next()) {

                    PreparedStatement cnd1;

                    try {
                        cnd1 = con.conecta().prepareStatement("select nome_material from material where cod_material = " + rs.getString("material_cod_material"));
                        ResultSet rs1;
                        rs1 = cnd1.executeQuery();
                        codMateriais[i] = rs.getString("material_cod_material");
                        i++;
                        rs1.next();
                        nomeMaterial = rs1.getString("nome_material");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Erro ao procurar nome do material");
                    }
                    //System.out.println("Nome: " + rs.getString("nome"));
                    model.addRow(
                            new Object[]{
                                rs.getString("retirada_material_cod_retirada"),
                                nomeMaterial,
                                rs.getString("qtde_retirada")

                            }
                    );
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro inserir valores na tabela Devolucao Material");
            }

        }
    }

    private void btDevolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDevolverActionPerformed
        // TODO add your handling code here:
        devolverMaterial();
    }//GEN-LAST:event_btDevolverActionPerformed

    private void btSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSairActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btSairActionPerformed

    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
        // TODO add your handling code here:

        int qtdVazios = 0;
        for (int i = 0; i < tbHas.getRowCount(); i++) {
            try {
                if (tbHas.getValueAt(i, 3).toString().equals("")) {
                }
            } catch (NullPointerException erro) {
                qtdVazios++;
            }
        }
        if (qtdVazios == 0) {
            String codTipo = "";
            if ((tbHas.getRowCount() != 0)) {
                for (int i = 0; i < tbHas.getRowCount(); i++) {
                    PreparedStatement cnd1;

                    try {
                        cnd1 = con.conecta().prepareStatement("SELECT codigo_tipo_material as codTipo FROM tipo_material WHERE descricao_material = '" + tbHas.getValueAt(i, 1).toString() + "'");
                        ResultSet rs;
                        rs = cnd1.executeQuery();
                        rs.next();
                        codTipo = rs.getString("codTipo");
                    } catch (Exception erro) {
                        JOptionPane.showMessageDialog(null, "Erro ao procurar codigo do tipo de material " + erro + " " + codTipo);
                    }
                    refazerAcoes(Integer.parseInt(tbHas.getValueAt(i, 3).toString()), codMateriais[i], codTipo);
                    int qtdRetirada = 0;
                    int qtdDevolvida = 0;

                    qtdRetirada = Integer.parseInt(tbHas.getValueAt(i, 2).toString());
                    qtdDevolvida = Integer.parseInt(tbHas.getValueAt(i, 3).toString());
                    if (qtdRetirada > qtdDevolvida) {
                        String obs = JOptionPane.showInputDialog("Situação do Material não devolvido?");
                        try {
                            PreparedStatement cnd7;

                            cnd7 = con.conecta().prepareStatement("UPDATE retirada_material_has_material SET obs = ? WHERE retirada_material_cod_retirada = " + tbHas.getValueAt(0, 0).toString() + " AND material_cod_material = " + codMateriais[i] + " ");

                            cnd7.setString(1, "" + obs);
                            cnd7.executeUpdate();
                        } catch (Exception erro) {
                            JOptionPane.showMessageDialog(null, "Erro ao inserir observação");
                        }
                    }
                }
                
                               
            }
            String dataDevolucao = "";
            java.util.Date data1 = new java.util.Date();
            java.util.Date data2 = new java.util.Date();
            DateFormat formato1 = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat formato2 = new SimpleDateFormat("HH:mm:ss");
            dataDevolucao = formato1.format(data1) + " as " + formato2.format(data2);

            try {

                PreparedStatement cnd5;

                cnd5 = con.conecta().prepareStatement("UPDATE retirada_material SET data_devolucao = ? WHERE cod_retirada = " + tbHas.getValueAt(0, 0).toString() + " ");

                cnd5.setString(1, dataDevolucao);
                cnd5.executeUpdate();

            } catch (Exception erro) {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar data da devolucao");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Você deve preencher o campo Qtde Devolvida de todos os materiais");
        }
       
        ((DefaultTableModel) tbHas.getModel()).setNumRows(0);
         atualizaTabela(codPolicial);
               
    }//GEN-LAST:event_btSalvarActionPerformed

    private void tbRetiradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRetiradaMouseClicked
        // TODO add your handling code here:
        if(tbRetirada.getSelectedRowCount() > 0){
            btDevolver.setEnabled(true);
        }
    }//GEN-LAST:event_tbRetiradaMouseClicked

    private void tbHasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHasMouseClicked
        // TODO add your handling code here:
        if(tbHas.getSelectedRowCount() > 0){
            btSalvar.setEnabled(true);
        }
    }//GEN-LAST:event_tbHasMouseClicked

    public void refazerAcoes(int qtd, String codMaterial, String codTipoMaterial) {
        PreparedStatement cnd3;
        int quantidade = 0;
        try {
            cnd3 = con.conecta().prepareStatement("select qtde_material from material WHERE cod_material = " + codMaterial.toString() + ";");
            ResultSet rs3;
            rs3 = cnd3.executeQuery();
                       //int i = 0;
            //String tipo[] = {};
            while (rs3.next()) {
                           //JOptionPane.showMessageDialog(null, ""+rs.getString("descricao_material"));
                //tipo[i] = rs.getString("codigo_tipo_material");
                quantidade = Integer.parseInt(rs3.getString("qtde_material"));
                //i++;
            }
            quantidade = quantidade + qtd;

            int qtdTipo = 0;
            PreparedStatement cnd5;
            cnd5 = con.conecta().prepareStatement("select quantidade_material from tipo_material WHERE codigo_tipo_material = " + codTipoMaterial.toString() + ";");
            ResultSet rs5;
            rs5 = cnd5.executeQuery();
                       //int i = 0;
            //String tipo[] = {};
            while (rs5.next()) {
                           //JOptionPane.showMessageDialog(null, ""+rs.getString("descricao_material"));
                //tipo[i] = rs.getString("codigo_tipo_material");
                qtdTipo = Integer.parseInt(rs5.getString("quantidade_material"));
                //i++;
            }
            try {

                PreparedStatement cnd2;
                cnd2 = con.conecta().prepareStatement("UPDATE material SET qtde_material= ?  WHERE cod_material = " + codMaterial.toString() + " ");
                cnd2.setString(1, "" + quantidade);
                cnd2.executeUpdate();
                PreparedStatement cnd4;

                cnd4 = con.conecta().prepareStatement("UPDATE tipo_material SET quantidade_material= ?  WHERE codigo_tipo_material = " + codTipoMaterial.toString() + " ");
                cnd4.setString(1, "" + (qtdTipo + qtd));

                cnd4.executeUpdate();

                PreparedStatement cnd6;

                cnd6 = con.conecta().prepareStatement("UPDATE retirada_material_has_material SET qtde_devolvida = ? WHERE retirada_material_cod_retirada = " + tbHas.getValueAt(0, 0).toString() + " AND material_cod_material = " + codMaterial.toString() + " ");

                cnd6.setString(1, "" + qtd);
                cnd6.executeUpdate();

                JOptionPane.showMessageDialog(null, "Materiais devolvidos com sucesso!");

            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, erro);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao procurar Quantidade!");
        }
    }

    public void atualizaTabela(String codPolicial) {
        con.conecta();

        String nome = "";
        String local = "";
        PreparedStatement cnd1;

        try {
            cnd1 = con.conecta().prepareStatement("SELECT nome_policial FROM usuario WHERE cod_policial = " + codPolicial);
            ResultSet rs;
            rs = cnd1.executeQuery();
            rs.next();
            nome = rs.getString("nome_policial");
        } catch (Exception erro) {
         //   JOptionPane.showMessageDialog(null, "Erro ao procurar nome de usuario");
            JOptionPane.showMessageDialog(null, "retirada nao cadastrada!");
        }
        String sql = "select * from retirada_material where usuario_cod_policial = " + codPolicial + " and data_devolucao is NULL ";

        PreparedStatement cnd;

        try {
            cnd = con.conecta().prepareStatement(sql);
            ResultSet rs;
            rs = cnd.executeQuery();

            DefaultTableModel model = (DefaultTableModel) tbRetirada.getModel();
            model.setNumRows(0);

            tbRetirada.getColumnModel().getColumn(0).setMinWidth(0);
            tbRetirada.getColumnModel().getColumn(0).setMinWidth(0);

            tbRetirada.getColumnModel().getColumn(1).setMinWidth(40);
            tbRetirada.getColumnModel().getColumn(2).setMinWidth(40);

            while (rs.next()) {
                //System.out.println("Nome: " + rs.getString("nome"));
                PreparedStatement cnd2;

                try {
                    cnd2 = con.conecta().prepareStatement("SELECT nome_destino FROM destino_material WHERE cod_destino = " + rs.getString("destino_material_cod_destino"));
                    ResultSet rs2;
                    rs2 = cnd2.executeQuery();
                    rs2.next();
                    local = rs2.getString("nome_destino");
                } catch (Exception erro) {
                    JOptionPane.showMessageDialog(null, "Erro ao procurar local do destino " + erro + " " + local);
                }
                model.addRow(
                        new Object[]{
                            rs.getString("cod_retirada"),
                            local,
                            nome,
                            rs.getString("data_retirada")

                        }
                );
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro inserir valores na tabela Retirada");
        }
    }

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
            java.util.logging.Logger.getLogger(TelaDevolucaoEstoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaDevolucaoEstoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaDevolucaoEstoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaDevolucaoEstoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaDevolucaoEstoque().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btBuscar;
    private javax.swing.JButton btDevolver;
    private javax.swing.JButton btSair;
    private javax.swing.JButton btSalvar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbHas;
    private javax.swing.JTable tbRetirada;
    // End of variables declaration//GEN-END:variables
}