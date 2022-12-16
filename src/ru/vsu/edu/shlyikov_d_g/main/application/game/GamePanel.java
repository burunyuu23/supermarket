package ru.vsu.edu.shlyikov_d_g.main.application.game;

import ru.vsu.edu.shlyikov_d_g.humans.buyers.Customer;
import ru.vsu.edu.shlyikov_d_g.main.application.helper.AbstractPanel;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.rooms.Store;
import ru.vsu.edu.shlyikov_d_g.visualisation.graphics.adapters.ReadyListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends AbstractPanel{
    BarcodeAutoPanel barcodeAutoPanel;
    BarcodeManualPanel barcodeManualPanel;
    CashdeskPanel cashdeskPanel;
    ScalesPanel scalesPanel;
    CurrentGamePanel currentGamePanel;
    boolean customerChosen = false;
    boolean scalesRight = false;
    private List<ReadyListener> listeners = new ArrayList<>();

    public List<ReadyListener> getListeners() {
        return listeners;
    }

    public void addListener(ReadyListener toAdd) {
        listeners.add(toAdd);
    }

    public boolean isCompleted() {
        return barcodeManualPanel.isCompleted();
    }

    public BarcodeManualPanel.Checker getChecker(){
        return barcodeManualPanel.getChecker();
    }

    public void setCompleted(boolean completed) {
        barcodeManualPanel.setCompleted(completed);
    }

    public void setScalesRight(boolean scalesRight) {
        this.scalesRight = scalesRight;
    }

    public boolean isScalesRight() {
        return scalesRight;
    }

    public boolean isSubmitted() {
        return barcodeAutoPanel.isSubmitted();
    }

    public boolean isCustomerChosen() {
        return customerChosen;
    }

    public void setCustomerChosen(boolean customerChosen) {
        this.customerChosen = customerChosen;
    }

    Store store;
    Customer customer;
    Consignment consignment;

    public Consignment getConsignment() {
        return consignment;
    }

    public enum CurrentGamePanel {
        CASHDESK_PANEL,
        SCALES_PANEL,
        BARCODE_AUTO_PANEL,
        BARCODE_MANUAL_PANEL
    }

    private void goToLayout(JPanel cards) {
        CardLayout cardLayout = (CardLayout) cards.getLayout();
        cardLayout.show(cards, String.valueOf(currentGamePanel));
    }

    public CurrentGamePanel getCurrentGamePanel() {
        return currentGamePanel;
    }

    public void setCurrentGamePanel(CurrentGamePanel currentGamePanel) {
        this.currentGamePanel = currentGamePanel;
        goToLayout(this);
    }

    public GamePanel() {
    super(new Dimension());
    }

    public GamePanel(Customer customer, Consignment consignment, Store store) {
        super(new Dimension());
        this.customer = customer;
        this.consignment = consignment;
        this.store = store;

        setLayout(new CardLayout());

        cashdeskPanel = new CashdeskPanel(customer);
        cashdeskPanel.addAllListener(listeners);

        scalesPanel = new ScalesPanel(consignment);
        scalesPanel.addAllListener(listeners);

        barcodeAutoPanel = new BarcodeAutoPanel(consignment);
        barcodeAutoPanel.addAllListener(listeners);

        barcodeManualPanel = new BarcodeManualPanel(consignment);
        barcodeManualPanel.addAllListener(listeners);

        add(cashdeskPanel, String.valueOf(CurrentGamePanel.CASHDESK_PANEL));
        add(scalesPanel, String.valueOf(CurrentGamePanel.SCALES_PANEL));
        add(barcodeAutoPanel, String.valueOf(CurrentGamePanel.BARCODE_AUTO_PANEL));
        add(barcodeManualPanel, String.valueOf(CurrentGamePanel.BARCODE_MANUAL_PANEL));

        currentGamePanel = CurrentGamePanel.CASHDESK_PANEL;
        goToLayout(this);
    }
}
