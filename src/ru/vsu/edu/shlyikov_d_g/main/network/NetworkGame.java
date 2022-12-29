package ru.vsu.edu.shlyikov_d_g.main.network;

import ru.vsu.edu.shlyikov_d_g.events.Purchase;
import ru.vsu.edu.shlyikov_d_g.humans.buyers.Customer;
import ru.vsu.edu.shlyikov_d_g.main.Shop;
import ru.vsu.edu.shlyikov_d_g.main.visualisation.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetworkGame implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Game game;

    public NetworkGame(Socket socket, Game game){
        try {
            this.socket = socket;
            this.game = game;



            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
    }
}
