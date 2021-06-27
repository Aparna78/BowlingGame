package com.game.bowling;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.game.model.Player;
import com.game.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class BowlingApplicationTests {
	
	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private TestEntityManager entityManager;

	@Autowired
	private PlayerRepository playerRepository;

	@Test
	void contextLoads() {
	}
	
	@SuppressWarnings("deprecation")
	@Test
	@Transactional
	public void testPlayerRepository() {
	    log.info("... testPersistRepository ...");
		Player p = new Player();
        p.setName("Aparna");
        p.setLaneNumber(10);
        playerRepository.save(p);
        
        Player p1 = playerRepository.findByName("Aparna");
        Assert.assertEquals(new Integer(10), p1.getLaneNumber());
        }

	@Test
	public void findAllPlayerTest() throws Exception {

		this.mockMvc.perform(get("/players")).andExpect(status().isOk());
	}
}
