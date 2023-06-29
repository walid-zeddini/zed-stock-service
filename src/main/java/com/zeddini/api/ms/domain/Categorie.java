package com.zeddini.api.ms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "categorie")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value="Categorie",description="Catégorie de produit")

public class Categorie implements Serializable {

	 private static final long serialVersionUID = 1L;

	    @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	    @SequenceGenerator(name = "sequenceGenerator")
	    private Long id;

	    @NotNull
	    @Column(name = "code", nullable = false)
	    private String code;

	    @NotNull
	    @Column(name = "libelle", nullable = false)
	    private String libelle;

	    @JsonIgnore
	    @Transient
	    private Set<Produit> produits = new HashSet<>();
	    
	    /**
	     * In the case of Monolithic Application with 
	     * a relational DB containing all entities of the Application, 
	     * we can define OneToMany and  ManyToMany relations between 
	     * persistent entities. in MS, it's recommended to discuss and implement
	     * in Service Classes
	     */  
//		@OneToMany(fetch = FetchType.LAZY, mappedBy = "categorieId", orphanRemoval = true)
//		
//	    @JsonIgnoreProperties(value = "produits", allowSetters = true)
//	    private Set<Produit> produits = new HashSet<>();

	}

