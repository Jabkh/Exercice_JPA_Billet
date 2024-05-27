package org.example.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Evenement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evenement")

    private int id;

    private String nom;
    private LocalDate dateEvent;
    private LocalDateTime heureEvent;
    private int nbrPlace;

    @OneToMany
    @JoinColumn(name = "id_billet")
    private List<Billet> billets;

    @ManyToOne
    @JoinColumn(name = "id_lieu")
    private Lieu lieu;

}