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

import checkout.Extension;
import food.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                        Extension extension = new Extension();
                        extension.setVisible(true);
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
            Extension extension = new Extension();
            extension.setVisible(true);
            extension.dispose();
        }
    } else {
        JOptionPane.showMessageDialog(this, "체크아웃할 데이터를 선택하세요.");
    }
});
    
    ModifyButt.addActionListener(e -> {
    int selectedRow = clientTable.getSelectedRow();
    if (selectedRow != -1) {
        // 선택된 행의 데이터 가져오기
        String name = clientTable.getValueAt(selectedRow, 0).toString();
        String tel = clientTable.getValueAt(selectedRow, 1).toString();
        String room = clientTable.getValueAt(selectedRow, 2).toString();
        String clientNum = clientTable.getValueAt(selectedRow, 3).toString();
        String cardNum = clientTable.getValueAt(selectedRow, 4).toString();
        String payday = clientTable.getValueAt(selectedRow, 5).toString();
        String checkin = clientTable.getValueAt(selectedRow, 6).toString();
        String payWay = clientTable.getValueAt(selectedRow, 7).toString();
        String roomType = clientTable.getValueAt(selectedRow, 8).toString();
        String checkCheck = clientTable.getValueAt(selectedRow, 9).toString();

        // Reservation_Form 열기
        Reservation_Form reservationForm = new Reservation_Form();
        reservationForm.setReservationData(name, tel, room, clientNum, cardNum, payday, checkin, payWay, roomType,checkCheck);
        reservationForm.setVisible(true);
    } else {
        JOptionPane.showMessageDialog(null, "수정할 행을 선택하세요.");
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
                tableModel.addRow(new Object[]{
        rowData[0], // 고객명
        rowData[1], // 전화번호
        rowData[2], // 객실
        rowData[3], // 손님수
        rowData[4], // 카드번호
        rowData[5], // 결제일
        rowData[6], // 체크인일
        rowData[7], // 결제방식
        rowData[8],  // 객실 종류
        rowData[9] // 체크인 유무          
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
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ReservationButt = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        clientTable = new javax.swing.JTable();
        CheckoutButt = new javax.swing.JButton();
        ModifyButt = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        foodTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        foodSelect = new javax.swing.JButton();
        foodModify = new javax.swing.JButton();
        foodDelete = new javax.swing.JButton();
        foodAdd = new javax.swing.JButton();
        DeleteButt = new javax.swing.JButton();

        jLabel3.setText("예약 명단");

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

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
                "고객명", "전화번호", "객실", "손님 수", "카드번호", "결제일", "체크인일", "결제방식", "객실종류", "체크인 유무"
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

        foodTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(foodTable);

        jLabel2.setText("식사");

        foodSelect.setText("선택");
        foodSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                foodSelectActionPerformed(evt);
            }
        });

        foodModify.setText("수정");
        foodModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                foodModifyActionPerformed(evt);
            }
        });

        foodDelete.setText("삭제");

        foodAdd.setText("메뉴추가");

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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 778, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ModifyButt, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ReservationButt, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DeleteButt, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CheckoutButt))
                        .addGap(14, 14, 14))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 645, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(foodModify, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(foodDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(foodAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(foodSelect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(73, 73, 73))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(366, 366, 366)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(315, 315, 315)
                        .addComponent(jLabel2)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ReservationButt)
                        .addGap(18, 18, 18)
                        .addComponent(ModifyButt)
                        .addGap(18, 18, 18)
                        .addComponent(DeleteButt)
                        .addGap(26, 26, 26)
                        .addComponent(CheckoutButt)))
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(foodSelect)
                        .addGap(30, 30, 30)
                        .addComponent(foodModify)
                        .addGap(26, 26, 26)
                        .addComponent(foodDelete)
                        .addGap(30, 30, 30)
                        .addComponent(foodAdd))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
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
         int selectedRow = foodTable.getSelectedRow();  // 선택된 행 인덱스
    if (selectedRow != -1) {  // 유효한 선택인지 확인
        // 테이블의 열 수 확인
        int columnCount = foodTable.getColumnCount();  // 열 개수

        if (columnCount >= 10) {  // 9개 이상의 열이 있는지 확인
            // 테이블의 각 컬럼에서 데이터 가져오기
            String clientNum = (String) foodTable.getValueAt(selectedRow, 3);  // 클라이언트 번호 (예시)
            String name = (String) foodTable.getValueAt(selectedRow, 0);  // 이름 (예시)
            String phone = (String) foodTable.getValueAt(selectedRow, 1);  // 전화번호 (예시)
            String room = (String) foodTable.getValueAt(selectedRow, 2);  // 객실 (예시)
            String cardNumber = (String) foodTable.getValueAt(selectedRow, 4);  // 카드번호 (예시)
            String payday = (String) foodTable.getValueAt(selectedRow, 5);  // 결제일 (예시)
            String checkin = (String) foodTable.getValueAt(selectedRow, 6);  // 체크인 날짜 (예시)
            String payWay = (String) foodTable.getValueAt(selectedRow, 7);  // 결제 방식 (예시)
            String roomType = (String) foodTable.getValueAt(selectedRow, 8);  // 객실 종류 (예시)
            String checkCheck =(String) foodTable.getValueAt(selectedRow, 9); // 체크인 유무
            // Reservation_Form을 생성하고 수정 모드로 설정
            Reservation_Form reservationForm = new Reservation_Form();
            
            // 기존 데이터를 Reservation_Form에 전달
            reservationForm.setReservationData(name, phone, room, clientNum, cardNumber, payday, checkin, payWay, roomType,checkCheck);
            
            // 수정 모드로 표시
            reservationForm.setEditMode(true);
            
            // Reservation_Form 창 보이기
            reservationForm.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "테이블의 열이 부족합니다. 데이터를 수정할 수 없습니다.", "알림", JOptionPane.WARNING_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "수정할 항목을 선택하세요.", "알림", JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_ModifyButtActionPerformed

    private void foodSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foodSelectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_foodSelectActionPerformed

    private void foodModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foodModifyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_foodModifyActionPerformed

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
    private javax.swing.JButton foodAdd;
    private javax.swing.JButton foodDelete;
    private javax.swing.JButton foodModify;
    private javax.swing.JButton foodSelect;
    private javax.swing.JTable foodTable;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
