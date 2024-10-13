package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.Catalog;
import jakarta.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface CatalogRepository extends CrudRepository<Catalog, Integer> {
    Catalog getCatalogByIdIsNotNull();

    Integer deleteCatalogByIdNotNull();
}