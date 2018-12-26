package uk.co.ameth.logger.parser;

public class TempPair {
    public String name;
    public double reading;

    public TempPair(String name, double reading) {
        this.name = name;
        this.reading = reading;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", reading=" + reading +
                '}';
    }
}
