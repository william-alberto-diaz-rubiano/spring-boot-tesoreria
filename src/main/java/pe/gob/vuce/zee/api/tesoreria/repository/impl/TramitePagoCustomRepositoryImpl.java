package pe.gob.vuce.zee.api.tesoreria.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.zee.api.tesoreria.models.TramitePagoEntity;
import pe.gob.vuce.zee.api.tesoreria.repository.TramitePagoCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TramitePagoCustomRepositoryImpl implements TramitePagoCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<TramitePagoEntity> busqueda(Integer estado, Integer activo, Integer tipoTramite, String nombreTramite, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return busqueda(estado,activo,tipoTramite,nombreTramite,fechaInicio,fechaFin,-1,-1);
    }

    @Override
    public List<TramitePagoEntity> busqueda(Integer estado, Integer activo, Integer tipoTramite, String nombreTramite, LocalDateTime fechaInicio, LocalDateTime fechaFin, int offset, int size) {

        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(TramitePagoEntity.class);
        var root = cq.from(TramitePagoEntity.class);
        Predicate[] predicatesArray;
        var predicates = new ArrayList<Predicate>();

        if (estado != null) {
            predicates.add(cb.equal(root.get("estado"), estado));
        }
        if (tipoTramite != null) {
            predicates.add(cb.equal(root.get("configuradorOperacion"), tipoTramite));
        }
        if (nombreTramite != null) {
            predicates.add(cb.equal(root.get("nombrePago"), nombreTramite));
        }
        if (!((fechaInicio == null) && (fechaFin == null))
        ) {
            predicates.add(cb.between(root.get("fechaCreacion"), fechaInicio, fechaFin));
        }

        predicatesArray = predicates.toArray(new Predicate[0]);
        if (!predicates.isEmpty()) {
            cq.where(predicatesArray);
        }
        cq.orderBy(cb.desc(root.get("fechaCreacion")));

        var result = em.createQuery(cq);
        if (offset != -1) {
            result = result.setFirstResult(offset);
        }
        if (size != -1) {
            result = result.setMaxResults(size);
        }
        return result.getResultList();

    }

    @Override
    public Page<TramitePagoEntity> busquedaPageable(Integer estado, Integer activo, Integer tipoTramite, String nombreTramite, LocalDateTime fechaInicio, LocalDateTime fechaFin, Pageable pageable) {
        var offset = pageable.getPageNumber() * pageable.getPageSize();
        var resultList =busqueda(estado,activo,tipoTramite,nombreTramite,fechaInicio,fechaFin,offset,pageable.getPageSize());
        var count =contar(estado,activo,tipoTramite,nombreTramite,fechaInicio,fechaFin);
        return new PageImpl<>(resultList, pageable, count);
    }

    @Override
    public Long contar(Integer estado, Integer activo, Integer tipoTramite, String nombreTramite, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Long.class);
        var root = cq.from(TramitePagoEntity.class);

        cq.select(cb.count(root));
        Predicate[] predicatesArray;
        var predicates = new ArrayList<Predicate>();
        if (estado != null) {
            predicates.add(cb.equal(root.get("estado"), estado));
        }
        if (tipoTramite != null) {
            predicates.add(cb.equal(root.get("configuradorOperacion"), tipoTramite));
        }
        if (nombreTramite != null) {
            predicates.add(cb.equal(root.get("nombrePago"), nombreTramite));
        }
        if (!((fechaInicio == null) && (fechaFin == null))
        ) {
            predicates.add(cb.between(root.get("fechaCreacion"), fechaInicio, fechaFin));
        }

        predicatesArray = predicates.toArray(new Predicate[0]);
        if (!predicates.isEmpty()) {
            cq.where(predicatesArray);
        }

        var query = em.createQuery(cq);
        return query.getSingleResult();
    }
}
