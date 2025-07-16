package services;

import models.SimulationResult;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CommandHandler {

    public static void handleCommand(String command) {
        if (command.equalsIgnoreCase("show")) {
            System.out.println("Available Commands:");
            System.out.println("- show : Show all command");
            System.out.println("- save <sheetName> : Save the last simulation result");
            System.out.println("- load : Load data from API");
            return;
        }

        if (command.toLowerCase().startsWith("save ")) {
            String sheetName = command.substring(5).trim();
            saveSheet(sheetName);
            return;
        }

        if (command.equalsIgnoreCase("load")) {
            ApiService.loadFromApi();
            return;
        }

        System.out.println("Command not found.");
    }

    private static void saveSheet(String name) {
        SimulationResult result = SimulationState.lastResult;
        if (result == null) {
            System.out.println("No simulation result available.");
            return;
        }

        try {
            File folder = new File("sheets");
            if (!folder.exists())
                folder.mkdirs();

            File file = new File(folder, name + ".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            writer.write("# Credit simulation\n");
            writer.write("vehicleType: " + result.vehicleType + "\n");
            writer.write("vehicleCondition: " + result.vehicleCondition + "\n");
            writer.write("vehicleYear: " + result.vehicleYear + "\n");
            writer.write("totalLoanAmount: " + String.format("%.0f", result.totalLoan) + "\n");
            writer.write("loanTenure: " + result.loanTenure + "\n");
            writer.write("downPayment: " + String.format("%.0f", result.downPayment) + "\n");
            writer.write("" + "\n");
            writer.write("# Yearly payment:\n");

            for (String line : result.results) {
                writer.write(line + "\n");
            }

            writer.close();
            System.out.println("Simulation saved to: sheets/" + name + ".txt");
        } catch (IOException e) {
            System.out.println("Failed to save file: " + e.getMessage());
        }
    }
}
