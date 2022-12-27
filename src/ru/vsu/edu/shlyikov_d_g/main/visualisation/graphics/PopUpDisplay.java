package ru.vsu.edu.shlyikov_d_g.main.visualisation.graphics;

import ru.vsu.edu.shlyikov_d_g.main.application.helper.ChequePanel;
import ru.vsu.edu.shlyikov_d_g.main.application.helper.InfoPanel;

public class PopUpDisplay {
    public static void showHelp(String helpText) {
        new InfoPanel(helpText);
    }

    public static void showCheque(String message) {
        new ChequePanel(message);
    }
}
