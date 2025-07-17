import models.SimulationResult;
import models.Vehicle;
import services.CreditCalculator;
import services.SimulationState;
import services.VehicleFactory;
import services.CommandHandler;
import utils.InputValidator;
import utils.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0) {
            // File Mode
            handleFileInput(args[0]);
        } else {
            // Interactive Mode
            runInteractiveMode();
        }
    }

    private static void runInteractiveMode() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            showMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    runManualInput();
                    break;
                case "2":
                    runApiInput();
                    break;
                case "3":
                    showAvailableCommands();
                    // Don't exit here - continue the loop to show menu again
                    break;
                case "4":
                    System.out.println("Exiting application...");
                    scanner.close();
                    return; // Exit the application
                default:
                    System.out.println("Invalid choice. Please select 1-4.");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n=== CREDIT SIMULATOR MENU ===");
        System.out.println("1. Manual data input");
        System.out.println("2. Load from API");
        System.out.println("3. Show available commands");
        System.out.println("4. Exit");
        System.out.print("Select menu [1-4]: ");
    }

    private static void showAvailableCommands() {
        System.out.println("\n=== AVAILABLE COMMANDS ===");
        System.out.println("Available Commands:");
        System.out.println("- show : Show last simulation result");
        System.out.println("- save <sheetName> : Save the last simulation result");
        System.out.println("- load : Load data from API");
        System.out.println("- exit : Exit application");

        // Wait for user to press Enter before returning to menu
        System.out.print("\nPress Enter to return to main menu...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    private static void runApiInput() {
        try {
            System.out.println("\n=== LOADING FROM API ===");
            services.ApiService.loadFromApi();

            // After API load, show result management commands (no load option)
            runResultCommands(false); // false = don't show load option

        } catch (Exception e) {
            System.out.println("Error loading from API: " + e.getMessage());
        }
    }

    private static void handleFileInput(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine());
            }
            scanner.close();

            String fileContent = sb.toString().trim();
            if (fileContent.startsWith("{")) {
                // JSON Object Mode
                System.out.println("\n=== PROCESSING FILE INPUT ===");
                Map<String, String> data = JsonParser.parse(fileContent);
                Vehicle vehicle = VehicleFactory.create(
                        data.get("vehicleType"),
                        data.get("vehicleCondition"),
                        Integer.parseInt(data.get("vehicleYear")),
                        Double.parseDouble(data.get("totalLoanAmount")),
                        Integer.parseInt(data.get("loanTenure")),
                        Double.parseDouble(data.get("downPayment")));

                InputValidator.validateVehicle(vehicle);
                SimulationResult result = CreditCalculator.calculate(vehicle);
                SimulationState.lastResult = result;

                // After file input, show result management commands (no load option)
                runResultCommands(false); // false = don't show load option

            } else {
                // Command Mode
                CommandHandler.handleCommand(fileContent);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        } catch (Exception e) {
            System.out.println("Error processing file: " + e.getMessage());
        }
    }

    private static void runManualInput() {
        try {
            System.out.println("\n=== MANUAL INPUT MODE ===");

            String type = promptWithValidation("Masukkan Jenis Kendaraan (Motor/Mobil): ",
                    InputValidator::validateVehicleType);
            String condition = promptWithValidation("Masukkan Kondisi Kendaraan (Baru/Bekas): ",
                    InputValidator::validateVehicleCondition);
            int year = promptWithValidation("Masukkan Tahun Kendaraan (yyyy): ", InputValidator::validateYear);
            double loan = promptWithValidation("Masukkan Jumlah Pinjaman Total: ",
                    input -> InputValidator.validatePositiveDouble(input, "Jumlah Pinjaman"));
            int tenor = promptWithValidation("Masukkan Tenor Pinjaman (1-6): ", InputValidator::validateTenor);
            double dp = promptWithValidation("Masukkan Jumlah DP: ",
                    input -> InputValidator.validatePositiveDouble(input, "Jumlah DP"));

            Vehicle vehicle = VehicleFactory.create(type, condition, year, loan, tenor, dp);
            InputValidator.validateVehicle(vehicle);

            SimulationResult result = CreditCalculator.calculate(vehicle);
            SimulationState.lastResult = result;

            // After manual input, show result management commands (no load option)
            runResultCommands(false); // false = don't show load option

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void runResultCommands(boolean showLoadOption) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("\nSelect command (show/save");
            if (showLoadOption) {
                System.out.print("/load");
            }
            System.out.print("/exit): ");

            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Returning to main menu...");
                break;
            }

            // If load command is used but not allowed, show error
            if (input.equalsIgnoreCase("load") && !showLoadOption) {
                System.out.println("Load command not available in this context. Use 'Load from API' in main menu.");
                continue;
            }

            CommandHandler.handleCommand(input);
        }
    }

    private static String prompt(String message) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty())
                return input;
            System.out.println("Input cannot be empty.");
        }
    }

    private static <T> T promptWithValidation(String message, InputParser<T> parser) {
        while (true) {
            try {
                String input = prompt(message);
                return parser.parse(input);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FunctionalInterface
    interface InputParser<T> {
        T parse(String input);
    }
}