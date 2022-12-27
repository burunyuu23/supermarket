package ru.vsu.edu.shlyikov_d_g.main.network;

import ru.vsu.edu.shlyikov_d_g.main.Shop;
import ru.vsu.edu.shlyikov_d_g.main.visualisation.Game;
import ru.vsu.edu.shlyikov_d_g.main.visualisation.graphics.Panel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private BufferedReader in;
    private PrintWriter out;
    private Shop shop = new Shop();
    private NetworkPanel panel;

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

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);

        Socket socket;
        while ((socket = serverSocket.accept()) != null) {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
//            start();

            System.out.printf("Клиент пришел из %s\n", socket.getInetAddress());

            out.println(Response.PLAYER_COUNT.toString() + Response.SEPARATOR + playerCounter);

            Game game;
            final String command = in.readLine();
            Response response = Response.valueOf(command);

            switch (response){
                case GO -> {
                    playerCounter++;
                    if (playerCounter > 0) {
                        if (playerCounter == 1) {
                            panel = new NetworkPanel(shop, socket);
                            game = panel;
                        } else {
                            game = new PurchaseGame(panel, socket);
                        }
                    }
                }
                    case SEPARATOR -> {

                    }
                }
                // получает запрос о покупке
                // делает покупку в магазине newtworkGame перенести на сервер
                // оповестить всех о покупке

                out.println("CHAT=burunyuu23");

                NetworkGame session = new NetworkGame(socket, game);
                new Thread(session).start();
            }
        }

    public void stop(Socket socket) throws IOException {
        in.close();
        out.close();
        socket.close();
        serverSocket.close();
    }

}
