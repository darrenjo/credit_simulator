package services;

import models.SimulationResult;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CommandHandler {

    public static void handleCommand(String command) {
        if (command.equalsIgnoreCase("show")) {
            showSimulationResult();
            return;
        }

        if (command.toLowerCase().startsWith("save ")) {
            String sheetName = command.substring(5).trim();
            if (sheetName.isEmpty()) {
                System.out.println("Please provide a sheet name. Usage: save <sheetName>");
                return;
            }
            saveSheet(sheetName);
            return;
        }

        if (command.equalsIgnoreCase("load")) {
            ApiService.loadFromApi();
            return;
        }

        System.out.println("Command not found. Available commands: show, save <name>, load");
    }

    private static void showSimulationResult() {
        SimulationResult result = SimulationState.lastResult;
        if (result == null) {
            System.out.println("No simulation result available.");
            return;
        }

        System.out.println("\n=== SIMULATION RESULT ===");
        System.out.println("Vehicle Type: " + result.vehicleType);
        System.out.println("Vehicle Condition: " + result.vehicleCondition);
        System.out.println("Vehicle Year: " + result.vehicleYear);
        System.out.println("Total Loan Amount: Rp. " + String.format("%,.0f", result.totalLoan));
        System.out.println("Down Payment: Rp. " + String.format("%,.0f", result.downPayment));
        System.out.println("Loan Tenure: " + result.loanTenure + " years");
        System.out.println("\n=== MONTHLY INSTALLMENTS ===");

        for (String monthlyResult : result.results) {
            System.out.println(monthlyResult);
        }

        double loanAmount = result.totalLoan - result.downPayment;
        System.out.println("\nLoan Amount (after DP): Rp. " + String.format("%,.0f", loanAmount));
    }

    private static void saveSheet(String name) {
        SimulationResult result = SimulationState.lastResult;
        if (result == null) {
            System.out.println("No simulation result available. Please run a simulation first.");
            return;
        }

        try {
            // Create sheets folder if it doesn't exist
            File folder = new File("sheets");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Create file with .txt extension
            File file = new File(folder, name + ".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            // Write header
            writer.write("=== CREDIT SIMULATION RESULT ===\n");
            writer.write("Generated on: " + java.time.LocalDateTime.now().toString() + "\n");
            writer.write("\n");

            // Write vehicle details
            writer.write("VEHICLE INFORMATION:\n");
            writer.write("Vehicle Type: " + result.vehicleType + "\n");
            writer.write("Vehicle Condition: " + result.vehicleCondition + "\n");
            writer.write("Vehicle Year: " + result.vehicleYear + "\n");
            writer.write("\n");

            // Write loan details
            writer.write("LOAN INFORMATION:\n");
            writer.write("Total Loan Amount: Rp. " + String.format("%,.0f", result.totalLoan) + "\n");
            writer.write("Down Payment: Rp. " + String.format("%,.0f", result.downPayment) + "\n");
            writer.write("Loan Amount: Rp. " + String.format("%,.0f", (result.totalLoan - result.downPayment)) + "\n");
            writer.write("Loan Tenure: " + result.loanTenure + " years\n");
            writer.write("\n");

            // Write monthly installments
            writer.write("MONTHLY INSTALLMENTS:\n");
            for (String line : result.results) {
                writer.write(line + "\n");
            }

            writer.close();
            System.out.println("Simulation saved successfully to: sheets/" + name + ".txt");

        } catch (IOException e) {
            System.out.println("Failed to save file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error occurred while saving: " + e.getMessage());
        }
    }
}