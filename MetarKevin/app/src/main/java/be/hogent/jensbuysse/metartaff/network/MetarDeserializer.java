package be.hogent.jensbuysse.metartaff.network;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

//Kevinimportsjwz

import org.json.*;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import be.hogent.jensbuysse.metartaff.models.Metar;

/**
 * Created by eothein on 08.11.17.
 */

public class MetarDeserializer implements JsonDeserializer<Metar>{
    @Override
    public Metar deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        try{
            JSONObject jsonRaw = new JSONObject(json.toString());
            if(jsonRaw.optString("Error","Geen").equals("Geen")){
                final Metar metar = new Metar();
                JSONObject metaInfo = jsonRaw.getJSONObject("Meta");
                Date x = json2Time(metaInfo.getString("Cache-Timestamp"));
                metar.setWindDirection(jsonRaw.optInt("Wind-Direction",0));
                metar.setSight(jsonRaw.optInt("Visibility",0));
                metar.setDayOfMonth(x.getDay());
                metar.setUitSchieters(jsonRaw.optInt("Wind-Gust",0));
                metar.setWindSpeed(jsonRaw.optInt("Wind-Speed",0));
                metar.setTime(jsonRaw.optString("Time","Unknown"));
                metar.setRawMetar(jsonRaw.optString("Raw-Report","Unknown"));
                metar.setRowName(metaInfo.optString("Cache-Timestamp","Unknown Date"));
                return metar;
            }else{
                return null;
            }

        }catch (JSONException e){
return null;
        }

    }
    private Date json2Time(String s){
        //Looks like: Sat, 06 Jan 2018 15:55:42 GMT
        Date returnDate = null;
        DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH);
        try {
            returnDate = format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnDate;
    }
}
