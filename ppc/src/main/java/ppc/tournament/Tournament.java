package ppc.tournament;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

import ppc.event.TournamentCreateEvent;
import ppc.manager.LogsManager;
import ppc.manager.TournamentManager;

/**
 * This class represents a tournament and will contain all data of a tournament
 * once opened. When instantiated, it will only contain the tournament's name
 * and the location of the folder where CSV files are.
 * 
 * @see TournamentManager
 * @see TournamentCreateEvent
 * 
 * @author Adrien Jakubiak
 *
 */
public class Tournament {

	private static final String NAME = "name";
	private static final String ROUNDS_NUMBER = "rounds";
	private static final String GROUPS_NUMBER = "groups";
	private static final String MAX_TIME = "time";
	private static final String STUDENT_THRESHOLD = "students";
	private static final String CLASSES_THRESHOLD = "classes";

	private File dataFolder;

	private Properties properties;

	public Tournament(File tournamentFile) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(tournamentFile));
		properties = new Properties();

		properties.load(reader);
		reader.close();

		if (!verifyTournamentProperties(properties))
			throw new IOException("Wrong properties file!");
	}

	public Tournament(String name, int matchesNumber, int groupsNumber, int maxSearchingTime,
			float studentsMetThreshold, float classesMetThreshold, File dataFolder) {
		properties = new Properties();

		properties.setProperty(NAME, name);
		properties.setProperty(ROUNDS_NUMBER, String.valueOf(matchesNumber));
		properties.setProperty(GROUPS_NUMBER, String.valueOf(groupsNumber));
		properties.setProperty(MAX_TIME, String.valueOf(maxSearchingTime));
		properties.setProperty(STUDENT_THRESHOLD, String.valueOf(studentsMetThreshold));
		properties.setProperty(CLASSES_THRESHOLD, String.valueOf(classesMetThreshold));

		this.dataFolder = dataFolder;
	}

	public File getDataFolder() {
		return dataFolder;
	}

	public Properties getTournamentProperties() {
		return properties;
	}

	public void openTournament() {
		System.out.println("Loading tournament...");
	}

	public static boolean verifyTournamentProperties(Properties properties) {
		String name = properties.getProperty(NAME);
		String matchesString = properties.getProperty(ROUNDS_NUMBER);
		String levelsString = properties.getProperty(GROUPS_NUMBER);
		String timeString = properties.getProperty(MAX_TIME);
		String studentThresholdString = properties.getProperty(STUDENT_THRESHOLD);
		String classesThresholdString = properties.getProperty(CLASSES_THRESHOLD);

		if (properties.size() != 6) {
			LogsManager.getInstance().writeErrorMessage("Wrong number of properties for a tournament...");
			return false;
		}

		if (name == null || matchesString == null || levelsString == null || timeString == null
				|| studentThresholdString == null || classesThresholdString == null) {
			LogsManager.getInstance().writeErrorMessage("Properties cannot be null");
			return false;
		}

		if (name.isBlank()) {
			LogsManager.getInstance().writeErrorMessage("Tournament's name cannot be empty");
			return false;
		}

		int matches;
		try {
			matches = Integer.valueOf(matchesString);
		} catch (Exception e1) {
			LogsManager.getInstance().writeErrorMessage("Error when parsing matches' number...");
			return false;
		}

		int levels;
		try {
			levels = Integer.valueOf(levelsString);
		} catch (Exception e1) {
			LogsManager.getInstance().writeErrorMessage("Error when parsing levels' number...");
			return false;
		}

		int time;
		try {
			time = Integer.valueOf(timeString);
		} catch (Exception e1) {
			LogsManager.getInstance().writeErrorMessage("Error when parsing searching time...");
			return false;
		}

		studentThresholdString = studentThresholdString.substring(0, studentThresholdString.length() - 1);
		float studentThreshold;
		try {
			studentThreshold = Float.valueOf(studentThresholdString);
		} catch (Exception e1) {
			LogsManager.getInstance().writeErrorMessage("Error when parsing student's threshold...");
			return false;
		}

		classesThresholdString = classesThresholdString.substring(0, classesThresholdString.length() - 1);
		float classesThreshold;
		try {
			classesThreshold = Float.valueOf(classesThresholdString);
		} catch (Exception e1) {
			LogsManager.getInstance().writeErrorMessage("Error when parsing classes threshold...");
			JOptionPane.showMessageDialog(null, "Impossible de lire la valeur de seuil de classes", "Erreur",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return verifyTournamentProperties(name, matches, levels, time, studentThreshold, classesThreshold).equals("");
	}

	public static String verifyTournamentProperties(String name, int matchesNumber, int groupsNumber,
			int maxSearchingTime, float studentsMetThreshold, float classesMetThreshold) {
		String message = "";

		if (matchesNumber < 0) {
			LogsManager.getInstance().writeErrorMessage("Tournament's matches' number cannot be negative!");
			message = "Le nombre de parties ne peut pas être négatif !";
		} else if (groupsNumber < 0) {
			LogsManager.getInstance().writeErrorMessage("Tournament's groups' number cannot be negative!");
			message = "Le nombre de groupes ne peut pas être négatif !";
		} else if (maxSearchingTime < 0) {
			LogsManager.getInstance().writeErrorMessage("Tournament's max time search cannot be negative!");
			message = "Le temps maximal de recherche ne peut pas être négatif !";
		} else if (studentsMetThreshold < 0f) {
			LogsManager.getInstance().writeErrorMessage("Tournament's students threshold cannot be negative!");
			message = "Le seuil d'étudiants ne peut pas être négatif !";
		} else if (studentsMetThreshold > 1f) {
			LogsManager.getInstance().writeErrorMessage("Tournament's students threshold cannot be over 1!");
			message = "Le seuil d'étudiants ne peut pas être supérieur à 100% !";
		} else if (classesMetThreshold < 0f) {
			LogsManager.getInstance().writeErrorMessage("Tournament's classes threshold cannot be negative!");
			message = "Le seuil de classes ne peut pas être négatif !";
		} else if (classesMetThreshold > 1f) {
			LogsManager.getInstance().writeErrorMessage("Tournament's classes threshold can not be over 1!");
			message = "Le seuil de classes ne peut pas être supérieur à 100% !";
		}

		return message;
	}
}
