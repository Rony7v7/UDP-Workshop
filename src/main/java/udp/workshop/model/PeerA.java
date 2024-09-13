package udp.workshop.model;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.*;

public class PeerA extends Application {

    private DatagramSocket socket;
    private InetAddress ipAddress;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Crear la GUI de PeerA
        TextArea chatArea = new TextArea();
        chatArea.setEditable(false);
        TextField messageField = new TextField();
        Button sendButton = new Button("Send");

        VBox vbox = new VBox(chatArea, messageField, sendButton);
        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setTitle("Chat - PeerA");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Configurar la conexión UDP
        socket = new DatagramSocket(5000);  // PeerA escucha en el puerto 5000
        ipAddress = InetAddress.getByName("127.0.0.1");  // Dirección de PeerB

        // Hilo para recibir mensajes
        new Thread(() -> {
            try {
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                while (true) {
                    socket.receive(packet);  // Recibir mensaje
                    String receivedMsg = new String(packet.getData(), 0, packet.getLength()).trim();

                    // Mostrar el mensaje en el área de texto (chatArea)
                    Platform.runLater(() -> chatArea.appendText("PeerB: " + receivedMsg + "\n"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Acción del botón para enviar mensajes
        sendButton.setOnAction(e -> {
            String messageToSend = messageField.getText();
            messageField.clear();  // Limpiar el campo de texto
            sendMessage(messageToSend);
            chatArea.appendText("You: " + messageToSend + "\n");
        });
    }

    // Método para enviar un mensaje
    private void sendMessage(String messageToSend) {
        try {
            DatagramPacket packet = new DatagramPacket(
                messageToSend.getBytes(),
                messageToSend.length(),
                ipAddress,
                5001  // Enviar a PeerB en el puerto 5001
            );
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
