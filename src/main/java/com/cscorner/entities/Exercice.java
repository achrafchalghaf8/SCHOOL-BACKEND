package com.cscorner.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.*;

@Entity
@Table(name = "exercices")
public class Exercice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contenu;

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

    @ManyToMany
    @JoinTable(
        name = "exercice_classes",
        joinColumns = @JoinColumn(name = "exercice_id"),
        inverseJoinColumns = @JoinColumn(name = "classe_id")
    )
    private List<Classe> classes = new ArrayList<>();

    // **Relation ManyToOne avec Cour**
    @ManyToOne
    @JoinColumn(name = "cour_id")
    private Cour cour;

    public Exercice() {}

    public Exercice(Long id, String contenu, Date datePublication, String fichier, List<Classe> classes, Cour cour) {
        this.id = id;
        this.contenu = contenu;
        this.datePublication = datePublication;
        this.fichier = fichier;
        this.classes = classes;
        this.cour = cour;
    }

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public Date getDatePublication() { return datePublication; }
    public void setDatePublication(Date datePublication) { this.datePublication = datePublication; }

    public String getFichier() { return fichier; }
    public void setFichier(String fichier) { this.fichier = fichier; }

    public List<Classe> getClasses() { return classes; }
    public void setClasses(List<Classe> classes) { this.classes = classes; }

    public Cour getCour() { return cour; }
    public void setCour(Cour cour) { this.cour = cour; }
}
