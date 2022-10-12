package ru.vsu.edu.shlyikov_d_g.main;

import ru.vsu.edu.shlyikov_d_g.humans.buyers.Supplier;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        final Game game = new Game();

        game.start();
    }

}
