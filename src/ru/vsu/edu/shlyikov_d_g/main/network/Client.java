package ru.vsu.edu.shlyikov_d_g.main.network;

import ru.vsu.edu.shlyikov_d_g.main.Shop;
import ru.vsu.edu.shlyikov_d_g.main.visualisation.Game;
import ru.vsu.edu.shlyikov_d_g.main.visualisation.GameVisualise;
import ru.vsu.edu.shlyikov_d_g.main.visualisation.graphics.Panel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    private String host;
    private int port;
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    private int number = 0;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 8080);
        client.start();
    }

    public void start() throws IOException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

            String response = in.readLine();

            String[] parsed = response.split(Response.SEPARATOR.getSign());
            System.out.println("Client like a ; " + Arrays.toString(parsed));

            Response command = Response.valueOf(parsed[0]);
            System.out.println("Client like a ; " + command);

            switch (command) {
                case PLAYER_COUNT -> {
                    number = Integer.parseInt(parsed[1]);
                    System.out.println("Я игрок номер " + number);
                    out.println("GO");
                }
                case CHAT -> {
                    String name = parsed[1];
                    System.out.println("Привет, " + name + "!");
                }
            }

        new Thread(new PurchaseGame(socket, number)).start();

    }
}
