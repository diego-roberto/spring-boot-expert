package io.github.diegoroberto.domain.repository;

import io.github.diegoroberto.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(value =  " select c from Client c " +
                    " where c.name like '%:name%' ")
    List<Client> findByName(@Param("name") String name);

    @Query(value =  " delete from Client c " +
                    " where c.name = :name ")
    @Modifying
    void deleteByName(String name);

    boolean existsByName(String name);

    @Query(value =  " select c from Client c " +
                    " left join fetch c.orders " +
                    " where c.id = :id ")
    Client findClientFetchOrders(@Param("id") Long id);

}
