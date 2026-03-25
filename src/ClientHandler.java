import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Statistics statistics;

    //    Assigns client socket to the clientSocket field.
//    Assigns Statistics object (statistics) to the statistics field,
//    so collecting data relating to TCP clients-server(server-client) connections is stored.
    public ClientHandler(Socket clientSocket, Statistics statistics) {
        this.clientSocket = clientSocket;
        this.statistics = statistics;
    }

    //    Client-server(server-client) communication.
//    Reads messages from client and delivers operations results to them.
    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String task;

            while ((task = in.readLine()) != null) {
                String[] taskArgs = task.split(" ");

//                Check taskArgs delivered by client, if they're correct does a proper action on them.
                if (taskArgs.length != 3) {
                    out.println("ERROR");
                    statistics.reportOperation("", 0, true);
                } else {
                    String oper = taskArgs[0];
                    int result = 0;

                    System.out.println(
                            ">>> " +
                                    taskArgs[1] + " " +
                                    taskArgs[0] + " " +
                                    taskArgs[2] + ":"
                    );

                    try {
                        int arg1 = Integer.parseInt(taskArgs[1]);
                        int arg2 = Integer.parseInt(taskArgs[2]);

//                        Does a proper operation on args1 and arg2.
                        switch (oper) {
                            case "ADD":
                                result = arg1 + arg2;
                                out.println(result);
                                statistics.reportOperation("ADD", result, false);
                                break;
                            case "SUB":
                                result = arg1 - arg2;
                                out.println(result);
                                statistics.reportOperation("SUB", result, false);
                                break;
                            case "MUL":
                                result = arg1 * arg2;
                                out.println(result);
                                statistics.reportOperation("MUL", result, false);
                                break;
                            case "DIV":
                                if (arg2 == 0) {
                                    out.println("ERROR");
                                    statistics.reportOperation("", 0, true);
                                    break;
                                }
                                result = arg1 / arg2;
                                out.println(result);
                                statistics.reportOperation("DIV", result, false);
                                break;
                            default:
                                out.println("ERROR");
                                statistics.reportOperation("", 0, true);
                                break;
                        }
                    } catch (Exception e) {
                        statistics.reportOperation("", 0, true);
                        out.println("ERROR");
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
