package me.ohvalsgod.spectre.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.UUID;

public class MojangAPI {

    public String getSignatureByUUID(UUID uuid) {
        String signature = "";
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", ""));
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            String jsonR = EntityUtils.toString(entity);

            Gson gson = new Gson();
            JsonObject json = (JsonObject) new JsonParser().parse(jsonR);
            signature = json.get("textures").getAsString();
        } catch (IOException e) {
            signature = "null";
        }
        return signature;
    }

}
