import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPHandler implements Runnable {
    private int port;
    private Statistics statistics;

    //    Assigns port number to the port field, so further creating of the socket for TCP connection with client is possible.
//    Assigns Statistics object (statistics) to the statistics field,
//    so collecting data relating to TCP clients-server(server-client) connections is stored.
    public TCPHandler(int port, Statistics statistics) {
        this.port = port;
        this.statistics = statistics;
    }

    //    Clients TCP communication detection (used in CCS class on object of this class to create one thread).
    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {

//            Creates new thread for single client as needed,
//            but reuses previously constructed threads when they are available.
            ExecutorService pool = Executors.newCachedThreadPool();

//            Listens for new client and executes its run method.
            while (true) {
                Socket clientSocket = serverSocket.accept();
                statistics.clientCounter();
                pool.execute(new ClientHandler(clientSocket, statistics));
            }
        } catch (IOException e) {
            System.out.printf("Error duiring server startup - %s\n", e.getMessage());
        }
    }
}
