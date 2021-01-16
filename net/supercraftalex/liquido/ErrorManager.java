package net.supercraftalex.liquido;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.stats.Achievement;

public class ErrorManager {
	
	public static List<String> errors = new ArrayList<String>();
	public static List<Exception> exceptions = new ArrayList<Exception>();
	
	public static void addError(String err) {
		errors.add(err);
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
		return errors.get(errors.size() - 1);
	}
	
	public static Exception getLatestException() {
		return exceptions.get(exceptions.size() - 1);
	}
	
	public static void clearErrors() {
		errors.clear();
	}
	
	public static void clearExceptions() {
		exceptions.clear();
	}
	
}
