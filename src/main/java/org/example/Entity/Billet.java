package org.example.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Billet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_billet")
    private int id;

    private int numeroPlace;

    private String client;

    @ManyToOne
    @JoinColumn(name = "id_evenement")
    private Evenement evenement;
    public enum Place {
        STANDARD,
        MEDIUM,
        HIGH
    }

    private Place place;
}