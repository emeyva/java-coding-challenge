package com.unbabel.challenge.controller;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Map;

import com.unbabel.challenge.model.Message;
import com.unbabel.challenge.controller.IndexController;
import org.junit.BeforeClass;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.junit.Test;

public class IndexControllerTest {
    static Message mTest = new Message();
    static Model model;
    static IndexController indexController;

    @BeforeClass
	public static void initTests() {
        indexController = new IndexController();
        
        
    }
    @Test
	public static void testTrans() {
        mTest.setMsg("home");
        mTest.setLan("pt");
        indexController.checkMsgInfo(mTest, model);
        assertEquals("Correct message",mTest.getCon(),"casa");
        assertEquals("Correct status",mTest.getStatus(),"completed");
        assertEquals("Correct language",mTest.getOriLan(),"en");
	}
}