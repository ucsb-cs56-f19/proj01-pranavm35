package earthquakes.osm;

import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.type.TypeReference;


public class Place {
    private static Logger logger = LoggerFactory.getLogger(Place.class);
    public long place_id;
    public double lat, lon;
    public String display_name, type;
    public List<Place> places;

     /**
    * Create a Place List object from json representation
    * 
    * @param json String of json returned by API endpoint {@code /classes/search}
    * @return a new Place List object
    * @see <a href=
    *      "https://tools.ietf.org/html/rfc7946">https://tools.ietf.org/html/rfc7946</a>
    */
    public static List<Place> listFromJson(String json){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<Place> myObjects = objectMapper.readValue(json, new TypeReference<List<Place>>(){});

            //List<Place> myObjects = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Place.class));
            return myObjects;
        } catch (JsonProcessingException jpe) {
            logger.error("JsonProcessingException:" + jpe);
            return null;
        } catch (Exception e) {
            logger.error("Exception:" + e);
            return null;
        }
    }
}