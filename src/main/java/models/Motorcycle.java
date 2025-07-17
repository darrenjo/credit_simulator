package models;

public class Motorcycle extends Vehicle {
    public Motorcycle(String condition, int year, double totalLoan, int tenor, double downPayment) {
        super(condition, year, totalLoan, tenor, downPayment);
    }

    @Override
    public double getBaseInterestRate() {
        return 9.0;
    }
}
