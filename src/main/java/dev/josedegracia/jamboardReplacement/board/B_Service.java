package dev.josedegracia.jamboardReplacement.board;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class B_Service {

    private final BoardRepository repository;

    public B_Service(BoardRepository repository) {
        this.repository = repository;
    }

    public List<Board> findAll() {
        return repository.findAll();
    }

    public Board save(Board board) {
        return repository.save(board);
    }

    public Optional<Board> findById(int id) {
        return repository.findById(id);
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public List<Board> findByDate(LocalDate date) {
        // Aquí podrías necesitar una consulta personalizada en el repositorio si es una búsqueda específica
        return repository.findAll().stream()
                .filter(board -> board.getCreationDate().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    public void updateNameBoard(String name, Integer id) {
        Optional<Board> existingBoard = findById(id);
        existingBoard.ifPresent(board -> {
            board.setName(name);
            repository.save(board);
        });
    }


}
