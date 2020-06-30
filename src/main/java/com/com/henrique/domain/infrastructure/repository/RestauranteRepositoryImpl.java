package com.com.henrique.domain.infrastructure.repository;

import com.com.henrique.domain.model.Restaurante;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RestauranteRepositoryImpl implements com.com.henrique.domain.repository.RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager em;

//    @Override
//    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
//
//        String jpql = "from Restaurante where nome like :nome and taxaFrete between :taxaFreteInicial and :taxaFreteFinal";
//
//        return em.createQuery(jpql,Restaurante.class)
//                .setParameter("nome","%" + nome + "%")
//                .setParameter("taxaFreteInicial", taxaFreteInicial)
//                .setParameter("taxaFreteFinal", taxaFreteFinal).getResultList();
//
//    }

    @Override
    public List<Restaurante> findCriteriaQuery(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        List<Predicate> predicates = new ArrayList<>();

        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteria.from(Restaurante.class);

        if(StringUtils.hasText(nome)){
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }

        if(taxaFreteInicial != null){
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"),taxaFreteInicial));
        }

        if (taxaFreteFinal != null){
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"),taxaFreteFinal));
        }

        criteria.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Restaurante> query = em.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public List<Restaurante> findComJPQL(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){

        StringBuilder jpql = new StringBuilder();
        jpql.append("from Restaurante where 0 = 0 ");

        Map<String,Object> parametros = new HashMap<>();


        if (StringUtils.hasLength(nome)) {
            jpql.append("and nome like :nome ");
            parametros.put("nome", "%" + nome + "%");
        }

        if (taxaFreteInicial != null) {
            jpql.append("and taxaFrete >= :taxaInicial ");
            parametros.put("taxaInicial", taxaFreteInicial);
        }

        if (taxaFreteFinal != null) {
            jpql.append("and taxaFrete <= :taxaFinal ");
            parametros.put("taxaFinal", taxaFreteFinal);
        }

        TypedQuery<Restaurante> query = em
                .createQuery(jpql.toString(), Restaurante.class);

        parametros.forEach((chave, valor) -> query.setParameter(String.valueOf(chave), valor));

        return query.getResultList();

    }

}
