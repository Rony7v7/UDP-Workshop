package util;

import java.io.IOException;
import java.net.*;

public class UDPConnection extends Thread {

    private DatagramSocket socket;

    private static UDPConnection instance;

    private UDPConnection () {}

    public static UDPConnection getInstance(){
        if (instance == null){
            instance = new UDPConnection();
        }
        return instance;
    }

    public void setPort(int port) {
        try {
            this.socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        // Recepción
        try {
            DatagramPacket packet = new DatagramPacket(new byte[24], 24);

            System.out.println("Waiting ....");
            while (true) {  // Bucle infinito para seguir recibiendo mensajes
                this.socket.receive(packet);
                String msj = new String(packet.getData()).trim();
                System.out.println(msj);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendDatagram(String msj, String ipDest, int portDest){
        // Envio de Infromación
        new Thread(() -> {
            try {
                InetAddress ipAddress = InetAddress.getByName(ipDest);
                DatagramPacket packet = new DatagramPacket(msj.getBytes(), msj.length(), ipAddress, portDest);
                socket.send(packet);
            } catch (SocketException | UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
