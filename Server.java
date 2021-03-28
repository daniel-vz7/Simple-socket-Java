import java.net.*;
import java.io.*;

public class Server {
  public static void main(String args[]) throws IOException {
    // Load data
    BufferedReader reader = new BufferedReader(new FileReader("movies.csv"));
    String line = null;
    int flag = 0;
    String[][] data = new String[78][8];
    // Fill array   
    while ((line = reader.readLine()) != null) {
      String[] fields = line.split(",");
      int pelicula = flag++;
      for (int i = 0; i < fields.length; i++) {
        data[pelicula][i] = fields[i].trim();
      }
    }

    // Start receiving requests
    ServerSocket serverSocket = new ServerSocket(1234);
    System.out.println("Escuchando en el puerto " + 1234);
    while (true) {
      Socket socketInstance = serverSocket.accept(); // Wait and accept a connection
      // Get instruction
      DataInputStream dIn = new DataInputStream(socketInstance.getInputStream());
      byte[] bs = new byte[500];
      dIn.read(bs);  
      String instruction = "";
      for (byte b:bs) {  
        char c = (char)b;  
        instruction += c;
      }
      instruction = instruction.trim();

      int option = Integer.parseInt(instruction.split(",")[0]);
      String filmName = instruction.split(",")[1];
      String response = "";

      // Output streams
      OutputStream s1out = socketInstance.getOutputStream();
      DataOutputStream dos = new DataOutputStream(s1out);

      // Search response
      for (int i = 1; i < data.length; i++) {
        if (data[i][0].equals(filmName)) {
          response = data[i][--option];
          break;
        }
      }
      if (response.equals("")) dos.write(("0,0").getBytes());
      else dos.write(("1," + response).getBytes());

      // Close the connection, but not the server socket
      dos.close();
      s1out.close();
      socketInstance.close();
    }
  }
}
