package ppc.tournament.solver;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Stores information about the solution found. Assigns half of the students to
 * a permanent table.
 * </p>
 * 
 * @author Sarah Mousset
 *
 */
public class Solution {

	private Integer[][] matches;
	private int[] studentClasses;
	private Integer[][] listClasses;
	private Map<Integer, Integer> idToTable;
	private Map<Integer, String[]> idToName;
	private int ghost;
	private int firstTable;

	public Solution(Integer[][] matches, int[] studentClasses, Integer[][] listClasses, Map<Integer, String[]> idToName,
			int ghost, int firstTable) {
		this.matches = matches;
		this.studentClasses = studentClasses;
		this.listClasses = listClasses;
		this.idToName = idToName;
		this.ghost = ghost;
		this.firstTable = firstTable;
	}

	private void initIdToTable() {
		this.idToTable = new HashMap<>();
		int firstStudent = (this.ghost == -1) ? 0 : 1;
		int offset = (this.ghost == -1) ? 1 : 0;
		int table = firstTable;
		for (int student = firstStudent; student <= matches.length / 2 - offset; student++)
			this.idToTable.put(student, table++);
	}

	public Integer[][] getMatches() {
		return this.matches;
	}

	public int[] getStudentClasses() {
		return this.studentClasses;
	}

	public Integer[][] getListClasses() {
		return this.listClasses;
	}

	public int getIdToTable(int id) {
		if (this.idToTable == null)
			this.initIdToTable();
		return this.idToTable.get(id);
	}

	public String[] getIdToName(int id) {
		return this.idToName.get(id);
	}

	public int getGhost() {
		return this.ghost;
	}
}
