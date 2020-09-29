package example.micronaut;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class CommandLedSave {

    private String leds;


    public CommandLedSave() {
    }

    public String getLeds() {
        return leds;
    }

    public void setLeds(String leds) {
        this.leds = leds;
    }

}