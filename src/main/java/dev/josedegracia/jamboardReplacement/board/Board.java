package dev.josedegracia.jamboardReplacement.board;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private LocalDateTime creationDate;
    private String owner;  // Almacena el nombre del propietario

    @ElementCollection
    private List<String> imageUrls = new ArrayList<>();


    public Board() {
        //creación de la fecha de creación
        this.creationDate = LocalDateTime.now();
    }

    // Getters y settersBoar

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void addImageUrl(String imageUrl) {
        this.imageUrls.add(imageUrl);
    }
}
