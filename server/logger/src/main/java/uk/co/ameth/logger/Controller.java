package uk.co.ameth.logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.ameth.logger.downloader.Authenticater;
import uk.co.ameth.logger.downloader.DataLoad;
import uk.co.ameth.logger.parser.JsonParser;
import uk.co.ameth.logger.uploader.DynamoClient;

import java.util.logging.Logger;

@Component
public class Controller {

    private static Logger LOG = Logger.getLogger(Controller.class.getName());
    @Autowired
    private Authenticater authenticater;

    @Autowired
    private DataLoad dataLoad;

    @Autowired
    private JsonParser jsonParser;

    @Autowired
    private DynamoClient dynamoClient;

    public void run(String... args) throws Exception {
        LOG.info("got here");
        authenticater.authenticate();
        dataLoad.load(authenticater.getToken());
        System.out.println("113");
        jsonParser.parseJson(dataLoad.getJsonString());
//        System.out.println("112-"+dataLoad.getJsonString());
        dynamoClient.insertReadings(jsonParser.getReadings());
        System.out.println(jsonParser.getReadings());
    }


}
