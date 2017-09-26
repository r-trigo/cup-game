/**
 *
 * @author anabela & seksu
 */
package Cup;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anabela & seksu
 */
public class Client {
    private static Scanner sn = new Scanner(System.in);
    static Random ball = new Random();
    static boolean cup1, cup2, cup3 = false;

    public static void main(String[] args) {
        int id;
        try {
            System.out.println("Entrar com o IP Address:");
            String IPaddress = sn.nextLine();
            Socket client = new Socket(IPaddress, 8794);
            DataInputStream fromserver = new DataInputStream(client.getInputStream());
            DataOutputStream toserver = new DataOutputStream(client.getOutputStream());

            // Id do Player
            id = Integer.parseInt(fromserver.readUTF().split(":")[1]);
            System.out.println("Id do Cliente: " + id);

            // Ciclo para correr a pergunta 5 vezes
            for (int i = 0; i < 5; i++) {
                System.out.println("Por favor escolhe um dos Copos (1,2,3)");
                // String para saber o que o player escolheu
                String choise = new Scanner(System.in).nextLine();
                //Envia para o server a escolha
                toserver.writeUTF(choise);
                //Recebe do server o resulta e mostra-o aos players
                String result = fromserver.readUTF();
                System.out.println(result);
            }
            System.out.println(fromserver.readUTF());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
