package net.supercraftalex.liquido.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Properties;
import java.util.Scanner;

import net.minecraft.client.Minecraft;
import net.supercraftalex.liquido.ErrorManager;
import net.supercraftalex.liquido.Liquido;

public class FileUtil {
	
	public static File baseDir = new File(Minecraft.getMinecraft().mcDataDir, Liquido.INSTANCE.NAME);
	public static File configDir = new File(Minecraft.getMinecraft().mcDataDir, Liquido.INSTANCE.NAME+"/configs");
	
	public static void writeFile(File dir,String name, String text) {
	      try {    
	         FileWriter fw = new FileWriter(dir.getAbsolutePath() + "/" + name);    
	         fw.write(text);    
	         fw.close();    
	      }
	      catch(Exception e) {
	    	  ErrorManager.addException(e);
	      }    
	}
	
	public static String readFile(File dir,String name) {
		String fin = "";
	    try {
	        File myObj = new File(dir.getAbsolutePath() + "/" + name);
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {
	          String data = myReader.nextLine();
	          fin = fin + "\n" + data;
	        }
	        myReader.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	ErrorManager.addException(e);
	  	}
	    return fin;
	}
	
	public static Properties readPropertyFile(File dir, String name) {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(dir.getAbsolutePath()+"/"+name));
			String probsVersion = props.getProperty("version");
		} catch (Exception e) {
			System.out.println(e);
			ErrorManager.addException(e);;
		}
		return props;
	}
	public static String getPropertyFileVersion(File dir, String name) {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(dir.getAbsolutePath()+name));
			String probsVersion = props.getProperty("version");
			return probsVersion;
		} catch (Exception e) {
			ErrorManager.addException(e);;
		}
		return null;
	}
	public static String getPropertyFileElement(File dir, String name, String element) {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(dir.getAbsolutePath()+name));
			String el = props.getProperty(element);
			return el;
		} catch (Exception e) {
			ErrorManager.addException(e);;
		}
		return null;
	}
	public static boolean existsFiles(File dir, String name) {
		for(String f : dir.list()) {
			if(f.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	public static boolean deleteFIle(File dir, String name) {
	    File myObj = new File(dir.getAbsolutePath()+"/"+name); 
	    if (myObj.delete()) { 
	    	return true;
	    } else {
	    	return false;
	    } 
	}
	
}
