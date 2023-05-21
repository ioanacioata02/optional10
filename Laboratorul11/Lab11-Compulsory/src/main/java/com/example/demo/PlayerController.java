package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping("/players")
public class PlayerController {

        private final List<Player> players = new ArrayList<>();
        // get
        @GetMapping
        public List<Player> getPlayers() {
            return players;
        }
        @GetMapping("/count")
        public int countPLayers() {
            return players.size();
        }
        @GetMapping("/{id}")
        public Player getPlayer(@PathVariable("id") int id) {
            return players.stream()
                    .filter(p -> p.getId() == id).findFirst().orElse(null);
        }


        // post
        @PostMapping
        public int createPlayer(@RequestParam String name, @RequestParam int color) {
            Player player = new Player(color, name);
            players.add(player);
            return player.getId();
        }

        @PostMapping(value = "/obj", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String>  createPlayer(@RequestBody JsonNode jsonPlayer) {
            System.out.println(jsonPlayer);
            ObjectMapper objectMapper = new ObjectMapper();
            Player player = null;
            HttpStatus httpStatus = HttpStatus.CREATED;
            String message = "Player created successfully";
            try {
                player = objectMapper.treeToValue(jsonPlayer, Player.class);
                player.setId(Player.incrementNr());
                /*
                if(jsonPlayer.get("socket")!=null)
                    System.out.println("E setat si socketul");
                * */
                players.add(player);
            } catch (JsonProcessingException e) {
                System.err.println(e);
                message = "Failed to create player";
                httpStatus = HttpStatus.METHOD_FAILURE;
            }
            return new ResponseEntity<>(message, httpStatus);
        }

        //put
        @PutMapping("/{id}")
        public ResponseEntity<String> updatePlayer(@PathVariable int id, @RequestParam String name) {
            Player player = findById(id);
            if (player == null) {
                return new ResponseEntity<>(
                        "Player not found", HttpStatus.NOT_FOUND); //or GONE
            }
            player.setName(name);
            return new ResponseEntity<>("Player updated successsfully", HttpStatus.OK);
        }

        //delete
        @DeleteMapping(value = "/{id}")
        public ResponseEntity<String> deleteProduct(@PathVariable int id) {
            Player player = findById(id);
            if (player == null) {
                return new ResponseEntity<>(
                        "Player not found", HttpStatus.GONE);
            }
            players.remove(player);
            return new ResponseEntity<>("Player removed", HttpStatus.OK);
        }
        private Player findById(int id){
            return players.stream()
                    .filter(player -> player.getId() == id)
                    .findFirst()
                    .orElse(null);
        }
}
