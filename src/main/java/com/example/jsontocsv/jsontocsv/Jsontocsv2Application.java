package com.example.jsontocsv.jsontocsv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class Jsontocsv2Application {

	public static void main(String[] args) {
		SpringApplication.run(Jsontocsv2Application.class, args);
		// Specify the input JSON file path
        String jsonFilePath = "C:\\csvTest\\test1.json";

        // Specify the output CSV file path
        String csvFilePath = "C:\\csvTest\\test1.csv";

        // Convert JSON to CSV
        convertJsonToCsv(jsonFilePath, csvFilePath);

        System.out.println("Conversion completed successfully.");
		
	}
	
	private static void convertJsonToCsv(String jsonFilePath, String csvFilePath) {
        try {
            // Read JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(new File(jsonFilePath));

            // Prepare CSV writer
            try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFilePath))) {
                // Write CSV header
                csvWriter.writeNext(getHeaders(jsonNode));

                // Write JSON data to CSV
                writeJsonNodeToCsv(jsonNode, csvWriter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] getHeaders(JsonNode jsonNode) {
        // Extract JSON field names as CSV headers
        List<String> headerList = new ArrayList<>();
        jsonNode.fieldNames().forEachRemaining(headerList::add);
        return headerList.toArray(new String[0]);
    }

    private static void writeJsonNodeToCsv(JsonNode jsonNode, CSVWriter csvWriter) {
        // Write JSON data to CSV
        jsonNode.forEach(node -> {
            String[] rowData = new String[node.size()];
            int index = 0;
            for (JsonNode field : node) {
                rowData[index++] = field.isTextual() ? field.asText() : field.toString();
            }
            csvWriter.writeNext(rowData);
        });
    }

}
