package ru.vsu.edu.shlyikov_d_g.main.network;

import ru.vsu.edu.shlyikov_d_g.main.application.room.RoomPanel;
import ru.vsu.edu.shlyikov_d_g.main.visualisation.Game;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.*;
import java.util.List;

import ru.vsu.edu.shlyikov_d_g.main.visualisation.graphics.Panel;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.products.units.TransferUnit;
import ru.vsu.edu.shlyikov_d_g.utils.Utils;

import javax.swing.*;

import static ru.vsu.edu.shlyikov_d_g.main.network.Response.*;

public class PurchaseGame extends RoomPanel implements Game, Runnable {
    private JFrame jFrame = new JFrame();
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private List<String> consignmentList = new ArrayList<>();
    private int number;

    public PurchaseGame(Socket socket, int number) throws IOException {
        super("Купить");
        this.socket = socket;
        this.number = number;

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        jFrame.setTitle("Supermarket");
        jFrame.setContentPane(this);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(1600, 900);

        jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(jFrame,
                        "Покинуть магазин?", "пока нам будет тебя не хватать пал палыч",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
//                    String response = Response.createResponse(BYE.toString(), SEPARATOR.toString(),String.valueOf(number));
//                    out.println(response);

                    try {
                        in.close();
                        out.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                }
            }
        });

        addListener(() -> {
            // посылать серверу
            if (consignmentList.size() > 0) {
                String response = Response.createResponse(PURCHASE.toString(), SEPARATOR.toString(), consignmentList.toString());
                System.out.println("Отослана команда: " + response);
                out.println(response);
                consignmentList.clear();
            }
        });
        winMain();
        start();
    }

    private void start() throws IOException {
        String response;
        while ((response = in.readLine()) != null) {
            String[] parsed = response.split(Response.SEPARATOR.getSign());
            System.out.println("Пришла команда: " + response);

            Response command = Response.valueOf(parsed[0]);

            switch (command) {
                case SUPPLY -> {
                    updateShop(consignmentInput(Arrays.stream(parsed[1].split(",")).toList()), consignmentList);
                    repaint();
                }
                case CHANGE -> {
                    number = Integer.parseInt(parsed[1]);
                }
            }
        }
    }

    private List<Consignment> consignmentInput(List<String> input){
        List<Consignment> list = new ArrayList<>();
        for (String s:input) {
            list.add(new Consignment(s));
        }

        return list;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        repaint();
    }

    @Override
    public void repaint(Rectangle r) {
        super.repaint(r);
    }

    private void winMain() {
        Locale.setDefault(Locale.ROOT);

        java.awt.EventQueue.invokeLater(() -> {
            jFrame.setSize(1600, 900);
            jFrame.setVisible(true);
        });
    }

    @Override
    public void run() {

    }
}
