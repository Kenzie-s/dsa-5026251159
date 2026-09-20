package lw01.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        File file = findJobsFile();
        Scanner scanner = null;
        try {
            if (file != null && file.exists()) {
                scanner = new Scanner(file);
            } else {
                InputStream is = Main.class.getResourceAsStream("jobs.txt");
                if (is != null) {
                    scanner = new Scanner(is);
                } else {
                    scanner = new Scanner(new File("jobs.txt"));
                }
            }

            List<PrintJob> jobs = new ArrayList<>();

            while (scanner.hasNext()) {
                String type = scanner.next();
                String id = scanner.next();
                int pages = scanner.nextInt();

                if (type.equalsIgnoreCase("MONO")) {
                    jobs.add(new MonoPrint(id, pages));
                } else if (type.equalsIgnoreCase("COLOUR")) {
                    jobs.add(new ColourPrint(id, pages));
                }
            }

            for (PrintJob job : jobs) {
                System.out.println(job.summary());
            }
        } catch (FileNotFoundException e) {
            System.err.println("File jobs.txt tidak ditemukan: " + e.getMessage());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private static File findJobsFile() {
        String[] candidatePaths = {
            "jobs.txt",
            "src/lw01/prelab/jobs.txt",
            "lw01/prelab/jobs.txt",
            "dsa-5026251159/src/lw01/prelab/jobs.txt",
            "C:/A. User Files/A. Kuliah/Semester 3/ASD/Prelab 1/dsa-5026251159/src/lw01/prelab/jobs.txt"
        };

        for (String path : candidatePaths) {
            File f = new File(path);
            if (f.exists()) {
                return f;
            }
        }
        return null;
    }
}
