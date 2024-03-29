import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame{
    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream output; // a stream is a connection with another computer, there are 2 streams, output and input 
    private ObjectInputStream input;
    private ServerSocket server; 
    private Socket connection;


    // constructor
    public Server(){
        super("Isha's Instant Messenger");
        userText = new JTextField();
        userText.setEditable(false); // before you are connected to anyone, this box is disabled during booting up
        // Dont type stuff when there is no stream to receive the text
        userText.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent event){
                        sendMessage(event.getActionCommand());
                        userText.setText("");
                }
            }
        );
        add(userText, BorderLayout.NORTH);
        chatWindow =  new JTextArea();
        add(new JScrollPane(chatWindow));
        setSize(300,150);
        setVisible(true);
    }
    //set up your application set up
    //set up and run the server 

    public void startRunning(){
        try{
            server = new ServerSocket(6789, 100);
            while(true){
                try{
                    waitForConnection();
                    setupStreams();
                    whileChatting();
                    //connect and have conversation
                }catch(EOFException eofException){
                    showMessage("\n Server ended the connection! ");
                }finally{
                    closeCrap();
                }
            }
        }catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
    //wait for connection, then display connection information
    private void waitForConnection() throws IOException{
        showMessage("Waiting for someone to connect ...\n");
        connection = server.accept();
        showMessage("Now connected to "+ connection.getInetAddress().getHostName());
    }
    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        showMessage("\n Streams are now setup! \n");
    }
    // during the chat conversation
    private void whileChatting() throws IOException{
        String message = "You are now connected!";
        sendMessage(message);
        ableToType(true);
        do{
            try{
                message = (String) input.readObject();
                showMessage("\n" + message);
            }catch(ClassNotFoundException classNotFoundException){
                showMessage("\n idk wtf that user send!");
            }
        }while(!message.equals("CLIENT - END"));
    }
    private void closeCrap(){
        showMessage("\n Closing connections... \n");
        ableToType(false);
        try{
            output.close();
            input.close();
            connection.close();
        }catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
    private void sendMessage(String message){
        try{
            output.writeObject("SERVER - " + message);
            output.flush();
            showMessage("\n SERVER - " + message);
        }catch(IOException ioException){
            chatWindow.append("\n ERROR: DUDE I CANT SEND THAT MESSAGE");
        }
    }
    //updates chatWindow
    private void showMessage(final String text){
        SwingUtilities.invokeLater(
            new Runnable(){
                public void run(){
                    chatWindow.append(text);
                }
            }
        );  
    }
    //Let the user type stuff into their box
    private void ableToType(final boolean tof){
        SwingUtilities.invokeLater(
            new Runnable(){
                public void run(){
                    userText.setEditable(tof);
                }
            }
        );  
    }
}
