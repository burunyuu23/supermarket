package ru.vsu.edu.shlyikov_d_g.humans.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Genders {
    Муж, Жен;

    private static final List<Genders> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Genders randomGender()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public static boolean isGender(String s)  {
        return VALUES.toString().contains(s);
    }
}
