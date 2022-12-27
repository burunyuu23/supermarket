package ru.vsu.edu.shlyikov_d_g.main.network;

import ru.vsu.edu.shlyikov_d_g.events.Purchase;
import ru.vsu.edu.shlyikov_d_g.main.Shop;
import ru.vsu.edu.shlyikov_d_g.main.application.ShopFrame;
import ru.vsu.edu.shlyikov_d_g.main.visualisation.graphics.Panel;
import ru.vsu.edu.shlyikov_d_g.products.units.SupplyUnit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NetworkPanel extends Panel {
    private Shop shop;
    private int consignmentIndex = 0;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private List<Purchase> purchaseList = new ArrayList<>();
    private int k = 0;

    public void addPurchase(Purchase purchase){
        purchaseList.add(purchase);
        k++;
    }

    public NetworkPanel(Shop shop, Socket socket) throws IOException {
        super(shop);
        shop.setVisualise(this);

        this.shop = shop;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    protected void purchases() {
        if (shop.getPurchaseCount() == 0){
            for (int i = 0; i < 5; i++) {
                purchaseList.add(shop.purchase());
            }
            System.out.println(purchaseList);
        }
        if (shop.isOverPurchaseCount(k)) {
            if (purchaseList.get(shop.getPurchaseCount()).getCustomer().getBasket().size() > 0) {
                consignmentIndex = 0;
                continuePurchase(consignmentIndex);
            } else {
                purchases();
            }
        } else {
            goToSupply();
        }
    }
}
