package model;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class PeerB {
    public static void main(String[] args) {
        // Hilo para recibir información
        new Thread(() -> {
            try {
                DatagramSocket socket = new DatagramSocket(5001);  // Escucha en el puerto 5001
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);

                System.out.println("PeerB: Waiting for messages..." + "\n");
                while (true) {
                    socket.receive(packet);  // Recibe mensajes continuamente
                    String receivedMsg = new String(packet.getData(), 0, packet.getLength()).trim();
                    System.out.println("PeerA message: " + receivedMsg + "\n");
                }
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Hilo para enviar información desde consola
        new Thread(() -> {
            try (Scanner scanner = new Scanner(System.in)) {
                DatagramSocket socket = new DatagramSocket();
                InetAddress ipAddress = InetAddress.getByName("127.0.0.1");  // Dirección de PeerA

                while (true) {
                    String messageToSend = scanner.nextLine();  
                    

                    DatagramPacket packet = new DatagramPacket(messageToSend.getBytes(), messageToSend.length(), ipAddress, 5000);  // Enviar a PeerA en el puerto 5000
                    socket.send(packet);
                    System.out.println("Message sent" + "\n");
                }
            } catch (SocketException | UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
