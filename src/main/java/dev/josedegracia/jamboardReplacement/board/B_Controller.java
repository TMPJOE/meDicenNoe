package dev.josedegracia.jamboardReplacement.board;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://tmpjoe.github.io")
@RequestMapping("/api/boards")
public class B_Controller {

    private final B_Service b_service;
    private final CloudinaryService cloudinaryService;

    public B_Controller(B_Service b_service, CloudinaryService cloudinaryService) {
        this.b_service = b_service;
        this.cloudinaryService = cloudinaryService;
    }

    // GET methods
    @GetMapping("")
    public List<Board> findAll() {
        return b_service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoardById(@PathVariable int id) {
        Optional<Board> board = b_service.findById(id);
        return board.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/find/{date}")
    public List<Board> getBoardsByDate(@PathVariable LocalDate date) {
        return b_service.findByDate(date);
    }

    // POST methods
    @PostMapping("")
    public ResponseEntity<Board> createBoard(@RequestBody Board board) {
        Board savedBoard = b_service.save(board);
        return ResponseEntity.ok(savedBoard);
    }

    // PUT methods
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable int id, @RequestBody Board boardDetails) {
        b_service.updateNameBoard(boardDetails.getName(), id);
        return ResponseEntity.ok().build();
    }

    // DELETE methods
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable int id) {
        b_service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // POST method to upload images
    @PostMapping("/{id}/upload")
    public ResponseEntity<?> uploadImage(@PathVariable int id, @RequestParam("file") MultipartFile file) {
        try {
            Map result = cloudinaryService.uploadImage(file);
            Optional<Board> boardOptional = b_service.findById(id);
            if (boardOptional.isPresent()) {
                Board board = boardOptional.get();
                board.addImageUrl(result.get("url").toString());
                b_service.save(board);
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(404).body("Board not found");
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading image: " + e.getMessage());
        }
    }


    // Método para generar identificadores temporales de colaboradores
    /*@GetMapping("/{id}/collaborators")
    public List<String> getCollaborators(@PathVariable int id) {
        // Generar nombres de animales como identificadores de colaboradores
        String[] animals = {"Tiger", "Elephant", "Lion", "Zebra", "Giraffe",
                "Monkey", "Panda", "Koala", "Kangaroo", "Penguin", "Polar bear",
                "Seal", "Walrus", "Dolphin", "Shark", "Whale", "Octopus", "Jellyfish"
        };
        Random random = new Random();
        return random.ints(5, 0, animals.length)
                .mapToObj(i -> animals[i])
                .collect(Collectors.toList());
    }*/
}
