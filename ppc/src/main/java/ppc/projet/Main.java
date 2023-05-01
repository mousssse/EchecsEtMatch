package ppc.projet;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.itextpdf.text.DocumentException;

import ppc.projet.outputGeneration.PdfGenerator;
import ppc.projet.tournament.LevelThread;
import ppc.projet.tournament.Solution;

public class Main {
	public static void main(String[] args) {
		// works
		// Integer[][] classes = { { 0, 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10, 11 }, { 12,
		// 13, 14, 15, 16, 17 } };

		// to test maximisation + better maxClassesMet for paul
		// Integer[][] classes = { { 0, 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9, 10, 11 }, {
		// 12, 13, 14, 15, 16 },
		// { 17, 18, 19, 20 }, { 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33 } };

		// works with the ghost player!
		// Integer[][] classes = { { 0, 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10, 11 }, { 12,
		// 13, 14, 15, 16 } };

		// tests paul vs adrien: fonctionne pas pour adrien !
		// Integer[][] classes = { { 0, 1, 2, 3, 4, 5, 6 }, { 7, 8, 9, 10, 11 }, { 12,
		// 13, 14, 15, 16 }, { 17, 18, 19, 20} };

		// rencontre pélissier marche pas, besoin de soft :
		// Integer[][] classes = { {0, 1, 2, 3}, {4, 5, 6}, {7, 8, 9, 10, 11} };
		
		//niveau 1 rencontre 2019
		String[][] classesLvl1 = { { "Anthonin", "Lilian", "Nathan", "Maël" }, { "Gabi", "Guney", "Izye" },
				{ "Louna", "Maxine", "Enzo", "Maéva", "Alexandre", "Kaissy", "Elie" },
				{ "Adam", "Nathan", "Chloé", "Jules", "Lucile", "Brian", "Loan", "Lohenn" },
				{ "Amaury", "Noé", "Leïla", "Matylio" } };

		// niveau 2 rencontre 2019
		String[][] classesLvl2 = {
				{ "Valentin", "Vaea", "Thomas", "Manon", "Lucas", "Jeanne", "Juliette", "Romane", "Agnese", "Pauline",
						"Camille", "Loan", "Inès", "Manon", "Elsa", "Matéo" },
				{ "Lilou", "Lena", "Lucas", "Lilou", "Mirtille", "Judith", "Paul", "Pedago", "Kenji", "Tao", "Lison",
						"William", "Yaelle", "Yanis", "Kynian", "Sacha", "Gauthier", "Jules", "Erwan" },
				{ "Coraline", "Chloé", "Loan", "Charlot", "Lana", "Sans prénom", "Imène", "Loris", "Benjamin",
						"Re sans prénom" },
				{ "Rafael", "Pablo", "Léna", "Rafael", "Malon", "Lisa", "Lino", "Lilou", "Loane", "Bixente" },
				{ "Arnaud", "Matili", "Lyse", "Clément", "Julian", "Lou", "Elise", "Turis", "Célia",
						"Sans nom le retour", "Maxime" } };

		// niveau 3 rencontre 2019
		String[][] classesLvl3 = { {
				"Milane", "Nathan", "Adrien", "Léna", "Abdellah"}, {"Céci", "Emma", "Luca", "Julian", "Manon"}, {"Dine", "Augustin", "Fay", "Nicolas", "Louane", "Rayan"},
								{"Nayah", "Angie", "Erika", "Ela", "Mathias", "Périne", "Cécile", "Maylie"}, {"Thomas", "Maïssa", "Inès", "Lise", "Manon", "Maëly", "Johan", "Maïssane", "Lili", "Ella"}};
		
		// tournoi MJ
		// Integer[][] classesLvl1 = { { 0, 1, 2, 3, 4, 5, 6, 7 }, { 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 },
		//		{ 20, 21, 22, 23, 24, 25, 26, 27, 28 }, { 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41 },
		//		{ 42, 43, 44, 45, 46, 47, 48 }, { 49, 50, 51, 52, 53, 54 } };
		
		List<String[][]> classesByLevel = new ArrayList<>(Arrays.asList(classesLvl1, classesLvl2, classesLvl3));
		boolean softConstraint = false;
		int timeout = 20;
		
		int level = 1;
		List<Thread> threads = new ArrayList<>();
		List<LevelThread> lvlThreads = new ArrayList<>();
		for (String[][] lvlClasses : classesByLevel) {
			LevelThread lvlThread = new LevelThread(lvlClasses, level++, softConstraint, timeout);
			Thread thread = new Thread(lvlThread);
			thread.start();
			threads.add(thread);
			lvlThreads.add(lvlThread);
		}
		
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		int lastLevelWithGhost = -1;
		List<Solution> solutions = new ArrayList<>();
		for (int lvl = 0; lvl < lvlThreads.size(); lvl++) {
			Solution currentSolution = lvlThreads.get(lvl).getSolution();
			solutions.add(currentSolution);
			if (currentSolution.getGhost() != -1)
				lastLevelWithGhost = lvl;
		}
		
		PdfGenerator pdfGen = new PdfGenerator(solutions, classesByLevel.get(0).length, lastLevelWithGhost);
		try {
			System.out.print("creating pdf ListeMatches... ");
			pdfGen.createPdfListeMatches();
			System.out.println("done");
			System.out.print("creating pdf ListeClasses... ");
			pdfGen.createPdfListeClasses();
			System.out.println("done");
			System.out.print("creating pdf ListeNiveaux... ");
			pdfGen.createPdfListeNiveaux();
			System.out.println("done");
			System.out.print("creating pdf FicheProf... ");
			pdfGen.createPdfProfs();
			System.out.println("done");
			System.out.print("creating pdf FichesEleves... ");
			pdfGen.createPdfEleves();
			System.out.println("done");
		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
