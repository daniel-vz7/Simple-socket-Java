// SimpleClient.java: a simple client program
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
  public static void main(String args[]) throws IOException {
    Scanner input = new Scanner(System.in);
    Scanner input2 = new Scanner(System.in);
    // Display menu
    System.out.println("Ingresa el dato que deseas obtener");
    System.out.println("1. Nombre de pelicula");
    System.out.println("2. Genero");
    System.out.println("3. Estudio");
    System.out.println("4. Puntuacion de audiencia");
    System.out.println("5. Ganancia");
    System.out.println("6. Rotten Tomatoes");
    System.out.println("7. Ganancia global");
    System.out.println("8. Año");
    // Enter option
    System.out.println("Ingresa el dato a obtener");
    String option = input.next();
    // Enter film name
    System.out.println("Ingresa el nombre de la pelicula");
    String filmName = input2.nextLine();
    input.close();
    input2.close();

    // Preparing socket and data
    Socket clientSocket = new Socket("localhost", 1234);
    String instruction = option.trim() + ',' + filmName.trim();
    DataOutputStream dataToSend = new DataOutputStream(clientSocket.getOutputStream());
    dataToSend.writeUTF(instruction);
    dataToSend.flush();

    // Receive response
    InputStream inputStream = clientSocket.getInputStream();
    DataInputStream responseStream = new DataInputStream(inputStream);
    String payload = new String(responseStream.readUTF());
    String operation = payload.split(",")[0];
    String response = payload.split(",")[1];
    if (operation.equals("1")) System.out.println("La respuesta es: " + response);
    else System.out.println("No se encontro la pelicula");

    // Close streams and connection
    responseStream.close();
    inputStream.close();
    clientSocket.close();
  }
}
