package uk.co.ameth.logger.parser;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JsonParser {


    public Readings getReadings() {
        return readings;
    }

    private Readings readings = null;

    public void parseJson(String jsonString) throws IOException {
        JsonNode rootNode = getRootNode(jsonString);
        readings = extractTemperatures(rootNode);

    }

    private Readings extractTemperatures(JsonNode rootNode) {
        JsonNode nodes = rootNode.path("nodes");
        Readings readings = new Readings();
        for (JsonNode node: nodes) {
            String nodeName = node.get("name").asText();
            JsonNode tempNode = node.at("/features/temperature_sensor_v1/temperature");
            if (!tempNode.isMissingNode()) {
//                System.out.println(nodeName+":"+ tempNode.get("displayValue"));
                readings.addReading(nodeName, tempNode.get("displayValue").asDouble());
            }
        }
        return readings;
    }

    private JsonNode getRootNode(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(jsonString);
    }

}
