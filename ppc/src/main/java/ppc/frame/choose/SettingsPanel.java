package ppc.frame.choose;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import ppc.event.SettingsChangeEvent;
import ppc.manager.EventManager;
import ppc.manager.SettingsManager;

public class SettingsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1726322560210668868L;

	private JTextField resultsPath;
	private JButton choosePath;
	private JComboBox<String> colorBoxes;
	private JCheckBox newFolderOnCopy;
	private JTextField matchesValue;
	private JTextField levelsValue;
	private JTextField timeValue;
	private JTextField studentsValue;
	private JTextField classesValue;

	public SettingsPanel() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.anchor = GridBagConstraints.BASELINE;
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(8, 8, 8, 8);

		c.gridx = 0;
		c.gridy = 0;
		JPanel panel = createFormPanel();
		this.add(panel, c);

		c.gridx = 0;
		c.gridy = 1;
		JButton button = new JButton("Sauvegarder les changements");
		button.addActionListener(e -> changeSettings());
		this.add(button, c);

	}

	private JPanel createFormPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.anchor = GridBagConstraints.BASELINE;
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(8, 8, 8, 8);

		JPanel generalSettingsPanel = new JPanel();
		generalSettingsPanel.setLayout(new GridBagLayout());

		c.gridx = 0;
		c.gridy = 0;
		JLabel resultsLabel = new JLabel("Chemin du dossier résultats");
		generalSettingsPanel.add(resultsLabel, c);

		c.gridx = 1;
		c.gridy = 0;
		resultsPath = new JTextField(20);
		resultsPath.setEditable(false);
		resultsPath.setText(SettingsManager.getInstance().getResultsPath());
		generalSettingsPanel.add(resultsPath, c);

		c.gridx = 2;
		c.gridy = 0;
		choosePath = new JButton("...");
		choosePath.addActionListener(e -> searchNewResultsFolder());
		
		generalSettingsPanel.add(choosePath, c);

		c.gridx = 0;
		c.gridy = 1;
		JLabel newFolderLabel = new JLabel("Créer un dossier avant de copier les fichiers");
		generalSettingsPanel.add(newFolderLabel, c);

		c.gridx = 1;
		c.gridy = 1;
		newFolderOnCopy = new JCheckBox();
		newFolderOnCopy.setSelected(SettingsManager.getInstance().createFolderWhenCopy());

		generalSettingsPanel.add(newFolderOnCopy, c);

		c.gridx = 0;
		c.gridy = 2;
		JLabel colorLabel = new JLabel("Couleur de la bar de progression");
		generalSettingsPanel.add(colorLabel, c);

		c.gridx = 1;
		c.gridy = 2;
		colorBoxes = new JComboBox<>(new String[] { "Défaut", "Vert", "Violet" });
		generalSettingsPanel.add(colorBoxes, c);
		switch (SettingsManager.getInstance().getProgressBarColor().toLowerCase()) {
		case "défaut":
		case "default":
			colorBoxes.setSelectedIndex(0);
			break;

		case "vert":
		case "green":
			colorBoxes.setSelectedIndex(1);
			break;

		case "violet":
			colorBoxes.setSelectedIndex(2);
			break;

		default:
			break;
		}
		generalSettingsPanel.setBorder(BorderFactory.createTitledBorder("Paramètres généraux"));

		JPanel defaultValuesPanel = new JPanel();
		defaultValuesPanel.setLayout(new GridBagLayout());
		c.gridwidth = 1;

		c.gridx = 0;
		c.gridy = 0;
		JLabel matchesLabel = new JLabel("Nombre de parties");
		defaultValuesPanel.add(matchesLabel, c);

		c.gridx = 1;
		c.gridy = 0;
		matchesValue = new JTextField(String.valueOf(SettingsManager.getInstance().getMatchesNumber()));
		defaultValuesPanel.add(matchesValue, c);

		c.gridx = 0;
		c.gridy = 1;
		JLabel levelsLabel = new JLabel("Nombre de groupes");
		defaultValuesPanel.add(levelsLabel, c);

		c.gridx = 1;
		c.gridy = 1;
		levelsValue = new JTextField(String.valueOf(SettingsManager.getInstance().getGroupsNumber()));
		defaultValuesPanel.add(levelsValue, c);

		c.gridx = 0;
		c.gridy = 2;
		JLabel timeLabel = new JLabel("Temps max de recherche (en s)");
		defaultValuesPanel.add(timeLabel, c);

		c.gridx = 1;
		c.gridy = 2;
		timeValue = new JTextField(String.valueOf(SettingsManager.getInstance().getMaxTime()), 10);
		defaultValuesPanel.add(timeValue, c);

		c.gridx = 0;
		c.gridy = 3;
		JLabel studentsLabel = new JLabel("Seuil d'élèves différents rencontrés (en %)");
		defaultValuesPanel.add(studentsLabel, c);

		c.gridx = 1;
		c.gridy = 3;
		studentsValue = new JTextField(
				String.valueOf(SettingsManager.getInstance().getStudentsMetThreshold() * 100 + "%"), 10);
		defaultValuesPanel.add(studentsValue, c);

		c.gridx = 0;
		c.gridy = 4;
		JLabel classesLabel = new JLabel("Seuil de classes différentes rencontrées (en %)");
		defaultValuesPanel.add(classesLabel, c);

		c.gridx = 1;
		c.gridy = 4;
		classesValue = new JTextField(
				String.valueOf(SettingsManager.getInstance().getClassesMetThreshold() * 100 + "%"), 10);
		defaultValuesPanel.add(classesValue, c);
		defaultValuesPanel.setBorder(BorderFactory.createTitledBorder("Valeurs de création de tournoi par défaut"));

		c.gridx = 0;
		c.gridy = 0;
		panel.add(generalSettingsPanel, c);

		c.gridx = 0;
		c.gridy = 1;
		panel.add(defaultValuesPanel, c);

		return panel;
	}

	private void changeSettings() {
		String createString = newFolderOnCopy.isSelected() ? "1" : "0";
		String studentString = String.valueOf(Float.valueOf(studentsValue.getText().replace("%", "")) / 100);
		String classesString = String.valueOf(Float.valueOf(classesValue.getText().replace("%", "")) / 100);

		SettingsChangeEvent event = new SettingsChangeEvent(resultsPath.getText(), createString, matchesValue.getText(),
				levelsValue.getText(), colorBoxes.getSelectedItem().toString(), timeValue.getText(), studentString,
				classesString);

		EventManager.getInstance().callEvent(event);
	}
	
	private void searchNewResultsFolder() {
		JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int choose = chooser.showOpenDialog(this);
		if (choose == JFileChooser.APPROVE_OPTION) {
			File chosenDir = chooser.getSelectedFile();
			resultsPath.setText(chosenDir.getAbsolutePath());
		}
	}

}