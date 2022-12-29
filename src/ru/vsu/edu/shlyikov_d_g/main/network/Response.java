package ru.vsu.edu.shlyikov_d_g.main.network;

public enum Response {
    SEPARATOR ("="),
    PLAYER_COUNT("PLAYER_COUNT"),
    GO ("GO"),
    CHAT ("CHAT"),
    BUY("BUY"),
    BYE ("BYE"),
    SUPPLY("SUPPLY"),
    PURCHASE ("PURCHASE"),
    CHANGE ("CHANGE"),
    CONSIGNMENT_SEPARATOR(":");

    public static String createResponse(String ... a){
        StringBuilder sb = new StringBuilder();
        for (String str : a) {
            sb.append(str);
        }
        return sb.toString();
    }

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
