/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package reservaarmamento;

import com.nitgen.SDK.BSP.NBioBSPJNI;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author glaucia
 */
public class CadastrarPM extends javax.swing.JFrame {
    Conexao con_cad_pm;
    private NBioBSPJNI                      bsp;
    NBioBSPJNI.IndexSearch      IndexSearchEngine;
    private NBioBSPJNI.DEVICE_ENUM_INFO     deviceEnumInfo;
    private short                           openedDevice;
    private NBioBSPJNI.FIR_HANDLE           hSavedFIR;
    private NBioBSPJNI.FIR                  fullSavedFIR;
    private NBioBSPJNI.FIR_TEXTENCODE       textSavedFIR;
    static String codigoPM = "";

    /**
     * Creates new form CadastrarPM
     */

    public CadastrarPM(String cod) {
        
        this.setExtendedState(MAXIMIZED_BOTH);
        
        con_cad_pm = new Conexao();
        con_cad_pm.conecta();
        con_cad_pm.executeSQL("select * from usuario");
        
        
        initComponents();
        setLocationRelativeTo(null);
        codigoPM = cod;
        if(!(codigoPM.equals("-1"))){
            preencheCampos();
        }
        
       
        
         /*

        if (CheckError())
            return ;

       setTitle("NBioAPI_JavaDemo BSP version: " + bsp.GetVersion());

       NBioBSPJNI.INIT_INFO_0 initInfo0 = bsp.new INIT_INFO_0();
       bsp.GetInitInfo(initInfo0);

       if (CheckError())
            return ;

       //editEIQ.setText("" + initInfo0.EnrollImageQuality);
       //editVIQ.setText("" + initInfo0.VerifyImageQuality);
       //editMaxFinger.setText("" + initInfo0.MaxFingersForEnroll);
       //editSPFinger.setText("" + initInfo0.SamplesPerFinger);
       //editDTimeout.setText("" + initInfo0.DefaultTimeout);
       //comboSLevel.setSelectedIndex(initInfo0.SecurityLevel - 1);

       //btnGet.setEnabled(true);
       //btnSet.setEnabled(true);
       //btnOpen.setEnabled(true);

       if (SetDeviceList() == false)
          return ;

       //rbtnHFIR.setSelected(true);

       status.setText("Inicializado com sucesso!");
       */
       //
    }
    
    public void preencheCampos(){
        con_cad_pm.conecta();
         String sql = "select * from usuario where cod_policial = "+codigoPM;

         PreparedStatement cnd;
         try {
                cnd = con_cad_pm.conecta().prepareStatement(sql);
                ResultSet rs;
                rs = cnd.executeQuery();
                rs.next();
                      
                      posto.setSelectedItem(rs.getString("posto_graduacao"));
                      nome.setText(rs.getString("nome_policial"));
                      rg.setText(rs.getString("rg_policial"));
                      matricula.setText(rs.getString("matricula_policial"));
                      funcao.setText(rs.getString("tipo_usu"));
                      senha.setText(rs.getString("senhar"));
                      usuario.setText(rs.getString("usuario"));
             }catch(Exception erro){
                 JOptionPane.showMessageDialog(null, erro+"  Erro ao buscar dados do PM");
             }
    }

    CadastrarPM(Object object, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        salvar = new javax.swing.JButton();
        fechar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nome = new UpperCaseField();
        jLabel8 = new javax.swing.JLabel();
        rg = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        matricula = new UpperCaseField();
        jLabel5 = new javax.swing.JLabel();
        usuario = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        senha = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        funcao = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        polegar = new javax.swing.JLabel();
        status = new javax.swing.JLabel();
        posto = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Novo Cadastro");
        setBackground(new java.awt.Color(153, 255, 255));
        setPreferredSize(new java.awt.Dimension(990, 397));
        setResizable(false);

        salvar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        salvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/icones/salvar.png"))); // NOI18N
        salvar.setText("Salvar");
        salvar.setPreferredSize(new java.awt.Dimension(99, 33));
        salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salvarActionPerformed(evt);
            }
        });

        fechar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        fechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/icones/fechar.png"))); // NOI18N
        fechar.setText("Sair");
        fechar.setPreferredSize(new java.awt.Dimension(99, 33));
        fechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fecharActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações Funcionais", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 14))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel1.setText("Nome:");

        nome.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel8.setText("R.G.:");

        rg.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setText("Matricula:");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setText("Post./Grad.:");
        jLabel2.setPreferredSize(new java.awt.Dimension(73, 23));

        matricula.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel5.setText("Usuário:");

        usuario.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuarioActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel6.setText("Senha:");

        senha.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel7.setText("Função:");

        funcao.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Polegar Direito", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 18))); // NOI18N
        jPanel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jButton1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jButton1.setText("Capturar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        polegar.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        polegar.setText("Sem digital!");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(polegar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(polegar)
                .addGap(18, 18, 18))
        );

        status.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        status.setText("Status");

        posto.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        posto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CEL PM", "TC PM", "MAJ PM", "CAP PM", "TEN PM", "ASP OF PM", "ST PM", "SGT PM", "CB PM", "SD PM" }));
        posto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(matricula, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(posto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(senha, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(funcao, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nome)))
                        .addGap(52, 52, 52)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rg))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(rg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(matricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(posto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(senha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(funcao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addComponent(status)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(fechar, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(304, 304, 304))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fechar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private boolean SetDeviceList(){
        
        int i, n;
        String szValue;

        deviceEnumInfo = bsp.new DEVICE_ENUM_INFO();
        bsp.EnumerateDevice(deviceEnumInfo);

        if (CheckError())  {
           //btnOpen.setEnabled(false);
           return false;
        }

        n = deviceEnumInfo.DeviceCount;

        if (n == 0)  {
            status.setText("Não há leitor biométrico conectado!!");
            //btnOpen.setEnabled(false);
            return false;
        }

        szValue = "Auto_Detect";
        //comboDevice.addItem(szValue);

        for (i = 0; i < n; i++)  {
            szValue = deviceEnumInfo.DeviceInfo[i].Name + "(ID: " + deviceEnumInfo.DeviceInfo[i].Instance + ")";
            //comboDevice.addItem(szValue);
        }

        //comboDevice.setSelectedIndex(0);
        //btnOpen.setEnabled(true);
        openedDevice = 0;
        
        return true;
    }
    
     public void Closing()
    {
        this.disposes();
    }
     
     private void AbrirBiometria(){
               status.setText("NBioBSP OpenDevice start");

        if (openedDevice != 0)
            bsp.CloseDevice(openedDevice);

        openedDevice = 0;

        //int nSelectedIndex = comboDevice.getSelectedIndex();

       // if (nSelectedIndex == 0)
            bsp.OpenDevice();
       // else  {
        //    nSelectedIndex -= 1;
           // bsp.OpenDevice(deviceEnumInfo.DeviceInfo[nSelectedIndex].NameID, deviceEnumInfo.DeviceInfo[nSelectedIndex].Instance);
        //}

        if (CheckError())
            return ;

        openedDevice = bsp.GetOpenedDeviceID();
        //btnEnroll.setEnabled(true);
       // btnCapture.setEnabled(true);

       status.setText("Leitor Biométrico conectado!");
    }
    
     public Boolean CheckError(){
         
        if (bsp.IsErrorOccured())  {
            status.setText("NBioBSP Error Occured [" + bsp.GetErrorCode() + "]");
            return true;
        }

        return false;
    }
     
      public void disposes(){
          
        if (hSavedFIR != null)  {
            hSavedFIR.dispose();
            hSavedFIR = null;
        }

        if (fullSavedFIR != null)
            fullSavedFIR = null;

        if (textSavedFIR != null)
            textSavedFIR = null;

        if (bsp != null) {
            bsp.dispose();
            bsp = null;
        }
        
        //this.dispose();
    }
     
    private void salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salvarActionPerformed
        if(nome.getText().trim().isEmpty()){

            JOptionPane.showMessageDialog(null,"Preencha o campo Nome!!!","Campo Vazio", JOptionPane.INFORMATION_MESSAGE);
            nome.requestFocus();

        }else if(rg.getText().trim().isEmpty()){

            JOptionPane.showMessageDialog(null,"Preencha o campo R.G. !!!","Campo Vazio", JOptionPane.INFORMATION_MESSAGE);
            rg.requestFocus();
         }
        else if(matricula.getText().trim().isEmpty()){

            JOptionPane.showMessageDialog(null,"Preencha o campo matricula!!!","Campo Vazio", JOptionPane.INFORMATION_MESSAGE);
            matricula.requestFocus();

        }
         else{
              // if (ManterPM.codexec==1){          
        
      
                //inclui novo participante
                try {
                    String sql = "insert into usuario (posto_graduacao,nome_policial, rg_policial, matricula_policial,  usuario, senhar, digital_policial, tipo_usu)"+
                    "values ('"+posto.getSelectedItem().toString()+"','"+nome.getText()+"', '"+rg.getText()+"', '"+matricula.getText()+"',"
                    + "'"+usuario.getText()+"', '"+senha.getText()+"','"+textSavedFIR.TextFIR+"','"+funcao.getText()+"');";
                   con_cad_pm.statement.executeUpdate(sql);
                   JOptionPane.showMessageDialog(null, "cadastro realizada com sucesso");
                //   dispose();
             }catch(SQLException | HeadlessException erro){
            JOptionPane.showMessageDialog(null, "houve um erro:");
             }
          }
      //  }    
        
    }//GEN-LAST:event_salvarActionPerformed

    private void fecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fecharActionPerformed
        // TODO add your handling code here:
       dispose();
    }//GEN-LAST:event_fecharActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//       CadBiometria obj = new CadBiometria(this, rootPaneCheckingEnabled);
        bsp = new NBioBSPJNI();
        AbrirBiometria();
        status.setText("Aguardando Digitais!");
       //int codigo = ManterPM.pegaCodigo();

        if (hSavedFIR != null)  {
            hSavedFIR.dispose();
            hSavedFIR = null;
        }

        NBioBSPJNI.FIR_PAYLOAD payLoad;
        String szValue;

        payLoad = bsp.new FIR_PAYLOAD();
       // payLoad.SetText(String.valueOf(codigo));

        hSavedFIR = bsp.new FIR_HANDLE();

        bsp.Enroll(hSavedFIR, payLoad);

        if (CheckError())
            return ;

        if (fullSavedFIR != null)
            fullSavedFIR = null;

        if (textSavedFIR != null)
            textSavedFIR = null;

        fullSavedFIR = bsp.new FIR();
        bsp.GetFIRFromHandle(hSavedFIR, fullSavedFIR);

        if (CheckError())
            return ;

        textSavedFIR = bsp.new FIR_TEXTENCODE();
        bsp.GetTextFIRFromHandle(hSavedFIR, textSavedFIR);

        if (CheckError())
            return ;

        status.setText("Leitura realizada com sucesso");
        
        if (CheckError())
            return ;
       
                //Salva os dados alterados
               /* try {
                    // TODO add your handling code here:
                   con_cad_pm = new Conexao();

                    // String para atualização no banco
                    String query = "update usuario set digital_policial = ?"
                    + " where cod_policial = 14";

                    //Cria o comando
                    PreparedStatement stnt = con_cad_pm.conecta().prepareStatement(query);

                    //Seta os valores na
                    stnt.setString(1, textSavedFIR.TextFIR);
                   // stnt.setInt(2, codigo);
                    
                    //Execulta o comando no banco
                    stnt.executeUpdate();

                    //Fechar as conecções
                    stnt.close();
                    //con.close();

                    //limparCampos();
                  //  ManterPM.atualizaTabela();
                    //this.dispose();

                } catch (SQLException e){
                    //System.out.println("Ocorreu um erro de SQL");
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro de SQL");
                }*/
        
        hSavedFIR.dispose();
        hSavedFIR = null;

        status.setText("Capturado com sucesso!");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usuarioActionPerformed

    private void postoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_postoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_postoActionPerformed

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
            java.util.logging.Logger.getLogger(CadastrarPM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastrarPM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastrarPM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastrarPM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastrarPM(codigoPM).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton fechar;
    private javax.swing.JTextField funcao;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField matricula;
    private javax.swing.JTextField nome;
    private javax.swing.JLabel polegar;
    private javax.swing.JComboBox posto;
    private javax.swing.JTextField rg;
    private javax.swing.JButton salvar;
    private javax.swing.JPasswordField senha;
    private javax.swing.JLabel status;
    private javax.swing.JTextField usuario;
    // End of variables declaration//GEN-END:variables

    
}
