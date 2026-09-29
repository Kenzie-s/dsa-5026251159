package lw02.unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        File file = new File("orders.txt");


        LinkedList<String[]> orders = new LinkedList<>();

        LinkedList<String[]> foodStock = new LinkedList<>();
        foodStock.add(new String[]{"Bakso", "2"});
        foodStock.add(new String[]{"Sate", "1"});
        foodStock.add(new String[]{"Soto", "2"});

        LinkedList<String[]> drinkStock = new LinkedList<>();
        drinkStock.add(new String[]{"EsTeh", "4"});
        drinkStock.add(new String[]{"EsJeruk", "2"});

        LinkedList<String[]> successfulOrders = new LinkedList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String name = scanner.next();
                String sideDish = scanner.next();
                String drink = scanner.next();
                String table = scanner.next();

                orders.add(new String[]{name, sideDish, drink, table});
            }
        } catch (FileNotFoundException e) {
            System.out.println("File orders.txt tidak ditemukan");
            return;
        }

        Queue<String[]> queue = new LinkedList<>();
        while (!orders.isEmpty()) {
            queue.add(orders.poll());
        }

        Stack<String[]> failedOrders = new Stack<>();

        while (!queue.isEmpty()) {
            String[] order = queue.poll();
            String foodName = order[1];
            String drinkName = order[2];

            boolean foodAvailable = true;
            String[] targetFood = null;
            if (!foodName.equals("-")) {
                for (String[] food : foodStock) {
                    if (food[0].equals(foodName)) {
                        targetFood = food;
                        if (Integer.parseInt(food[1]) <= 0) {
                            foodAvailable = false;
                        }
                        break;
                    }
                }
            }

            boolean drinkAvailable = true;
            String[] targetDrink = null;
            if (!drinkName.equals("-")) {
                for (String[] drink : drinkStock) {
                    if (drink[0].equals(drinkName)) {
                        targetDrink = drink;
                        if (Integer.parseInt(drink[1]) <= 0) {
                            drinkAvailable = false;
                        }
                        break;
                    }
                }
            }

            if (foodAvailable && drinkAvailable) {
                if (targetFood != null) {
                    int currentFoodStock = Integer.parseInt(targetFood[1]);
                    targetFood[1] = String.valueOf(currentFoodStock - 1);
                }

                if (targetDrink != null) {
                    int currentDrinkStock = Integer.parseInt(targetDrink[1]);
                    targetDrink[1] = String.valueOf(currentDrinkStock - 1);
                }

                successfulOrders.add(order);
            } else {
                failedOrders.push(order);
            }
        }

        System.out.println("=== Successfully Processed Orders ===");
        for (String[] order : successfulOrders) {
            System.out.println(order[0] + " " + order[1] + " " + order[2] + " " + order[3]);
        }

        System.out.println();

        System.out.println("=== Remaining Food Stock ===");
        for (String[] food : foodStock) {
            System.out.println(food[0] + " : " + food[1]);
        }

        System.out.println();

        System.out.println("=== Remaining Drink Stock ===");
        for (String[] drink : drinkStock) {
            System.out.println(drink[0] + " : " + drink[1]);
        }

        System.out.println();


        System.out.println("=== Failed Orders ===");
        while (!failedOrders.isEmpty()) {
            String[] failed = failedOrders.pop();
            System.out.println(failed[0] + " " + failed[1] + " " + failed[2] + " " + failed[3]);
        }
    }
}
