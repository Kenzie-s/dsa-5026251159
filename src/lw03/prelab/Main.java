package lw03.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        List<String> playlist = new ArrayList<>();
        File playlistFile = getFile("playlist.txt");

        try (Scanner scanner = new Scanner(playlistFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("ADD ")) {
                    String song = line.substring(4).trim();
                    playlist.add(song);
                } else if (line.startsWith("INSERT ")) {
                    int spaceIndex = line.indexOf(' ', 7);
                    int index = Integer.parseInt(line.substring(7, spaceIndex).trim());
                    String song = line.substring(spaceIndex + 1).trim();
                    playlist.add(index, song);
                } else if (line.startsWith("REMOVE ")) {
                    String song = line.substring(7).trim();
                    playlist.remove(song);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File playlist.txt tidak ditemukan");
        }

        System.out.println("===== Problem 1 =====");
        System.out.println("Total songs: " + playlist.size());
        for (int i = 0; i < playlist.size(); i++) {
            System.out.println((i + 1) + ": " + playlist.get(i));
        }

        System.out.println();

        Set<String> participants = new LinkedHashSet<>();
        int duplicateCount = 0;
        File participantsFile = getFile("participants.txt");

        try (Scanner scanner = new Scanner(participantsFile)) {
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine().trim();
                if (name.isEmpty()) continue;

                if (participants.contains(name)) {
                    duplicateCount++;
                } else {
                    participants.add(name);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File participants.txt tidak ditemukan");
        }

        System.out.println("===== Problem 2 =====");
        System.out.println("Unique participants: " + participants.size());
        int rank = 1;
        for (String name : participants) {
            System.out.println(rank + ". " + name);
            rank++;
        }
        System.out.println("Duplicate registrations: " + duplicateCount);

        System.out.println();

        Map<String, Integer> inventory = new LinkedHashMap<>();
        int failedSales = 0;
        File inventoryFile = getFile("inventory.txt");

        try (Scanner scanner = new Scanner(inventoryFile)) {
            while (scanner.hasNext()) {
                String type = scanner.next();
                String product = scanner.next();
                int qty = scanner.nextInt();

                if (type.equals("ADD")) {
                    if (!inventory.containsKey(product)) {
                        inventory.put(product, qty);
                    } else {
                        inventory.put(product, inventory.get(product) + qty);
                    }
                } else if (type.equals("SELL")) {
                    if (inventory.containsKey(product) && inventory.get(product) >= qty) {
                        inventory.put(product, inventory.get(product) - qty);
                    } else {
                        failedSales++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File inventory.txt tidak ditemukan");
        }

        System.out.println("===== Problem 3 =====");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Failed sales: " + failedSales);
    }

    private static File getFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            file = new File("src/lw03/prelab/" + filename);
        }
        return file;
    }
}
