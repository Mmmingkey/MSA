package org.example.catalogservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.catalogservice.entity.CatalogEntity;
import org.example.catalogservice.repository.CatalogRepository;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService{

    private final CatalogRepository repository;
    @Override
    public Iterable<CatalogEntity> getAllCatalogs() {
        return repository.findAll();
    }

    @Override
    public CatalogEntity getCatalogByProductId(String id) {
        return repository.findByProductId(id);
    }
}
