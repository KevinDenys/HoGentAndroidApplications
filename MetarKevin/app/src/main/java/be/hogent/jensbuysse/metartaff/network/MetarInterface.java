package be.hogent.jensbuysse.metartaff.network;

import be.hogent.jensbuysse.metartaff.models.Metar;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by eothein on 08.11.17.
 */

public interface MetarInterface {


    /**
     * Retourneert The metar for a give ICAO
     * @param icao The abbreviation for the airport
     * @return
     */
    @GET("metar/{ICAO}")
    Call<Metar> retrieveMetar(@Path("ICAO") String icao);
}
