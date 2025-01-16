import org.json.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PolynomialSolver {

    public static void main(String[] args) {
        try {
           
            String jsonData = new String(Files.readAllBytes(Paths.get("input.json")));
            JSONObject jsonObject = new JSONObject(jsonData);

            JSONArray keys = jsonObject.getJSONArray("keys");
            Map<Integer, Integer> points = new HashMap<>();

            for (int i = 0; i < keys.length(); i++) {
                JSONObject point = keys.getJSONObject(i);
                int x = point.getInt("x");
                int base = point.getInt("base");
                String encodedValue = point.getString("value");
                int y = Integer.parseInt(encodedValue, base);
                points.put(x, y);
            }

          
            double constantTerm = lagrangeInterpolation(points);

            
            try (PrintWriter writer = new PrintWriter("output.txt")) {
                writer.println("The constant term (c) of the polynomial is: " + constantTerm);
            }

            System.out.println("Output written to output.txt");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double lagrangeInterpolation(Map<Integer, Integer> points) {
        double result = 0.0;

        for (Map.Entry<Integer, Integer> entry1 : points.entrySet()) {
            int x1 = entry1.getKey();
            int y1 = entry1.getValue();

            double term = y1;
            for (Map.Entry<Integer, Integer> entry2 : points.entrySet()) {
                int x2 = entry2.getKey();
                if (x1 != x2) {
                    term *= (0 - x2) / (double) (x1 - x2);
                }
            }
            result += term;
        }

        return result;
    }
}
