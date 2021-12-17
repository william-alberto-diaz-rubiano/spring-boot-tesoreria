package pe.gob.vuce.zee.api.tesoreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.gob.vuce.zee.api.tesoreria.models.MaestroEntity;

import java.util.UUID;

public interface MaestroRepository extends JpaRepository<MaestroEntity, UUID> {
    MaestroEntity findByPrefijoAndCorrelativo(Integer prefijo, Integer correlativo);
}
