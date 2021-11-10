package pe.gob.vuce.zee.api.maestros.consumer;

import pe.gob.vuce.zee.api.maestros.dto.SunatResponseDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface SunatAPI {
    @GET("v1/vuce-services/endose/sunatutils/contribuyente")
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    Call<SunatResponseDTO> consultaRuc(@Query("numeroRuc") String ruc);
}
