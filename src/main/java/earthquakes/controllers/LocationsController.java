package earthquakes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import earthquakes.AuthControllerAdvice;
import earthquakes.entities.Location;
import earthquakes.osm.Place;
import earthquakes.repositories.LocationRepository;
import earthquakes.searches.LocSearch;
import earthquakes.services.LocationQueryService;

@Controller
public class LocationsController {

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    private LocationRepository locationRepository;

    @Autowired
    public LocationsController(LocationRepository lr) {
        System.out.println("ran locations controller");
        this.locationRepository = lr;
    }

    @GetMapping("/locations/search")
    public String getLocationsSearch(Model model, OAuth2AuthenticationToken oAuth2AuthenticationToken,
            LocSearch locSearch) {
        return "locations/search";
    }

    @GetMapping("/locations/results")
    public String getEarthquakesResults(Model model, OAuth2AuthenticationToken oAuth2AuthenticationToken,
            LocSearch locSearch) {
        LocationQueryService l = new LocationQueryService();
        model.addAttribute("locSearch", locSearch);
        String json = l.getJSON(locSearch.getLocation());
        model.addAttribute("json", json);
        List<Place> place = Place.listFromJson(json);
        model.addAttribute("place", place);
        return "locations/results";
    }

    @GetMapping("/locations")
    public String index(Model model, OAuth2AuthenticationToken token) {
        String uid = token.getPrincipal().getAttributes().get("id").toString();
        Iterable<Location> locations = locationRepository.findByUid(uid);

        for (Location l : locations) {
            l.setUid(uid);
            locationRepository.save(l);
        }
        model.addAttribute("locations", locations);
        return "locations/index";
    }

    @GetMapping("/locations/admin")
    public String admin(Model model, OAuth2AuthenticationToken token) {
        String uid = token.getPrincipal().getAttributes().get("id").toString();
        Iterable<Location> locations = locationRepository.findAll();
        model.addAttribute("locations", locations);
        return "locations/admin";
    }

    @PostMapping("/locations/add")
    public String add(Location location, Model model, OAuth2AuthenticationToken token) {
        location.setUid(token.getPrincipal().getAttributes().get("id").toString());
        locationRepository.save(location);
        String uid = token.getPrincipal().getAttributes().get("id").toString();
        Iterable<Location> locations = locationRepository.findByUid(uid);

        for (Location l : locations) {
            l.setUid(uid);
            locationRepository.save(l);
        }
        model.addAttribute("locations", locations);
        return "locations/index";
    }

    @DeleteMapping("/locations/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid courseoffering Id:" + id));
        locationRepository.delete(location);
        model.addAttribute("locations", locationRepository.findAll());
        return "locations/index";
    }
}
