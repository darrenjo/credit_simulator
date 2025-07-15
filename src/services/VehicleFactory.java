package services;

import models.*;

public class VehicleFactory {
    public static Vehicle create(String type, String condition, int year, double totalLoan, int tenor,
            double downPayment) {
        if (type.equalsIgnoreCase("Mobil")) {
            return new Car(condition, year, totalLoan, tenor, downPayment);
        } else if (type.equalsIgnoreCase("Motor")) {
            return new Motorcycle(condition, year, totalLoan, tenor, downPayment);
        } else {
            throw new IllegalArgumentException("Tipe kendaraan tidak dikenal.");
        }
    }
}
