package pe.gob.vuce.zee.api.tesoreria.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "identidad", ignoreInvalidFields = true)
public class IdentidadConfig {
    private String consultaDniUrl;
    private String consultaRucUrl;
    private String consultaCeUrl;
}
