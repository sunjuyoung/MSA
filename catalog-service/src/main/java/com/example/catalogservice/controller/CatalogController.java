package com.example.catalogservice.controller;

import com.example.catalogservice.entity.CatalogEntity;
import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.vo.ResponseCatalog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;
    private final ModelMapper modelMapper;

    @GetMapping("/catalogs")
    public ResponseEntity<?> getAll(){
        Iterable<CatalogEntity> allCatalogs = catalogService.getAllCatalogs();
        List<ResponseCatalog> responseCatalogs = new ArrayList<>();
        allCatalogs.forEach(catalogEntity ->
                responseCatalogs.add(modelMapper.map(catalogEntity,ResponseCatalog.class)));
        return ResponseEntity.ok().body(responseCatalogs);
    }
}
