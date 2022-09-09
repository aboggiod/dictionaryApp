import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class Dictionary {
    public static void define(String theWord) {
        BufferedReader reader;
        StringBuilder response = new StringBuilder();
        HttpURLConnection conn;
        String line;

        try {
            URL url = new URL("https://api.dictionaryapi.dev/api/v2/entries/en/" + theWord);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int status = conn.getResponseCode();

            if (status >= 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder definitionList = new StringBuilder();
        JSONObject obj = new JSONObject(response);
        JSONArray definitions = obj.getJSONArray("meanings");
        for(int i = 0; i < definitions.length(); i++)
        {
            definitionList.append(definitions.get(i));
            definitionList.append("\n");
        }
        JOptionPane.showMessageDialog(null, definitionList);
    }
    public static void main(String[] args) {

        // Creating the main window and main panel
        JFrame mainWindow = new JFrame("Dictionary App");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //mainWindow.setPreferredSize(new Dimension(300, 400));
        JPanel mainPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(boxlayout);
        mainPanel.setBorder(new EmptyBorder(new Insets(15, 20, 15, 20)));

        // Creating main window's components
        JButton define = new JButton("Define");
        JButton exit = new JButton("Exit");
        JLabel wordLabel = new JLabel("Enter word to define:");
        JLabel sourceLabel = new JLabel("Enter source:");
        JTextField wordText = new JTextField();
        JTextField sourceText = new JTextField();

        // Adding components to window
        mainPanel.add(wordLabel);
        mainPanel.add(wordText);
        //mainPanel.add(sourceLabel);
        //mainPanel.add(sourceText);
        mainPanel.add(define);
        mainPanel.add(exit);
        mainWindow.add(mainPanel);

        // Making the buttons "listen" for a click
        define.addActionListener(e -> define(wordText.getText()));
        exit.addActionListener(e -> mainWindow.dispose());

        // Make main window visible
        mainWindow.pack();
        mainWindow.setVisible(true);




    }
}
