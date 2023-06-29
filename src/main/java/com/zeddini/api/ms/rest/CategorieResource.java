package com.zeddini.api.ms.rest;


import com.zeddini.api.ms.domain.Categorie;
import com.zeddini.api.ms.domain.Produit;
import com.zeddini.api.ms.service.ICategorieService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

/**
 * REST controller for managing {@link com.cifopims.domain.Categorie}.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:4200")
@Api(value="Categorie",tags="REST - Categorie")
//@ApiIgnore
public class CategorieResource {

    private final Logger log = LoggerFactory.getLogger(CategorieResource.class);

 

    private ICategorieService categorieService;

    public CategorieResource(ICategorieService categorieService) {
        this.categorieService = categorieService;
    }

    /**
     * {@code POST  /categories} : Create a new categorie.
     *
     * @param categorie the categorie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categorie, or with status {@code 400 (Bad Request)} if the categorie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categories")
    public ResponseEntity<Categorie> createCategorie(@Valid @RequestBody Categorie categorie) throws URISyntaxException {
        log.debug("REST request to save Categorie : {}", categorie);
   
        Categorie result = categorieService.save(categorie);
        return ResponseEntity.created(new URI("/api/categories/" + result.getId()))
                .body(result);
    }
    /**
     * {@code POST  /categories} : Create many categorie.
     *
     * @param list of categorie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categorie, or with status {@code 400 (Bad Request)} if the categorie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categories/save/many")
    public List<Categorie> createManyPCategorie( @RequestBody List<Categorie> categories) throws URISyntaxException {
        log.debug("REST request to save many Categorie : {}");
 
        categories = categorieService.saveMany(categories);
        
        return categories;
        }
    /**
     * {@code PUT  /categories} : Updates an existing categorie.
     *
     * @param categorie the categorie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorie,
     * or with status {@code 400 (Bad Request)} if the categorie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categorie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categories")
    public ResponseEntity<Categorie> updateCategorie(@Valid @RequestBody Categorie categorie) throws URISyntaxException {
        log.debug("REST request to update Categorie : {}", categorie);

        Categorie result = categorieService.save(categorie);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code GET  /categories} : get all the categories - Pageable.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     */
    @GetMapping("/categories")
    public ResponseEntity<List<Categorie>> getAllCategories(Pageable pageable) {
        log.debug("REST request to get a page of Categories");
        Page<Categorie> page = categorieService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }
    
    @GetMapping("/categories/all")
    @ApiOperation(value = "Get all Categories in the Stock - list")
    public  List<Categorie> getAllCategories() {
        log.debug("REST request to get all Produits in the Stock");
        return categorieService.findAll();
    }

    /**
     * {@code GET  /categories/:id} : get the "id" categorie.
     *
     * @param id the id of the categorie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categorie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categories/{id}")
    public ResponseEntity<Categorie> getCategorie(@PathVariable Long id) {
        log.debug("REST request to get Categorie : {}", id);
        Optional<Categorie> categorie = categorieService.findOne(id);
        return ResponseEntity.ok().body(categorie.get());
    }

    /**
     * {@code DELETE  /categories/:id} : delete the "id" categorie.
     *
     * @param id the id of the categorie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
        log.debug("REST request to delete Categorie : {}", id);
        categorieService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * {@code DELETE  /categories/:many} : delete many categorie.
     *
     * @param id the id of the categorie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categories/many")
    public ResponseEntity<Void> deleteManyCategorie( @RequestBody List<Categorie> categorieList) {
        log.debug("REST request to delete many Categorie : {}");
        categorieService.deleteMany(categorieList);
        return ResponseEntity.noContent().build();
    }
}