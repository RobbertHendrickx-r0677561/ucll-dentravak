
package be.ucll.da.dentravak.model.db;

import be.ucll.da.dentravak.model.domain.Orders;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

//@RepositoryRestResource(collectionResourceRel = "orderlist", path = "orderlist")
public interface OrderRepository extends PagingAndSortingRepository<Orders, UUID> {


    List<Orders> findByDate(@Param("date")Date date);
}
