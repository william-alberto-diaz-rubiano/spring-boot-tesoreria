package pe.gob.vuce.zee.api.maestros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.zee.api.maestros.models.MaestroEntity;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface MaestroRepository extends JpaRepository<MaestroEntity, UUID> {
    Set<MaestroEntity> findByPrefijoAndEstado(Integer prefijo, Integer estado);
    Optional<MaestroEntity> findByPrefijoAndCorrelativoAndEstado(Integer prefijo, Integer correlativo, Integer estado);
}
