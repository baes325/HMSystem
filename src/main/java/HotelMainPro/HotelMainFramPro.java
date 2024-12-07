/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package HotelMainPro;

import HotelMain.Reservation_Form;
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

public class HotelMainFramPro extends javax.swing.JFrame {

    /**
     * Creates new form HotelMainFram
     */
    public HotelMainFramPro() {
        initComponents();
        
        DefaultTableModel reservationTableModel = (DefaultTableModel) clientTablePro.getModel();
        DefaultTableModel foodTableModel = (DefaultTableModel) foodTable.getModel(); // 음식 테이블 모델

        // 텍스트 파일에서 데이터 읽기
        loadTableData(reservationTableModel);
        loadFoodTableData(foodTableModel); // 음식 데이터 파일

        // 파일 변경 감지 추가
        addFileWatchService(reservationTableModel);
        addFoodFileWatchService(foodTableModel);
        
         DeleteButt.addActionListener(e -> {
        int selectedRow = clientTablePro.getSelectedRow(); // 선택된 행 인덱스 가져오기
    if (selectedRow != -1) { // 행이 선택되었는지 확인
        DefaultTableModel tableModel = (DefaultTableModel) clientTablePro.getModel();
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
                } 
            } 
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "파일 처리 중 오류가 발생했습니다: " + ex.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(this, "삭제할 데이터를 선택하세요.");
    }
    });
        
    CheckoutButt.addActionListener(e -> {
    int selectedRow = clientTablePro.getSelectedRow(); // 선택된 행 인덱스 가져오기
    if (selectedRow != -1) { // 행이 선택되었는지 확인
        DefaultTableModel tableModel = (DefaultTableModel) clientTablePro.getModel();
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
                    } 
                } 
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "파일 처리 중 오류가 발생했습니다: " + ex.getMessage());
            }
        } else { // 체크아웃 유무가 X일 때
            Extension extension = new Extension();
            extension.setVisible(true);
            extension.dispose();
            JOptionPane.showMessageDialog(this, "체크아웃이 불가능합니다. 상태가 'O'여야 합니다.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "체크아웃할 데이터를 선택하세요.");
    }
});
    
    ModifyButt.addActionListener(e -> {
    int selectedRow = clientTablePro.getSelectedRow();
    if (selectedRow != -1) {
        // 선택된 행의 데이터 가져오기
        String name = clientTablePro.getValueAt(selectedRow, 0).toString();
        String tel = clientTablePro.getValueAt(selectedRow, 1).toString();
        String room = clientTablePro.getValueAt(selectedRow, 2).toString();
        String clientNum = clientTablePro.getValueAt(selectedRow, 3).toString();
        String cardNum = clientTablePro.getValueAt(selectedRow, 4).toString();
        String payday = clientTablePro.getValueAt(selectedRow, 5).toString();
        String checkin = clientTablePro.getValueAt(selectedRow, 6).toString();
        String payWay = clientTablePro.getValueAt(selectedRow, 7).toString();
        String roomType = clientTablePro.getValueAt(selectedRow, 8).toString();
        String checkCheck = clientTablePro.getValueAt(selectedRow, 9).toString();

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
    
    private void loadFoodTableData(DefaultTableModel tableModel) {
        try {
            // 절대 경로를 사용하여 파일 객체 생성
            String paths = System.getProperty("user.dir");
            File FoodFile = new File(paths + "/menuData.txt");

            if (!FoodFile.exists()) {
                JOptionPane.showMessageDialog(this, "menuData.txt 파일이 존재하지 않습니다.");
                return;
            }
            
            
            
            // 파일 읽기
            BufferedReader br = new BufferedReader(new FileReader(FoodFile));
            String line;
            while ((line = br.readLine()) != null) {
                String[] rowData = line.split(","); // "/" 구분자로 데이터 분리
                tableModel.addRow(new Object[]{
        rowData[0], // 메뉴 번호
        rowData[1], // 메뉴명
        rowData[2], // 메뉴 가격   
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
    
    // 파일 변경 감지 설정 메서드
    private void addFoodFileWatchService(DefaultTableModel tableModel) {
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
                        if (changed.getFileName().toString().equals("menuData.txt")) {
                            // 파일 변경 시 테이블 데이터 갱신
                            SwingUtilities.invokeLater(() -> {
                                tableModel.setRowCount(0); // 기존 데이터 초기화
                                loadFoodTableData(tableModel); // 새 데이터 로드
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
        clientTablePro = new javax.swing.JTable();
        CheckoutButt = new javax.swing.JButton();
        ModifyButt = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        foodSelect = new javax.swing.JButton();
        foodModify = new javax.swing.JButton();
        foodDelete = new javax.swing.JButton();
        foodAdd = new javax.swing.JButton();
        DeleteButt = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        foodTable = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        GainUnion = new javax.swing.JButton();

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
        jLabel1.setText("HOTEL MANAGER SYSTEM(PRO)");

        jLabel5.setText("고객명단");

        ReservationButt.setText("예약");
        ReservationButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReservationButtActionPerformed(evt);
            }
        });

        clientTablePro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "고객명", "전화번호", "객실", "손님 수", "카드번호", "결제일", "체크인일", "결제방식", "객실종류", "체크인 유무"
            }
        ));
        jScrollPane3.setViewportView(clientTablePro);

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

        foodTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "메뉴번호", "메뉴이름", "가격"
            }
        ));
        jScrollPane2.setViewportView(foodTable);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null,  new Boolean(false)}
            },
            new String [] {
                "이름", "아이디", "관리자"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTable3);

        jLabel4.setText("직원 관리 시스템");

        jButton2.setText("수정");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("삭제");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton8.setText("추가");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        GainUnion.setText("총 매출 보기");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 778, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ModifyButt, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ReservationButt, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DeleteButt, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CheckoutButt)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(foodSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton2)
                                        .addGap(96, 96, 96)
                                        .addComponent(jButton1)
                                        .addGap(66, 66, 66)
                                        .addComponent(jButton8))
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addComponent(GainUnion, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(14, 14, 14))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(366, 366, 366)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(foodAdd)
                        .addGap(32, 32, 32)
                        .addComponent(foodModify, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(foodDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(217, 217, 217)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(150, 150, 150))
            .addGroup(layout.createSequentialGroup()
                .addGap(349, 349, 349)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(foodAdd)
                            .addComponent(foodModify)
                            .addComponent(foodDelete)
                            .addComponent(foodSelect)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton1)
                            .addComponent(jButton8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(GainUnion, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        foodSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Hotel3 selectMenu = new Hotel3();
                selectMenu.setVisible(true);
            }
        });
        foodModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Hotel1 mainFrame = new Hotel1();
                Hotel4 modifyMenu = new Hotel4(mainFrame);
                modifyMenu.setVisible(true);
            }
        });
        foodDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Hotel1 mainFrame = new Hotel1();
                Hotel5 deleteMenu = new Hotel5(mainFrame);
                deleteMenu.setVisible(true);
            }
        });
        foodAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Hotel1 mainFrame = new Hotel1();
                Hotel2 addFood = new Hotel2(mainFrame);
                addFood.setVisible(true);
            }
        });

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
         int selectedRow = clientTablePro.getSelectedRow();  // 선택된 행 인덱스
    if (selectedRow != -1) {  // 유효한 선택인지 확인
        // 테이블의 열 수 확인
        int columnCount = clientTablePro.getColumnCount();  // 열 개수

        if (columnCount >= 10) {  // 10개 이상의 열이 있는지 확인
            // 테이블의 각 컬럼에서 데이터 가져오기
            String clientNum = (String) clientTablePro.getValueAt(selectedRow, 3);  // 클라이언트 번호 (예시)
            String name = (String) clientTablePro.getValueAt(selectedRow, 0);  // 이름 (예시)
            String phone = (String) clientTablePro.getValueAt(selectedRow, 1);  // 전화번호 (예시)
            String room = (String) clientTablePro.getValueAt(selectedRow, 2);  // 객실 (예시)
            String cardNumber = (String) clientTablePro.getValueAt(selectedRow, 4);  // 카드번호 (예시)
            String payday = (String) clientTablePro.getValueAt(selectedRow, 5);  // 결제일 (예시)
            String checkin = (String) clientTablePro.getValueAt(selectedRow, 6);  // 체크인 날짜 (예시)
            String payWay = (String) clientTablePro.getValueAt(selectedRow, 7);  // 결제 방식 (예시)
            String roomType = (String) clientTablePro.getValueAt(selectedRow, 8);  // 객실 종류 (예시)
            String checkCheck =(String) clientTablePro.getValueAt(selectedRow, 9); // 체크인 유무
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        //수정창으로 넘어가게 하기
        int selectedRow = foodTable.getSelectedRow();
        if (selectedRow != -1) {
            String name = (String) foodTable.getValueAt(selectedRow, 0);
            String id = (String) foodTable.getValueAt(selectedRow, 1);
            boolean Admin = (boolean) foodTable.getValueAt(selectedRow, 2);

      
        } else {
            JOptionPane.showMessageDialog(this, "수정할 행을 선택하세요.");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
  
       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
       
    }//GEN-LAST:event_jButton8ActionPerformed

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        new HotelMainFramPro();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CheckoutButt;
    private javax.swing.JButton DeleteButt;
    private javax.swing.JButton GainUnion;
    private javax.swing.JButton ModifyButt;
    private javax.swing.JButton ReservationButt;
    private javax.swing.JTable clientTablePro;
    private javax.swing.JButton foodAdd;
    private javax.swing.JButton foodDelete;
    private javax.swing.JButton foodModify;
    private javax.swing.JButton foodSelect;
    private javax.swing.JTable foodTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton8;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable3;
    // End of variables declaration//GEN-END:variables
}
