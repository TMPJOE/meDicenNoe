package dev.josedegracia.jamboardReplacement.board;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public class B_Repository {
    Random randomId = new Random();
    private  List <Board> boards = new ArrayList<>();

    List<Board> findAll(){
        //return all boards with toString
        return boards;
    }

    //create a new board
    public void createBoard(String bName){
        boards.add(new Board(bName));
    }

    Optional<Board> findById(int id){
        //return board by id
        return boards.stream().
                filter(board -> board.getId() == id).
                findFirst();
    }

    @PostConstruct
    private void init(){
        boards.add(new Board( "Board 1"));
        boards.add(new Board("Board 2"));
    }
}
