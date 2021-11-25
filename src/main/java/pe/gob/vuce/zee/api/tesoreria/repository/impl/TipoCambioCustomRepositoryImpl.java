package pe.gob.vuce.zee.api.tesoreria.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.zee.api.tesoreria.models.TipoCambioEntity;
import pe.gob.vuce.zee.api.tesoreria.repository.TipoCambioCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TipoCambioCustomRepositoryImpl implements TipoCambioCustomRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<TipoCambioEntity> busqueda(Integer estado, Integer activo, BigDecimal cambioCompra, BigDecimal cambioVenta, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return busqueda(estado, activo,cambioCompra,cambioVenta,fechaInicio,fechaFin, -1, -1);
    }

    @Override
    public List<TipoCambioEntity> busqueda(Integer estado, Integer activo,BigDecimal cambioCompra,BigDecimal cambioVenta, LocalDateTime fechaInicio, LocalDateTime fechaFin, int offset, int size) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(TipoCambioEntity.class);
        var tipoCambio = cq.from(TipoCambioEntity.class);
        Predicate[] predicatesArray;
        var predicates = new ArrayList<Predicate>();

        if (estado != null) {
            predicates.add(cb.equal(tipoCambio.get("estado"), estado));
        }
        if (cambioCompra != null) {
            predicates.add(cb.equal(tipoCambio.get("cambioCompra"), cambioCompra));
        }
        if (cambioVenta != null) {
            predicates.add(cb.equal(tipoCambio.get("cambioVenta"), cambioVenta));
        }
        if (!((fechaInicio == null) && (fechaFin == null))
        ) {
            predicates.add(cb.between(tipoCambio.get("fechaCreacion"), fechaInicio, fechaFin));
        }

        predicatesArray = predicates.toArray(new Predicate[0]);
        if (!predicates.isEmpty()) {
            cq.where(predicatesArray);
        }

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
    public Page<TipoCambioEntity> busquedaPageable(Integer estado, Integer activo,BigDecimal cambioCompra,BigDecimal cambioVenta, LocalDateTime fechaInicio, LocalDateTime fechaFin, Pageable pageable) {
        var offset = pageable.getPageNumber() * pageable.getPageSize();
        var resultList = busqueda(estado, activo,cambioCompra,cambioVenta,fechaInicio,fechaFin, offset, pageable.getPageSize());
        var count = contar(estado, activo,cambioCompra,cambioVenta,fechaInicio,fechaFin);
        return new PageImpl<>(resultList, pageable, count);
    }

    @Override
    public Long contar(Integer estado, Integer activo,BigDecimal cambioCompra,BigDecimal cambioVenta, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Long.class);
        var tipoCambio = cq.from(TipoCambioEntity.class);

        cq.select(cb.count(tipoCambio));
        Predicate[] predicatesArray;
        var predicates = new ArrayList<Predicate>();

        if (estado != null) {
            predicates.add(cb.equal(tipoCambio.get("estado"), estado));
        }
        if (cambioCompra != null) {
            predicates.add(cb.equal(tipoCambio.get("cambioCompra"), cambioCompra));
        }
        if (cambioVenta != null) {
            predicates.add(cb.equal(tipoCambio.get("cambioVenta"), cambioVenta));
        }
        if (!((fechaInicio == null) && (fechaFin == null))
        ) {
            predicates.add(cb.between(tipoCambio.get("fechaCreacion"), fechaInicio, fechaFin));
        }

        predicatesArray = predicates.toArray(new Predicate[0]);
        if (!predicates.isEmpty()) {
            cq.where(predicatesArray);
        }
        var query = em.createQuery(cq);
        return query.getSingleResult();
    }
}
