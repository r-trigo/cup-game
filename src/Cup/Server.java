/**
 *
 * @author anabela & seksu
 */
package Cup;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anabela & seksu
 */
public class Server {

    private static ServerSocket server;
    static Random ball = new Random();

    private static List<Info> infos = new ArrayList<>();
    public static int id = 0;

    public static void main(String[] args) throws IOException {
        try {
            server = new ServerSocket(8794);
            Thread mustUseThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 4; i++) {
                        try {
                            Socket client = server.accept();
                            Info info = new Info();
                            info.id = id;
                            info.fromclient = new DataInputStream(client.getInputStream());
                            info.toclient = new DataOutputStream(client.getOutputStream());
                            //Id do cliente 
                            info.toclient.writeUTF("ready:" + info.id);
                            System.out.println("Cliente Adicionado");
                            infos.add(info);
                            id++;

                        } catch (IOException ex) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
            });
            mustUseThread.start();
            try {

                mustUseThread.join();

            } catch (InterruptedException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

            int player1won = 0;
            int player2won = 0;
            int player3won = 0;
            int player4won = 0;

            // 5 Vezes a jogar 
            for (int i = 0; i < 5; i++) {
                boolean cup1 = false;
                boolean cup2 = false;
                boolean cup3 = false;

                // Encontrar bola aleatoriamente
                double aleatorio = Math.random();
                if (aleatorio <= 0.33) {
                    cup1 = true;
                } else if (aleatorio <= 0.66) {
                    cup2 = true;
                } else {
                    cup3 = true;
                }

                //Mensagem para o Servidor saber onde esta a bola
                System.out.println("Copos em que posição: Copo 1 " + cup1 + " Copo 2 " + cup2 + " Copo 3 " + cup3);

                //Obter informacao do cliente, ou seja, o copo que o cliente escolheu (1,2,3)
                String res1 = infos.get(0).fromclient.readUTF();
                String res2 = infos.get(1).fromclient.readUTF();
                String res3 = infos.get(2).fromclient.readUTF();
                String res4 = infos.get(3).fromclient.readUTF();
                String res = "";
                
                // Quem ganha a ronda                
                if (cup1 == true) {
                    if (res1.equals("1") && res2.equals("1") && res3.equals("1") && res4.equals("1")) {
                        res = "Ronda ganha por ambos os Players";
                        player1won++;
                        player2won++;
                        player3won++;
                        player4won++;
                    } else if (res1.equals("1")) {
                        res = "Ronda ganha pelo Player 1";
                        player1won++;
                    } else if (res2.equals("1")) {
                        res = "Ronda ganha pelo Player 2";
                        player2won++;
                    } else if (res3.equals("1")) {
                        res = "Ronda ganha pelo Player 3";
                        player3won++;
                    } else if (res4.equals("1")) {
                        res = "Ronda ganha pelo Player 4";
                        player4won++;
                    } else {
                        res = "Nenhum dos Players Seleccionou o Certo";
                    }
                } else if (cup2 == true) {
                    if (res1.equals("2") && res2.equals("2") && res3.equals("2") && res4.equals("2")) {
                        res = "Ronda ganha por ambos os Players";
                        player1won++;
                        player2won++;
                        player3won++;
                        player4won++;
                    } else if (res1.equals("2")) {
                        res = "Ronda ganha pelo Player 1";
                        player1won++;
                    } else if (res2.equals("2")) {
                        res = "Ronda ganha pelo Player 2";
                        player2won++;
                    } else if (res3.equals("2")) {
                        res = "Ronda ganha pelo Player 3";
                        player3won++;
                    } else if (res4.equals("2")) {
                        res = "Ronda ganha pelo Player 4";
                        player4won++;
                    } else {
                        res = "Nenhum dos Players Seleccionou o Certo";
                    }
                } else if (cup3 == true) {
                    if (res1.equals("3") && res2.equals("3") && res3.equals("3") && res4.equals("3")) {
                        res = "Ronda ganha por ambos os Players";
                        player1won++;
                        player2won++;
                        player3won++;
                        player4won++;
                    } else if (res1.equals("3")) {
                        res = "Ronda ganha pelo Player 1";
                        player1won++;
                    } else if (res2.equals("3")) {
                        res = "Ronda ganha pelo Player 2";
                        player2won++;
                    } else if (res3.equals("3")) {
                        res = "Ronda ganha pelo Player 3";
                        player3won++;
                    } else if (res4.equals("4")) {
                        res = "Ronda ganha pelo Player 4";
                        player4won++;
                    } else {
                        res = "Nenhum dos Players Seleccionou o Certo";
                    }
                }

                //Resposta aos Players de quem ganhou
                for (Info info : infos) {
                    info.toclient.writeUTF(res);
                }
            }

            //Somatoria de rondas ganhas por cada player
            for (Info info : infos) {
                info.toclient.writeUTF("Ronhas Ganhas pelo Player 1 = " + player1won + "\nRondas Ganhas pelo Plyer 2 = " + player2won + "\nRondas Ganhas pelo Player 3 = " + player3won + "\nRondas Ganhas pelo Player 4 = " + player4won);
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static class Info {

        public int id;
        public DataInputStream fromclient;
        public DataOutputStream toclient;
    }
}
