package ru.vsu.edu.shlyikov_d_g.visualisation.graphics;

import ru.vsu.edu.shlyikov_d_g.visualisation.graphics.adapters.ReadyListener;

public class ReadyEvent implements ReadyListener {
    boolean clicked = false;

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    @Override
    public void ready() {setClicked(true);}
}
