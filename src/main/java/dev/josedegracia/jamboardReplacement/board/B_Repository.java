package dev.josedegracia.jamboardReplacement.board;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class B_Repository {

    private final List <Board> boards = new ArrayList<>();

    List<Board> findAll(){
        //return all boards with toString
        return boards;
    }

    //create a new board
    public void createBoard(String bName){
        boards.add(new Board(bName));
    }

    //update board
    void updateBoard(String name, Integer id){
        Optional<Board> existingBoard = findById(id);
        existingBoard.ifPresent(board -> board.setName(name));
    }

    //delete board
    void deleteBoard(int id){
        boards.removeIf(board -> board.getId() == (id));
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

    List<Board> findByDate(LocalDate date){
        //return boards by date
        return boards.stream()
                .filter(board -> board.getCreationDate().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }
}
