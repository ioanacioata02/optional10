package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
        CallService cs = new CallService();
        cs.getGames();
        cs.getPlayers();
       // cs.updatePlayer(1,"Raluca");
        //cs.addPlayer(new Player(-1,null,null,1,"JavaSpring",true));
        //cs.getPlayers();
//    cs.deletePlayer(3);
//	cs.getPlayers();
    }

}
