import javax.swing.*;
import java.io.*;
import java.util.stream.Collectors;

public class Dictionary {
    public static void main(String[] args) {
        String word = JOptionPane.showInputDialog("Enter a word:");
        String command = "curl -X GET https://api.dictionaryapi.dev/api/v2/entries/en/" + word;
        Runtime rt = Runtime.getRuntime();
        try{Process pr = rt.exec(command);

//Java 8 version
        String result = new BufferedReader(
                new InputStreamReader(pr.getInputStream()))
                .lines()
                .collect(Collectors.joining("\n"));
        JOptionPane.showMessageDialog(null, result);}
        catch(IOException e){System.out.println("Oops - error in CURL");}

    }
}
