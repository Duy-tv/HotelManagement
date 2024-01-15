package Data;

import Bussiness.HotelInformation;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * This class provides methods for loading and saving hotel information data to
 * and from a file.
 */
public class DataFile {

    public boolean loadData(List<HotelInformation> list, String fileName) {
        list.clear();
        File f = new File(fileName);
        if (!f.exists()) {
            return false;
        }
        try (FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis)) {

            if (f.length() == 0) {
                System.err.println("File is empty");
            }

            boolean check = true;
            while (check) {
                try {
                    HotelInformation c = (HotelInformation) ois.readObject();
                    list.add(c);
                } catch (EOFException e) {
                    break;
                }
            }
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
            return false;
        } catch (IOException | ClassNotFoundException e) {
            if (f.length() != 0) {
                System.err.println("Error reading from file: " + fileName + " " + e);
                return false;
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing double value from input.");
            return false;
        }
        return true;
    }

    public boolean saveData(List<HotelInformation> list, String fileName, String msg) {

        try {

            File f = new File(fileName);
            if (!f.exists()) {
                System.out.println("Empty list");
                return false;
            }

            
            try (FileOutputStream fos = new FileOutputStream(f)) {
                ObjectOutputStream fileOut = new ObjectOutputStream(fos);
                for (HotelInformation item : list) {
                    fileOut.writeObject(item);
                }
                fileOut.close();
                fos.close();
                System.out.println(msg);
            }
        } catch (IOException e) {
            System.out.println(e);

        }
        return false;
    }

}
