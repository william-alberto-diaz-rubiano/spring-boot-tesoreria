package pe.gob.vuce.zee.api.maestros.consumer;

import pe.gob.vuce.zee.api.maestros.dto.ReniecResponseDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ReniecAPI {
    @GET("pide/Reniec/Consultar")
    @Headers({"Accept: application/json", "Host: landing-desa.vuce.gob.pe"})
    Call<ReniecResponseDTO> consultaDni(@Query("nuDniConsulta") String dni, @Query("aplicacion") String aplicacion);
}
