package earthquakes.services;

import java.util.Arrays;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

public class LocationQueryService {

    private Logger logger = LoggerFactory.getLogger(LocationQueryService.class);

    public String getJSON(String location) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity("body", headers);

        String uri = "https://nominatim.openstreetmap.org/search/"+location+"?format=json";

        String url = uri ;
        logger.info("url=" + url);

        String retVal="";
        try {   
            ResponseEntity<String> re = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
             MediaType contentType = re.getHeaders().getContentType();
            HttpStatus statusCode = re.getStatusCode();
            System.out.println("body: "+ re.getHeaders().getContentType());
            retVal = re.getBody();
        } catch (HttpClientErrorException e) {
            retVal = "{\"error\": \"401: Unauthorized\"}";
        }
        logger.info("from EarthquakeQueryService.getJSON: " + retVal);
        return retVal;
    }

}