package lw01.unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File file = new File("washes.txt");
        if (!file.exists()) {
            file = new File("src/lw01/unguided/washes.txt");
        }

        try (Scanner scanner = new Scanner(file)) {
            int count = scanner.nextInt();
            WashService[] washes = new WashService[count];

            for (int i = 0; i < count; i++) {
                String type = scanner.next();
                String id = scanner.next();
                int days = scanner.nextInt();
                int units = scanner.nextInt();

                if (type.equalsIgnoreCase("MOTORCYCLE")) {
                    washes[i] = new MotorcycleWash(id, days, units);
                } else if (type.equalsIgnoreCase("CAR")) {
                    washes[i] = new CarWash(id, days, units);
                }
            }

            for (WashService wash : washes) {
                System.out.println(wash.summary());
            }
        } catch (FileNotFoundException e) {
            System.err.println("File washes.txt tidak ditemukan: " + e.getMessage());
        }
    }
}
