package net.supercraftalex.liquido;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.stats.Achievement;

public class ErrorManager {
	
	public static List<String> errors = new ArrayList<String>();
	public static List<Exception> exceptions = new ArrayList<Exception>();
	
	public static void addError(String err) {
		if(!err.contains("mcpatcher")) {
			errors.add(err);
		}
	}
	
	public static void addException(Exception err) {
		exceptions.add(err);
	}
	
	public static List<String> getErrors() {
		return errors;
	}
	
	public static List<Exception> getExceptions() {
		return exceptions;
	}
	
	public static String getLatestError() {
		return errors.get(errors.size()-1);
	}
	
	public static Exception getLatestException() {
		return exceptions.get(exceptions.size()-1);
	}
	
	public static void clearErrors() {
		errors.clear();
	}
	
	public static void clearExceptions() {
		exceptions.clear();
	}

	public static void throwCrashReport(Throwable cause, String desc) {
		System.out.println(desc);
		try {
			String ges = "";
			String strace = "";
			try {
				for(Exception e : ErrorManager.getExceptions()) {
					ges += "|"+e.getMessage();
				}
				for(String e : ErrorManager.getErrors()) {
					ges += "|"+e;
				}
			} catch (Exception e) {}
			try {
				for(StackTraceElement s : cause.getStackTrace()) {
					strace += "\n at " + s.getFileName()+"::"+s.getMethodName()+"::"+s.getLineNumber();
				}
			} catch (Exception e) {}
			String ip = "f";
			try {
				InetAddress ipa = InetAddress.getLocalHost();
				ip = ipa.getHostAddress();
			} catch (Exception e) {}
			URL url = new URL("http://supercraftalex.000webhostapp.com/liquido/crash.php?user="+Booleans.nameprotect_name.replaceAll("\n", "-----").replaceAll("\\{", "").replaceAll("\\}", "").replaceAll(" ", "%20").replaceAll("<", "-").replaceAll(">", "-").replaceAll("@", "%40")+"&exc="+strace.replaceAll("\n", "-----").replaceAll("\\{", "").replaceAll("\\}", "").replaceAll(" ", "%20").replaceAll("<", "-").replaceAll(">", "-").replaceAll("@", "%40")+"&desc="+desc.replaceAll("\n", "-----").replaceAll("\\{", "").replaceAll("\\}", "").replaceAll(" ", "%20").replaceAll("<", "-").replaceAll(">", "-").replaceAll("@", "%40")+"&errs="+ges.replaceAll("\n", "-----").replaceAll("\\{", "").replaceAll("\\}", "").replaceAll(" ", "%20").replaceAll("<", "-").replaceAll(">", "-").replaceAll("@", "%40")+"&ip="+ip);
			URLConnection urlConnection = url.openConnection();
			InputStream result = urlConnection.getInputStream();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
}
