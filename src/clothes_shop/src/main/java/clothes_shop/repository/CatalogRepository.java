package clothes_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clothes_shop.model.Catalog;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, String> {
	
}
