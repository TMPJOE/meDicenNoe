package dev.josedegracia.jamboardReplacement.board;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Board {
    private int id;
    private String name;
    private LocalDateTime creationDate;
    private Set<AnonymounsUser> collaborators = new HashSet<>(); // Default empty set

    //constructor
    public Board(int id, String name, LocalDateTime creationDate, AnonymounsUser collaborator) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.collaborators.add(collaborator);
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