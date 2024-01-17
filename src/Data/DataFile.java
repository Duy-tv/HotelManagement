package Data;

import Bussiness.HotelInformation;
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

    /**
     * Loads data from a file into the provided list of HotelInformation
     * objects. Clears the existing list before loading the data.
     *
     * @param list The list to populate with loaded data.
     * @param fileName The name of the file from which to load data.
     * @return True if data loading is successful, false otherwise.
     */
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

            while (fis.available() > 0) {
                HotelInformation c = (HotelInformation) ois.readObject();
                list.add(c);
                /*
                 * Condition available() checks whether there are still bytes available to be
                 * read from the file.
                 * If there are still bytes available to be read -> fis.available() will return
                 * a positive value greater than 0.
                 * Else fis.available() will return 0 and the loop exits.
                 */
            }

            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
            return false;
        } catch (IOException | ClassNotFoundException e) {
            if (f.length() != 0) {
                System.err.println("Error reading from file: " + fileName);
                return false;
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing double value from input.");
            return false;
        }
        return true;
    }

    /**
     * Saves data from the list into a file.
     *
     * @param list The list containing HotelInformation objects to be saved.
     * @param fileName The name of the file to which the data will be saved.
     * @param msg The message to be displayed upon successful data saving.
     * @return True if data saving is successful, false otherwise.
     */
    public boolean saveData(List<HotelInformation> list, String fileName, String msg) {

        try {

            File f = new File(fileName);
            if (!f.exists()) {
                System.out.println("Empty list");
                return false;
            }

            try (FileOutputStream fos = new FileOutputStream(f);
                    ObjectOutputStream oos = new ObjectOutputStream(fos)) {

                for (HotelInformation item : list) {
                    oos.writeObject(item);
                }
                oos.close();
                fos.close();
                System.out.println(msg);
            }
        } catch (IOException e) {
            System.out.println(e);

        }
        return false;
    }

}
