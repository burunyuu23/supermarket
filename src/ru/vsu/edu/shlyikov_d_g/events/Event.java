package ru.vsu.edu.shlyikov_d_g.events;

import ru.vsu.edu.shlyikov_d_g.rooms.Room;
import ru.vsu.edu.shlyikov_d_g.main.visualisation.GameVisualise;

public interface Event {
    Room storage = null;
    Room store = null;
    GameVisualise gameVisualise = null;

    void proces();
}
