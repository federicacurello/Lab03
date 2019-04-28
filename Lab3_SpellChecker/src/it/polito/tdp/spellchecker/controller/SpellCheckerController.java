/**
 * Sample Skeleton for 'SpellChecker.fxml' Controller Class
 */

package it.polito.tdp.spellchecker.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class SpellCheckerController {
	List<Dictionary> dictionaries;
	Dictionary dictionary;
	static String[] LANGUAGE_AVAIABLE = {"English", "Italian"};
	private final static boolean dichotomicSearch = false;
	private final static boolean linearSearch = true;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="Menu"
    private ComboBox<String> Menu; // Value injected by FXMLLoader

    @FXML // fx:id="txtInput"
    private TextArea txtInput; // Value injected by FXMLLoader

    @FXML // fx:id="btnSpell"
    private Button btnSpell; // Value injected by FXMLLoader

    @FXML // fx:id="txtErrori"
    private TextArea txtErrori; // Value injected by FXMLLoader

    @FXML // fx:id="numErrori"
    private Label numErrori; // Value injected by FXMLLoader

    @FXML // fx:id="btnClear"
    private Button btnClear; // Value injected by FXMLLoader

    @FXML // fx:id="tempo"
    private Label tempo; // Value injected by FXMLLoader

    @FXML
    void doClearText(ActionEvent event) {
    	txtInput.clear();
    	txtErrori.clear();
    	numErrori.setText("The text contains 0 errors");
    	tempo.setText("Spell check completed in 0 seconds");
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	txtErrori.clear();
    	Dictionary diz= new Dictionary();
    	String riga= txtInput.getText().toLowerCase();
    	riga=riga.replaceAll("\n", " ");
    	riga=riga.replaceAll("[.,\\/#!?$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", "");
    	
    	if(riga== null || riga.length()==0) {
    		txtErrori.setText("Inserire una o più parole");
    		return;
    	}
    	
    	diz.loadDictionary(Menu.getValue());
    	List<String> input= new LinkedList<String>();
    	
    	StringTokenizer st= new StringTokenizer( riga, " ");
    	
    	while(st.hasMoreTokens()) {
    		String p= st.nextToken();
    		input.add(p);
    	}
    	long start = System.nanoTime();
    	List<RichWord> result;
    	if (dichotomicSearch) {
			result = diz.spellCheckTextDichotomic(input);
		} else if (linearSearch) {
			result = diz.spellCheckTextLinear(input);
		} else {
			result = diz.spellCheckText(input);

		}
    	
    	long end = System.nanoTime();
    	txtErrori.setText(result.toString().replaceAll("[.,\\/#!?$%\\^&\\*;:{} =\\-_`~()\\[\\]\"]", ""));
    	numErrori.setText("The text contains "+result.size()+" errors");
    	tempo.setText("Spell check completed in " + (end - start) / 1e9 + " seconds");

    }
    @FXML
    void doChangeLanguage(ActionEvent event) {
    	String language = (String) Menu.getValue();
    	for (Dictionary tempDictionary : dictionaries) {
    		if (tempDictionary.getLingua().equals(language))
    			dictionary = tempDictionary;
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert Menu != null : "fx:id=\"Menu\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnSpell != null : "fx:id=\"btnSpell\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtErrori != null : "fx:id=\"txtErrori\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert numErrori != null : "fx:id=\"numErrori\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert tempo != null : "fx:id=\"tempo\" was not injected: check your FXML file 'SpellChecker.fxml'.";

        dictionaries = new LinkedList<Dictionary>();
    	
    	for (int i = 0; i < LANGUAGE_AVAIABLE.length; i++) {
    		Dictionary tempDictionary = new Dictionary();
    		tempDictionary.loadDictionary(LANGUAGE_AVAIABLE[i]);
    		Menu.getItems().add(LANGUAGE_AVAIABLE[i]);
    		dictionaries.add(tempDictionary);
    	}
    	
    	Menu.getSelectionModel().selectFirst();
    	dictionary = dictionaries.get(0);
    }
    
    }

