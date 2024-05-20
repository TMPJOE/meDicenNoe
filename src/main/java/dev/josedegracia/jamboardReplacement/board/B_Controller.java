package dev.josedegracia.jamboardReplacement.board;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController // This class is a REST controller
@RequestMapping("/api/boards")
public class B_Controller {

    private final B_Repository b_repository;

    public B_Controller(B_Repository b_repository) {
        this.b_repository = b_repository;
    }

    //post
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void createBoard(@RequestBody String bName){
        b_repository.createBoard(bName);
    }

    //put
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void updateBoard(@PathVariable Integer id, @RequestBody String name){
        b_repository.updateBoard(name, id);
    }

    //delete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id){
        b_repository.deleteBoard(id);
    }

    @GetMapping("")
    List<Board> findAll(){
        return b_repository.findAll();
    }

    @GetMapping("/{date}")//se tiene que dar: año-mes-dia
    List<Board> findByDate (@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
        return b_repository.findbyDate(date);
    }

    @GetMapping("/{id}")
    Board findById(@PathVariable int id){
        Optional<Board> board = b_repository.findById(id);
        if (board.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return board.get();
    }

}
