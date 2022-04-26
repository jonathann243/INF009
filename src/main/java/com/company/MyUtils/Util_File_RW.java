package main.java.com.company.MyUtils;

import java.io.*;
import java.util.ArrayList;

import static main.java.com.company.MyUtils.Util_File_path.S_ECR;

public class Util_File_RW {
    /***
     *
     * @param message message a ecrire dans le fichier
     * @param path chemin du fichier en string
     */
    public static void writeToFile(String message,String path) {
        File file = new File(path);
        try(
                FileWriter fileWriter = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fileWriter);
                PrintWriter out = new PrintWriter(bw)){
            out.println(message);
            System.out.println(message);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     *
     * @param path le chemin du fichier
     * @return list des lignes du fichier en String
     */
    public static ArrayList<String> ReadToFile(String  path) {
        ArrayList<String> lines = new ArrayList<>();
        try {
            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                lines.add(st);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

}
