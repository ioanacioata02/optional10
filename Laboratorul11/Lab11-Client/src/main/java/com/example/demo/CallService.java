package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/call")
public class CallService {
    final Logger log = LoggerFactory.getLogger(CallService.class);
    final String uri = "http://localhost:8090/players";
    private RestTemplate restTemplate;

    public CallService() {
        this.restTemplate = new RestTemplate();
    }

    @GetMapping("/players")
    public List<Player> getPlayers() {
        log.info("Start");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Player>> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Player>>() {
                });
        List<Player> result = response.getBody();
        result.forEach(p -> log.info(p.toString()));
        log.info("Stop");
        return result;
    }

    @GetMapping("/games")
    public List<Game> getGames() {
        log.info("Start");
        String uri = "http://localhost:8090/games";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Game>> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Game>>() {
                });
        List<Game> result = response.getBody();
        result.forEach(p -> log.info(p.toString()));
        log.info("Stop");
        return result;
    }


    public void updatePlayer(int id, String name) {
        String url = "http://localhost:8090/players/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // asta inseamna ca request body-ul e URL-encoded
// in a MultivaluedMap, we can have zero or more objects associated with the same key.
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("name", name);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Void> response = restTemplate.exchange(
                url, HttpMethod.PUT, request, Void.class);


    }

    public void deletePlayer(int id) {
        String uri = "http://localhost:8090/players/" + id;
        ResponseEntity<Void> response = restTemplate.exchange(
                uri, HttpMethod.DELETE, null,
                Void.class);

    }


    @PostMapping

    public void addPlayer(Player player) {
        String url = "http://localhost:8090/players/obj";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Player> request = new HttpEntity<>(player, headers);
        System.out.println(request);
        ResponseEntity<Void> response = restTemplate.exchange(
                url, HttpMethod.POST, request, Void.class);


    }


}