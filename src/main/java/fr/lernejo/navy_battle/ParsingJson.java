package fr.lernejo.navy_battle;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ParsingJson
{
    public ParsingJson()
    {

    }

    /*
    *
    *
    * */
    public boolean checkJson(InputStream inputStream) throws IOException, ValidationException
    {
        if (inputStream == null)
            return false;
        String request = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        JSONObject obj = new JSONObject(request);

        // gerer le json avec un schema
        /*JSONObject jsonSchema = new JSONObject(
            new JSONTokener(Objects.requireNonNull(ParsingJson.class.getResourceAsStream("../schema/schema1.json"))));

        Schema schema = SchemaLoader.load(jsonSchema);
        try{
            schema.validate(obj);
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }*/

        return (obj.has("id") && obj.has("url") && obj.has("message"));
    }


    public String generateJsonToString(String id, String url, String message)
    {
        var result = new JSONObject();
        result.put("id", id);
        result.put("url", url);
        result.put("message", message);
        
        return result.toString();
    }
}
