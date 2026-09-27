package lw02.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;


public class Main {
    public static void main(String[] args) {
        File file = new File("transactions.txt");
        if (!file.exists()) {
            file = new File("src/lw02/prelab/transactions.txt");
        }

        // list untuk simpan transaksi dan data customer
        LinkedList<String[]> transactions = new LinkedList<>();
        LinkedList<String[]> customers = new LinkedList<>();

        // baca file transactions.txt
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String name = scanner.next();
                String type = scanner.next();
                String amount = scanner.next();

                transactions.add(new String[]{name, type, amount});

                // cek apakah customer sudah ada di list
                boolean exists = false;
                for (String[] customer : customers) {
                    if (customer[0].equals(name)) {
                        exists = true;
                        break;
                    }
                }

                // kalau belum ada, tambahkan dengan saldo awal 0
                if (!exists) {
                    customers.add(new String[]{name, "0"});
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan");
            return;
        }

        // pindahkan transaksi dari linkedlist ke queue
        Queue<String[]> queue = new LinkedList<>();
        while (!transactions.isEmpty()) {
            queue.add(transactions.poll());
        }

        // stack untuk simpan transaksi gagal
        Stack<String[]> failedTransactions = new Stack<>();

        // proses transaksi di queue (FIFO)
        while (!queue.isEmpty()) {
            String[] tx = queue.poll();
            String name = tx[0];
            String type = tx[1];
            int amount = Integer.parseInt(tx[2]);

            // cari customer yang sesuai
            for (String[] customer : customers) {
                if (customer[0].equals(name)) {
                    int balance = Integer.parseInt(customer[1]);

                    if (type.equals("DEPOSIT")) {
                        balance += amount;
                        customer[1] = String.valueOf(balance);
                    } else if (type.equals("WITHDRAW")) {
                        if (amount > balance) {
                            failedTransactions.push(tx);
                        } else {
                            balance -= amount;
                            customer[1] = String.valueOf(balance);
                        }
                    }
                    break;
                }
            }
        }

        // print saldo akhir tiap customer
        System.out.println("=== Final Balances ===");
        for (String[] customer : customers) {
            System.out.println(customer[0] + " : " + customer[1]);
        }

        System.out.println();

        // print transaksi yang gagal (LIFO)
        System.out.println("=== Failed Transactions ===");
        while (!failedTransactions.isEmpty()) {
            String[] failed = failedTransactions.pop();
            System.out.println(failed[0] + " " + failed[1] + " " + failed[2]);
        }
    }
}
