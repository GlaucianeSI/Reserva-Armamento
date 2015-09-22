/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservaarmamento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author glaucia
 */
public class Relatorio extends javax.swing.JFrame {

    static int codexec = 0;
    Conexao com;

    /**
     * Creates new form Relatorio
     */
    public Relatorio() {
        com = new Conexao();
        initComponents();
    }

    /**
     *
     * @throws JRException
     */
    public void relatorioDiario() throws JRException {

        com.conecta();
        String sql = "SELECT usuario.nome_policial, usuario.rg_policial, material.nome_material, destino_material.local_destino, retirada_material.data_retirada, retirada_material.data_devolucao FROM usuario, material, destino_material, retirada_material";

        PreparedStatement cnd;
        ArrayList<RelatorioMaterialPago> relatorios = new ArrayList<RelatorioMaterialPago>();
        try {
            cnd = com.conecta().prepareStatement(sql);
            ResultSet rs;
            rs = cnd.executeQuery();

            //DefaultTableModel model = (DefaultTableModel) tbPart.getModel();
            //model.setNumRows(0);
            while (rs.next()) {
                //System.out.println("Nome: " + rs.getString("nome"));

                RelatorioMaterialPago relatorio = new RelatorioMaterialPago();
                relatorio.setNome(rs.getString("nome_policial"));
                relatorio.setRg(rs.getString("rg_policial"));
                relatorio.setMaterial(rs.getString("nome_material"));
                relatorio.setDestino(rs.getString("local_destino"));
                relatorio.setDataret(rs.getString("data_retirada"));
                relatorio.setDatadev(rs.getString("data_devolucao"));

                relatorios.add(relatorio);
                System.out.print(relatorios);
            }

            // compilacao do JRXML
            JasperReport report = JasperCompileManager
                    .compileReport("relatorio/RelatorioDiario.jrxml");

            // preenchimento do relatorio, note que o metodo recebe 3 parametros:
            // 1 - o relatorio
            //
            // 2 - um Map, com parametros que sao passados ao relatorio
            // no momento do preenchimento. No nosso caso eh null, pois nao
            // estamos usando nenhum parametro
            //
            // 3 - o data source. Note que nao devemos passar a lista diretamente,
            // e sim "transformar" em um data source utilizando a classe
            // JRBeanCollectionDataSource
            JasperPrint print = JasperFillManager.fillReport(report, null,
                    new JRBeanCollectionDataSource(relatorios));

            // exportacao do relatorio para outro formato, no caso PDF
            JasperExportManager.exportReportToPdfFile(print,
                    "relatorio/RelatorioDiario.pdf");

            final JasperViewer jv = new JasperViewer(print, false);
            jv.setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(ManterPM.class.getName()).log(Level.SEVERE, null, ex);

            JOptionPane.showMessageDialog(rootPane, "erro " + ex.getMessage());
        }
    }

    public void relatorioConsumo() throws JRException {
 
        com.conecta();
        String sql = "select usuario.nome_policial, material.nome_material, retirada_material.data_retirada, retirada_material.data_devolucao, retirada_material_has_material.obs from usuario, material, retirada_material, retirada_material_has_material where usuario.cod_policial = retirada_material.usuario_cod_policial";

        PreparedStatement cnd;
        ArrayList<RelatorioConsumo> relatorios = new ArrayList<RelatorioConsumo>();
        try {
            cnd = com.conecta().prepareStatement(sql);
            ResultSet rs;
            rs = cnd.executeQuery();

            //DefaultTableModel model = (DefaultTableModel) tbPart.getModel();
            //model.setNumRows(0);
            while (rs.next()) {
                //System.out.println("Nome: " + rs.getString("nome"));

                //nome_policial 	nome_material 	data_retirada 	data_devolucao
                RelatorioConsumo relatorio = new RelatorioConsumo();
                relatorio.setNomePM(rs.getString("nome_policial"));
                relatorio.setMaterialRetirado(rs.getString("nome_material"));
               // relatorio.setMaterialDevolvido(rs.getString(""));
                relatorio.setDataRetirada(rs.getString("data_retirada"));
                relatorio.setDataDevolucao(rs.getString("data_devolucao"));
                relatorio.setObservacao(rs.getString("obs"));
                relatorios.add(relatorio);
                //System.out.print(relatorios);
            }

            // compilacao do JRXML
            JasperReport report = JasperCompileManager
                    .compileReport("relatorio/RelatorioConsumo.jrxml");

            // preenchimento do relatorio, note que o metodo recebe 3 parametros:
            // 1 - o relatorio
            //
            // 2 - um Map, com parametros que sao passados ao relatorio
            // no momento do preenchimento. No nosso caso eh null, pois nao
            // estamos usando nenhum parametro
            //
            // 3 - o data source. Note que nao devemos passar a lista diretamente,
            // e sim "transformar" em um data source utilizando a classe
            // JRBeanCollectionDataSource
            JasperPrint print = JasperFillManager.fillReport(report, null,
                    new JRBeanCollectionDataSource(relatorios));

            // exportacao do relatorio para outro formato, no caso PDF
            JasperExportManager.exportReportToPdfFile(print,
                    "relatorio/RelatorioConsumo.pdf");

            final JasperViewer jv = new JasperViewer(print, false);
            jv.setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(ManterPM.class.getName()).log(Level.SEVERE, null, ex);

            JOptionPane.showMessageDialog(rootPane, "erro " + ex.getMessage());
        }
    }

    public void relatorioMaterial() throws JRException {

        com.conecta();
        String sql = "SELECT material.nome_material,tipo_material.quantidade_material, retirada_material_has_material.qtde_Retirada, retirada_material_has_material.qtde_devolvida FROM material, tipo_material, retirada_material_has_material";

        PreparedStatement cnd;
        ArrayList<RelatorioMaterial> relatorios = new ArrayList<RelatorioMaterial>();
        try {
            cnd = com.conecta().prepareStatement(sql);
            ResultSet rs;
            rs = cnd.executeQuery();

            //DefaultTableModel model = (DefaultTableModel) tbPart.getModel();
//nome_material 	quantidade_material 	qtde_Retirada 	qtde_devolvida 
            //model.setNumRows(0);
            while (rs.next()) {
                //System.out.println("Nome: " + rs.getString("nome"));

                RelatorioMaterial relatorio = new RelatorioMaterial();
                relatorio.setNome(rs.getString("nome_material"));
                relatorio.setQuantidadematerial(rs.getInt("quantidade_material"));
                relatorio.setQuantidadeexistente(rs.getInt("qtde_Retirada"));
                relatorio.setQuantidadeextraviado(rs.getInt("qtde_devolvida"));

                relatorios.add(relatorio);
                System.out.print(relatorios);
            }

            // compilacao do JRXML
            JasperReport report = JasperCompileManager
                    .compileReport("relatorio/RelatorioMaterial.jrxml");

            // preenchimento do relatorio, note que o metodo recebe 3 parametros:
            // 1 - o relatorio
            //
            // 2 - um Map, com parametros que sao passados ao relatorio
            // no momento do preenchimento. No nosso caso eh null, pois nao
            // estamos usando nenhum parametro
            //
            // 3 - o data source. Note que nao devemos passar a lista diretamente,
            // e sim "transformar" em um data source utilizando a classe
            // JRBeanCollectionDataSource
            JasperPrint print = JasperFillManager.fillReport(report, null,
                    new JRBeanCollectionDataSource(relatorios));

            // exportacao do relatorio para outro formato, no caso PDF
            JasperExportManager.exportReportToPdfFile(print,
                    "relatorio/RelatorioMaterial.pdf");

            final JasperViewer jv = new JasperViewer(print, false);
            jv.setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(ManterPM.class.getName()).log(Level.SEVERE, null, ex);

            JOptionPane.showMessageDialog(rootPane, "erro " + ex.getMessage());
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

        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(689, 433));
        setResizable(false);

        jButton4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/icones/fechar.png"))); // NOI18N
        jButton4.setText("Sair");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jButton1.setText("Gerar Relatório");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel1.setText("Retirada de Material:");

        jButton2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jButton2.setText("Gerar Relatório");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setText("Consultar Estoque:");

        jButton3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jButton3.setText("Gerar Relatório");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setText("Relatório de Consumo:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(191, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jButton2))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jButton3))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(289, 289, 289)
                        .addComponent(jButton4)))
                .addContainerGap(453, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addContainerGap(132, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
            relatorioDiario();
        } catch (JRException ex) {
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            // TODO add your handling code here:
            relatorioMaterial();

        } catch (JRException ex) {
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            // TODO add your handling code here:

            relatorioConsumo();
        } catch (JRException ex) {
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(Relatorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Relatorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Relatorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Relatorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Relatorio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
