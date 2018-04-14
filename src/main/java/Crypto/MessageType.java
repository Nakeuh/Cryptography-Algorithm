package Crypto;


public enum MessageType {
    KEY_RSA("rsa"),
    KEY_DES("des"),
    MSG("msg"),
    MSG_CLOSE("cls");

    private String id;

    MessageType(String id) {
        this.id = id;
    }

    public static MessageType stringToMessageType(String messageType) {
        switch (messageType){
            case "rsa":
                return KEY_RSA;
            case "des":
                return KEY_DES;
            case "msg":
                return MSG;
            case "cls":
                return MSG_CLOSE;
            default:
                return MSG;
        }
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }
}
