package com.example.networkget.GSON;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Create by 钱俊华 on 2018/12/30 0030
 */
public class GsonListUtil {

    /**
     * 解析Picture的JSON数据，把它转成Picture类
     * @param jsonData
     * @return
     */
    public static ArrayList<Picture> parseJSONWithGSON(String jsonData){
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonData).getAsJsonObject();
        JsonArray array = jsonObject.getAsJsonArray("results");
        Gson gson = new Gson();
        ArrayList<Picture> pictureList = new ArrayList<>();
        for(JsonElement user : array){
            Picture picture = gson.fromJson(user,new TypeToken<Picture>(){}.getType());
            pictureList.add(picture);
        }
        return pictureList;

    }
}
