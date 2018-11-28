package be.ucll.da.dentravak.model.db;

import be.ucll.da.dentravak.model.domain.Sandwich;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.UUID;

public interface SandwichRepository extends PagingAndSortingRepository<Sandwich, UUID> {

}
