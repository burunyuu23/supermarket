package ru.vsu.edu.shlyikov_d_g.main.application.non_game;

import ru.vsu.edu.shlyikov_d_g.main.visualisation.graphics.PopUpDisplay;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainMenuPanel extends JPanel implements ActionListener {
    private final Color textFieldColor = new Color(207,203,140);
    private final Font textFont = new Font("Microsoft Sans Serif", Font.BOLD, 46);
    private final int textLimit = 11;
    private boolean isContinue = false;

    private final List<JButton> buttons = new ArrayList<>();

    private BufferedImage background;
    private Graphics2D g;
    private JTextField textField = new JTextField();
    private JButton testGame;
    private JButton startGame;
    private JButton helpGame;

    public boolean isContinue() {
        return isContinue;
    }

    public JTextField getTextField() {
        return textField;
    }

    public JButton getTestGame() {
        return testGame;
    }
    public JButton getStartGame() {
        return startGame;
    }

    public JButton getHelpGame() {
        return helpGame;
    }

    public void setStartGame(JButton startGame) {
        this.startGame = startGame;
        addButton(startGame);
    }

    public void setTestGame() {
        testGame = new JButton("можно анекдот?");

        testGame.setBackground(textFieldColor);
        testGame.setFont(textFont);
        testGame.setBounds(547,-222,634,90);

        testGame.addActionListener(actionEvent -> {
            PopUpDisplay.showHelp("""
                    Работа программиста и шамана имеет много общего -\040
                    оба бормочут непонятные слова, совершают непонятные действия\040
                    и не могут объяснить, как оно работает
                    """);
        });
        helpGame = new JButton("помогите пожалусто");
        helpGame.setBackground(textFieldColor);
        helpGame.setFont(textFont);
        helpGame.setBounds(547,-222,634,90);

        helpGame.addActionListener(actionEvent -> PopUpDisplay.showHelp("test"));
        buttons.add(testGame);
        buttons.add(helpGame);
    }

    public void setTextField(JTextField textField) {
        this.textField = textField;
        this.textField.setFont(textFont);
        this.textField.setBackground(textFieldColor);
        this.textField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if(textField.getText().length() >= textLimit && !(evt.getKeyChar()== KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
                    getToolkit().beep();
                    evt.consume();
                }
            }
        });
        this.textField.setVisible(true);
        this.textField.setBounds(547,-222,634,90);
    }

    {
        try {
            background = ImageIO.read(new File("C:\\Users\\zEzzLike\\IdeaProjects\\oop_atta1\\src\\ru\\vsu\\edu\\shlyikov_d_g\\resources\\mainMenu.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    {
        this.setBorder(BorderFactory.createLineBorder(Color.red));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        System.setProperty("sun.awt.noerasebackground", "true");
    }

    public void addButton(JButton button){
        button.setBackground(textFieldColor);
        button.setFont(textFont);
        this.buttons.add(button);
    }

    @Override
    public void paint(Graphics gr) {
        super.paint(gr);
        Graphics2D g = (Graphics2D) gr;
        this.g = g;

        g.drawImage(background, 0, 0, null);


        draw(-0.12, 0.955+0.1, 1.1, textField.getBounds().getX()+130, textField.getBounds().getY()+133+244, textField);

        for (int i = 0; i < buttons.size(); i++) {
            draw(-0.07, 0.755+0.1, 0.91, buttons.get(i).getBounds().getX()-490, buttons.get(i).getBounds().getY()+420+244+100*i, buttons.get(i));
        }
   }

    private void draw(double shy, double dx, double dy, double x, double y, JComponent component){
        g.scale(dx,dy);
        g.shear(0,shy);
        g.translate(x, y);

        g.setColor(Color.BLACK);
        component.setForeground(Color.BLACK);
        component.paint(g);

        g.translate(-x, -y);
        g.shear(0,-shy);
        g.scale(1.0/dx,1/dy);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
