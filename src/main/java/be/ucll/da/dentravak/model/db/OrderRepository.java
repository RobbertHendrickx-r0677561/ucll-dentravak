package be.ucll.da.dentravak.model.db;

import be.ucll.da.dentravak.model.domain.Order;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "orderlist", path = "orderlist")
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
}
