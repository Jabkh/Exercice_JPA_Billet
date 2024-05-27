package org.example.Entity;


import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@SuperBuilder
@Data
public class Lieu extends Adresse {

    private int id;

    private int capacite;

    @OneToMany
    @JoinColumn(name = "id_evenement")
    private List<Evenement> evenements;

    public Lieu() {
    }
}