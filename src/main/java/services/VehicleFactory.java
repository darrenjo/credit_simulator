package services;

import models.*;

public class VehicleFactory {
    public static Vehicle create(String vehicleType, String condition, int year, double totalLoan,
            int tenor, double downPayment) {

        Vehicle v;

        if (vehicleType.equalsIgnoreCase("Mobil")) {
            v = new Car(condition, year, totalLoan, tenor, downPayment);
        } else if (vehicleType.equalsIgnoreCase("Motor")) {
            v = new Motorcycle(condition, year, totalLoan, tenor, downPayment);
        } else {
            throw new IllegalArgumentException("Unknown vehicle type.");
        }

        v.setVehicleType(vehicleType);

        return v;
    }
}
