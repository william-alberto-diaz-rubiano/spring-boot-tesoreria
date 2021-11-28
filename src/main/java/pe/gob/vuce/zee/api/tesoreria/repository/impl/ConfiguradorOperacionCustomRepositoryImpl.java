package pe.gob.vuce.zee.api.tesoreria.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.tesoreria.models.ConfiguradorOperacionEntity;
import pe.gob.vuce.zee.api.tesoreria.models.TipoCambioEntity;
import pe.gob.vuce.zee.api.tesoreria.repository.ConfiguradorOperacionCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConfiguradorOperacionCustomRepositoryImpl implements ConfiguradorOperacionCustomRepository {
    @PersistenceContext
    private EntityManager em;



    @Override
    public List<ConfiguradorOperacionEntity> busqueda(Integer estado, Integer activo, Integer tramite, Integer operacion) {
        return busqueda(estado,activo, tramite, operacion, -1,-1 );
    }

    @Override
    public List<ConfiguradorOperacionEntity> busqueda(Integer estado, Integer activo, Integer tramite, Integer operacion, int offset, int size) {

        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(ConfiguradorOperacionEntity.class);
        var root = cq.from(ConfiguradorOperacionEntity.class);
        Predicate[] predicatesArray;
        var predicates = new ArrayList<Predicate>();

        if (estado != null) {
            predicates.add(cb.equal(root.get("estado"), estado));
        }
        if (tramite != null) {
            predicates.add(cb.equal(root.get("tramite"), tramite));
        }
        if (operacion != null) {
            predicates.add(cb.equal(root.get("operacion"), operacion));
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
    public Page<ConfiguradorOperacionEntity> busquedaPageable(Integer estado, Integer activo, Integer tramite, Integer operacion, Pageable pageable) {
        var offset = pageable.getPageNumber() * pageable.getPageSize();
        var resultList = busqueda(estado,activo, tramite, operacion,offset,pageable.getPageSize());
        var count = contar(estado, activo,tramite,operacion);
        return new PageImpl<>(resultList, pageable, count);
    }

    @Override
    public Long contar(Integer estado, Integer activo, Integer tramite, Integer operacion) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Long.class);
        var root = cq.from(TipoCambioEntity.class);

        cq.select(cb.count(root));
        Predicate[] predicatesArray;
        var predicates = new ArrayList<Predicate>();

        if (estado != null) {
            predicates.add(cb.equal(root.get("estado"), estado));
        }
        if (tramite != null) {
            predicates.add(cb.equal(root.get("tramite"), tramite));
        }
        if (operacion != null) {
            predicates.add(cb.equal(root.get("operacion"), operacion));
        }

        predicatesArray = predicates.toArray(new Predicate[0]);
        if (!predicates.isEmpty()) {
            cq.where(predicatesArray);
        }
        var query = em.createQuery(cq);
        return query.getSingleResult();
    }
}
