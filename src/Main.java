import models.Vehicle;
import services.CreditCalculator;
import services.VehicleFactory;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Masukkan Jenis Kendaraan (Motor/Mobil): ");
        String type = scanner.nextLine();

        System.out.println("Masukkan Kondisi Kendaraan (Baru/Bekas): ");
        String condition = scanner.nextLine();

        System.out.println("Masukkan Tahun Kendaraan (yyyy): ");
        int year = Integer.parseInt(scanner.nextLine());

        System.out.println("Masukkan Jumlah Pinjaman Total: ");
        double loan = Double.parseDouble(scanner.nextLine());

        System.out.println("Masukkan Tenor Pinjaman (1-6): ");
        int tenor = Integer.parseInt(scanner.nextLine());

        System.out.println("Masukkan Jumlah DP: ");
        double dp = Double.parseDouble(scanner.nextLine());

        try {
            Vehicle vehicle = VehicleFactory.create(type, condition, year, loan, tenor, dp);
            CreditCalculator.calculate(vehicle);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}
