package com.zeddini.api.ms.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.zeddini.api.ms.domain.Categorie;
import com.zeddini.api.ms.domain.Produit;
import com.zeddini.api.ms.repository.CategorieRepository;
import com.zeddini.api.ms.repository.ProduitRepository;


/**
 * Service Implementation for implementing methods declared in Interface linked to {@link InventaryDTO}.
 */
@Service
@Transactional
public class StockServiceImpl implements IStockService {

    private final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);

    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository;
    
    
//    @Autowired
//    private CircuitBreakerFactory circuitBreakerFactory;
    
    private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	public StockServiceImpl (ProduitRepository produitRepository, CategorieRepository categorieRepository) {
		this.produitRepository = produitRepository;
		this.categorieRepository = categorieRepository;
		

	}

	 /**
     * Create an produit.
     *
     * @param produit the entity to save.
     * @return the persisted entity.
     */
	
 
	 
	    /**
	     * Save a produit.
	     *
	     * @param produit the entity to save.
	     * @return the persisted entity.
	     */
			 
	    public Produit save(Produit produit) {
	        log.debug("Request to save ProduitDTO : {}", produit);
	        
	        if(categorieRepository.getById(produit.getCategorieId())!=null) {
	        	
	        produit = produitRepository.save(produit);
	        
	        } 
	        
	        return produit;
	    }
	    /**
	     * Save many produit.
	     *
	     * @param List produit  to save.
	     * @return the persisted entities.
	     */    
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

    /**
     * Get all the produits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Produit> findAll(Pageable pageable) {
        log.debug("Request to get all Stocks");
   
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


    @Transactional(readOnly = true)
    public List<Produit> findAll() {
        log.debug("Request to get all Stocks");
   
        List<Produit> produits = produitRepository.findAll();
        
        return  getProduitsByCategorieId (produits);
    }

   

    /**
     * Get one produit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Produit> findOne(Long id) {
        log.debug("Request to get ProduitDTO : {}", id);
         
     
        Produit produit = produitRepository.getById(id);
        
    	if (produit != null) {
    		if(produit.getCategorieId()!=null)
			produit.setCategorie(categorieRepository.getById(produit.getCategorieId()));
		}
	     
        return Optional.ofNullable(produit) ;
    }
    
  
    /**
     * Delete one by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */

    public void delete(Long id) {
        log.debug("Request to delete ProduitDTO : {}", id);
      
        if(produitRepository.existsById(id));
        produitRepository.deleteById(id);
        
    }


    /**
     * Delete one by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */

	public void deleteMany(List<Produit> produits) {
		log.debug("Request to delete List of produits : {}");
		for (Produit produit : produits) {
			if (produit != null) {
				if (produit.getId() != null)
					produitRepository.deleteById(produit.getId());
			}
		}
        
    }
	/**
	 * Set Categorie to each item of Produit List  
	 * @param produits
	 * @return
	 */
	private List<Produit>  getProduitsByCategorieId (List<Produit> produits ){
		for (Produit produit : produits) {
			if (produit != null) {
				if(produit.getCategorieId()!=null)
				produit.setCategorie(categorieRepository.getById(produit.getCategorieId()));
			}
		}
         return produits;
	}
	

    
    /**
     * Get all the produits that its marque is Like .
     *
     * @param marque.
     * @return the list of entities.
     */
	public List<Produit> getByMarqueLike(String marque) {
		
		List<Produit> produits = produitRepository.findByMarqueLike("%"+marque+"%");
		return  getProduitsByCategorieId (produits);
         
	}
	
    /**
     * Get all the produits that its modele is Like .
     *
     * @param modele.
     * @return the list of entities.
     */
	@Override
	public List<Produit> getByModeleLike(String modele) {
		List<Produit> produits = produitRepository.findByModeleLike("%"+modele+"%");
		return  getProduitsByCategorieId (produits);
	}

    /**
     * Get all the produits that its caracteristique is Like .
     *
     * @param caracteristique.
     * @return the list of entities.
     */
	@Override
	public List<Produit> getByCaracteristiquesLike(String caracteristiques) {
		List<Produit> produits = produitRepository.findByCaracteristiquesLike("%"+caracteristiques+"%");
		return  getProduitsByCategorieId (produits);
	}

    /**
     * Get all the produits by categorie Id .
     *
     * @param categorieId.
     * @return the list of entities.
     */
	@Override
	public List<Produit> getByCategorieId(Long categorieId) {	
		List<Produit> produits = produitRepository.findByCategorieId(categorieId);
 		for (Produit produit : produits) {
			if (produit != null) {
				produit.setCategorie(categorieRepository.getById(produit.getCategorieId()));
			}
		}
         return produits;
	}

    /**
     * Get all the produits that qte > than .
     *
     * @param qte.
     * @return the list of entities.
     */
	@Override
	public List<Produit> getByQuantiteGreaterThan(Long qte) {
		List<Produit> produits = produitRepository.findByQuantiteGreaterThan(qte);
		return  getProduitsByCategorieId (produits);
	}

	   /**
     * Get all the produits that qte < than .
     *
     * @param qte.
     * @return the list of entities.
     */
	@Override
	public List<Produit> getByQuantiteLessThan(Long qte) {
		List<Produit> produits = produitRepository.findByQuantiteLessThan(qte);
		return  getProduitsByCategorieId (produits);
	}

	   /**
     * Get all the produits that puProduit > than .
     *
     * @param puProduit.
     * @return the list of entities.
     */
	@Override
	public List<Produit> getByPrixUnitaireGreaterThan(BigDecimal puProduit) {
		List<Produit> produits = produitRepository.findByPrixUnitaireGreaterThan(puProduit);
		return  getProduitsByCategorieId (produits);
	}

 /**
  * Get all the produits that puProduit < than .
  *
  * @param puProduit.
  * @return the list of entities.
  */
	@Override
	public List<Produit> getByPrixUnitaireLessThan(BigDecimal puProduit) {
		List<Produit> produits = produitRepository.findByPrixUnitaireLessThan(puProduit);
		return  getProduitsByCategorieId (produits);
	}

	 /**
	  * Get all the produits that puProduit between min and max .
	  *
	  * @param puProduit.
	  * @return the list of entities.
	  */
	public List<Produit> getAllProduitsByPrixTBetween(BigDecimal prixMin, BigDecimal prixMax) {
		List<Produit> produits = produitRepository.findByPrixUnitaireLessThanAndPrixUnitaireGreaterThan(prixMax, prixMin);
		return  getProduitsByCategorieId (produits);
	}

	 /**
	  * Get all the produits that puProduit between min and max .
	  *
	  * @param qteMin.qteMax
	  * @return the list of entities.
	  */
	public List<Produit> getAllProduitsByPQteBetween(BigDecimal qteMin, BigDecimal qteMax) {
		List<Produit> produits = produitRepository.findByQuantiteLessThanAndQuantiteGreaterThan(qteMin, qteMin);
		return  getProduitsByCategorieId (produits);
	}

	 /**
	  * Get produit by PK.
	  *
	  * @param id
	  * @return Produit.
	  */
	public Produit getProditById(Long id) {
		return (produitRepository.findById(id)).get();
	}
    
   
}