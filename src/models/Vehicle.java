package models;

public abstract class Vehicle {
    protected String condition;
    protected int year;
    protected double totalLoan;
    protected int tenor;
    protected double downPayment;

    public Vehicle(String condition, int year, double totalLoan, int tenor, double downPayment) {
        this.condition = condition;
        this.year = year;
        this.totalLoan = totalLoan;
        this.tenor = tenor;
        this.downPayment = downPayment;
    }

    public int getTenor() {
        return tenor;
    }

    public double getTotalLoan() {
        return totalLoan;
    }

    public double getDownPayment() {
        return downPayment;
    }

    public abstract double getBaseInterestRate();
}
