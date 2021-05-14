package net.supercraftalex.liquido;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class VersionChecker {
	
	public static boolean isLatestVersion() {
		return Booleans.isLatestversion;
	}
	
	public static String getLatestVersion() {
		return Booleans.latestVersion;
	}
	
	public static void initVersionInfo() {
		try {
			 BufferedReader input =  new BufferedReader(new InputStreamReader(new URL("http://supercraftalex.000webhostapp.com/clog.txt").openStream()));
			 String line = null;
			 int index = 0;
			 while (( line = input.readLine()) != null){
			      if(index == 0) {
			    	  Booleans.latestVersion = line;
			      }
			      index++;
			  }
			 input.close();
		} catch(Exception e) {}
		try {
			 BufferedReader input =  new BufferedReader(new InputStreamReader(new URL("http://supercraftalex.000webhostapp.com/clog.txt").openStream()));
			 String line = null;
			 int index = 0;
			 while (( line = input.readLine()) != null){
			      if(index == 0) {
			    	  Booleans.isLatestversion = line.contains(Liquido.VERSION);
			      }
			      index++;
			  }
			 input.close();
		} catch(Exception e) {}
	}
	
}
