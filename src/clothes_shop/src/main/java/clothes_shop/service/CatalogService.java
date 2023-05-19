package clothes_shop.service;

import java.util.List;

import clothes_shop.model.Catalog;

public interface CatalogService {
	List<Catalog> getAll();
	void save(Catalog catalog);
	Catalog getOne(String name);
}
