package test;

import models.Vehicle;
import models.SimulationResult;
import services.CreditCalculator;
import services.VehicleFactory;
import utils.InputValidator;

public class CreditCalculatorTest {

    public static void main(String[] args) {
        System.out.println("Running test for CreditCalculator...");

        Vehicle motor = VehicleFactory.create("Motor", "Baru", 2025, 150000000, 3, 60000000);
        InputValidator.validateVehicle(motor);
        SimulationResult result = CreditCalculator.calculate(motor);

        assert result != null : "Result should not be null";
        assert result.results.size() == 3 : "Should have 3 yearly results";
        assert result.results.get(0).contains("Tahun 1") : "First result should contain Tahun 1";
        assert result.vehicleType.equals("Motor") : "Vehicle type should be Motor";
        assert result.vehicleCondition.equals("Baru") : "Vehicle condition should be Baru";
        assert result.vehicleYear == 2025 : "Vehicle year should be 2025";
        assert result.loanTenure == 3 : "Loan tenure should be 3 years";

        System.out.println("All tests for CreditCalculator passed.");
    }
}
