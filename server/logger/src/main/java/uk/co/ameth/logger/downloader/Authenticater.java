package uk.co.ameth.logger.downloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.co.ameth.logger.ConfigReader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Component
public class Authenticater {

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

    @Value("${login.url}")
    public String urlString;

    @Value("${login.params}")
    public String loginParams;

    @Value("${usename}")
    public String usename;

    @Value("${password}")
    public String password;

    @Autowired
    ConfigReader configReader;

    private int responseCode=0;
    private String responseString;

    public Token getToken() {
        return token;
    }

    private Token token = null;

    public void dumpAttributes() {
        System.out.println("Attributes:");
        System.out.println("Headers:");
        System.out.println(headers.getHeaders());
        System.out.println("url: "+urlString);
        System.out.println("params: "+loginParams);
    }

    public void authenticate() throws IOException {
        headers.setHeaders(configReader.readProps("auth.headers"));
        URL url = new URL(urlString+'?'+loginParams);
        System.out.println("998"+url.toString());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        headers.assignToHttpUrlConnection(httpURLConnection);
        System.out.println("777");
        httpURLConnection.setDoOutput(true);
        System.out.println("778");
        attachPayload(httpURLConnection.getOutputStream());
        System.out.println("779");
        processResponse(httpURLConnection);
        System.out.println("780");
        generateToken();
    }

    private void generateToken() throws IOException {
        if (responseCode == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            token = objectMapper.readValue(responseString, Token.class);
//            System.out.println(token.toString());
        }
    }


    public void attachPayload(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        String payload = "username="+usename+"&password="+password;
        System.out.println("776"+payload);
        dataOutputStream.writeBytes(payload);
        dataOutputStream.flush();
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
            responseString = stringBuilder.toString();
            System.out.println(responseString);
        } else {
            System.out.println("The server returned nothing useful");
        }
    }

}
