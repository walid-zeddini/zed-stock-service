package com.zeddini.api.ms.service;
import com.zeddini.api.ms.domain.Produit;
import com.zeddini.api.ms.repository.CategorieRepository;
import com.zeddini.api.ms.repository.ProduitRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for implementing methods declared in Interface linked to {@link Produit}.
 */
@Service
@Transactional
public class ProduitServiceImpl implements IProduitService {

    private final Logger log = LoggerFactory.getLogger(ProduitServiceImpl.class);

    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository;


    public ProduitServiceImpl(ProduitRepository produitRepository, CategorieRepository categorieRepository) {
        this.produitRepository = produitRepository;
        this.categorieRepository = categorieRepository;
    }

    /**
     * Save a produit.
     *
     * @param produit the entity to save.
     * @return the persisted entity.
     */
    public Produit save(Produit produit) {
        log.debug("Request to save Produit : {}", produit);
        return produitRepository.save(produit);
    }

    /**
     * Get all the produits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Produit> findAll(Pageable pageable) {
        log.debug("Request to get all Produits");
        
        Page<Produit> produits = produitRepository.findAll(pageable);
        
     	
    	for (Produit produit : produits) {
    		
    		if (produit != null) {
    			if(produit.getCategorieId()!=null)
    			produit.setCategorie(categorieRepository.getById(produit.getCategorieId()));
	        }
		}
    
    	Page<Produit> pages = new PageImpl<Produit>(produits.getContent(), pageable, Integer.valueOf(produits.getSize()).longValue());
    
    	return pages;
    }


    /**
     * Get one produit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Produit> findOne(Long id) {
        log.debug("Request to get Produit : {}", id);
        return produitRepository.findById(id);
    }

    /**
     * Delete the produit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Produit : {}", id);
        produitRepository.deleteById(id);
    }

	@Override
	public List<Produit> saveMany(List<Produit> produitList) {
		List<Produit> result = new ArrayList<Produit>();
		for (Produit produit : produitList) {
			if(produit.getCategorieId()!=null)
			{
			produit = save(produit);
			result.add(produit);
			}
		}
		return result;
	}

	@Override
	public List<Produit> findAll() {
		  List<Produit> produits = produitRepository.findAll();
	        
       	
			for (Produit produit : produits) {
				if (produit != null) {
					if(produit.getCategorieId()!=null)
					produit.setCategorie(categorieRepository.getById(produit.getCategorieId()));
				}
			}
	         return produits;
	}
}
