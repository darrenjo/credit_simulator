package services;

import models.SimulationResult;
import models.Vehicle;
import utils.InputValidator;
import utils.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class ApiService {
    public static void loadFromApi() {
        try {
            URL url = new URL("https://run.mocky.io/v3/9108b1da-beec-409e-ae14-e8091955666c");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line.trim());
            }
            reader.close();

            String responseJson = sb.toString();

            // ✅ Manually parse JSON using our own JsonParser
            Map<String, String> data = JsonParser.parse(responseJson);

            Vehicle vehicle = VehicleFactory.create(
                    data.get("vehicleType"),
                    data.get("vehicleCondition"),
                    Integer.parseInt(data.get("vehicleYear")),
                    Double.parseDouble(data.get("totalLoanAmount")),
                    Integer.parseInt(data.get("loanTenor")),
                    Double.parseDouble(data.get("downPayment")));

            InputValidator.validateVehicle(vehicle);
            SimulationResult result = CreditCalculator.calculate(vehicle);
            SimulationState.lastResult = result;

        } catch (Exception e) {
            System.out.println("Failed to load API: " + e.getMessage());
        }
    }
}
