package indp.nbarthen.proj.controller;

import java.util.Vector;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import indp.nbarthen.proj.repository.LoLMatch;
import indp.nbarthen.proj.repository.Participant;
import indp.nbarthen.proj.repository.PlayerAcc;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RedirectUrl {
	
	public static String getRedirectUrl(HttpServletRequest request, PlayerAcc summoner){
		String urlOptionalParameters = request.getQueryString();
		int index = urlOptionalParameters.indexOf("matchType=");
 		if(index != -1) {
 		    int endIndex = urlOptionalParameters.indexOf("&", index);
 		    if(endIndex == -1) {
 		        endIndex = urlOptionalParameters.length();
 		    }
 		   urlOptionalParameters = urlOptionalParameters.substring(0, index) + "matchType=" + summoner.getMatchType() + urlOptionalParameters.substring(endIndex);
 		}
 		
 		String url = request.getRequestURL().toString() + "?" + urlOptionalParameters;

	    return url;
	}






}
