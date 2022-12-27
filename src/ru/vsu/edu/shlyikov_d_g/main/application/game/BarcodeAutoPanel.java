package ru.vsu.edu.shlyikov_d_g.main.application.game;

import ru.vsu.edu.shlyikov_d_g.main.application.helper.AbstractDrawer;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.main.visualisation.graphics.adapters.ReadyListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BarcodeAutoPanel extends AbstractDrawer{
    BufferedImage img = null;
    JTextField jTextField = new JTextField(11);
    JButton submit = new JButton("Пробить");
    boolean submitted = false;

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public BarcodeAutoPanel(Consignment consignment){
        setLayout(null);
        setNormal(submit);
        add(submit);
        jTextField.setBounds(81, 76, 400,50);
        submit.setBounds(81, 150, 400,50);
        submit.addActionListener(a ->{
            if (consignment.getVendorCode().equals(jTextField.getText())){
                listeners.forEach(ReadyListener::ready);
            }
//            submitted = consignment.getVendorCode().equals(jTextField.getText());
        });
        jTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if(jTextField.getText().length() > 11 && !(evt.getKeyChar()== KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
                    getToolkit().beep();
                    evt.consume();
                }
            }
        });
        add(jTextField);
        setNormal(jTextField);
        revalidate();
            try {
                img = ImageIO.read(new File("C:\\Users\\zEzzLike\\IdeaProjects\\oop_atta1\\src\\ru\\vsu\\edu\\shlyikov_d_g\\resources\\barcode1.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    protected void draw(Graphics2D g) {
        g.drawImage(img, 0, 0, null);
        g.translate(81, 76);
        jTextField.paint(g);
        g.translate(0, 74);
        jTextField.grabFocus();
        submit.paint(g);
        g.translate(-81, -150);
    }
}
