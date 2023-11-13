package io.github.diegoroberto.domain.repository;

import io.github.diegoroberto.domain.entity.Client;
import io.github.diegoroberto.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByClient(Client client);

    @Query(value =  " select p from Order p " +
                    " left join fetch p.items " +
                    " where p.id = :id ")
    Optional<Order> findByIdFetchItems(@Param("id") Long id);

}
