import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPHandler implements Runnable {
    private DatagramSocket socket;

    //    Creates socket, so the UDP connection is available for clients on the given port number.
    public UDPHandler(int port) {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    //    Clients UDP communication detection (used in CCS class on object of this class to create one thread).
    @Override
    public void run() {
        byte[] bufferReceive = new byte[32];
        DatagramPacket packetReceive = new DatagramPacket(bufferReceive, bufferReceive.length);

        while (!socket.isClosed()) {
            try {
//                Receiving message.
                socket.receive(packetReceive);
                if (new String(packetReceive.getData(), 0, packetReceive.getLength())
                        .startsWith("CCS DISCOVER")) {

//                    Sending message.
                    String messageToSend = "CCS FOUND";
                    byte[] bufferSend = messageToSend.getBytes();

                    DatagramPacket packetSend = new DatagramPacket(bufferSend,
                            bufferSend.length,
                            packetReceive.getAddress(),
                            packetReceive.getPort());
                    socket.send(packetSend);
                }
            } catch (IOException e) {
                System.out.printf("Error duiring server startup - %s\n", e.getMessage());
            }
        }
    }
}
