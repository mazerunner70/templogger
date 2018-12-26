package uk.co.ameth.logger.downloader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import uk.co.ameth.logger.ConfigReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Component
public class DataLoad {

    class Headers {
        public List<String> getHeaders() {
            return headers;
        }

        public void setHeaders(List<String> headers) {
            this.headers = headers;
        }

        private List<String> headers;

        public void assignToHttpUrlConnection(HttpURLConnection httpURLConnection) {
            for (String header: headers) {
                String[] parts = header.split(": ", 2);
                httpURLConnection.setRequestProperty(parts[0], parts[1]);
                System.out.println(parts[0]+"::"+parts[1]+"::"+httpURLConnection.getRequestProperty(parts[0]));
            }
        }
    }



    private Headers headers = new Headers();

    @Value("${retrieve.url}")
    private String urlString;

    @Autowired
    ConfigReader configReader;
    private int responseCode = 0;

    public String getJsonString() {
        return jsonString;
    }

    private String jsonString = null;


    public void load(Token token) throws IOException {
        headers.setHeaders(configReader.readProps("load.headers"));
        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        headers.assignToHttpUrlConnection(httpURLConnection);
        System.out.println("116");
        httpURLConnection.setRequestProperty("Authorization", "Bearer "+token.getAccessToken());
        processResponse(httpURLConnection);

    }

    public void processResponse(HttpURLConnection httpURLConnection) throws IOException {
        InputStream inputStream = null;
        try {
            responseCode = httpURLConnection.getResponseCode();
            System.out.println("Response code:" + responseCode);
            inputStream = httpURLConnection.getInputStream();
        } catch (IOException e) {
            inputStream = httpURLConnection.getErrorStream();
        }
        if (inputStream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            bufferedReader.close();
            String responseString = stringBuilder.toString();
            if (responseCode == 200) {
                jsonString = responseString;
            }
//            System.out.println(responseString);
        } else {
            System.out.println("The server returned nothing useful");
        }
    }


}
