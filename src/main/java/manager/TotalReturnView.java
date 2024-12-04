package manager;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */



import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.nio.file.*;

/**
 *
 * @author 82106_dr0g
 */
public class TotalReturnView extends javax.swing.JFrame {

    /**
     * Creates new form Testing
     */
    private DefaultTableModel model;
    
  private static final String FILE_PATH = "data" + File.separator + "EmployeeList.txt";

    
    public TotalReturnView() {
        initComponents();
        model = (DefaultTableModel) jTable2.getModel();
         loadEmployeeData();
    }
    
     private void loadEmployeeData() {
        // 파일이 존재하는 경우 파일의 데이터를 테이블에 불러옴
        File file = new File(FILE_PATH);
    if (file.exists()) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");  // ','로 구분된 데이터
                model.addRow(data);  // 테이블에 데이터 추가
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "파일 로드 실패: " + e.getMessage());
        }
    } else {
        // 파일이 없으면 새로 만들기
        try {
            file.createNewFile();  // 파일 생성
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "파일 생성 실패: " + e.getMessage());
        }
        }
    }

      private void saveEmployeeData() {
        // 테이블의 데이터를 파일에 저장
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
        for (int i = 0; i < model.getRowCount(); i++) {
            String name = (String) model.getValueAt(i, 0);
            String id = (String) model.getValueAt(i, 1);
            Boolean isAdmin = (Boolean) model.getValueAt(i, 2);
writer.write(name + "," + id + "," + (isAdmin ? "true" : "false"));
writer.newLine();
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "파일 저장 실패: " + e.getMessage());
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        Btn_Delete = new javax.swing.JButton();
        Btn_Fix = new javax.swing.JButton();
        Btn_Add = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("직원 관리 시스템");

        Btn_Delete.setText("삭제");
        Btn_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_DeleteActionPerformed(evt);
            }
        });

        Btn_Fix.setText("수정");
        Btn_Fix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_FixActionPerformed(evt);
            }
        });

        Btn_Add.setText("추가");
        Btn_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_AddActionPerformed(evt);
            }
        });

        jScrollPane3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null,  new Boolean(false)},
                {null, null, null}
            },
            new String [] {
                "이름", "아이디", "관리자"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(Btn_Fix)
                        .addGap(18, 18, 18)
                        .addComponent(Btn_Delete)
                        .addGap(18, 18, 18)
                        .addComponent(Btn_Add)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Btn_Delete)
                    .addComponent(Btn_Fix)
                    .addComponent(Btn_Add))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   
    
    private void Btn_FixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_FixActionPerformed
        // TODO add your handling code here:
    //수정창으로 넘어가게 하기
     int selectedRow = jTable2.getSelectedRow();
        if (selectedRow != -1) {
            // 수정 창을 띄운다.
            FixFrame fixFrame = new FixFrame(model, selectedRow);
            fixFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "수정할 행을 선택해주세요.");
        }
    }//GEN-LAST:event_Btn_FixActionPerformed

    private void Btn_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_DeleteActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow != -1) {
            model.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "삭제할 행을 선택해주세요.");
        }
    }//GEN-LAST:event_Btn_DeleteActionPerformed

    private void Btn_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_AddActionPerformed
        // TODO add your handling code here:
        //추가창으로 넘어가게 하기
         AddFrame addFrame = new AddFrame(model);
        addFrame.setVisible(true);
    }//GEN-LAST:event_Btn_AddActionPerformed

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
            java.util.logging.Logger.getLogger(TotalReturnView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TotalReturnView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TotalReturnView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TotalReturnView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new TotalReturnView().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Add;
    private javax.swing.JButton Btn_Delete;
    private javax.swing.JButton Btn_Fix;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
