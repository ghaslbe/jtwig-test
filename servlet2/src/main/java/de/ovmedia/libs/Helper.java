package main.java.de.ovmedia.libs;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class Helper {
//
	private  Logger _logger = Logger.getRootLogger();

	public void newLogger() {
		try {
			PatternLayout layout = new PatternLayout("%d{ISO8601} %-5p [%t] %c: %m%n");
			DailyRollingFileAppender fileAppender = new DailyRollingFileAppender(layout, "logs/MeineLogDatei.log", "'.'yyyy-MM-dd_HH-mm");
			_logger.addAppender(fileAppender);
			_logger.setLevel(Level.ALL);
		} catch (Exception ex) {
			System.out.println(ex);
		}

	}

	public  void log(String message) {
		try {
			_logger.info(message);

		} catch (Exception ex) {
			System.out.println(ex);
		}

	}
}
