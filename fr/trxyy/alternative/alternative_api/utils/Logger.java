package fr.trxyy.alternative.alternative_api.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Trxyy
 */
public class Logger {
	// Path to log file
	private static final File LOG_FILE = new File("logs/launcher/latest.txt");

	/**
	 * Log a text
	 * @param s The text to log
	 */
	public static void log(String s) {
		String logMessage = getName() + (s.startsWith("=") || s.startsWith("|") ? "" : " ") + s;
		System.out.println(logMessage);
		saveToFile(getTime() + logMessage);
	}

	/**
	 * Log a text with error
	 * @param s The text to log
	 */
	public static void err(String s) {
		String logMessage = getName() + "[ERROR] " + s;
		System.err.println(logMessage);
		saveToFile(getTime() + logMessage);
	}

	private static boolean isFirstLog = true;
	/**
	 * Saves logs to the file
	 * @param logMessage The log message to save
	 */
	private static void saveToFile(String logMessage) {
		// Ensure the logs directory exists
		File logDir = new File("logs/launcher");
		if (!logDir.exists()) {
			logDir.mkdirs();
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, !isFirstLog))) {
			writer.write(logMessage);
			writer.newLine();
		} catch (IOException e) {
			System.err.println("[Logger] Failed to write log to file: " + e.getMessage());
		}

		if (isFirstLog){
			isFirstLog = false;
		}
	}

	/**
	 * @return The current time
	 */
	private static String getTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return "[" + sdf.format(cal.getTime()) + "]";
	}

	private static String getName() {
		return "[Krosmocraft]";
	}
}
