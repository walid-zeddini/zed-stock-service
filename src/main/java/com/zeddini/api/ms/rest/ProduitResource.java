package com.zeddini.api.ms.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zeddini.api.ms.domain.Produit;
import com.zeddini.api.ms.service.IProduitService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:4200")
@Api(value = "Produit", tags = "REST - Produit")
public class ProduitResource {

	private final Logger log = LoggerFactory.getLogger(ProduitResource.class);

	private IProduitService produitService;

	public ProduitResource(IProduitService produitService) {
		this.produitService = produitService;
	}

	@GetMapping("/produits")
	public ResponseEntity<List<Produit>> getPageableProduits(Pageable pageable) {
		Page<Produit> page = produitService.findAll(pageable);
		return ResponseEntity.ok().body(page.getContent());
	}

	@GetMapping("/produits/all")
	public List<Produit> getAllListProduits() {
		return produitService.findAll();
	}

	@GetMapping("/produits/{id}")
	public ResponseEntity<Produit> getProduit(@PathVariable Long id) {
		log.debug("REST request to get Produit : {}", id);
		Optional<Produit> produit = produitService.findOne(id);
		return ResponseEntity.ok().body(produit.get());
	}

	@PutMapping("/produits")
	public ResponseEntity<Produit> updateProduit(@Valid @RequestBody Produit produit) throws URISyntaxException {
		log.debug("REST request to update Produit : {}", produit);

		Produit result = produitService.save(produit);
		return ResponseEntity.ok().body(result);
	}

	@PutMapping("/produits/mo")
	public Produit updateNativeProduit(@Valid @RequestBody Produit produit) throws URISyntaxException {
		log.debug("REST request to update Produit : {}", produit);

		Produit result = produitService.save(produit);
		return result;
	}

	@DeleteMapping("/produits/del/{id}")
	public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
		log.debug("REST request to delete Produit : {}", id);
		produitService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/produits/{id}")
	public void deleteNativeProduit(@PathVariable Long id) {
		log.debug("REST request to delete Produit : {}", id);
		produitService.delete(id);
	}

	@PostMapping("/produits")
	public ResponseEntity<Produit> createProduit(@Valid @RequestBody Produit produit) throws URISyntaxException {
		log.debug("REST request to save Produit : {}", produit);

		Produit result = produitService.save(produit);
		return ResponseEntity.created(new URI("/api/produits/" + result.getId())).body(result);
	}

	@PostMapping("/produits/save/many")
	public List<Produit> createManyProduit(@RequestBody List<Produit> produit) throws URISyntaxException {
		log.debug("REST request to save many Produit : {}");

		produit = produitService.saveMany(produit);

		return produit;
	}
	
	

}
