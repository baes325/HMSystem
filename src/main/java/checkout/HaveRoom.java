/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package checkout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Suvier
 */
public class HaveRoom extends javax.swing.JFrame {

    /**
     * Creates new form HaveRoom
     */
    public HaveRoom() {
        initComponents();
    }
    
    public class FileRead {
        public static class ClientInfo {
            public int checkinDate;
            public int checkoutDate;
            public String roomType;
            
            public int ciYear, ciMonth, ciDay;
            public int coYear, coMonth, coDay;
            
            public ClientInfo(int checkinDate, int checkoutDate, String roomType) {
                this.checkinDate = checkinDate;
                this.checkoutDate = checkoutDate;
                this.roomType = roomType;
                
                convertDate(checkinDate, "checkin");
                convertDate(checkoutDate, "checkout");
            }
            
            private void convertDate(int date, String type) {
                int year = date / 10000;
                int month = (date / 100) % 100;
                int day = date % 100;
                
                if (type.equals("checkin")) {
                    this.ciYear = year;
                    this.ciMonth = month;
                    this.ciDay = day;
                }
                
                else if (type.equals("checkout")) {
                    this.coYear = year;
                    this.coMonth = month;
                    this.coDay = day;
                }
            }
            
            public void extendCheckoutDate(int extensionDays) {
                this.coDay = extensionDays;
                
                while (this.coDay >30) {
                    this.coDay -= 30;
                    this.coMonth += 1;
                }
                while (this.coMonth > 12) {
                    this.coMonth -= 12;
                    this.coYear += 1;
                }
                
                this.checkoutDate = (this.coYear * 10000) + (this.coMonth * 100) + this.coDay;
            }
        }
        
        public static List<ClientInfo> readClientData(String filepath) {
            List<ClientInfo> clientDataList = new ArrayList<>();
            
            try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
                String line;
                
                while ((line = reader.readLine()) != null) {
                    String[] rowData = line.split("/");
                    
                    if (rowData.length >= 10) {
                        int checkinDate = parseToInt(rowData[6]);
                        int checkoutDate = parseToInt(rowData[5]);
                        String roomType = rowData[8];
                        
                        clientDataList.add(new ClientInfo(checkoutDate, checkinDate, roomType));
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            
            return clientDataList;
        }
        
        public static void updateCheckoutDate(String filePath, String targetClientName, int extensionDays) {
            File inputFile = new File(filePath);
            File tempFile = new File(filePath + "_temp");
            
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                 String line;
                 
                 while ((line = reader.readLine()) != null) {
                     String[] rowData = line.split("/");
                     if (rowData[0].equals(targetClientName)) {
                         int currentCheckoutDate = parseToInt(rowData[5]);
                         int newCheckoutDate = addDaysToIntDate(currentCheckoutDate, extensionDays);
                         rowData[5] = Integer.toString(newCheckoutDate);
                         line = String.join("/", rowData);
                     }
                     writer.write(line);
                     writer.newLine();
                 }
             }
            catch (IOException e) {
                e.printStackTrace;
            }
            
            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);
            }
        }
        
        private static int parseToInt(String str) {
            try {
                return Integer.parseInt(str.trim());
            }
            catch (NumberFormatException e) {
                return 0;
            }
        }
        
        private static int addDaysToIntDate(int date, int daysToAdd) {
            int year = date / 10000;
            int month = (date / 100) % 100;
            int day = date % 100;
            
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.set(year, month - 1, day);
            calendar.add(java.util.Calendar.DAY_OF_MONTH, daysToAdd);
            
            year = calendar.get(java.util.Calendar.YEAR);
            month = calendar.get(java.util.Calendar.MONTH);
            day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
            
            return year * 10000 + month * 100 + day;
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

        ExtensionComplete = new javax.swing.JLabel();
        OKButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ExtensionComplete.setText("연장이 완료되었습니다.");

        OKButton.setText("확인");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(ExtensionComplete))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(OKButton)))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(ExtensionComplete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(OKButton)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        OKButton.addActionListener(e -> {
            FileRead.updateCheckoutDate("clientInfo1.txt", selectedClient, newCheckoutDate);

            JOptionPane.showMessage(this, "체크아웃 날짜가 성공적으로 연장되었습니다.");
            this.dispose();
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ExtensionComplete;
    private javax.swing.JButton OKButton;
    // End of variables declaration//GEN-END:variables
}
