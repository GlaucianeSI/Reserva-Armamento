/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservaarmamento;

import reservaarmamento.biometria.VerificarBio;
import reservaarmamento.conexao.Conexao;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.JoinRowSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author glaucia
 */
public class TelaDistribuicaoMaterial extends javax.swing.JFrame {

    static int codexec = 0;
    Conexao con;
    DefaultTableModel modelT2;
    int qtdClique = 0;
    int qtdCadastrados = 0;

    /**
     * Creates new form TelaArmeiro
     */
    public TelaDistribuicaoMaterial() {

        con = new Conexao();
        con.conecta();
        initComponents();
        btIncluir.setEnabled(false);
        btConfirmar.setEnabled(false);
        btApagar.setEnabled(false);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

    }

    public void listarCodigo() {

        PreparedStatement cnd;
        try {
            
            cnd = con.conecta().prepareStatement("select * from destino_material");
            ResultSet rs;
            rs = cnd.executeQuery();
               //int i = 0;
            //String tipo[] = {};
            while (rs.next()) {
                   //JOptionPane.showMessageDialog(null, ""+rs.getString("descricao_material"));
                //tipo[i] = rs.getString("codigo_tipo_material");
                jComboBox1.addItem(rs.getString("cod_destino") + "  " + rs.getString("nome_destino"));
                //i++;
            }
            jComboBox1.addItem("Novo");
        } catch (Exception e) {
        }
    }

    public int[] pegaCodigo() {
        int codigo[] = new int[100];
        int linha[] = new int[100];
        int i;
        linha = tbMat.getSelectedRows();

        for (i = 0; i < linha.length; i++) {
            codigo[i] = Integer.parseInt(tbMat.getValueAt(linha[i], 0).toString());
        }
        return codigo;

    }

    public void incluirMaterialTabela() {
        int acheiTotal = 0;
        con.conecta();
        if (tbMat.getSelectedRows() != null) {

            int codigo[] = pegaCodigo();
            int linhaAtual[] = new int[100];
            for (int i = 0; i < codigo.length; i++) {
                for (int j = 0; j < jTable2.getRowCount(); j++) {
                    linhaAtual[j] = Integer.parseInt(jTable2.getValueAt(j, 0).toString());
                }

                String sql = "SELECT * FROM material WHERE cod_material = " + codigo[i] + ";";

                PreparedStatement cnd;

                try {
                    cnd = con.conecta().prepareStatement(sql);
                    ResultSet rs;
                    rs = cnd.executeQuery();

                    jTable2.getColumnModel().getColumn(0).setMinWidth(0);
               /*     jTable2.getColumnModel().getColumn(1).setMinWidth(0);
                    jTable2.getColumnModel().getColumn(2).setMinWidth(200);
                    jTable2.getColumnModel().getColumn(3).setMinWidth(100);
                    jTable2.getColumnModel().getColumn(4).setMinWidth(180);
                    jTable2.getColumnModel().getColumn(5).setMinWidth(120);
                    jTable2.getColumnModel().getColumn(6).setMinWidth(100);
                    jTable2.getColumnModel().getColumn(7).setMinWidth(100);
                    jTable2.getColumnModel().getColumn(8).setMinWidth(150);
                    jTable2.getColumnModel().getColumn(9).setMinWidth(100);
                    jTable2.getColumnModel().getColumn(10).setMinWidth(100);
                    jTable2.getColumnModel().getColumn(11).setMinWidth(100);*/

                    int achei = 0;
                    for (int j = 0; j < jTable2.getRowCount(); j++) {
                        if (codigo[i] == linhaAtual[j]) {
                            achei++;
                            acheiTotal = acheiTotal + achei;
                        }
                    }
                    if (achei == 0) {
                        while (rs.next()) {
                            //System.out.println("Nome: " + rs.getString("nome"));
                            int qtd = Integer.parseInt(JOptionPane.showInputDialog("Quantidade de " + rs.getString("nome_material") + " a ser retirado(a)?"));
                            int qtdAnterior = 0;
                            qtdAnterior = Integer.parseInt(rs.getString("qtde_material"));
                            if (qtd > qtdAnterior) {
                                JOptionPane.showMessageDialog(null, "Estoque insuficiente");
                            } else {

                                atualizaQuantidade(qtd, rs.getString("cod_material"), rs.getString("tipo_material_codigo_tipo_material"));

                                modelT2.addRow(
                                        new Object[]{
                                            rs.getString("cod_material"),
                                            rs.getString("tipo_material_codigo_tipo_material"),
                                            rs.getString("nome_material"),
                                            String.valueOf(qtd),
                                            rs.getString("situacao_material"),
                                            rs.getString("numero_material"),
                                            rs.getString("numero_patrimonio_material"),
                                            rs.getString("marca_material"),
                                            rs.getString("modelo_material"),
                                            rs.getString("calibre_material"),
                                            rs.getString("numero_tombo_material"),
                                            rs.getString("observacao")
                                        }
                                );
                            }
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ManterMaterial.class.getName()).log(Level.SEVERE, null, ex);

                }
            }
        }
        if (acheiTotal != 0) {
            JOptionPane.showMessageDialog(null, "Um ou mais materiais já foram selecionados!!!");
        }
    }

    public void atualizaTabela() {
        con.conecta();

        String sql = "select * from material where nome_material LIKE '" + jTextField1.getText().toString() + "%'";

        PreparedStatement cnd;

        try {
            cnd = con.conecta().prepareStatement(sql);
            ResultSet rs;
            rs = cnd.executeQuery();

            DefaultTableModel model = (DefaultTableModel) tbMat.getModel();
            model.setNumRows(0);

           tbMat.getColumnModel().getColumn(0).setMinWidth(0);
           tbMat.getColumnModel().getColumn(1).setMinWidth(0);
            tbMat.getColumnModel().getColumn(2).setMinWidth(200);
            tbMat.getColumnModel().getColumn(3).setMinWidth(100);
            tbMat.getColumnModel().getColumn(4).setMinWidth(120);
            tbMat.getColumnModel().getColumn(5).setMinWidth(100);
            tbMat.getColumnModel().getColumn(6).setMinWidth(110);
            tbMat.getColumnModel().getColumn(7).setMinWidth(110);
            tbMat.getColumnModel().getColumn(8).setMinWidth(110);
            tbMat.getColumnModel().getColumn(9).setMinWidth(110);
            tbMat.getColumnModel().getColumn(10).setMinWidth(0);
            tbMat.getColumnModel().getColumn(11).setMinWidth(0);
            while (rs.next()) {
                //System.out.println("Nome: " + rs.getString("nome"));
                model.addRow(
                        new Object[]{
                            rs.getString("cod_material"),
                            rs.getString("tipo_material_codigo_tipo_material"),
                            rs.getString("nome_material"),
                            rs.getString("qtde_material"),
                            rs.getString("situacao_material"),
                            rs.getString("numero_material"),
                            rs.getString("numero_patrimonio_material"),
                            rs.getString("marca_material"),
                            rs.getString("modelo_material"),
                            rs.getString("calibre_material"),
                            rs.getString("numero_tombo_material"),
                            rs.getString("observacao")

                        }
                );
                if (jTextField1.getText().toString().equals("")) {
                    PreparedStatement cnd2;
                    try {
                        cnd2 = con.conecta().prepareStatement("select SUM(qtde_material) as qtdMaterial from material ");
                        ResultSet rs2;
                        rs2 = cnd2.executeQuery();
                       //int i = 0;
                        //String tipo[] = {};
                        while (rs2.next()) {
                           //JOptionPane.showMessageDialog(null, ""+rs.getString("descricao_material"));
                            //tipo[i] = rs.getString("codigo_tipo_material");
                            qtdMaterial.setText("Quantidade no Estoque: " + rs2.getString("qtdMaterial"));
                            //i++;
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Erro ao procurar Quantidade!");
                    }
                } else {
                    PreparedStatement cnd3;
                    try {
                        cnd3 = con.conecta().prepareStatement("select SUM(qtde_material) as qtd from material WHERE nome_material LIKE '" + jTextField1.getText().toString() + "%'");
                        ResultSet rs3;
                        rs3 = cnd3.executeQuery();
                       //int i = 0;
                        //String tipo[] = {};
                        while (rs3.next()) {
                           //JOptionPane.showMessageDialog(null, ""+rs.getString("descricao_material"));
                            //tipo[i] = rs.getString("codigo_tipo_material");
                            qtdMaterial.setText("Quantidade no Estoque: " + rs3.getString("qtd"));
                            //i++;
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Erro ao procurar Quantidade!");
                    }
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(ManterMaterial.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(jTable2.getRowCount() > 0){
            btApagar.setEnabled(true);
            btConfirmar.setEnabled(true);
        }
        else{
            btApagar.setEnabled(false);
            btConfirmar.setEnabled(false);
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

        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        btConfirmar = new javax.swing.JButton();
        btApagar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        etNomeDestino = new UpperCaseField();
        etLocalDestino = new UpperCaseField();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        qtdMaterial = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbMat = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        btIncluir = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Distribuição de Material");
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(204, 204, 204));
        setPreferredSize(new java.awt.Dimension(1365, 721));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel5.setText("Data Saída:");

        jButton2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/icones/fechar.png"))); // NOI18N
        jButton2.setText("Sair");
        jButton2.setPreferredSize(new java.awt.Dimension(135, 44));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btConfirmar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/icones/confirmar3.png"))); // NOI18N
        btConfirmar.setText("Confirmar");
        btConfirmar.setPreferredSize(new java.awt.Dimension(135, 45));
        btConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btConfirmarActionPerformed(evt);
            }
        });

        btApagar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btApagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/icones/deletar.png"))); // NOI18N
        btApagar.setText("Excluir");
        btApagar.setPreferredSize(new java.awt.Dimension(100, 44));
        btApagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btApagarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Destino do Material"));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setText("Setor:");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel6.setText("Cidade:");

        etNomeDestino.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        etLocalDestino.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        etLocalDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                etLocalDestinoActionPerformed(evt);
            }
        });

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(7, 7, 7)
                        .addComponent(etLocalDestino))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(etNomeDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, 44)
                .addComponent(jComboBox1, 0, 206, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(etNomeDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(etLocalDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel1.setText("Nome do Material:");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/icones/pesq.png"))); // NOI18N
        jButton6.setText("Pesquisar");
        jButton6.setPreferredSize(new java.awt.Dimension(100, 80));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        qtdMaterial.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        qtdMaterial.setText("Quantidade ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(19, 19, 19)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 180, Short.MAX_VALUE)
                .addComponent(qtdMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(215, 215, 215))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(qtdMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Material Solicitado"));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setText("Retirada");

        jTable2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "COD. MATERIAL", "COD. TIPO", "NOME", "QUANTIDADE", "SITUAÇÃO", "NÚMERO", "N° PATRIMÔNIO", "MARCA", "MODELO", "CALIBRE", "TOMBO", "OBS."
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1225, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 66, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Existentes no Estoque"));

        tbMat.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tbMat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "COD.MATERIAL", "COD. TIPO", "NOME", "QUANTIDADE", "SITUAÇÃO", "NÚMERO", "N° PATRIMÔNIO", "MARCA", "MODELO", "CALIBRE", "TOMBO", "OBS."
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbMat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMatMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbMat);

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel4.setText("Material");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1212, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        btIncluir.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btIncluir.setText("Incluir");
        btIncluir.setPreferredSize(new java.awt.Dimension(100, 30));
        btIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btIncluirActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        jMenuItem1.setText("jMenuItem1");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("jMenuItem2");
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(125, 125, 125))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(172, 172, 172)
                                .addComponent(btApagar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)
                                .addComponent(btConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(620, 620, 620)
                        .addComponent(btIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btApagar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(230, 230, 230))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if ((jTable2.getRowCount() != 0) && (qtdCadastrados == 0)) {
            for (int i = 0; i < jTable2.getRowCount(); i++) {
                refazerAcoes(Integer.parseInt(jTable2.getValueAt(i, 3).toString()), jTable2.getValueAt(i, 0).toString(), jTable2.getValueAt(i, 1).toString());
            }
        }
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        btIncluir.setEnabled(false);
        atualizaTabela();
    }//GEN-LAST:event_jButton6ActionPerformed

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
                                //JOptionPane.showMessageDialog(null, "Dados atualizado com sucesso!");

            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, erro);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao procurar Quantidade!");
        }
    }

    public void atualizaQuantidade(int qtd, String codMaterial, String codTipoMaterial) {
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
            quantidade = quantidade - qtd;

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
                cnd4.setString(1, "" + (qtdTipo - qtd));

                cnd4.executeUpdate();
                                //JOptionPane.showMessageDialog(null, "Dados atualizado com sucesso!");

            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, erro);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao procurar Quantidade!");
        }
    }
    private void btIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btIncluirActionPerformed

        if (qtdClique == 0) {
            modelT2 = (DefaultTableModel) jTable2.getModel();
            modelT2.setNumRows(0);
            qtdClique++;
        }
        incluirMaterialTabela();
        atualizaTabela();
        if(qtdClique == 1){
            listarCodigo();
            qtdClique++;
        }
        
    }//GEN-LAST:event_btIncluirActionPerformed

    private void btApagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btApagarActionPerformed
        // TODO add your handling code here:
        
        int linha[] = new int[100];
        int i;
        linha = jTable2.getSelectedRows();

        for (i = 0; i < linha.length; i++) {
            refazerAcoes(Integer.parseInt(jTable2.getValueAt(linha[i], 3).toString()), jTable2.getValueAt(linha[i], 0).toString(), jTable2.getValueAt(linha[i], 1).toString());
            ((DefaultTableModel) jTable2.getModel()).removeRow(linha[i]);
        }
       atualizaTabela();
       
    }//GEN-LAST:event_btApagarActionPerformed

    private void tbMatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMatMouseClicked
        // TODO add your handling code here:
        //   incluirMaterialTabela();
        if(tbMat.getSelectedRowCount() > 0){
            btIncluir.setEnabled(true);
        }
    }//GEN-LAST:event_tbMatMouseClicked

    private void btConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btConfirmarActionPerformed
        // TODO add your handling code here:
        int qtdErro = 0;
        PreparedStatement cnd1;
        String dataRetirada = "";
        String codPolicial = "";
        String codDestino = "";
        String codRetirada = "";
        try {
            /* cnd1 = con.conecta().prepareStatement("select cod_policial from usuario");
             ResultSet rs1;
             rs1 = cnd1.executeQuery();
               
             rs1.next();
             codPolicial = rs1.getString("cod_policial");
               
             */
          //  CadBiometria cad = new CadBiometria(this, rootPaneCheckingEnabled);
            
            VerificarBio cad = new VerificarBio();
            codPolicial = cad.codigo;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "" + e);
            qtdErro++;
        }

        String codigo = (String) jComboBox1.getSelectedItem();
        if (!codigo.equals("Novo")) {
            codDestino = String.valueOf(codigo.charAt(0));

        } else {
            PreparedStatement cnd2;
            try {
                cnd2 = con.conecta().prepareStatement("select COUNT(cod_destino) as maximo from destino_material");
                ResultSet rs2;
                rs2 = cnd2.executeQuery();

                int qtd = 0;
                while (rs2.next()) {
                    qtd = Integer.parseInt(rs2.getString("maximo"));

                }
                codDestino = "" + (qtd + 1);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao procurar quantidade de destinos" + e);
                qtdErro++;
            }
            if ((etNomeDestino.getText().toString().equals("")) && (etLocalDestino.getText().toString().equals(""))) {
                codDestino = "";
            } else {
                try {

                    PreparedStatement cnd7;

                    String sql = "INSERT INTO destino_material (nome_destino, local_destino)"
                            + "VALUES(?,?)";
                    cnd7 = con.conecta().prepareStatement(sql);

                    cnd7.setString(1, etNomeDestino.getText().toString());
                    cnd7.setString(2, etLocalDestino.getText().toString());

                    cnd7.executeUpdate();
                    JOptionPane.showMessageDialog(null, "NOVO DESTINO CADASTRADO COM SUCESSO");
                } catch (Exception erro) {
                    JOptionPane.showMessageDialog(null, "Erro ao inserir na tabela destino_material" + erro);
                    qtdErro++;
                }
            }

        }

        if (!(codDestino.equals(""))) {
            java.util.Date data1 = new java.util.Date();
            java.util.Date data2 = new java.util.Date();
            DateFormat formato1 = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat formato2 = new SimpleDateFormat("HH:mm:ss");
            dataRetirada = formato1.format(data1) + " as " + formato2.format(data2);
            //JOptionPane.showMessageDialog(null, Integer.parseInt(codDestino.toString()));
            try {

                PreparedStatement cnd5;

                String sql1 = "INSERT INTO retirada_material(destino_material_cod_destino,"
                        + "usuario_cod_policial,"
                        + "data_retirada)"
                        + " VALUES (?,?,?)";
                cnd5 = con.conecta().prepareStatement(sql1);

                cnd5.setString(1, codDestino.toString());
                cnd5.setString(2, codPolicial.toString());
                cnd5.setString(3, dataRetirada);

                cnd5.executeUpdate();

    //+ " VALUES ('"+Integer.parseInt(codDestino.toString())+"',"
                //+ "'"+Integer.parseInt(codPolicial.toString())+"',"
                //+ "'"+dataRetirada+"')";
                // con.statement.executeQuery(sql1);
            } catch (NumberFormatException | SQLException | NullPointerException erro) {
                JOptionPane.showMessageDialog(null, "Erro ao inserir na tabela retirada_material '" + Integer.parseInt(codDestino.toString()) + "',"
                        + "'" + Integer.parseInt(codPolicial.toString()) + "',"
                        + "'" + dataRetirada + "Erro:" + erro);
                qtdErro++;
            }

            PreparedStatement cnd3;
            try {
                cnd3 = con.conecta().prepareStatement("select MAX(cod_retirada) as codRetirada from retirada_material");
                ResultSet rs3;
                rs3 = cnd3.executeQuery();
                rs3.next();
                codRetirada = rs3.getString("codRetirada");

            } catch (Exception erro) {
                JOptionPane.showMessageDialog(null, "Erro ao procurar codigo da retirada" + erro);
                qtdErro++;
            }

            for (int i = 0; i < jTable2.getRowCount(); i++) {
                try {

                    PreparedStatement cnd6;

                    String sql3 = "INSERT INTO retirada_material_has_material (retirada_material_cod_retirada,"
                            + "material_cod_material,"
                            + "qtde_Retirada)"
                            + " VALUES (?,?,?)";

                    cnd6 = con.conecta().prepareStatement(sql3);
                    cnd6.setString(1, codRetirada);
                    cnd6.setString(2, jTable2.getValueAt(i, 0).toString());
                    cnd6.setString(3, jTable2.getValueAt(i, 3).toString());
                    cnd6.executeUpdate();
                } catch (Exception erro) {
                    JOptionPane.showMessageDialog(null, "Erro ao inserir na tabela retirada_material_has_material" + erro);
                    qtdErro++;
                }
            }
            if (qtdErro == 0) {
                String nome = "";
                PreparedStatement cnd10;

                try {
                    cnd10 = con.conecta().prepareStatement("SELECT nome_policial FROM usuario WHERE cod_policial = " + codPolicial);
                    ResultSet rs;
                    rs = cnd10.executeQuery();
                    rs.next();
                    nome = rs.getString("nome_policial");
                }catch(Exception e){}
                JOptionPane.showMessageDialog(null, nome+"\nRETIRADA CADASTRADA COM SUCESSO");
                qtdCadastrados++;
                dispose();
                new TelaDistribuicaoMaterial().setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(null, "NOME E LOCAL DO DESTINO DEVEM SER INSERIDOS");
        }

    }//GEN-LAST:event_btConfirmarActionPerformed

    private void etLocalDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_etLocalDestinoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_etLocalDestinoActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        String codigo = (String) jComboBox1.getSelectedItem();
        if (!codigo.equals("Novo")) {
            PreparedStatement cnd3;
            try {
                cnd3 = con.conecta().prepareStatement("select * from destino_material where cod_destino = "+codigo.charAt(0));
                ResultSet rs3;
                rs3 = cnd3.executeQuery();

                rs3.next();
                etNomeDestino.setText(rs3.getString("nome_destino"));
                etNomeDestino.setEditable(false);
                etLocalDestino.setText(rs3.getString("local_destino"));
                etLocalDestino.setEditable(false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "" + e);
            }
        } else {
            etNomeDestino.setText("");
            etLocalDestino.setText("");
            etNomeDestino.setEditable(true);
            etLocalDestino.setEditable(true);
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        if ((jTable2.getRowCount() != 0) && (qtdCadastrados == 0)) {
            for (int i = 0; i < jTable2.getRowCount(); i++) {
                refazerAcoes(Integer.parseInt(jTable2.getValueAt(i, 3).toString()), jTable2.getValueAt(i, 0).toString(), jTable2.getValueAt(i, 1).toString());
            }
        }
    }//GEN-LAST:event_formWindowClosing

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTable2MouseClicked

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
            java.util.logging.Logger.getLogger(TelaDistribuicaoMaterial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaDistribuicaoMaterial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaDistribuicaoMaterial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaDistribuicaoMaterial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaDistribuicaoMaterial().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btApagar;
    private javax.swing.JButton btConfirmar;
    private javax.swing.JButton btIncluir;
    private javax.swing.JTextField etLocalDestino;
    private javax.swing.JTextField etNomeDestino;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel qtdMaterial;
    private javax.swing.JTable tbMat;
    // End of variables declaration//GEN-END:variables
}
