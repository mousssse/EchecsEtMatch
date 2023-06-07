package ppc.frame.open;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import ppc.annotation.EventHandler;
import ppc.event.Listener;
import ppc.event.SolutionFoundEvent;
import ppc.event.TournamentSolverFinishedEvent;

public class LoadingPanel extends JPanel implements Listener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2798203968951488959L;

	private int level;

	private JLabel levelLabel;
	private JProgressBar progressBarStudents;
	private JProgressBar progressBarClasses;
	private JButton stopButton;

	public LoadingPanel(int level) {
		//EventManager.getInstance().registerListener(this);
		this.level = level;

		GridLayout gridLayout = new GridLayout(4, 1);
		gridLayout.setVgap(10);
		setLayout(gridLayout);
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// TODO: set max panel size
		
		levelLabel = new JLabel("Niveau " + (level + 1));
		levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(levelLabel);

		progressBarStudents = new JProgressBar();
		progressBarStudents.setStringPainted(true);
		add(progressBarStudents);

		progressBarClasses = new JProgressBar();
		progressBarClasses.setStringPainted(true);
		add(progressBarClasses);

		stopButton = new JButton("Arrêter la recherche");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: stop search
				searchIsStopped();
			}
		});
		add(stopButton);
	}
	
	private void searchIsStopped() {
		levelLabel.setText("Niveau " + (level + 1) + " - recherche terminée");
		stopButton.setEnabled(false);
	}

	private void updateProgress(float progressStudents, float progressClasses) {
		progressBarStudents.setString(String.format("%.2f%%", progressStudents));
		progressBarStudents.setValue((int) progressStudents);
		progressBarClasses.setString(String.format("%.2f%%", progressClasses));
		progressBarClasses.setValue((int) progressClasses);
	}

	@EventHandler
	public void onSolutionFound(SolutionFoundEvent event) {
		if (event.getLevel() == this.level) {
			System.out.println("Updated!");
			this.updateProgress((float) event.getStudentsMet() / event.getMaxStudentsMet() * 100,
					(float) event.getClassesMet() / event.getMaxClassesMet() * 100);
			repaint();
		}
	}
	
	@EventHandler
	public void onFinalSolutionFound(TournamentSolverFinishedEvent event) {
		System.out.println("my level is: " + level);
		if (event.getLevel() == this.level) {
			this.updateProgress((float) event.getStudentsMet() / event.getMaxStudentsMet() * 100,
					(float) event.getClassesMet() / event.getMaxClassesMet() * 100);
			searchIsStopped();
		}
	}
}