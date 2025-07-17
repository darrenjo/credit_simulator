package models;

public abstract class Vehicle {
    protected String condition;
    protected int year;
    protected double totalLoan;
    protected int tenor;
    protected double downPayment;
    protected String vehicleType;

    public Vehicle(String condition, int year, double totalLoan, int tenor, double downPayment) {
        this.condition = condition;
        this.year = year;
        this.totalLoan = totalLoan;
        this.tenor = tenor;
        this.downPayment = downPayment;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String type) {
        this.vehicleType = type;
    }

    public String getCondition() {
        return condition;
    }

    public int getYear() {
        return year;
    }

    public double getTotalLoan() {
        return totalLoan;
    }

    public int getTenor() {
        return tenor;
    }

    public double getDownPayment() {
        return downPayment;
    }

    public abstract double getBaseInterestRate();
}
