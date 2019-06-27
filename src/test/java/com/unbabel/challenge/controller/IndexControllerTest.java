package com.unbabel.challenge.controller;

import static org.junit.Assert.*;
import java.util.Collection;
import java.util.Map;
import com.unbabel.challenge.model.Message;
import com.unbabel.challenge.controller.IndexController;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.springframework.ui.Model;
import org.junit.Test;

public class IndexControllerTest {
    static Message mTest = new Message();
    static Model model;
    static IndexController indexController;
    static String postResult;
    static String getResult;
    static JSONObject jsonObjGetResult;

    @BeforeClass
	public static void initTests() {
        indexController = new IndexController();
        mTest.setMsg("home");
        mTest.setLan("pt");
    }

    @Test
	public static void testTranslation(){
        try {
            postResult=indexController.postRequest(mTest.getMsg(),mTest.getLan());
            getResult=indexController.getRequest(postResult);
            jsonObjGetResult=new JSONObject(getResult);
            mTest.setCon(jsonObjGetResult.getString("translatedText"));
            mTest.setStatus(jsonObjGetResult.getString("status"));
            mTest.setOriLan(jsonObjGetResult.getString("source_language"));

            //check message fields
            assertEquals("Correct translated text","casa",mTest.getCon());
            assertEquals("Correct text","home",mTest.getMsg());
            assertEquals("Correct status","completed",mTest.getStatus());
            assertEquals("Correct final language","pt",mTest.getLan());
            assertEquals("Correct original language","en",mTest.getOriLan());
        } catch (Exception e) {
            mTest.setCon("Exception error: "+e);
            assertEquals("Error found","Exception error: "+e, mTest.getCon());
        }
        
    }
}