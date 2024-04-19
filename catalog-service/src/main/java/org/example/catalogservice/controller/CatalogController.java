package org.example.catalogservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.catalogservice.entity.CatalogEntity;
import org.example.catalogservice.service.CatalogService;
import org.example.catalogservice.vo.ResponseCatalog;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog-service")
public class CatalogController {

    private final CatalogService catalogService;
    private final Environment env;

    @GetMapping(value="/heath_check")
    public String status(HttpServletRequest request){
        return String.format("It's Working in Catalog Service on PORT %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getUsers(){
        Iterable<CatalogEntity> catalogList = catalogService.getAllCatalogs();

        List<ResponseCatalog> result = new ArrayList<>();
        catalogList.forEach( vo ->{
            ResponseCatalog map = new ModelMapper().map(vo, ResponseCatalog.class);
            System.out.println(map);
            result.add(map);
                }
        );

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/catalogs/{id}")
    public ResponseEntity<ResponseCatalog> getUser(@PathVariable(name="id", required = true) String id){
        ResponseCatalog result = new ModelMapper().map(catalogService.getCatalogByProductId(id), ResponseCatalog.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
