package org.example.Entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SuperBuilder
@Data
public abstract class Adresse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;

    private String rue;

    private String ville;

    @OneToMany
    @JoinColumn(name = "id_client")
    private List<Client> clients;

    public Adresse() {
    }

}