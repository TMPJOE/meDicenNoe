package dev.josedegracia.jamboardReplacement.board;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class B_Service {

    private final FirebaseService firebaseService;

    public B_Service(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    public List<Board> findAll() {
        // Implementar la lógica para obtener todos los boards de Firebase
        // Aquí sólo se muestra una forma básica, en un caso real se puede necesitar más lógica
        return List.of(); // Sustituir con la lógica real para obtener todos los boards
    }

    public Board save(Board board) throws ExecutionException, InterruptedException {
        return firebaseService.saveBoard(board);
    }

    public Optional<Board> findById(int id) throws ExecutionException, InterruptedException {
        return Optional.ofNullable(firebaseService.getBoard(id));
    }

    public void deleteById(int id) {
        Firestore db = FirestoreClient.getFirestore();
        db.collection("boards").document(String.valueOf(id)).delete();
    }

    public List<Board> findByDate(LocalDate date) {
        return findAll().stream()
                .filter(board -> board.getCreationDate().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    public void updateNameBoard(String name, Integer id) throws ExecutionException, InterruptedException {
        Optional<Board> existingBoard = findById(id);
        existingBoard.ifPresent(board -> {
            board.setName(name);
            try {
                save(board);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}