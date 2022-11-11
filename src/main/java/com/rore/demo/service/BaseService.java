package com.rore.demo.service;

import com.rore.demo.dto.BaseDTO;
import com.rore.demo.dto.common.GetListByOidSetRequestBodyDTO;
import com.rore.demo.entity.BaseEntity;
import com.rore.demo.exception.ApiRequestException;
import com.rore.demo.repository.ServiceRepository;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Data
public abstract class BaseService<E extends BaseEntity, D extends BaseDTO> {

    private final ServiceRepository<E> repository;
    private final ModelMapper modelMapper;

    public D getByOid(BaseDTO requestDTO) {
        return convertForRead(getById(requestDTO.getOid()));
    }

    public D getDtoById(String id) {
        return convertForRead(getById(id));
    }

    public List<D> getList() {
        return convertForRead(repository.findAll());
    }

    public List<D> getActiveList() {
        //String txt = getDtoClass().getSimpleName();
        return convertForRead(repository.findByActiveAndDeletedOrderByOidAsc(Boolean.TRUE,Boolean.FALSE));
    }

    public List<D> getListByIdSet(GetListByOidSetRequestBodyDTO requestDTO) {

        List<E> eList = repository.findAllById(requestDTO.getOids())
                .stream()
                .filter(e -> e.getDeleted().equals(Boolean.FALSE))
                .collect(Collectors.toList());

        handleStrictness(requestDTO.getOids(), eList, requestDTO.getStrict());

        return convertForRead(eList);
    }

    public List<D> getListByIdSet(Set<String> ids) {

        List<D> dList = repository.findAllById(ids)
                .stream()
                .filter(e -> e.getDeleted().equals(Boolean.FALSE))
                .map(e -> convertForRead(e))
                .collect(Collectors.toList());

        return dList;
    }

    private void handleStrictness(Set<String> oids, List<E> eList, String strict) {
        if (strict != null && strict.equals(Boolean.TRUE) && eList.size() < oids.size()) {
            Map<String, Boolean> idMap = new HashMap<>();
            List<String> missingList = new ArrayList<>();

            oids.forEach(l -> idMap.put(l, false));
            eList.forEach(e -> idMap.put(e.getOid().toString(), true));
            idMap.keySet().stream().filter(l -> !idMap.get(l)).forEach(missingList::add);

            throw new ApiRequestException( "No "
                    + String.join(" ", getEntityClass().getSimpleName().split("(?=\\p{Upper})"))
                    + " Found for IDs : " + String.join(",", missingList.toString())
            );

        }
    }

    public D create(D requestDTO) {
        E e = convertForCreate(requestDTO);
        //e.setOid(requestDTO.getBody().getOid());
        // TODO: Remove Hard-Coded IDs
        //e.setCreatedBy("System");
        e.setCreatedOn(new Date());
        e.setDeleted(Boolean.FALSE);
        E createdEntity = repository.save(e);

        log.info("Data create {}",createdEntity);
        return convertForRead(createdEntity);
    }

    public List<D> createAll(List<D> bodyList) {
        List<E> eList = convertForCreate(bodyList);
        eList.forEach(e -> {
            //e.setCreatedBy("System");
            e.setCreatedOn(new Date());
            e.setDeleted(Boolean.FALSE);
        });
        List<E> createdEntities = repository.saveAll(eList);

        log.info("Data createAll {}",createdEntities);
        return convertForRead(createdEntities);
    }

    public D update(D requestDTO) {
        if (requestDTO.getOid() == null)
            throw new ApiRequestException( "No id Provided for " + getEntityClass().getSimpleName());
        E e = getByIdForWrite(requestDTO.getOid());
        convertForUpdate(requestDTO, e);
        //e.setUpdatedBy("System");
        e.setUpdatedOn(new Date());
        e.setDeleted(Boolean.FALSE);
        E updatedEntity = repository.save(e);

        log.info("Data update {}",updatedEntity);
        return convertForRead(updatedEntity);
    }

    public List<D> updateAll(List<D> bodyList) {

        List<E> entityListForUpdate = new ArrayList<>();

        bodyList.forEach(body -> {
            String oid = body.getOid();
            if (oid == null)
                throw new ApiRequestException("No id Provided for " + getEntityClass().getSimpleName());
            E e = getByIdForWrite(oid);
            convertForUpdate(body, e);
            //e.setUpdatedBy("System");
            e.setUpdatedOn(new Date());
            e.setDeleted(Boolean.FALSE);

            entityListForUpdate.add(e);
        });

        List<E> updatedEntities = repository.saveAll(entityListForUpdate);

        log.info("Data updateAll {}",updatedEntities);
        return convertForRead(updatedEntities);
    }

    public D deleteById(BaseDTO requestDTO) {
        D d = deleteEntity(getByIdForWrite(requestDTO.getOid()));

        log.info("Data deleteById {}",requestDTO);
        return d;
    }

    public void deleteAll(List<D> bodyList) {
        bodyList.forEach(body -> {
            deleteEntity(getByIdForWrite(body.getOid()));
        });
    }

    protected D deleteEntity(E e) {
        e.setDeleted(Boolean.TRUE);
        repository.save(e);

        log.info("Data deleteEntity {}",e);
        return convertForRead(e);
    }

    private E getById(@NonNull String oid) {
        return getOptionalEntity(oid).orElseThrow(() -> new ApiRequestException(
                "No " + getEntityClass().getSimpleName() + " Found with ID: " + oid));
    }

    public E getByIdForWrite(@NonNull String oid) {
        return getOptionalEntity(oid)
                .orElseThrow(() -> new ApiRequestException(
                        "No " + getEntityClass().getSimpleName() + " Found with ID: " + oid));
    }

    private Optional<E> getOptionalEntity(@NonNull String oid) {
        return repository.findByOidAndDeleted(oid, Boolean.FALSE);
    }

    protected D convertForRead(E e) {
        return modelMapper.map(e, getDtoClass());
    }

    public E convertForCreate(D d) {
        E e = null;
        try {
            e = getEntityClass().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
        BeanUtils.copyProperties(d, e);
        return e;
    }

    protected List<E> convertForCreate(List<D> d) {
        return d.stream().map(this::convertForCreate).collect(Collectors.toList());
    }

    protected E convertForUpdate(D dsource, E eTarget) {
        BeanUtils.copyProperties(dsource, eTarget);
        return eTarget;
    }

    public List<D> convertForRead(List<E> e) {
        return e.stream().map(this::convertForRead).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public Class<E> getEntityClass() {
        return (Class<E>) (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    @SuppressWarnings("unchecked")
    public Class<D> getDtoClass() {
        return (Class<D>) (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
    }

    public Set<String> validateSet(Set<String> set) {
        if (set == null || set.size() == 0) {
            set.add("");
        }
        return set;
    }


}