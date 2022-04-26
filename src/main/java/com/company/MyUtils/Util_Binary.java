package main.java.com.company.MyUtils;

public class Util_Binary {

    public static String inToString(byte x){
        String s= String.format("%8s", Integer.toBinaryString(x & 0xFF)).replace(' ', '0');
        return s;
    }
    /***
     *  Classe util me permetatnt de changer la valeur d'un byte par bit
     * @param partofchamp changer le pr ps m
     * @param setvalue la valeur a changer
     * @param base le byte a modifier
     * @return
     */
        public static byte setPartOf(String partofchamp,byte setvalue,byte base){

            switch (partofchamp){
                case "pr":{
                    //pos 7
                   if ( (setvalue & 0b0000_0100) > 0 )  base |= 1 << 7;//passer a 1 a la position
                    else                            base  &= ~(1 << 7);//passer a 0 a la position
                    //pos 6
                    if ( (setvalue & 0b0000_0010) > 0 )  base |= 1 << 6;//passer a 1 a la position
                    else                            base  &= ~(1 << 6);//passer a 0 a la position
                    //pos 5
                    if ( (setvalue & 0b0000_0001) > 0 )  base |= 1 << 5;//passer a 1 a la position
                    else                            base  &= ~(1 << 5);//passer a 0 a la position

                    break;
                }
                case "ps":{
                    //pos 3
                    if ( (setvalue & 0b0000_0100) > 0 )  base |= 1 << 3;//passer a 1 a la position
                    else                            base  &= ~(1 << 3);//passer a 0 a la position
                    //pos 2
                    if ( (setvalue & 0b0000_0010) > 0 )  base |= 1 << 2;//passer a 1 a la position
                    else                            base  &= ~(1 << 2);//passer a 0 a la position
                    //pos 1
                    if ( (setvalue & 0b0000_0001) > 0 )  base |= 1 << 1;//passer a 1 a la position
                    else                            base  &= ~(1 << 1);//passer a 0 a la position

                        break;
                }
                case "m":{
                    //pos 0
                    if ( (setvalue & 0b0000_0001) > 0 )  base |= 1 << 4;//passer a 1 a la position
                    else                            base  &= ~(1 << 4);//passer a 0 a la position
                    break;
                }

            }
            return base;
        }

    /***
     * Methode qui permet de recuper partie de byte en goupe
     * @param partofchamp specification partie a prendre
     * @param setvalue  Specivication Byte
     * @return
     */
    public static byte getPartOf(String partofchamp,byte setvalue){
          byte base=0;
        switch (partofchamp){
            case "pr":{
                //pos 7

                if ( (setvalue & 0b1000_0000) > 0 )  base |= 1 << 2;//passer a 1 a la position
                else                            base  &= ~(1 << 2);//passer a 0 a la position
                //pos 6
                if ( (setvalue & 0b0100_0000) > 0 )  base |= 1 << 1;//passer a 1 a la position
                else                            base  &= ~(1 << 1);//passer a 0 a la position
                //pos 5
                if ( (setvalue & 0b0010_0000) > 0 )  base |= 1 << 0;//passer a 1 a la position
                else                            base  &= ~(1 << 0);//passer a 0 a la position

                break;
            }
            case "ps":{
                //pos 3
                if ( (setvalue & 0b0000_1000) > 0 )  base |= 1 << 2;//passer a 1 a la position
                else                            base  &= ~(1 << 2);//passer a 0 a la position
                //pos 2
                if ( (setvalue & 0b0000_0100) > 0 )  base |= 1 << 1;//passer a 1 a la position
                else                            base  &= ~(1 << 1);//passer a 0 a la position
                //pos 1
                if ( (setvalue & 0b0000_0010) > 0 )  base |= 1 << 0;//passer a 1 a la position
                else                            base  &= ~(1 << 0);//passer a 0 a la position

                break;
            }
            case "m":{
                //pos 4
                if ( (setvalue & 0b0001_0000) > 0 )  base |= 1 << 0;//passer a 1 a la position
                else                            base  &= ~(1 << 0);//passer a 0 a la position
                break;
            }

        }
        return base;
    }

}
