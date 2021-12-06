package pe.gob.vuce.zee.api.tesoreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.gob.vuce.zee.api.tesoreria.models.UitEntity;

import java.util.UUID;

public interface UitRepository extends JpaRepository<UitEntity, UUID> {
}
