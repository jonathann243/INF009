package main.java.com.company.Enum;

public enum Primitiv {
    N_CONNECT_req(new byte[]{0,0,0,0,1,0,1,1}),
    N_DATA_req(new byte[]{0}),
    N_DISCONNECT_req(new byte[]{0,0,0,1,0,0,1,1});

    byte[] directive;

     Primitiv(byte[] table) {
        this.directive = table;
    }
  }