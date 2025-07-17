package test;

import models.Vehicle;
import models.SimulationResult;
import services.CreditCalculator;
import services.VehicleFactory;
import utils.InputValidator;

public class MobilCalculationTest {

    public static void main(String[] args) {
        System.out.println("Testing Mobil calculation for 3 years...");

        // Create a Mobil (Car) with 3 year tenor
        Vehicle mobil = VehicleFactory.create("Mobil", "Baru", 2025, 200000000, 3, 80000000);
        InputValidator.validateVehicle(mobil);

        System.out.println("Expected rates: Year 1: 8.0%, Year 2: 8.1%, Year 3: 8.6%");
        System.out.println("Actual results:");

        SimulationResult result = CreditCalculator.calculate(mobil);

        System.out.println("Test completed!");
    }
}
