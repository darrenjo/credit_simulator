import models.Vehicle;
import services.CreditCalculator;
import services.VehicleFactory;
import utils.InputValidator;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
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
            CreditCalculator.calculate(vehicle);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static String prompt(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty())
                return input;
            System.out.println("Input tidak boleh kosong.");
        }
    }

    private static <T> T promptWithValidation(String message, InputParser<T> parser) {
        while (true) {
            try {
                String input = prompt(message);
                return parser.parse(input);
            } catch (Exception e) {
                System.out.println("❌ " + e.getMessage());
            }
        }
    }

    @FunctionalInterface
    interface InputParser<T> {
        T parse(String input);
    }
}
