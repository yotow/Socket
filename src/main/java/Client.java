import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        String host = "netology.homework";
        int port = 8089;

        try (Socket clientSocket = new Socket(host, port);
             DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
             DataInputStream in = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println(readString(in));
            System.out.println(readString(in));
            writeString(out, scanner.nextLine());
            System.out.println(readString(in));

            int lang = scanner.nextInt();
            out.writeInt(lang);

            System.out.println(readString(in));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String readString(DataInputStream in) throws IOException {
        int length = in.readInt();
        byte[] messageByte = new byte[length];
        boolean end = false;
        StringBuilder dataString = new StringBuilder(length);
        int totalBytesRead = 0;
        while (!end) {
            int currentBytesRead = in.read(messageByte);
            totalBytesRead = currentBytesRead + totalBytesRead;
            if (totalBytesRead <= length) {
                dataString
                        .append(new String(messageByte, 0, currentBytesRead));
            } else {
                dataString
                        .append(new String(messageByte, 0, length - totalBytesRead + currentBytesRead));
            }
            if (dataString.length() >= length) {
                end = true;
            }
        }
        return dataString.toString();
    }

    private static void writeString(DataOutputStream out, String s) throws IOException {
        byte[] dataInBytes = s.getBytes();
        out.writeInt(dataInBytes.length);
        out.write(dataInBytes);
    }
}
