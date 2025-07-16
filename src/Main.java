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
        Scanner scanner;

        if (args.length > 0) {
            // File Mode
            try {
                scanner = new Scanner(new File(args[0]));
                StringBuilder sb = new StringBuilder();
                while (scanner.hasNextLine()) {
                    sb.append(scanner.nextLine());
                }
                scanner.close();

                String fileContent = sb.toString().trim();
                if (fileContent.startsWith("{")) {
                    // JSON Object Mode
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

                    Scanner sc = new Scanner(System.in);
                    while (true) {
                        System.out.print("\nKetik command (show/save/load/exit): ");
                        String input = sc.nextLine().trim();
                        if (input.equalsIgnoreCase("exit")) {
                            System.out.println("Keluar dari simulasi ✅");
                            break;
                        }
                        CommandHandler.handleCommand(input);
                    }
                } else {
                    // Command Mode
                    CommandHandler.handleCommand(fileContent);
                }

            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + args[0]);
            } catch (Exception e) {
                System.out.println("Error processing file: " + e.getMessage());
            }

        } else {
            scanner = new Scanner(System.in);

            System.out.println("MENU:");
            System.out.println("1. Manual data input");
            System.out.println("2. Load from API");
            System.out.println("3. Show available command (show)");
            System.out.println("4. Exit");
            System.out.print("Select menu [1-4]: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    runManualInput(scanner);
                    break;
                case "2":
                    services.ApiService.loadFromApi();
                    break;
                case "3":
                    services.CommandHandler.handleCommand("show");
                    break;
                case "4":
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-4.");
            }
            scanner.close();
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

    private static void runManualInput(Scanner scanner) {
        try {
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

            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.print("\nSelect command (show/save/load/exit): ");
                String input = sc.nextLine().trim();
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Application exited.");
                    break;
                }
                CommandHandler.handleCommand(input);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FunctionalInterface
    interface InputParser<T> {
        T parse(String input);
    }
}
