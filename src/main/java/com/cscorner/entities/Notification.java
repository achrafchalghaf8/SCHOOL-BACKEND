package com.cscorner.entities;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contenu;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date date;

    @ManyToMany(mappedBy = "notifications")
    private List<Compte> comptes = new ArrayList<>();

    public Notification() {}

    public Notification(Long id, String contenu, Date date) {
        this.id = id;
        this.contenu = contenu;
        this.date = date;
    }

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public List<Compte> getComptes() { return comptes; }
    public void setComptes(List<Compte> comptes) { this.comptes = comptes; }

    // Méthodes utilitaires pour gérer la relation
    public void addCompte(Compte compte) {
        comptes.add(compte);
        compte.getNotifications().add(this);
    }

    public void removeCompte(Compte compte) {
        comptes.remove(compte);
        compte.getNotifications().remove(this);
    }
}