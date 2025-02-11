package com.unbabel.challenge.controller;

import com.unbabel.challenge.model.Message;
import com.unbabel.challenge.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.DataOutputStream;
import org.json.JSONObject;
import java.util.concurrent.TimeUnit;

@Controller
public class IndexController {
    String url = "https://sandbox.unbabel.com/tapi/v2/translation/";
    String username = "&username=fullstack-challenge";
    String apiKey = "?api_key=9db71b322d43a6ac0f681784ebdcc6409bb83359";
    HttpURLConnection con = null;
    String postResult;
    String getResult;
    String status;
    String translatedText;
    String originalLanguage;
    JSONObject jsonObjGetResult;
    

    @Autowired
    MessageRepository messageRepository;

    @Value("${java.challenge.company}")
    private String company; // Reads this value from Spring properties file
     
    @GetMapping("/")
    public String main(Model model) {
        //set variables to be used in thymeleaf template
        Message form = new Message();
        model.addAttribute("message", form);
        model.addAttribute("messages", messageRepository.findAll().iterator());
        
        return "index"; //thymeleaf template name (index -> index.html)
    }

    @PostMapping("/")
    public String checkMsgInfo(@ModelAttribute Message message, Model model) {
    
        //execute post and get request
        postResult=postRequest(message.getMsg(),message.getLan());
        getResult=getRequest(postResult);
        
        //use server answer to get status, translated text and original language
        jsonObjGetResult=new JSONObject(getResult);
        
        //set status, translated text and original language
        message.setCon(jsonObjGetResult.getString("translatedText"));
        message.setStatus(jsonObjGetResult.getString("status"));
        message.setOriLan(jsonObjGetResult.getString("source_language"));

        //save message to rep and add both to model
        messageRepository.save(message);
        model.addAttribute("message", message);
        model.addAttribute("messages", messageRepository.findAll());
        
        return "index";
    }
    
    
    public String getRequest(String jsonString){
        JSONObject jsonResponse = new JSONObject(jsonString);
        String getUrl=url+jsonResponse.getString("uid")+"/"+apiKey+username;
        String resultString = "";
        
        
        try {
            //set connection;
            URL obj = new URL(getUrl);
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("GET");
            con.setDoOutput(true);

            //get response from request
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer response = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            con.disconnect();

            //wait for translation to be complete
            JSONObject jsonFinalResponse = new JSONObject(response.toString());
            while(jsonFinalResponse.length()<9){
                TimeUnit.SECONDS.sleep(1);
                resultString=getRequest(jsonString);
                jsonFinalResponse = new JSONObject(resultString);
            }
        
            //return final json string
            resultString = jsonFinalResponse.toString();
            
        } catch (Exception e) {
            resultString="Error: "+e;
        } finally {
            if (con != null) {
                con.disconnect();
            }
            return resultString;
        }
    }


    public String postRequest(String text, String language){
        String postUrl=url+apiKey+username;
        String resultString = "";
        try {
            //set connection
            URL obj = new URL(postUrl);
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            
            String jsonInputString = "{\"text\" : \"" + text +
					"\", \"source_language\" : \"" + "en" +
					"\", \"target_language\" : \"" + language + 
					"\", \"text_format\" : \"text\"}";

            //get connection output
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(jsonInputString);
            wr.flush();	wr.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer response = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            con.disconnect();
            resultString = response.toString();
            
        } catch (Exception e) {
            resultString="Error: "+e;
        } finally {
            if (con != null) {
                con.disconnect();
            }
            return resultString;
        }
        
    }
    
}
