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

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jButton1.setText("RELATÓRIO RETIRADA DE MATERIAL");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jButton2.setText(" MATERIAL EXISTENTE NO ESTOQUE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jButton3.setText("RELATÓRIO CONSUMO");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField1.setText("jTextField1");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.setText("jTextField2");

        jTextField3.setText("jTextField3");

        jTextField4.setText("jTextField4");

        jTextField5.setText("jTextField5");

        jTextField6.setText("jTextField6");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton1)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(57, 57, 57)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(74, 74, 74)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(59, 59, 59)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(77, 77, 77)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jButton2)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(103, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addComponent(jButton3)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(89, 89, 89))
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

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

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
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
