package tool;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DataValidator {
	
	public static boolean validateIdx(String idx) {
		return idx.matches("^\\d{1,6}$");
	}
	
	public static String validateProductName(String name) {
		name = name.trim();
		if (name.length() > 20) {
			name = name.substring(0, 20);
		}
		return name.replaceAll("[^a-zA-Z0-9가-힣\\s]", "");
	}
	
	public static boolean validateDate(String date) {
		if (!date.matches("^\\d{8}$")) {
			return false;
		}
		
		try {
			LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}
	
	public static boolean validateQuantity(String quantity) {
		return quantity.matches("^\\d{1,10}$");
	}
	
	public static String validateStatus(String status) {
		status = status.toUpperCase();
		return status.equals("Y") || status.equals("N") ? status : "N";
	}
	
	public static boolean validateId(String id) {
		return id.matches("^\\d{1,6}$");
	}
	
	

}
