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
    CashdeskPanel cashdeskPanel;

    ScalesPanel scalesPanel;

    BarcodeAutoPanel barcodeAutoPanel;
    BarcodeManualPanel barcodeManualPanel;

    CurrentGamePanel currentGamePanel;
    boolean customerChosen = false;
    boolean scalesRight = false;

    private List<ReadyListener> cashDeskListeners = new ArrayList<>();

    private List<ReadyListener> scalesListeners = new ArrayList<>();

    private List<ReadyListener> barcodeAutoListeners = new ArrayList<>();

    private List<ReadyListener> barcodeManualListeners = new ArrayList<>();

    public void addBarcodeManualListener(ReadyListener toAdd) {
        barcodeManualListeners.add(toAdd);
    }

    public void addBarcodeAutoListener(ReadyListener toAdd) {
        barcodeAutoListeners.add(toAdd);
    }
    public void addScalesListener(ReadyListener toAdd) {
        scalesListeners.add(toAdd);
    }

    public void addCashDeskListener(ReadyListener toAdd) {
        cashDeskListeners.add(toAdd);
    }

    public CashdeskPanel getCashdeskPanel() {
        return cashdeskPanel;
    }

    public ScalesPanel getScalesPanel() {
        return scalesPanel;
    }

    public BarcodeAutoPanel getBarcodeAutoPanel() {
        return barcodeAutoPanel;
    }

    public BarcodeManualPanel getBarcodeManualPanel() {
        return barcodeManualPanel;
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
        cashdeskPanel.setListeners(cashDeskListeners);

        scalesPanel = new ScalesPanel(consignment);
        scalesPanel.setListeners(scalesListeners);

        barcodeAutoPanel = new BarcodeAutoPanel(consignment);
        barcodeAutoPanel.setListeners(barcodeAutoListeners);

        barcodeManualPanel = new BarcodeManualPanel(consignment);
        barcodeManualPanel.setListeners(barcodeManualListeners);

        add(cashdeskPanel, String.valueOf(CurrentGamePanel.CASHDESK_PANEL));
        add(scalesPanel, String.valueOf(CurrentGamePanel.SCALES_PANEL));
        add(barcodeAutoPanel, String.valueOf(CurrentGamePanel.BARCODE_AUTO_PANEL));
        add(barcodeManualPanel, String.valueOf(CurrentGamePanel.BARCODE_MANUAL_PANEL));

        currentGamePanel = CurrentGamePanel.CASHDESK_PANEL;
        goToLayout(this);
    }
}
