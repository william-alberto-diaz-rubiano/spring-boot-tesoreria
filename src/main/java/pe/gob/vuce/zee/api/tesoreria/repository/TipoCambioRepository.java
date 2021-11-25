package pe.gob.vuce.zee.api.tesoreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.zee.api.tesoreria.models.TipoCambioEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TipoCambioRepository extends JpaRepository<TipoCambioEntity, UUID>,TipoCambioCustomRepository {

}
