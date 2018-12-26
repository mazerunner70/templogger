package uk.co.ameth.logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConfigReader {

    @Autowired
    private Environment env;

    public List<String> readProps(String propsPrefix) {
        List<String> results = new ArrayList<>();
        int count = Integer.parseInt(env.getProperty(propsPrefix+".count"));
        for (int f = 0 ; f < count; ++f) {
            results.add(env.getProperty(propsPrefix+"["+f+"]"));
        }
        System.out.println("98"+results);
        return results;
    }

}
