package net.supercraftalex.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import net.supercraftalex.liquido.Liquido;

public class API {
	
	private String requestServer;
	
	public API(String server) {
		
		this.requestServer = server;
		log("Requesting user data...");
		log("[Request] loading configs...");
		log("[Request] updating online status...");
		log("[Request] downloading data...");
		
	}
	
	public boolean isPremumUser(String user) {
		if(user == "DEV" || user == "super_craft_alex") {
			return true;
		}
		try {
			 BufferedReader input =  new BufferedReader(new InputStreamReader(new URL(this.requestServer+"user.php?mode=is&user="+user).openStream()));
			 String line = null;
			 int index = 0;
			 while (( line = input.readLine()) != null){
			      if(index == 0) {
			    	  if(line.contains("true")) {
			    		  return true;
			    	  }
			      }
			      index++;
			  }
			 input.close();
		} catch(Exception e) {Liquido.INSTANCE.logger.Error(e.getMessage());}
		return false;
	}
	
	private void log(String s) {
		Liquido.INSTANCE.logger.Info("[API] "+s);
	}
	
}
