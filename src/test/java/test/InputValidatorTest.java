package test;

import models.Vehicle;
import services.VehicleFactory;
import utils.InputValidator;

public class InputValidatorTest {

    public static void main(String[] args) {
        System.out.println("Running test for InputValidator...");

        // ✅ Valid - Kendaraan baru, DP cukup, tenor valid
        Vehicle validNew = VehicleFactory.create("Motor", "Baru", 2025, 100000000, 3, 40000000);
        InputValidator.validateVehicle(validNew);
        System.out.println("Test for new vehicle passed.");

        // ❌ Invalid - Tenor > 6
        try {
            Vehicle overTenor = VehicleFactory.create("Motor", "Bekas", 2025, 100000000, 7, 30000000);
            InputValidator.validateVehicle(overTenor);
            System.out.println("Failed: Tenor > 6 should be rejected.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test for tenor > 6 passed.");
        }

        // ❌ Invalid - Tahun kendaraan baru terlalu tua
        try {
            int yearTooOld = java.time.Year.now().getValue() - 2;
            Vehicle oldNew = VehicleFactory.create("Mobil", "Baru", yearTooOld, 200000000, 3, 100000000);
            InputValidator.validateVehicle(oldNew);
            System.out.println("Failed: year of new vehicle too old should be rejected.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test for year of new vehicle too old passed.");
        }

        // ❌ Invalid - DP < 35% untuk kendaraan baru
        try {
            Vehicle lowDpNew = VehicleFactory.create("Mobil", "Baru", 2025, 200000000, 3, 60000000); // 30%
            InputValidator.validateVehicle(lowDpNew);
            System.out.println("Failed: DP < 35% for new vehicle should be rejected.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test for DP < 35% for new vehicle passed.");
        }

        // ❌ Invalid - DP < 25% kendaraan lama
        try {
            Vehicle lowDpOld = VehicleFactory.create("Motor", "Bekas", 2025, 100000000, 2, 20000000); // 20%
            InputValidator.validateVehicle(lowDpOld);
            System.out.println("Failed: DP < 25% for old vehicle should be rejected.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test for DP < 25% for old vehicle passed.");
        }

        // ✅ Valid - DP tepat 25% untuk kendaraan lama
        Vehicle exactDpOld = VehicleFactory.create("Motor", "Bekas", 2025, 100000000, 2, 25000000);
        InputValidator.validateVehicle(exactDpOld);
        System.out.println("Test for DP = 25% for old vehicle passed.");

        System.out.println("All tests for InputValidator passed.");
    }
}
