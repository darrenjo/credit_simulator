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

        double baseInterest = vehicle.getBaseInterestRate();
        double principal = vehicle.getTotalLoan() - vehicle.getDownPayment();

        // Calculate for each year with cumulative interest rate increases
        for (int year = 1; year <= vehicle.getTenor(); year++) {
            // Start with base interest rate
            double currentInterest = baseInterest;

            // Add cumulative increases for each completed year
            for (int i = 1; i < year; i++) {
                if (i % 2 == 0) {
                    currentInterest += 0.5; // 0.5% increase every 2 years
                } else {
                    currentInterest += 0.1; // 0.1% increase every odd year
                }
            }

            // Calculate monthly payment using standard loan formula
            double monthlyInterestRate = currentInterest / 100.0 / 12;
            int totalMonths = year * 12;

            double monthlyInstallment = (principal * monthlyInterestRate)
                    / (1 - Math.pow(1 + monthlyInterestRate, -totalMonths));

            String result = String.format("Tahun %d: Rp. %.2f/bln , Suku Bunga: %.1f%%",
                    year, monthlyInstallment, currentInterest);
            sim.results.add(result);
            System.out.println(result);
        }
        return sim;
    }
}
