package com.example.demo;

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
        public PlayerController() {
           players.add(new Player(1, "Raluca"));
            players.add(new Player(-1, "Ioana"));
            players.forEach(System.out::println);
        }
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
        public int createPlayer(@RequestParam int color, @RequestParam String name) {
            Player player = new Player(color, name);
            players.add(player);
            return player.getId();
        }
        @PostMapping(value = "/obj", consumes="application/json")
        public ResponseEntity<String>
        createProduct(@RequestBody Player player) {
            players.add(player);
            return new ResponseEntity<>("Player created successfully", HttpStatus.CREATED);
    }
}
