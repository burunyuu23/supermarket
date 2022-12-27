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
import java.util.Scanner;

public class Client {
    private String host;
    private int port;
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

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

        String response;
        while ((response = in.readLine()) != null) {
            String[] parsed = response.split(Response.SEPARATOR.getSign());

            Response command = Response.valueOf(parsed[0]);

            switch (command) {
                case PLAYER_COUNT -> {
                    int sessionNum = Integer.parseInt(parsed[1]);
                    System.out.println("Я игрок номер " + sessionNum);
                    out.println("GO");
                    out.println("Клиент говорит: Я игрок номер " + sessionNum);
                }
                case CHAT -> {
                    String name = response.split("=")[1];
                    out.println("Привет, " + name + "!");
                }
            }
        }
    }
}
