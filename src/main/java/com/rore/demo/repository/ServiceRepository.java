package com.rore.demo.repository;

import com.rore.demo.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@NoRepositoryBean
public interface ServiceRepository<E extends BaseEntity> extends JpaRepository<E, String>{

    Optional<E> findByOidAndDeleted(String oid, Boolean deleted);
    List<E> findByOidInAndDeleted(Set<String> oids, Boolean deleted);

    List<E> findByDeletedOrderByOidAsc(Boolean deleted);
    List<E> findByActiveAndDeletedOrderByOidAsc(Boolean active,Boolean deleted);

    List<E> findByActiveAndDeleted(Boolean active, Boolean deleted);

    //If Pagination required
    Page<E> findByOidInAndDeleted(Set<String> oids, Boolean deleted, Pageable pageable);

    Page<E> findByDeletedOrderByOidAsc(Boolean deleted, Pageable pageable);
    Page<E> findByActiveAndDeletedOrderByOidAsc(Boolean active,Boolean deleted, Pageable pageable);

    Page<E> findByActiveAndDeleted(Boolean active, Boolean deleted, Pageable pageable);

}