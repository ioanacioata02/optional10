package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

        private final List<Player> players = new ArrayList<>();
        public PlayerController() {
           players.add(new Player(1));
            players.add(new Player(1));
        }
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


}
