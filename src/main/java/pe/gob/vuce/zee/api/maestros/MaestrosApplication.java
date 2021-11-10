package pe.gob.vuce.zee.api.maestros;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import pe.gob.vuce.zee.api.maestros.config.IdentidadConfig;
import pe.gob.vuce.zee.api.maestros.consumer.ReniecAPI;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@SpringBootApplication
@EnableConfigurationProperties
public class MaestrosApplication extends SpringBootServletInitializer {
	private final IdentidadConfig identidadConfig;

	public MaestrosApplication(IdentidadConfig identidadConfig) {
		this.identidadConfig = identidadConfig;
	}

	public static void main(String[] args) {
		SpringApplication.run(MaestrosApplication.class, args);
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

}
