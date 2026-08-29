package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class DataDriver {
    public static JsonNode jsonReader() throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readTree(
                new File("src/test/resources/testData.json")
        );
    }
}
