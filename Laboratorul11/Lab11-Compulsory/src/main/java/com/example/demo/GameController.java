package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {
    private final List<Game> games = new ArrayList<>();

    public GameController() {
    }
    //get
    @GetMapping
    public List<Game> getGames(){
        return games;
    }
    
    //post
    @PostMapping(value = "/obj", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createGame(@RequestBody JsonNode jsonGame) {
        ObjectMapper objectMapper = new ObjectMapper();
        Game game = null;
        HttpStatus httpStatus = HttpStatus.CREATED;
        String message = "Game created successfully";
        try {
            game = objectMapper.treeToValue(jsonGame, Game.class);
            game.setId(game.incrementNr());
            games.add(game);
        } catch (JsonProcessingException e) {
            System.err.println(e);
            message = "Failed to create game";
            httpStatus = HttpStatus.METHOD_FAILURE;
        }
        return new ResponseEntity<>(message, httpStatus);
    }

    //delete
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        Game game = findById(id);
        if (game == null) {
            return new ResponseEntity<>("Game not found", HttpStatus.GONE);
        }
        games.remove(game);
        return new ResponseEntity<>("Game removed", HttpStatus.OK);
    }
    
    private Game findById(int id){
        return games.stream()
                .filter(game -> game.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
