package pe.gob.vuce.zee.api.tesoreria.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.zee.api.tesoreria.models.RegistroComprobanteEntity;
import pe.gob.vuce.zee.api.tesoreria.repository.RegistroComprobanteCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class RegistroComprobanteCustomRepositoryImpl implements RegistroComprobanteCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<RegistroComprobanteEntity> busqueda(UUID id, UUID estado, Integer activo, String serie, UUID comprobante) {
        return busqueda(id,estado,activo,serie,comprobante,-1,-1);
    }

    @Override
    public List<RegistroComprobanteEntity> busqueda(UUID id, UUID estado, Integer activo, String serie, UUID comprobante, int offset, int size) {

        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(RegistroComprobanteEntity.class);
        var root = cq.from(RegistroComprobanteEntity.class);

        Predicate[] predicatesArray;
        var predicates = new ArrayList<Predicate>();

        if (id != null) {
            predicates.add(cb.equal(root.get("id"), id));
        }
        if (estado != null) {
            predicates.add(cb.equal(root.get("estado").get("id"), estado));
        }
        if (comprobante != null) {
            predicates.add(cb.equal(root.get("codigoComprobante").get("id"), comprobante));
        }
        if (serie != null) {
            predicates.add(cb.equal(root.get("codigoSerie"), serie));
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
    public Page<RegistroComprobanteEntity> busquedaPageable(UUID id, UUID estado, Integer activo, String serie, UUID comprobante, Pageable pageable) {

        var offset = pageable.getPageNumber() * pageable.getPageSize();
        var resultList =busqueda(id,estado,activo,serie,comprobante,offset,pageable.getPageSize());
        var count =contar(id,estado,activo,serie,comprobante);
        return new PageImpl<>(resultList, pageable, count);
    }

    @Override
    public Long contar(UUID id, UUID estado, Integer activo, String serie, UUID comprobante) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Long.class);
        var root = cq.from(RegistroComprobanteEntity.class);

        cq.select(cb.count(root));
        Predicate[] predicatesArray;
        var predicates = new ArrayList<Predicate>();

        if (id != null) {
            predicates.add(cb.equal(root.get("id"), id));
        }
        if (estado != null) {
            predicates.add(cb.equal(root.get("estado").get("id"), estado));
        }
        if (comprobante != null) {
            predicates.add(cb.equal(root.get("codigoComprobante").get("id"), comprobante));
        }
        if (serie != null) {
            predicates.add(cb.equal(root.get("codigoSerie"), serie));
        }

        predicatesArray = predicates.toArray(new Predicate[0]);
        if (!predicates.isEmpty()) {
            cq.where(predicatesArray);
        }
        var query = em.createQuery(cq);
        return query.getSingleResult();
    }
}
