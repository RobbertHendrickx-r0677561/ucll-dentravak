package be.ucll.da.dentravak.model.db;

import be.ucll.da.dentravak.model.domain.Orders;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderRepository extends CrudRepository<Orders, UUID> {
}
