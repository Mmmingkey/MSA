package org.example.catalogservice.repository;

import org.example.catalogservice.entity.CatalogEntity;
import org.springframework.data.repository.CrudRepository;

public interface CatalogRepository extends CrudRepository<CatalogEntity, Long> {
    public CatalogEntity findByProductId(String productId);
}
