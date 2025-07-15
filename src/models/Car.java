package models;

public class Car extends Vehicle {
    public Car(String condition, int year, double totalLoan, int tenor, double downPayment) {
        super(condition, year, totalLoan, tenor, downPayment);
    }

    @Override
    public double getBaseInterestRate() {
        return 8.0;
    }
}
