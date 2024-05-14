package dev.josedegracia.jamboardReplacement.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    void createBoard(@RequestBody Board board){
        b_repository.createBoard(board);
    }

    @GetMapping("")
    List<Board> findAll(){
        return b_repository.findAll();
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
