package util;

import model.DatagramTransport;
import java.io.IOException;
import java.net.*;

public class UDPConnection extends Thread implements DatagramTransport {

    private static UDPConnection instance;
    
    private DatagramSocket socket;
    private int port;
    private InetAddress ip;


    protected UDPConnection (String ip, int port) throws UnknownHostException, SocketException {
        this.port = port;
        this.ip = InetAddress.getByName(ip);
        this.socket = new DatagramSocket(port);
        run();
    }

    public static UDPConnection getInstance(String ip, int port) throws UnknownHostException, SocketException {
        if (instance == null){
            instance = new UDPConnection(ip, port);
        }
        return instance;
    }

    @Override
    public void sendDatagram (String msj) {
        try {
            DatagramPacket packet = new DatagramPacket(msj.getBytes(), msj.length(), this.ip, this.port);
            this.socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendDatagram(String msj, String ipDest, int portDest){
        // Envio de Infromación
        try {
            //                                         el mensaje     | length             | ip dest             | puerto destino
            DatagramPacket packet = new DatagramPacket(msj.getBytes(), msj.length(), InetAddress.getByName(ipDest), portDest);
            // envia la información
            socket.send(packet);

        } catch (SocketException | UnknownHostException e) {

        } catch (IOException e) {

        }
    } 

    @Override
    public void run(){
        String msg = receiveDatagram();
        System.out.println(msg);
    }

    @Override
    public String receiveDatagram() {
        try {
            DatagramPacket packet = new DatagramPacket(new byte[24], 24);
            this.socket.receive(packet);
            return new String(packet.getData(), 0, packet.getLength());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return the socket
     */
    public DatagramSocket getSocket() {
        return socket;
    }

    /**
     * @param socket the socket to set
     */
    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the ip
     */
    public InetAddress getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

}