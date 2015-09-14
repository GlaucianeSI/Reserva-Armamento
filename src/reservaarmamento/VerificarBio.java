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
public class VerificarBio{

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
    public VerificarBio(){
       super();
       

        bsp = new NBioBSPJNI();

        IndexSearchEngine = bsp.new IndexSearch();

        if (CheckError()) {
           
        }

        

        NBioBSPJNI.INIT_INFO_0 initInfo0 = bsp.new INIT_INFO_0();
        bsp.GetInitInfo(initInfo0);

        if (CheckError()) {
            return;
        }

        if (SetDeviceList() == false) {
            return;
        }

        //status.setText("Inicializado com sucesso!");

        AbrirBiometria();
        LerParticipante();
    }

    private void AbrirBiometria() {
//        status.setText("NBioBSP OpenDevice start");

        if (openedDevice != 0) {
            bsp.CloseDevice(openedDevice);
        }

        openedDevice = 0;

        
        bsp.OpenDevice();
        

        if (CheckError()) {
            return;
        }

        openedDevice = bsp.GetOpenedDeviceID();
        
   //     JOptionPane.showMessageDialog(null,"Leitor Conectado!");

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

//        status.setText("Aguardando Cadastro...");

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
            //JOptionPane.showMessageDialog(null,"Policial Militar não cadastrado!!!");
            JOptionPane.showMessageDialog(null,"Policial Militar não cadastrado!!!");
        //    jLabel1.setVisible(true);
        //    jLabel2.setVisible(false);

        } else {

            String nome = VerificaNome(fpInfo.ID);
            // String tipo = VerificaEntradaSaida(fpInfo.ID);
            // Closing();  
            // return true; 
            //JOptionPane.showMessageDialog(null, "Usuário encontrado: "+ fpInfo.ID);
            //status.setText("Registrado: " + nome);
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
            JOptionPane.showMessageDialog(null,"NBioBSP Error Occured [" + bsp.GetErrorCode() + "]");
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
            JOptionPane.showMessageDialog(null,"Não há leitor biometrico conectado!!");

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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    
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

    }

    // End of variables declaration                   
}
