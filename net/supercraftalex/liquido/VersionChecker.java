package net.supercraftalex.liquido;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class VersionChecker {
	
	public static boolean isLatestVersion() {
		try {
			 BufferedReader input =  new BufferedReader(new InputStreamReader(new URL("http://supercraftalex.000webhostapp.com/clog.txt").openStream()));
			 String line = null;
			 int index = 0;
			 while (( line = input.readLine()) != null){
			      if(index == 0) {
			    	  if(line.contains(Liquido.VERSION)) {
			    		  return true;
			    	  } else {
			    		  return false;
			    	  }
			      }
			      index++;
			  }
			 input.close();
		} catch(Exception e) {Liquido.INSTANCE.logger.Error(e.getMessage());}
		return false;
	}
	
	public static String getLatestVersion() {
		try {
			 BufferedReader input =  new BufferedReader(new InputStreamReader(new URL("http://supercraftalex.000webhostapp.com/clog.txt").openStream()));
			 String line = null;
			 int index = 0;
			 while (( line = input.readLine()) != null){
			      if(index == 0) {
			    	  return line;
			      }
			      index++;
			  }
			 input.close();
		} catch(Exception e) {Liquido.INSTANCE.logger.Error(e.getMessage());}
		return "-Error-";
	}
	
}
