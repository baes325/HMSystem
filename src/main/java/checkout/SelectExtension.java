/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package checkout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Suvier
 */
public class SelectExtension extends javax.swing.JFrame {

    /**
     * Creates new form SelectExtension
     */
    public SelectExtension() {
        initComponents();
    }
    
    public static int roomCount = 1;
    public static int extensionDays = 0;
    public static int days = 5;
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ExtensionDay = new javax.swing.JLabel();
        ExtensionDate = new javax.swing.JTextField();
        OKButton = new javax.swing.JButton();
        Date = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ExtensionDay.setText("연장 일수를 입력해주세요. :");

        ExtensionDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExtensionDateActionPerformed(evt);
            }
        });

        OKButton.setText("확인");
        OKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKButtonActionPerformed(evt);
            }
        });

        Date.setText("일");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(ExtensionDay)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ExtensionDate, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Date))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(OKButton)))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ExtensionDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ExtensionDay)
                    .addComponent(Date))
                .addGap(18, 18, 18)
                .addComponent(OKButton)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    extensionDays = Integer.parseInt(ExtensionDate.getText());
                }
                catch (NumberFormatException ex) {
                    javax.swing.JOptionPane.showMessageDialog(null, "유효한 숫자를 입력해주세요.");
                }
                if (roomCount > 0) {
                    HaveRoom haveRoom = new HaveRoom();
                    haveRoom.setVisible(true);
                    roomCount--;
                    days += extensionDays;
                    SelectExtension.this.dispose();
                }
                else {
                    NoRoom noRoom = new NoRoom();
                    noRoom.setVisible(true);
                    SelectExtension.this.dispose();
                }
            }
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ExtensionDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExtensionDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ExtensionDateActionPerformed

    private void OKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OKButtonActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Date;
    private javax.swing.JTextField ExtensionDate;
    private javax.swing.JLabel ExtensionDay;
    private javax.swing.JButton OKButton;
    // End of variables declaration//GEN-END:variables
}