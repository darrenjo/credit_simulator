package services;

import models.Vehicle;

public class CreditCalculator {
    public static void calculate(Vehicle vehicle) {
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

            System.out.printf("Tahun %d: Rp. %.2f/bln , Suku Bunga: %.1f%%\n", i, monthlyInstallment, interest);
        }
    }
}
