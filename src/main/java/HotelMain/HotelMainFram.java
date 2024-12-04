/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package HotelMain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hoon
 */

public class HotelMainFram extends javax.swing.JFrame {

    /**
     * Creates new form HotelMainFram
     */
    public HotelMainFram() {
        initComponents();
        
        DefaultTableModel reservationTableModel = (DefaultTableModel) clientTable.getModel();

        // 텍스트 파일에서 데이터 읽기
        loadTableData(reservationTableModel);

        // 파일 변경 감지 추가
        addFileWatchService(reservationTableModel);
        
         DeleteButt.addActionListener(e -> {
        int selectedRow = clientTable.getSelectedRow(); // 선택된 행 인덱스 가져오기
    if (selectedRow != -1) { // 행이 선택되었는지 확인
        DefaultTableModel tableModel = (DefaultTableModel) clientTable.getModel();
        String clientName = (String) tableModel.getValueAt(selectedRow, 0); // 고객명 가져오기
        String reservationNumber = (String) tableModel.getValueAt(selectedRow, 1); // 예약번호 가져오기

        tableModel.removeRow(selectedRow); // 테이블에서 해당 행 삭제

        // 파일에서 해당 데이터 삭제
        try {
            String paths = System.getProperty("user.dir");
            File reservationFile = new File(paths + "/clientInfo1.txt");
            File tempFile = new File(paths + "/clientInfo1_temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(reservationFile));
            String line;
            boolean isDeleted = false;

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                while ((line = reader.readLine()) != null) {
                    String[] rowData = line.split("/");
                    if (rowData[0].equals(clientName) && rowData[1].equals(reservationNumber)) {
                        isDeleted = true; // 삭제된 데이터 찾기
                        continue; // 해당 데이터는 건너뜀
                    }
                    writer.write(line); // 나머지 데이터는 임시 파일에 저장
                    writer.newLine();
                }
            }
            reader.close();

            // 원본 파일 삭제하고 임시 파일로 교체
            if (reservationFile.delete() && tempFile.renameTo(reservationFile)) {
                if (isDeleted) {
                    JOptionPane.showMessageDialog(this, "선택한 데이터가 삭제되었습니다.");
                } else {
                    JOptionPane.showMessageDialog(this, "데이터 삭제 중 문제가 발생했습니다.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "파일 업데이트 중 문제가 발생했습니다.");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "파일 처리 중 오류가 발생했습니다: " + ex.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(this, "삭제할 데이터를 선택하세요.");
    }
    });
        
    CheckoutButt.addActionListener(e -> {
    int selectedRow = clientTable.getSelectedRow(); // 선택된 행 인덱스 가져오기
    if (selectedRow != -1) { // 행이 선택되었는지 확인
        DefaultTableModel tableModel = (DefaultTableModel) clientTable.getModel();
        String checkOutStatus = (String) tableModel.getValueAt(selectedRow, 2); // 체크아웃 유무 가져오기 (O 또는 X)

        if ("O".equals(checkOutStatus)) { // 체크아웃 유무가 O일 때만 삭제 처리
            String clientName = (String) tableModel.getValueAt(selectedRow, 0); // 고객명 가져오기
            String reservationNumber = (String) tableModel.getValueAt(selectedRow, 1); // 예약번호 가져오기

            tableModel.removeRow(selectedRow); // 테이블에서 해당 행 삭제

            // 파일에서 해당 데이터 삭제
            try {
                String paths = System.getProperty("user.dir");
                File reservationFile = new File(paths + "/clientInfo1.txt");
                File tempFile = new File(paths + "/clientInfo1_temp.txt");

                BufferedReader reader = new BufferedReader(new FileReader(reservationFile));
                String line;
                boolean isDeleted = false;

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                    while ((line = reader.readLine()) != null) {
                        String[] rowData = line.split("/");
                        if (rowData[0].equals(clientName) && rowData[1].equals(reservationNumber)) {
                            isDeleted = true; // 삭제된 데이터 찾기
                            continue; // 해당 데이터는 건너뜀
                        }
                        writer.write(line); // 나머지 데이터는 임시 파일에 저장
                        writer.newLine();
                    }
                }
                reader.close();

                // 원본 파일 삭제하고 임시 파일로 교체
                if (reservationFile.delete() && tempFile.renameTo(reservationFile)) {
                    if (isDeleted) {
                        JOptionPane.showMessageDialog(this, "체크아웃이 완료되었습니다.");
                    } else {
                        JOptionPane.showMessageDialog(this, "체크아웃 처리 중 문제가 발생했습니다.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "파일 업데이트 중 문제가 발생했습니다.");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "파일 처리 중 오류가 발생했습니다: " + ex.getMessage());
            }
        } else { // 체크아웃 유무가 X일 때
            JOptionPane.showMessageDialog(this, "체크아웃이 불가능합니다. 상태가 'O'여야 합니다.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "체크아웃할 데이터를 선택하세요.");
    }
});

         
        setVisible(true);
    }

    // 텍스트 파일에서 데이터를 읽어 테이블에 추가하는 메서드
    private void loadTableData(DefaultTableModel tableModel) {
        try {
            // 절대 경로를 사용하여 파일 객체 생성
            String paths = System.getProperty("user.dir");
            File reservationFile = new File(paths + "/clientInfo1.txt");

            if (!reservationFile.exists()) {
                JOptionPane.showMessageDialog(this, "clientInfo1.txt 파일이 존재하지 않습니다.");
                return;
            }

            // 파일 읽기
            BufferedReader br = new BufferedReader(new FileReader(reservationFile));
            String line;
            while ((line = br.readLine()) != null) {
                String[] rowData = line.split("/"); // "/" 구분자로 데이터 분리
                tableModel.addRow(new Object[]{ // JTable에 데이터 추가
                    rowData[0], rowData[1], "X" // 데이터가 고객명, 예약번호, 체크인 여부로 구성됨
                });
            }
            br.close(); // 리소스 닫기
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "파일을 읽을 수 없습니다: " + e.getMessage());
        }
    }

    // 파일 변경 감지 설정 메서드
    private void addFileWatchService(DefaultTableModel tableModel) {
        new Thread(() -> {
            try {
                String paths = System.getProperty("user.dir");
                Path dirPath = Paths.get(paths);
                WatchService watchService = dirPath.getFileSystem().newWatchService();
                dirPath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                while (true) {
                    WatchKey key = watchService.take(); // 파일 변경 이벤트 대기
                    for (WatchEvent<?> event : key.pollEvents()) {
                        Path changed = (Path) event.context();
                        if (changed.getFileName().toString().equals("clientInfo1.txt")) {
                            // 파일 변경 시 테이블 데이터 갱신
                            SwingUtilities.invokeLater(() -> {
                                tableModel.setRowCount(0); // 기존 데이터 초기화
                                loadTableData(tableModel); // 새 데이터 로드
                            });
                        }
                    }
                    key.reset();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "파일 감지 중 오류가 발생했습니다: " + e.getMessage());
            }
        }).start();
        
    
    }

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ReservationButt = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        clientTable = new javax.swing.JTable();
        CheckoutButt = new javax.swing.JButton();
        ModifyButt = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        DeleteButt = new javax.swing.JButton();

        jLabel3.setText("예약 명단");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setBackground(new java.awt.Color(255, 255, 102));
        jLabel1.setText("HOTEL MANAGER SYSTEM");

        jLabel5.setText("고객명단");

        ReservationButt.setText("예약");
        ReservationButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReservationButtActionPerformed(evt);
            }
        });

        clientTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "고객명", "전화번호", "체크인 유무"
            }
        ));
        jScrollPane3.setViewportView(clientTable);

        CheckoutButt.setText("체크아웃");
        CheckoutButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckoutButtActionPerformed(evt);
            }
        });

        ModifyButt.setText("수정");
        ModifyButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifyButtActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "메뉴명", "가격"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel2.setText("식사");

        jButton4.setText("선택");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("수정");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("삭제");

        jButton7.setText("메뉴추가");

        DeleteButt.setText("삭제");
        DeleteButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 198, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(172, 172, 172))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ReservationButt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CheckoutButt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ModifyButt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4)
                            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(DeleteButt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(197, 197, 197)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(188, 188, 188)
                        .addComponent(jLabel5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(ReservationButt)
                        .addGap(18, 18, 18)
                        .addComponent(ModifyButt)
                        .addGap(22, 22, 22)
                        .addComponent(DeleteButt)
                        .addGap(18, 18, 18)
                        .addComponent(CheckoutButt))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jButton4)
                        .addGap(38, 38, 38)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton7)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ReservationButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReservationButtActionPerformed
        // TODO add your handling code here:
        Reservation_Form rf = new Reservation_Form();
        rf.setVisible(true);
        rf.setSize(1000, 600);
    }//GEN-LAST:event_ReservationButtActionPerformed

    private void CheckoutButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckoutButtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CheckoutButtActionPerformed

    private void ModifyButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifyButtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ModifyButtActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void DeleteButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DeleteButtActionPerformed

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        new HotelMainFram();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CheckoutButt;
    private javax.swing.JButton DeleteButt;
    private javax.swing.JButton ModifyButt;
    private javax.swing.JButton ReservationButt;
    private javax.swing.JTable clientTable;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
