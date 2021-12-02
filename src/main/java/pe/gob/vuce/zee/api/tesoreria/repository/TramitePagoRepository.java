package pe.gob.vuce.zee.api.tesoreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.zee.api.tesoreria.models.TramitePagoEntity;

import java.util.UUID;

@Repository
public interface TramitePagoRepository extends JpaRepository<TramitePagoEntity, UUID>,TramitePagoCustomRepository {
}
