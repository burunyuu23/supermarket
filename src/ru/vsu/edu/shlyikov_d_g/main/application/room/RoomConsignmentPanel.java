package ru.vsu.edu.shlyikov_d_g.main.application.room;

import ru.vsu.edu.shlyikov_d_g.main.application.helper.AbstractPanel;
import ru.vsu.edu.shlyikov_d_g.main.application.helper.ConsignmentPanel;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.visualisation.graphics.Panel;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class RoomConsignmentPanel extends AbstractPanel {

    public RoomConsignmentPanel(Consignment consignment, Panel panel, String operationName, int field1, int field2,int width) {
        super(panel);

        setMinimumSize(new Dimension(width, getHeight()));
        setBorder(BorderFactory.createLineBorder(Color.RED));

        this.setLayout(new FlowLayout());

        JTextPane consignmentName = new JTextPane();
        consignmentName.setText(consignment.toStringStorage());
        consignmentName.setEditable(false);
        consignmentName.setLocation(0,0);
        setNormal(consignmentName);
        add(consignmentName);

        JButton doSmth = new JButton();
        doSmth.setSize(50,50);
        doSmth.addActionListener(a -> new ConsignmentPanel(consignment, panel, operationName, field1, field2));
        setNormal(doSmth);
        add(doSmth);

        ImageIcon buttonIcon;
        if (operationName.toLowerCase(Locale.ROOT).equals("удалить")){
            buttonIcon = new ImageIcon("C:\\Users\\zEzzLike\\IdeaProjects\\oop_atta1\\src\\ru\\vsu\\edu\\shlyikov_d_g\\resources\\deleteButton.png");
        }
        else if (operationName.toLowerCase(Locale.ROOT).equals("переместить")){
            buttonIcon = new ImageIcon("C:\\Users\\zEzzLike\\IdeaProjects\\oop_atta1\\src\\ru\\vsu\\edu\\shlyikov_d_g\\resources\\translateButton.png");
        }
        else{
            doSmth.setVisible(false);
            buttonIcon = new ImageIcon();
        }

        doSmth.setIcon(buttonIcon);
    }
}
