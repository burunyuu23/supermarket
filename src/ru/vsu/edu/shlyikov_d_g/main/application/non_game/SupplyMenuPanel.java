package ru.vsu.edu.shlyikov_d_g.main.application.non_game;

import ru.vsu.edu.shlyikov_d_g.humans.buyers.Supplier;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.visualisation.graphics.adapters.ReadyListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SupplyMenuPanel extends JPanel implements ActionListener {
    protected final Color textFieldColor = new Color(207,203,140);
    private final Font textFont = new Font("Microsoft Sans Serif", Font.BOLD, 46);

    BufferedImage backBox;
    BufferedImage frontBox;
    int width;
    int height;
    Point[][] points = new Point[3][3];
    BufferedImage background;
    List<Supplier> supplierList = new ArrayList<>();
    Graphics2D g;
    JButton buyButton = new JButton("Купить!");
    int days;
    private List<ReadyListener> listeners = new ArrayList<>();

    public void addListener(ReadyListener toAdd) {
        listeners.add(toAdd);
    }

    public Point getWidthAndHeight(){
        return new Point(width, height);
    }

    public Point[][] getPoints() {
        return points;
    }

    public void setSupplierList(List<Supplier> supplierList){
        this.supplierList = supplierList;
    }

    public Consignment getConsignment(int supplier, int consignment){
        return supplierList.get(supplier).getBasket().get(consignment);
    }

    public void plusDay(){
        days++;
    }

    public SupplyMenuPanel(int days){
        this.days = days;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        try {
            backBox = ImageIO.read(new File("C:\\Users\\zEzzLike\\IdeaProjects\\oop_atta1\\src\\ru\\vsu\\edu\\shlyikov_d_g\\resources\\backBox.png"));
            frontBox = ImageIO.read(new File("C:\\Users\\zEzzLike\\IdeaProjects\\oop_atta1\\src\\ru\\vsu\\edu\\shlyikov_d_g\\resources\\frontBox.png"));
            background = ImageIO.read(new File("C:\\Users\\zEzzLike\\IdeaProjects\\oop_atta1\\src\\ru\\vsu\\edu\\shlyikov_d_g\\resources\\supplyMenu.jpg"));
            width = backBox.getWidth();
            height = backBox.getHeight();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        setLayout(null);
        buyButton.addActionListener(a -> {
            listeners.forEach(ReadyListener::ready);
        });
        buyButton.setFont(textFont);
        buyButton.setBounds(340, 15, 300, 50);
        buyButton.setBackground(textFieldColor);
        buyButton.setVisible(true);
        this.add(buyButton);
    }

    @Override
    public void paint(Graphics gr) {
        super.paint(gr);
        Graphics2D g = (Graphics2D) gr;
        this.g = g;

        buyButton.paint(g);
        g.drawImage(background, 0, 0, null);

        g.setFont(textFont);
        g.setColor(Color.GREEN);

        g.drawString(String.format("Сейчас %d день!", days), 320, 110);

        for (int i = 0; i < supplierList.size(); i++) {
            g.drawString(supplierList.get(i).getName(), 300+i*450, 250);
            drawThreeBoxes(150+i*450, 350, supplierList.get(i), i);
        }
    }

    private void drawThreeBoxes(int x, int y, Supplier supplier, int j){
        for (int i = 0; i < supplier.getBasket().size(); i++) {
            drawBox(x-i*50, y+150*i, supplier.getBasket().get(i));
            points[j][i] = new Point(x-i*50, y+150*i);
        }
    }

    private void drawBox(int x, int y, Consignment consignment){
        g.drawImage(backBox, x,y, null);
        g.drawString(consignment.getVendorCode(), x+30,y+80);
        g.drawImage(frontBox, x,y, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
