package com.lanagj.adviseme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class AdviseMeApplicationTests {

	@Autowired
	protected MongoTemplate mongoTemplate;

}
