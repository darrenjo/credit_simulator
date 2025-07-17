package utils;

import models.Vehicle;
import java.time.Year;
import java.util.Set;

public class InputValidator {

    private static final Set<String> VALID_TYPES = Set.of("motor", "mobil");
    private static final Set<String> VALID_CONDITIONS = Set.of("baru", "bekas");

    public static void validateVehicle(Vehicle vehicle) {
        int currentYear = Year.now().getValue();

        if (vehicle.getTenor() < 1 || vehicle.getTenor() > 6) {
            throw new IllegalArgumentException("Tenor harus antara 1 sampai 6 tahun.");
        }

        boolean isNew = vehicle.getCondition().equalsIgnoreCase("Baru");
        if (isNew && vehicle.getYear() < (currentYear - 1)) {
            throw new IllegalArgumentException("Tahun kendaraan baru tidak boleh lebih kecil dari tahun ini - 1.");
        }

        double dp = vehicle.getDownPayment();
        double total = vehicle.getTotalLoan();
        double dpPercentage = (dp / total) * 100;

        if (isNew && dpPercentage < 35) {
            throw new IllegalArgumentException("DP kendaraan baru harus >= 35% dari jumlah pinjaman.");
        } else if (!isNew && dpPercentage < 25) {
            throw new IllegalArgumentException("DP kendaraan bekas harus >= 25% dari jumlah pinjaman.");
        }
    }

    public static String validateVehicleType(String input) {
        input = input.trim().toLowerCase();
        if (!VALID_TYPES.contains(input)) {
            throw new IllegalArgumentException("Jenis kendaraan harus 'Motor' atau 'Mobil'.");
        }
        return input;
    }

    public static String validateVehicleCondition(String input) {
        input = input.trim().toLowerCase();
        if (!VALID_CONDITIONS.contains(input)) {
            throw new IllegalArgumentException("Kondisi kendaraan harus 'Baru' atau 'Bekas'.");
        }
        return input;
    }

    public static int validateYear(String input) {
        try {
            int year = Integer.parseInt(input.trim());
            if (year < 1000 || year > 9999) {
                throw new IllegalArgumentException("Tahun harus 4 digit valid.");
            }
            return year;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Tahun harus berupa angka 4 digit yang valid.");
        }
    }

    public static int validateTenor(String input) {
        try {
            int tenor = Integer.parseInt(input.trim());
            if (tenor < 1 || tenor > 6) {
                throw new IllegalArgumentException("Tenor harus 1 sampai 6 tahun.");
            }
            return tenor;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Tenor harus berupa angka antara 1 sampai 6.");
        }
    }

    public static double validatePositiveDouble(String input, String label) {
        try {
            double value = Double.parseDouble(input.trim());
            if (value <= 0) {
                throw new IllegalArgumentException(label + " harus lebih dari 0.");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(label + " harus berupa angka yang valid.");
        }
    }
}
