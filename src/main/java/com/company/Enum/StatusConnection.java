package main.java.com.company.Enum;

public enum StatusConnection{
    ConnectionEtablie(new byte[]{0,0,0,0,1,1,1,1});

    byte[] status;

    private StatusConnection(byte[] table) {
        this.status = table;
    }
}