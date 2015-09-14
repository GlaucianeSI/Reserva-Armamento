/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservaarmamento;

import com.nitgen.SDK.BSP.NBioBSPJNI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 *
 */
public class CadBiometria extends javax.swing.JDialog {

    // Variable BSP
    private NBioBSPJNI bsp;
    private NBioBSPJNI.DEVICE_ENUM_INFO deviceEnumInfo;
    private short openedDevice;
    private NBioBSPJNI.FIR_HANDLE hSavedFIR;
    private NBioBSPJNI.FIR fullSavedFIR;
    private NBioBSPJNI.FIR_TEXTENCODE textSavedFIR;
    private NBioBSPJNI.FIR_TEXTENCODE textCapturadFIR;
    NBioBSPJNI.IndexSearch IndexSearchEngine;
    private NBioBSPJNI.INPUT_FIR inputFIR;
    private NBioBSPJNI.IndexSearch.SAMPLE_INFO sampleInfo;
    NBioBSPJNI.WINDOW_OPTION winOption;
    int cod_ativ;
    int total;
    String codigo;

    /**
     * Creates new form CadBiometria
     *
     * @param parent
     * @param modal
     */
    public CadBiometria(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        bsp = new NBioBSPJNI();

        IndexSearchEngine = bsp.new IndexSearch();

        if (CheckError()) {
            return;
        }

        setTitle("NBioAPI_JavaDemo BSP version: " + bsp.GetVersion());

        NBioBSPJNI.INIT_INFO_0 initInfo0 = bsp.new INIT_INFO_0();
        bsp.GetInitInfo(initInfo0);

        if (CheckError()) {
            return;
        }

        if (SetDeviceList() == false) {
            return;
        }

        status.setText("Inicializado com sucesso!");

        AbrirBiometria();
        LerParticipante();
    }

    private void AbrirBiometria() {
        status.setText("NBioBSP OpenDevice start");

        if (openedDevice != 0) {
            bsp.CloseDevice(openedDevice);
        }

        openedDevice = 0;

        
        bsp.OpenDevice();
        

        if (CheckError()) {
            return;
        }

        openedDevice = bsp.GetOpenedDeviceID();
        
        status.setText("Biometria inicializada com SUCESSO!");

        try {

            Connection conn = conectaMysql.getConexao();

            String query = "select cod_policial, nome_policial, digital_policial from usuario";

            PreparedStatement cnd;

            cnd = conn.prepareStatement(query);

        
            ResultSet rs;

            rs = cnd.executeQuery();

            while (rs.next()) {
                //System.out.println("Nome: " + rs.getString("nome_policial"));
                //inputFIR.SetFullFIR(fullSavedFIR);
                if (rs.getString("digital_policial") != null) {

                    textSavedFIR = bsp.new FIR_TEXTENCODE();
                    textSavedFIR.TextFIR = rs.getString("digital_policial");
                    inputFIR = bsp.new INPUT_FIR();
                    inputFIR.SetTextFIR(textSavedFIR);

                    //indexando a digital e o ID do usuario extraídos do BD na memória  
                    IndexSearchEngine.AddFIR(inputFIR, rs.getInt("cod_policial"), sampleInfo);
                    
                    if (CheckError()) {
                        return;
                    }

                }

            }

        } catch (SQLException ex) {
            //System.out.println("Erro de SQL" + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Erro de SQL" + ex.getMessage());
        }

    }

    private void LerParticipante() {
        String szValue;
        int nUserID;

        status.setText("Aguardando Cadastro...");

        NBioBSPJNI.FIR_HANDLE hFIR = bsp.new FIR_HANDLE();

        //Capturando a digital do usuario para verificação
        NBioBSPJNI.FIR_HANDLE hCapture = bsp.new FIR_HANDLE();
        bsp.Capture(NBioBSPJNI.FIR_PURPOSE.VERIFY, hCapture, -1, null, winOption);
        if (CheckError()) {
            //return false;  
        }
        NBioBSPJNI.INPUT_FIR inputFIR1;
        inputFIR1 = bsp.new INPUT_FIR();
        inputFIR1.SetFIRHandle(hCapture);
        NBioBSPJNI.IndexSearch.FP_INFO fpInfo = IndexSearchEngine.new FP_INFO();

        //identificado a digital capturada com as que foram indexadas  
        IndexSearchEngine.Identify(inputFIR1, 5, fpInfo);
        if (CheckError()) {
            //Closing();  
            //return false;  
            status.setText("Policial Militar não cadastrado!!!");
            JOptionPane.showMessageDialog(null,"Policial Militar não cadastrado!!!");
        //    jLabel1.setVisible(true);
        //    jLabel2.setVisible(false);

        } else {

            String nome = VerificaNome(fpInfo.ID);
            // String tipo = VerificaEntradaSaida(fpInfo.ID);
            // Closing();  
            // return true; 
            //JOptionPane.showMessageDialog(null, "Usuário encontrado: "+ fpInfo.ID);
            status.setText("Registrado: " + nome);
            codigo = ""+fpInfo.ID;
            //JOptionPane.showMessageDialog(null,"Registrado NOME: " + nome +" CODIGO: "+fpInfo.ID);
                     
               
        }

        hFIR.dispose();
        hFIR = null;
    }
    //Função que pega o nome do participante
    private String VerificaNome(int codigo) {
        try {

            Connection conn = conectaMysql.getConexao();

            String query = "select * from usuario where cod_policial=?";

            PreparedStatement cnd;

            cnd = conn.prepareStatement(query);

            cnd.setInt(1, codigo);

            ResultSet rs;

            rs = cnd.executeQuery();

            while (rs.next()) {
                return (rs.getString("nome_policial"));
            }

        } catch (SQLException ex) {
            //System.out.println("Erro de SQL" + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Erro de SQL" + ex.getMessage());
        }
        return null;
    }

    public Boolean CheckError() {
        if (bsp.IsErrorOccured()) {
            status.setText("NBioBSP Error Occured [" + bsp.GetErrorCode() + "]");
            return true;
        }

        return false;
    }

    public void disposes() {
        if (hSavedFIR != null) {
            hSavedFIR.dispose();
            hSavedFIR = null;
        }

        if (fullSavedFIR != null) {
            fullSavedFIR = null;
        }

        if (textSavedFIR != null) {
            textSavedFIR = null;
        }

        if (bsp != null) {
            bsp.dispose();
            bsp = null;
        }

    }

    public void Closing() {
        this.disposes();
    }

    private boolean SetDeviceList() {
        int i, n;
        String szValue;

        deviceEnumInfo = bsp.new DEVICE_ENUM_INFO();
        bsp.EnumerateDevice(deviceEnumInfo);

        if (CheckError()) {

            return false;
        }

        n = deviceEnumInfo.DeviceCount;

        if (n == 0) {
            status.setText("Não há leitor biometrico conectado!!");

            return false;
        }

        szValue = "Auto_Detect";

        for (i = 0; i < n; i++) {
            szValue = deviceEnumInfo.DeviceInfo[i].Name + "(ID: " + deviceEnumInfo.DeviceInfo[i].Instance + ")";

        }

        openedDevice = 0;

        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btCancelar = new javax.swing.JButton();
        status = new javax.swing.JLabel();
        verificacao = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setTitle("Cadastro de Biometria");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        btCancelar.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        btCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/icones/fechar.png"))); // NOI18N
        btCancelar.setText("Sair");
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
            }
        });

        status.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        status.setText("Status");

        verificacao.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        verificacao.setText("Verificar Biometria");
        verificacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verificacaoActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Abrir");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(status))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(184, 184, 184)
                        .addComponent(verificacao, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(231, 231, 231)
                        .addComponent(btCancelar)))
                .addContainerGap(241, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(61, Short.MAX_VALUE)
                .addComponent(status)
                .addGap(47, 47, 47)
                .addComponent(verificacao, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btCancelar)
                .addGap(46, 46, 46))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void verificacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verificacaoActionPerformed
      
        AbrirBiometria();
        LerParticipante();


    }//GEN-LAST:event_verificacaoActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
      // int codigo = ManterPM.pegaCodigo();

        //Pasta do aplicativo

    }//GEN-LAST:event_formWindowOpened

    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btCancelarActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jMenuItem1ActionPerformed

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
            java.util.logging.Logger.getLogger(CadBiometria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadBiometria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadBiometria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadBiometria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
/*
         /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CadBiometria dialog = new CadBiometria(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancelar;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JLabel status;
    private javax.swing.JButton verificacao;
    // End of variables declaration//GEN-END:variables
}
