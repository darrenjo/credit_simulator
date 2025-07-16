package services;

import models.SimulationResult;
import models.Vehicle;

public class CreditCalculator {
    public static SimulationResult calculate(Vehicle vehicle) {

        SimulationResult sim = new SimulationResult();

        sim.vehicleType = vehicle.getVehicleType();
        sim.vehicleCondition = vehicle.getCondition();
        sim.vehicleYear = vehicle.getYear();
        sim.totalLoan = vehicle.getTotalLoan();
        sim.loanTenure = vehicle.getTenor();
        sim.downPayment = vehicle.getDownPayment();

        double interest = vehicle.getBaseInterestRate();

        for (int i = 1; i <= vehicle.getTenor(); i++) {
            if (i % 2 == 0)
                interest += 0.5;
            else
                interest += 0.1;

            double monthlyInterestRate = interest / 100.0 / 12;
            double principal = vehicle.getTotalLoan() - vehicle.getDownPayment();
            double monthlyInstallment = (principal * monthlyInterestRate)
                    / (1 - Math.pow(1 + monthlyInterestRate, -i * 12));

            String result = String.format("Tahun %d: Rp. %.2f/bln , Suku Bunga: %.1f%%", i, monthlyInstallment,
                    interest);
            sim.results.add(result);
            System.out.println(result);
        }
        return sim;
    }
}
