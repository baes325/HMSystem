/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hms.checkout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Suvier
 */
public class CheckoutInfo extends javax.swing.JFrame {

    /**
     * Creates new form CheckoutInfo
     */
    public CheckoutInfo() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        CheckoutInfo = new javax.swing.JLabel();
        CheckoutTime = new javax.swing.JLabel();
        CheckoutTimeInput = new javax.swing.JTextField();
        ClientFeedback = new javax.swing.JLabel();
        OKButton = new javax.swing.JButton();
        JScrollPane1 = new javax.swing.JScrollPane();
        FeedbackInput = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        CheckoutInfo.setText("체크아웃 정보");

        CheckoutTime.setText("체크아웃 시간 :");

        CheckoutTimeInput.setText("체크아웃 시간");

        ClientFeedback.setText("고객 피드백 :");

        OKButton.setText("확인");

        FeedbackInput.setColumns(20);
        FeedbackInput.setRows(5);
        JScrollPane1.setViewportView(FeedbackInput);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ClientFeedback)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(CheckoutTime)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CheckoutTimeInput)))
                .addContainerGap(53, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(CheckoutInfo)
                        .addGap(161, 161, 161))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(OKButton)
                        .addGap(153, 153, 153))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(CheckoutInfo)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckoutTime)
                    .addComponent(CheckoutTimeInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ClientFeedback)
                    .addComponent(JScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(OKButton)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NotTimeOver notTimeOver = new NotTimeOver();
                notTimeOver.setVisible(true);
            }
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CheckoutInfo;
    private javax.swing.JLabel CheckoutTime;
    private javax.swing.JTextField CheckoutTimeInput;
    private javax.swing.JLabel ClientFeedback;
    private javax.swing.JTextArea FeedbackInput;
    private javax.swing.JScrollPane JScrollPane1;
    private javax.swing.JButton OKButton;
    // End of variables declaration//GEN-END:variables
}
