package pe.gob.vuce.zee.api.tesoreria.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.zee.api.tesoreria.models.ConceptoPagoEntity;
import pe.gob.vuce.zee.api.tesoreria.repository.ConceptoPagoCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ConceptoPagoCustomRepositoryImpl implements ConceptoPagoCustomRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<ConceptoPagoEntity> busqueda(UUID id, UUID estado, Integer activo, UUID concepto, String nombreConcepto, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return busqueda(id,estado,activo,concepto, nombreConcepto,fechaInicio,fechaFin,-1,-1);
    }

    @Override
    public List<ConceptoPagoEntity> busqueda(UUID id, UUID estado, Integer activo, UUID concepto, String nombreConcepto, LocalDateTime fechaInicio, LocalDateTime fechaFin, int offset, int size) {

        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(ConceptoPagoEntity.class);
        var root = cq.from(ConceptoPagoEntity.class);

        Predicate[] predicatesArray;
        var predicates = new ArrayList<Predicate>();

        if (id != null) {
            predicates.add(cb.equal(root.get("id"), id));
        }
        if (estado != null) {
            predicates.add(cb.equal(root.get("estado").get("id"), estado));
        }
        if (concepto != null) {
            predicates.add(cb.equal(root.get("configuradorOperacion").get("tramite").get("id"), concepto));
        }
        if (nombreConcepto != null) {
            predicates.add(cb.equal(root.get("nombreConcepto"), nombreConcepto));
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
    public Page<ConceptoPagoEntity> busquedaPageable(UUID id, UUID estado, Integer activo, UUID concepto, String nombreConcepto, LocalDateTime fechaInicio, LocalDateTime fechaFin, Pageable pageable) {

        var offset = pageable.getPageNumber() * pageable.getPageSize();
        var resultList =busqueda(id,estado,activo,concepto,nombreConcepto,fechaInicio,fechaFin,offset,pageable.getPageSize());
        var count =contar(id,estado,activo,concepto,nombreConcepto,fechaInicio,fechaFin);
        return new PageImpl<>(resultList, pageable, count);
    }

    @Override
    public Long contar(UUID id, UUID estado, Integer activo, UUID concepto, String nombreConcepto, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Long.class);
        var root = cq.from(ConceptoPagoEntity.class);

        cq.select(cb.count(root));
        Predicate[] predicatesArray;
        var predicates = new ArrayList<Predicate>();

        if (id != null) {
            predicates.add(cb.equal(root.get("id"), id));
        }
        if (estado != null) {
            predicates.add(cb.equal(root.get("estado").get("id"), estado));
        }
        if (concepto != null) {
            predicates.add(cb.equal(root.get("configuradorOperacion").get("tramite").get("id"), concepto));
        }
        if (nombreConcepto != null) {
            predicates.add(cb.equal(root.get("nombreConcepto"), nombreConcepto));
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
