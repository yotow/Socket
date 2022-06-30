import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        System.out.println("Server started");
        int port = 8089;

        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket clientSocket = serverSocket.accept();
             DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
             DataInputStream in = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()))) {

            System.out.printf("New connection accepted. Port %d%n", clientSocket.getPort());
            writeString(out, String.format("Hi, you port is %d", clientSocket.getPort()));
            writeString(out, "Write your name");

            final String name = readString(in);

            writeString(out, name + " choose language\n1) Rus\n2) En");

            int lang = in.readInt();

            if (lang == 1) {
                writeString(out, "Добро пожаловать");
            } else if (lang == 2) {
                writeString(out, "Welcome");
            } else writeString(out, "exit");

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
