package dev.josedegracia.jamboardReplacement.board;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Board {

    Random randomId = new Random();
    private int id;
    private String name;
    private LocalDateTime creationDate;
    private Set<AnonymounsUser> collaborators = new HashSet<>(); // Default empty set

    //constructor
    public Board(String name) {
        this.id = randomId.nextInt();
        this.name = name;
        this.creationDate = LocalDateTime.now();
        this.collaborators.add(new AnonymounsUser());
    }

    //setters and getters
    //add collaborator
    public void addCollaborator(AnonymounsUser collaborator){
        collaborators.add(collaborator);
    }

    //id
    public long getId() {
        return id;
    }
    //name
    public String getName() {
        return name;
    }
    //creationDate
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    //collaborators
    public Set<AnonymounsUser> getCollaborators() {
        return collaborators;
    }
}