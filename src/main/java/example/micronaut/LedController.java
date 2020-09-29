package example.micronaut;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.views.View;
import rpi.sensehat.api.SenseHat;
import rpi.sensehat.api.dto.Color;

@Controller("/display")
public class LedController {
	
	SenseHat senseHat = new SenseHat();

	@View("led")
	@Get("/create")
	public Map<String, Object> create() {
		senseHat.ledMatrix.clear();
		return createModelWithBlankValues();
	}

	@View("led")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Post("/save")
	public Map<String, Object> save(@Valid @Body CommandLedSave cmd) {
		if(cmd != null) {
			if(cmd.getLeds() != null && !cmd.getLeds().equals("")) {
				String possitions[] = cmd.getLeds().split(",");
				Color[] pixels = createLedMatrix();
				int i = 0;
				int x = 0;
				int y = 0;
				while (i < possitions.length){
					x = Integer.parseInt(possitions[i++]);
					y = Integer.parseInt(possitions[i++]);
					if(x != 0) {
						x = x*8;
					}
					pixels[x+y] = Color.RED;
					
				}
				senseHat.ledMatrix.setPixels(pixels);
			}else {
				senseHat.ledMatrix.clear();
			}
		}

		final Map<String, Object> model = new HashMap<>();
		model.put("leds", cmd.getLeds());
		return model;
	}

	private Map<String, Object> createModelWithBlankValues() {
		final Map<String, Object> model = new HashMap<>();
		model.put("title", "");
		return model;
	}
	
	private Color[] createLedMatrix() {
		Color off = Color.of(0, 0, 0);
		Color[] pixels = new Color[64];
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = off;
		}
		return pixels;
	}

}