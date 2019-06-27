package com.unbabel.challenge;

import com.unbabel.challenge.controller.IndexControllerTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaChallengeApplicationTests {

	@Test
	public void contextLoads() {
		IndexControllerTest.initTests();
		IndexControllerTest.testTrans();
	}

}
