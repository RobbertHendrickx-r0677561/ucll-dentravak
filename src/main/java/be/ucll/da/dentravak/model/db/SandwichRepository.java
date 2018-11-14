package be.ucll.da.dentravak.model.db;

import be.ucll.da.dentravak.model.domain.Sandwich;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;


@RepositoryRestResource(collectionResourceRel = "sandwichlist", path = "sandwichlist")
public interface SandwichRepository extends PagingAndSortingRepository<Sandwich, Long> {

    List<Sandwich> findByName(@Param("name") String name);
}
