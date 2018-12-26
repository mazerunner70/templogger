package uk.co.ameth.logger.uploader;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import uk.co.ameth.logger.ConfigReader;
import uk.co.ameth.logger.parser.Readings;
import uk.co.ameth.logger.parser.TempPair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DynamoClient {

    class Mapping {
        public List<String> getMapping() {
            return mapping;
        }

        public void setMapping(List<String> mapping) {
            this.mapping = mapping;
        }

        private List<String> mapping;

        public Map<String, String> asMap() {
            Map<String, String> stringMap = new HashMap<>();
            for (String mapped: mapping) {
                String[] pairs = mapped.split(":", 2);
                stringMap.put(pairs[0], pairs[1]);
            }
            return stringMap;
        }
    }

    @Value("${dynamoDB.tablename}")
    private String dynamoDbTableName;
    @Value("${aws.region}")
    private String awsRegion;


    @Autowired
    ConfigReader configReader;

    private Mapping mapping = new Mapping();

    private Table table = null;

    public void locateTable() {
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard().withRegion(awsRegion).build();
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
        this.table = dynamoDB.getTable(dynamoDbTableName);
    }

    public Item asItem(Readings readings) {
        Item item = new Item()
                .withPrimaryKey("Timestamp", System.currentTimeMillis());
        Map<String, String> mapper = mapping.asMap();
        for (TempPair pair: readings.getTemperatures()) {
            item.withDouble(mapper.get(pair.name), pair.reading);
        }
        return item;
    }

    public void insertReadings(Readings readings) {
        mapping.setMapping(configReader.readProps("logging.mapping"));
        Item item = asItem(readings);
        locateTable();
        PutItemOutcome putItemOutcome = table.putItem(item);
        System.out.println("Outcome:");
        System.out.println(putItemOutcome.toString());
    }


}
