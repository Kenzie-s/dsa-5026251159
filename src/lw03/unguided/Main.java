package lw03.unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Set<String> registered = new LinkedHashSet<>();

        Scanner regScanner = new Scanner(getFile("registrations.txt"));
        while (regScanner.hasNextLine()) {
            String id = regScanner.nextLine().trim();
            if (id.isEmpty()) continue;
            registered.add(id);
        }
        regScanner.close();

        Set<String> checkedIn = new LinkedHashSet<>();

        List<String> results = new ArrayList<>();
        int rejected = 0;

        Scanner checkScanner = new Scanner(getFile("checkins.txt"));
        while (checkScanner.hasNextLine()) {
            String id = checkScanner.nextLine().trim();
            if (id.isEmpty()) continue;

            if (!registered.contains(id)) {
                results.add(id + ": Rejected (not registered)");
                rejected++;
            } else if (checkedIn.contains(id)) {
                results.add(id + ": Rejected (already checked in)");
                rejected++;
            } else {
                checkedIn.add(id);
                results.add(id + ": Checked in");
            }
        }
        checkScanner.close();

        System.out.println("===== Event Check-In Results =====");
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i));
        }

        System.out.println();
        System.out.println("===== Final Event Summary =====");
        System.out.println("Registered students: " + registered.size());
        System.out.println("Successful check-ins: " + checkedIn.size());
        System.out.println("Absent students: " + (registered.size() - checkedIn.size()));
        System.out.println("Rejected attempts: " + rejected);
    }

    private static File getFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            file = new File("src/lw03/unguided/" + filename);
        }
        if (!file.exists()) {
            file = new File("dsa-5026251159/src/lw03/unguided/" + filename);
        }
        return file;
    }
}
