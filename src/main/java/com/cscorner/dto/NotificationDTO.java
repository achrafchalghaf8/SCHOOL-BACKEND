package com.cscorner.dto;

import java.util.Date;
import java.util.List;

public class NotificationDTO {
    private Long id;
    private String contenu;
    private Date date;
    private List<Long> compteIds;

    public NotificationDTO() {}

    public NotificationDTO(Long id, String contenu, Date date, List<Long> compteIds) {
        this.id = id;
        this.contenu = contenu;
        this.date = date;
        this.compteIds = compteIds;
    }

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public List<Long> getCompteIds() { return compteIds; }
    public void setCompteIds(List<Long> compteIds) { this.compteIds = compteIds; }
}
