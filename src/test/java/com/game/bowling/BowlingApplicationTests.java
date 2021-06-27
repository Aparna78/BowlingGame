package com.game.bowling;


import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import com.game.model.Player;
import com.game.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
class BowlingApplicationTests {
	
	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	@Mock
	private TestEntityManager entityManager;

	@Autowired
	private PlayerRepository playerRepository;

	@Test
	void contextLoads() {
	}
	
	@Test
	@Transactional
	public void testPlayerRepository() {
	    log.info("... testPersistRepository ...");
		Player p = new Player();
        p.setName("Aparna");
        p.setLaneNumber("10");
        playerRepository.save(p);
        
        Player p1 = playerRepository.findByName("Aparna");
        Assert.assertEquals("10", p1.getLaneNumber());
        }

}
