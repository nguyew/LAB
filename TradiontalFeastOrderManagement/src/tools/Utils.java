package tools;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Utils {
    public static final int MIN=1;
    public static final int MAX=200;
    private final Scanner sc = new Scanner(System.in);
    
  
    public static String getString(String welcome) {
        boolean check = true;
        String result = "";
        do {
            Scanner sc = new Scanner(System.in);
            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.out.println("Input text!!!");
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static String updateString(String welcome, String oldData) {
        String result = oldData;
        Scanner sc = new Scanner(System.in);
        System.out.print(welcome);
        String tmp = sc.nextLine();
        if (!tmp.isEmpty()) {
            result = tmp;
        }
        return result;
    }

    public int getInt(String msg) {
        int num;
        while (true) {
            try {
                System.out.print(msg);
                num = Integer.parseInt(sc.nextLine().trim());
                return num;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    public static int updateInt(String welcome, int min, int max, int oldData) {
        boolean check = true;
        int number = oldData;
        do {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.print(welcome);
                String tmp = sc.nextLine();
                if (tmp.isEmpty()) {
                    check = false;
                } else {
                    number = Integer.parseInt(tmp);
                    check = false;
                }
            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number > max || number < min);
        return number;
    }

    public static boolean confirmYesNo(String welcome) {
        boolean result = false;
        String confirm = Utils.getString(welcome);
        if ("Y".equalsIgnoreCase(confirm)) {
            result = true;
        }
        return result;
    }

    public double getDouble(String msg) {
        double num;
        while (true) {
            try {
                System.out.print(msg);
                num = Double.parseDouble(sc.nextLine().trim());
                return num;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    public String inputAndLoop(String msg, String pattern) {
        String data;
        while (true) {
            System.out.print(msg);
            data = sc.nextLine().trim();
            if (Acceptable.isValid(data, pattern)) {
                return data;
            }
            System.out.println("Invalid input. Please try again.");
        }
    }
    
    public static <T> List<T> readFromFile (String filePath) throws FileNotFoundException, IOException, ClassNotFoundException {
        List<T> list = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return list;
        
        try (FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (fis.available() > 0) {
                T obj = (T) ois.readObject();
                list.add(obj);
            }
        } catch (EOFException eof) {
            
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return list;
    }
    
    public static <T> void saveToFile (List<T> list, String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (T obj : list) {
                oos.writeObject(obj);
            }
            System.out.println("");
        } catch (Exception e) {
        }
    }
}
