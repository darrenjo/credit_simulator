package models;

import java.util.ArrayList;
import java.util.List;

public class SimulationResult {
    public String vehicleType;
    public String vehicleCondition;
    public int vehicleYear;
    public double totalLoan;
    public int loanTenure;
    public double downPayment;
    public List<String> results = new ArrayList<>();
}
