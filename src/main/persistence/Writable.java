package persistence;

import org.json.JSONObject;


//interface with 1 function to implement
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
