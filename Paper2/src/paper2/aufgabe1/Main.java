package paper2.aufgabe1;

public class Main {

	public static void main(String[] args) {
		RandomAccessMachine ra = new RandomAccessMachine();
		String input = "0601" //Akkumulator auf Wert 1 setzen um es in der Hilfsvariable zu speichern.
				+ "0703" //In die Hilfsvariable(Adresse3) wird 1 gespeichert um nacher 1 abziehen zu können.
 				
				+ "0801" //Hier wird die Variable n(Adresse2) eingegeben.
				
 				//Rücksprungpunkt der "Schleife"
 				+ "0502" //Lade das Zwischenergebnis(Adresse2) in den Akkumulator.
				+ "0101" //Addiere das n(Adresse1) auf den Akkumulator.
				+ "0702" //Speichere das Ergebnis wieder in das Zwischenergebnis (Adresse 2).
				+ "0501" //Lade das n(Adresse1) in den Akkummulator. 
				+ "0203" //Führe die Rechnung n - 1 aus. 
				+ "0701" //Speichere n - 1 wieder in der n(Adresse1) Variable.
				
				+ "1004" //Solange Akkumulator > 0 ist werden die oberen sechs Schritte wiederholt.
				
				+ "0902" //Gib das Endergebnis(Adresse 2) aus.
				+ "0A99"; //Programmende
		input = "06000702080105010C09010207020B0309020A99";
		input = "0802130209020A99";
		ra.input(input);
	}

}


