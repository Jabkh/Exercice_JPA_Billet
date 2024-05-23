package org.example;
import org.example.Entity.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class IHM {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("exercice_jpa");
    private EntityManager em = emf.createEntityManager();
    private Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("Menu : ");
            System.out.println("1/ Ajouter un client");
            System.out.println("2/ Ajouter un événement");
            System.out.println("3/ Réserver un billet");
            System.out.println("4/ Afficher tous les clients");
            System.out.println("5/ Afficher tous les événements");
            System.out.println("6/ Afficher les billets d'un événement");
            System.out.println("7/ Quitter");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    addClient();
                    break;
                case 2:
                    addEvent();
                    break;
                case 3:
                    reserveBillet();
                    break;
                case 4:
                    getAllClients();
                    break;
                case 5:
                    getAllEvents();
                    break;
                case 6:
                    getEventBillets();
                    break;
                case 7:
                    close();
                    return;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }

    private void addClient() {
        System.out.println("Nom : ");
        String nom = sc.nextLine();
        System.out.println("Prénom : ");
        String prenom = sc.nextLine();
        System.out.println("Rue : ");
        String rue = sc.nextLine();
        System.out.println("Ville : ");
        String ville = sc.nextLine();
        System.out.println("Âge : ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.println("Numéro de téléphone : ");
        String numeroDeTelephone = sc.nextLine();

        Adresse adresse = Adresse.builder()
                .rue(rue)
                .ville(ville)
                .build();

        Client client = Client.builder()
                .nom(nom)
                .prenom(prenom)
                .adresse(adresse)
                .age(age)
                .numeroTelephone(numeroDeTelephone)
                .build();

        persistEntity(client);
    }

    private void addEvent() {
        System.out.println("Nom de l'événement : ");
        String nom = sc.nextLine();
        System.out.println("Rue du lieu : ");
        String rue = sc.nextLine();
        System.out.println("Ville du lieu : ");
        String ville = sc.nextLine();
        System.out.println("Capacité du lieu : ");
        int capacite = sc.nextInt();
        sc.nextLine();
        System.out.println("Date (AAAA-MM-JJ) : ");
        String dateStr = sc.nextLine();
        LocalDate date = LocalDate.parse(dateStr);
        System.out.println("Heure (HH:MM) : ");
        String heureStr = sc.nextLine();
        LocalDateTime heure = LocalDateTime.parse(heureStr);
        System.out.println("Nombre de places : ");
        int nombreDePlace = sc.nextInt();
        sc.nextLine();

        Lieu lieu = Lieu.builder()
                .rue(rue)
                .ville(ville)
                .capacite(capacite)
                .build();

        Evenement evenement = Evenement.builder()
                .nom(nom)
                .dateEvent(date)
                .heureEvent(heure)
                .nbrPlace(nombreDePlace)
                .lieu(lieu)
                .build();

        persistEntity(evenement);
    }

    private void reserveBillet() {
        System.out.println("ID de l'événement : ");
        int eventId = sc.nextInt();
        sc.nextLine();
        Evenement evenement = em.find(Evenement.class, eventId);
        if (evenement == null) {
            System.out.println("Événement non trouvé.");
            return;
        }
        if (evenement.getBillets().size() >= evenement.getNbrPlace()) {
            System.out.println("Pas de places disponibles.");
            return;
        }

        System.out.println("ID du client : ");
        int clientId = sc.nextInt();
        sc.nextLine();
        Client client = em.find(Client.class, clientId);
        if (client == null) {
            System.out.println("Client non trouvé.");
            return;
        }

        System.out.println("Numéro de place : ");
        int numeroDePlace = sc.nextInt();
        sc.nextLine();
        System.out.println("Type de place (STANDARD, MEDIUM, HIGH) : ");
        String typeDePlaceStr = sc.nextLine();
        Billet.Place typeDePlace = Billet.Place.valueOf(typeDePlaceStr.toUpperCase());

        Billet billet = Billet.builder()
                .numeroPlace(numeroDePlace)
                .client(client.getNom() + " " + client.getPrenom())
                .evenement(evenement)
                .place(typeDePlace)
                .build();

        persistEntity(billet);
    }

    private void getAllClients() {
        List<Client> clients = em.createQuery("SELECT c FROM Client c", Client.class).getResultList();
        System.out.println("Tous les clients :");
        for (Client client : clients) {
            System.out.println(client);
        }
    }

    private void getAllEvents() {
        List<Evenement> events = em.createQuery("SELECT e FROM Evenement e", Evenement.class).getResultList();
        System.out.println("Tous les événements :");
        for (Evenement event : events) {
            System.out.println(event);
        }
    }

    private void getEventBillets() {
        System.out.println("ID de l'événement : ");
        int eventId = sc.nextInt();
        sc.nextLine();
        Evenement evenement = em.find(Evenement.class, eventId);
        if (evenement == null) {
            System.out.println("Événement non trouvé.");
            return;
        }
        List<Billet> billets = evenement.getBillets();
        System.out.println("Billets pour l'événement " + evenement.getNom() + " :");
        for (Billet billet : billets) {
            System.out.println(billet);
        }
    }

    private void persistEntity(Object entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        System.out.println("Entité ajoutée : " + entity);
    }

    private void close() {
        em.close();
        emf.close();
    }
}
