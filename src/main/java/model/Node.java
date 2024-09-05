package model;

import java.net.SocketException;
import java.net.UnknownHostException;

import util.UDPConnection;

public class Node extends UDPConnection {
    private String ip;
    private int port;

    public Node(String ip, int port) throws UnknownHostException, SocketException {
        super(ip, port);
        this.ip = ip;
        this.port = port;
    }

    public void sendDatagram(String msj) {
        super.sendDatagram(msj);
    }

    public void sendDatagram(String msj, String ipDest, int portDest) {
        super.sendDatagram(msj, ipDest, portDest);
    }

    public String receiveDatagram() {
        return super.receiveDatagram();
    }

    public static void main(String[] args) {
        
    }
}
