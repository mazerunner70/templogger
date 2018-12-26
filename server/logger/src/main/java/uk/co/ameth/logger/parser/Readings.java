package uk.co.ameth.logger.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Readings {

    public List<TempPair> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(List<TempPair> temperatures) {
        this.temperatures = temperatures;
    }

    private List<TempPair> temperatures = new ArrayList<>();

    public void addReading(String name, double reading){
        temperatures.add(new TempPair(name, reading));
    }

    @Override
    public String toString() {
      return  "temperatures=" + temperatures.stream().map(
                    tempPair->tempPair.toString()
                ).collect(Collectors.joining(", "))+
            '}';
    }
}
