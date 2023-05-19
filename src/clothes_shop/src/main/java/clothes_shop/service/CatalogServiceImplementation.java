package clothes_shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clothes_shop.model.Catalog;
import clothes_shop.repository.CatalogRepository;

@Service
public class CatalogServiceImplementation implements CatalogService {
	@Autowired
	private CatalogRepository cr;
	
	@Override
	public void save(Catalog catalog) {
		cr.save(catalog);
	}

	@Override
	public Catalog getOne(String name) {
		Optional<Catalog> catalog = cr.findById(name);

		if (catalog.isPresent())
			return catalog.get();

		return null;
	}

	@Override
	public List<Catalog> getAll() {
		return cr.findAll();
	}
}
