package blockchain.util;

import java.io.*;

public class SerializationUtil {

    private static final String FILE_NAME = "blockchain.txt";

    public static void serialize(Object obj) {
        File file = new File(FILE_NAME);
        try (FileOutputStream fos = new FileOutputStream(file, false);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
        } catch (IOException exception) {
            System.out.println("ERROR > Could not serialize object" + exception.getMessage());
        }
    }

    public static Object deserialize() {
        Object obj = null;
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis);
                 ObjectInputStream ois = new ObjectInputStream(bis)) {
                obj = ois.readObject();
            } catch (IOException | ClassNotFoundException exception) {
                System.out.println("ERROR > Could not deserialize object");
            }
        }

        return obj;
    }
}
