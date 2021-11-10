package pe.gob.vuce.zee.api.maestros.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.gob.vuce.zee.api.maestros.consumer.ReniecAPI;
import pe.gob.vuce.zee.api.maestros.consumer.SunatAPI;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class CommonBeans {
    private final IdentidadConfig identidadConfig;

    public CommonBeans(IdentidadConfig identidadConfig) {
        this.identidadConfig = identidadConfig;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean(name = "reniecApiConsumer")
    public ReniecAPI getReniecApi(){
        var retrofit = new Retrofit.Builder()
                .baseUrl(identidadConfig.getConsultaDniUrl())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        return retrofit.create(ReniecAPI.class);
    }

    @Bean(name = "sunatApiConsumer")
    public SunatAPI getSunatApi(){
        var retrofit = new Retrofit.Builder()
                .baseUrl(identidadConfig.getConsultaRucUrl())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        return retrofit.create(SunatAPI.class);
    }
}
