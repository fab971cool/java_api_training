package fr.lernejo.navy_battle.FileParser;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ParsingJson
{
    public boolean checkJson(InputStream inputStream, String fileSchema) throws IOException, ValidationException
    {
        String request = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        JSONObject obj = new JSONObject(request);

        // gerer le json avec un schema
        var schemaStream = ParsingJson.class.getResourceAsStream(fileSchema);
        if (schemaStream == null)
            return false;
        JSONObject jsonSchema = new JSONObject( new JSONTokener(schemaStream));
        Schema schema = SchemaLoader.load(jsonSchema);
        try{ // on test la validation de l'objet
            schema.validate(obj);
            return true;
        }
        catch(Exception e) {
            return false;
        }
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
