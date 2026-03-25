import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CCS {
    public static void main(String[] args) {
        int port;

//        Checks if args from main method are all right.
        if (args.length != 1) {
            System.out.println("Error.\nUsage: java-jar CCS.jar <port>");
            return;
        }

//        String -> int.
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Error.\nInvalid port");
            return;
        }

//        Creates objects of: Statistics, UDPHandler, TCPHandler.
        Statistics statistics = new Statistics();
        UDPHandler udp = new UDPHandler(port);
        TCPHandler tcp = new TCPHandler(port, statistics);

//        Starts threads for objects of: Statistics, UDPHandler, TCPHandler.
        new Thread(udp).start();
        new Thread(tcp).start();
//        new Thread(statistics).start();

//        Scheduling object of Statistics, so its run method is executed every 10 seconds.
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(statistics, 10, 10, TimeUnit.SECONDS);
    }
}
