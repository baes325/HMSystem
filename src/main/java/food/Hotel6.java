package food;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Hotel6 extends javax.swing.JFrame {
    public Hotel6(String menuName, String paymentMethod, String location, String paymentType) {
        // GUI 초기화
        initCustomComponents(menuName, paymentMethod, location, paymentType);
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
private void initCustomComponents(String menuName, String paymentMethod, String location, String paymentType) {
        setTitle("Hotel6 - 결제 확인");
        setSize(300, 200);
        setLayout(new GridLayout(5, 1));
        setLocationRelativeTo(null);

        // 메뉴 가격 읽기
        double price = readPriceFromFile(menuName);

        // 정보 출력
        JLabel menuLabel = new JLabel("메뉴명: " + menuName);
        JLabel priceLabel = new JLabel(price > 0 ? "가격: " + price + "원" : "메뉴 정보를 찾을 수 없습니다.");
        JLabel paymentMethodLabel = new JLabel("결제 방식: " + paymentMethod);
        JLabel locationLabel = new JLabel("장소: " + location);
        JLabel paymentTypeLabel = new JLabel("결제 유형: " + paymentType);

        // 레이아웃에 컴포넌트 추가
        add(menuLabel);
        add(priceLabel);
        add(paymentMethodLabel);
        add(locationLabel);
        add(paymentTypeLabel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private double readPriceFromFile(String menuName) {
        double price = -1;
        try (BufferedReader br = new BufferedReader(new FileReader("menuData.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].trim().equalsIgnoreCase(menuName)) {
                    price = Double.parseDouble(parts[1].trim());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return price;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
