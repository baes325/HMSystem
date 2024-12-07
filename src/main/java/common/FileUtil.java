/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Suvier
 */
public class FileUtil {
    private static final String FILE_PATH = "clientInfo1.txt";

    public static List<ClientInfo> readClientInfo() {
        List<ClientInfo> clientList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("/");
                if (data.length == 4) { // 데이터 순서: 이름/객실 번호/체크인 날짜/체크아웃 날짜
                    clientList.add(new ClientInfo(data[0], data[1], data[2], data[3]));
                }
            }
        } catch (IOException e) {
        }
        return clientList;
    }

    public static void writeClientInfo(List<ClientInfo> clientList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (ClientInfo client : clientList) {
                writer.write(client.getName() + "/" + client.getRoomNumber() + "/" +
                        client.getCheckinDate() + "/" + client.getCheckoutDate() + "\n");
            }
        } catch (IOException e) {
        }
    }
}
