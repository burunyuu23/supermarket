package ru.vsu.edu.shlyikov_d_g.main.network;

public enum Response {
    SEPARATOR ("="),
    PLAYER_COUNT("PLAYER_COUNT"),
    GO ("GO"),
    CHAT ("CHAT");

    private String sign;

    Response(String sign){
        this.sign = sign;
    }

    @Override
    public String toString() {
        return sign;
    }

    public String getSign(){
        return sign;
    }

}
