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
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "https://tmpjoe.github.io")
@RequestMapping("/api/boards")
public class B_Controller {

    private final B_Service b_service;
    private final FirebaseService firebaseService;

    public B_Controller(B_Service b_service, FirebaseService firebaseService) {
        this.b_service = b_service;
        this.firebaseService = firebaseService;
    }

    @GetMapping("")
    public List<Board> findAll() throws ExecutionException, InterruptedException {
        return b_service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoardById(@PathVariable int id) throws ExecutionException, InterruptedException {
        Optional<Board> board = b_service.findById(id);
        return board.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/find/{date}")
    public List<Board> getBoardsByDate(@PathVariable LocalDate date) throws ExecutionException, InterruptedException {
        return b_service.findByDate(date);
    }

    @PostMapping("")
    public ResponseEntity<Board> createBoard(@RequestBody Board board) throws ExecutionException, InterruptedException {
        Board savedBoard = b_service.save(board);
        return ResponseEntity.ok(savedBoard);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable int id, @RequestBody Board boardDetails) throws ExecutionException, InterruptedException {
        b_service.updateNameBoard(boardDetails.getName(), id);
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable int id) throws ExecutionException, InterruptedException {
        b_service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<?> uploadImage(@PathVariable int id, @RequestParam("file") MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        Map<String, String> result = firebaseService.uploadImage(file);
        Optional<Board> boardOptional = b_service.findById(id);
        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();
            board.addImageUrl(result.get("url"));
            b_service.save(board);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(404).body("Board not found");
        }
    }
}