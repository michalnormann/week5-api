package pl.normann.demo.covid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;


@Controller
public class CovidController {

    private Covid[] getStats() {
        RestTemplate restTemplate = new RestTemplate();
        Covid[] covid = restTemplate.getForObject("https://api.covid19api.com/dayone/country/poland/status/confirmed", Covid[].class);
        return covid;
    }

    @GetMapping("/stats/{country}")
    public ResponseEntity<Covid[]> getStatsByCountry(@PathVariable String country) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Covid[]> covid = restTemplate.getForEntity("https://api.covid19api.com/dayone/country/" + country + "/status/confirmed", Covid[].class);
        return covid;
    }

    @GetMapping("/countries")
    public ResponseEntity<Countries[]> getCountries() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Countries[]> countries = restTemplate.getForEntity("https://api.covid19api.com/countries", Countries[].class);
        return countries;
    }

    @GetMapping("/covid")
    public String get(Model model) {
        model.addAttribute("covid", getStats());
        getCountries();
        return "covidStats";
    }
}
