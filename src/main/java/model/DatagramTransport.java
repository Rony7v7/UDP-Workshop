package model;

public interface DatagramTransport {
    void sendDatagram(String msj);
    void sendDatagram(String msj, String ipDest, int portDest);
    String receiveDatagram();
}
