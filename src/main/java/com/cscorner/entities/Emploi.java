package com.cscorner.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "emplois")
public class Emploi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_publication", nullable = false)
    private Date datePublication;

    @Lob
    @Column(name = "fichier", nullable = false, columnDefinition = "LONGTEXT")
    @NotBlank(message = "Le fichier est obligatoire")
    @Size(
        max = 33554432, // 25 Mo en base64 ≈ 33 554 432 caractères
        message = "Le fichier est trop volumineux (max 25MB en base64)"
    )
    private String fichier;


    @ManyToOne
    @JoinColumn(name = "classe_id", nullable = false)
    private Classe classe;

    public Emploi() {}

    public Emploi(Long id, Date datePublication, String fichier, Classe classe) {
        this.id = id;
        this.datePublication = datePublication;
        this.fichier = fichier;
        this.classe = classe;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getDatePublication() { return datePublication; }
    public void setDatePublication(Date datePublication) { this.datePublication = datePublication; }

    public String getFichier() { return fichier; }
    public void setFichier(String fichier) { this.fichier = fichier; }

    public Classe getClasse() { return classe; }
    public void setClasse(Classe classe) { this.classe = classe; }
}
