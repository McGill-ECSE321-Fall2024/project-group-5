package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.Catalog;

import org.springframework.data.repository.CrudRepository;

public interface CatalogRepository extends CrudRepository<Catalog, Integer> {
    Catalog getCatalogByIdIsNotNull();

    Integer deleteCatalogByIdNotNull();
}