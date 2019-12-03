import javax.crypto.Cipher;
import java.security.SecureRandom;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    Scanner in = new Scanner(System.in);
    String message = "Hello World!";

    private void run() {
        while (true) {
            System.out.println("\n___________________________\n"
                    +"Choose number from menu:\n"
                    +"1) Classic AES-256\n"
                    +"2) Improved AES-256\n"
                    +"3) Enter new string\n"
                    +"0) Exit"
                    +"\n___________________________\n");

            byte answer = in.nextByte();
            switch (answer) {
                case 1: {
                    printClassic();
                    break;
                }

                case 2: {
                    printImproved();
                    break;
                }

                case 3: {
                    enterString();
                    break;
                }

                case 0: {
                    System.exit(0);
                }
            }
        }
    }

    private void printClassic() {
        AES256interface aes256 = new AES256classic();
        for (int i = 0; i < 3; i++) {
            System.out.println( (i+1) + ") " );
            byte[] shifr = aes256.makeAes(message.getBytes(), Cipher.ENCRYPT_MODE);
            //System.out.println(new String(shifr));
            System.out.print("Crypt:    ");
            printCrypt(shifr);

            byte[] src = aes256.makeAes(shifr, Cipher.DECRYPT_MODE);
            //System.out.println(new String(src)+"\n");
            System.out.print("Decrypt:  ");
            printDecrypt(src);
            //System.out.println("\n");
        }
    }

    private void printImproved() {
        AES256interface aes256 = new AES256improved();
        byte[] salt = new byte[8];

        for (int i = 0; i < 3; i++) {
            System.out.println( (i+1) + ") " );

            //Генерация соли
            SecureRandom random = new SecureRandom();
            random.nextBytes(salt);

            //Преобразуем исходный текст в поток байт и добавим полученную соль
            byte[] srcMessage = message.getBytes();
            byte[] fullsrcMessage = new byte[srcMessage.length+8];
            System.arraycopy(srcMessage, 0, fullsrcMessage, 0, srcMessage.length);
            System.arraycopy(salt, 0, fullsrcMessage, srcMessage.length, salt.length);

            //Шифруем
            byte[] shifr = aes256.makeAes(fullsrcMessage, Cipher.ENCRYPT_MODE);
            //System.out.println(new String(shifr));
            System.out.print("Crypt:    ");
            printCrypt(shifr);

            //Дешифруем
            byte[] src = aes256.makeAes(shifr, Cipher.DECRYPT_MODE);
            //System.out.println(new String(src)+"\n");
            System.out.print("Decrypt:  ");
            printDecrypt(src);
            //System.out.println("\n");
        }
    }

    private void enterString() {
        System.out.println("New string:");
        message = in.next();
    }

    private void printCrypt(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            System.out.print(bytes[i]);
        }
        System.out.println("\n");
    }

    private void printDecrypt(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            System.out.print((char)bytes[i]);
        }
        System.out.println("\n");
    }
}