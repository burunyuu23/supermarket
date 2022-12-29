package ru.vsu.edu.shlyikov_d_g.main.network;

import ru.vsu.edu.shlyikov_d_g.humans.buyers.Customer;
import ru.vsu.edu.shlyikov_d_g.main.Shop;
import ru.vsu.edu.shlyikov_d_g.main.visualisation.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server {

    private List<PrintWriter> sockets = new ArrayList<>();
    private BufferedReader in;
    private PrintWriter out;
    private Shop shop = new Shop();

    public static void main(String[] args) throws IOException {
        Server server = new Server(8080);
        server.start();
    }

    private ServerSocket serverSocket;
    private final int port;
    private int playerCounter = 0;

    public Server(int port) {
        this.port = port;
    }

    private void sendAll(String msg){
        sockets.forEach(a -> a.println(msg));
    }

    private void sendAllChangeNumbers(Integer start){
        for (int i = start; i < sockets.size(); i++) {
            sockets.get(i).println(Response.createResponse(Response.CHANGE.toString(), Response.SEPARATOR.toString(), String.valueOf(i)));
        }
        playerCounter = sockets.size();
    }

    public void start() throws IOException{
        serverSocket = new ServerSocket(port);
        shop = new Shop();
        shop.supply(3);

        Socket socket;
        while ((socket = serverSocket.accept()) != null) {
            sockets.add(new PrintWriter(socket.getOutputStream(), true));

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = sockets.get(sockets.size() - 1);

            System.out.printf("Клиент пришел из %s\n", socket.getInetAddress());

            out.println(Response.PLAYER_COUNT.toString() + Response.SEPARATOR + playerCounter);

            Socket finalSocket = socket;
            new Thread(() -> {
                while (true) {
                    String msg = null;
                    try {
                        if ((msg = in.readLine()) == null) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    final String[] command = msg.split(String.valueOf(Response.SEPARATOR));

                    Response response = Response.valueOf(command[0]);
                    System.out.println("Пришла команда: " + msg);

                    // обрабатывает сервер от клиента у клиента

                    switch (response) {
                        case GO -> {
                            playerCounter++;

                            String resp = Response.createResponse(Response.SUPPLY.toString(), Response.SEPARATOR.toString(), shop.getStore().toResponse());
                            sendAll(resp);
                            System.out.println("Отослана команда: " + resp);
                        }
                        case PURCHASE -> {
                            shop.purchase(new Customer("пал палыч", 111, Arrays.stream(command[1].split(",")).toList(), shop.getStore()));

                            shop.supply(3);

                            String resp = Response.createResponse(Response.SUPPLY.toString(), Response.SEPARATOR.toString(), shop.getStore().toResponse());
                            sendAll(resp);
                            System.out.println("Отослана команда: " + resp);
                        }
                        case BYE -> {
//                            int deleted = Integer.parseInt(command[1]);
//                            sockets.remove(deleted);
//                            sendAllChangeNumbers(deleted);
                            try {
                                in.close();
                                finalSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            out.close();
                        }
                    }
                    // получает запрос о покупке
                    // делает покупку в магазине networkGame перенести на сервер
                    // оповестить всех о покупке
                }
            }).start();
//                NetworkGame session = new NetworkGame(socket, game);
//                new Thread(session).start();
            }
        }

    public void stop(Socket socket) throws IOException {
        in.close();
        out.close();
        socket.close();
    }

}
