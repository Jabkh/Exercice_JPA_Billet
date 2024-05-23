package org.example.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")

    private int id;

    private String nom;

    private String prenom;

    private int age;

    private String numeroTelephone;

    @ManyToOne
    @JoinColumn(name = "id_adresse")
    private Adresse adresse;
}