package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {
	
	private List<String> dizionario= new ArrayList<String>();
	private String lingua;
	
	public String getLingua() {
		return lingua;
	}
	public void setLingua(String lingua) {
		this.lingua = lingua;
	}
	public void loadDictionary(String language) {
		
			try {
				FileReader fr= new FileReader("rsc/"+language+".txt");
				BufferedReader br= new BufferedReader(fr);
				String word;
				while((word=br.readLine())!=null) {
					dizionario.add(word);
				}
				br.close();
				this.setLingua(language);

				System.out.println("Dizionario " + language + " loaded. Found " + dizionario.size() + " words.");
				
			}catch(IOException e) {
				System.out.println("Errore nella lettura del file\n");
			}
		
		}
	
	public List<RichWord> spellCheckText(List<String> inputTextList){
		List<RichWord> sbagliate= new ArrayList<RichWord>();
		for(String s: inputTextList) {
			if(!dizionario.contains(s)) {
				RichWord sb= new RichWord(s, false);
				sbagliate.add(sb);
				}
			else {
				RichWord b= new RichWord(s, true);
			}
			
		}
		return sbagliate;
		
	}
	public List<RichWord> spellCheckTextDichotomic(List<String> input) {
		List<RichWord> parole = new ArrayList<RichWord>();

		for (String str : input) {

			RichWord richWord = new RichWord(str,true);
			if (binarySearch(str.toLowerCase()))
				richWord.setCorretta(true);
			else {
				richWord.setCorretta(false);
				parole.add(richWord);
				}
		}

		return parole;
	}
	private boolean binarySearch(String stemp) {
		int inizio = 0;
		int fine = dizionario.size();

		while (inizio != fine) {
			int medio = inizio + (fine - inizio) / 2;
			if (stemp.compareToIgnoreCase(dizionario.get(medio)) == 0) {
				return true;
			} else if (stemp.compareToIgnoreCase(dizionario.get(medio)) > 0) {
				inizio = medio + 1;
			} else {
				fine = medio;
			}
		}

		return false;
	}
	public List<RichWord> spellCheckTextLinear(List<String> input) {
		List<RichWord> parole = new ArrayList<RichWord>();

		for (String str : input) {

			RichWord richWord = new RichWord(str,true);
			
			boolean found = false;
			for (String word : dizionario) {
				if (word.equalsIgnoreCase(str)) {
					found = true;
					break;
				}
			}
			
			if (found) {
				richWord.setCorretta(true);	
			} else {
				richWord.setCorretta(false);
				parole.add(richWord);
			}

		}

		return parole;

	}
}
