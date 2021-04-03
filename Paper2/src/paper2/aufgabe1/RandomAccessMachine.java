package paper2.aufgabe1;

import java.util.ArrayList;
import java.util.Scanner;

public class RandomAccessMachine {

	private ArrayList<Integer> addresses; //der index ist die adresse und addresses[index] enthält den wert der adresse
	private ArrayList<String> programmStorage; //Programmspeicher
	private int programmCounter; //befehlszähler
	private boolean isRunning;
	private Scanner sc;

	public RandomAccessMachine() {
		this.addresses = new ArrayList<>();
		this.programmStorage = new ArrayList<>();
		this.isRunning = true;
		this.programmCounter = 1;
		sc = new Scanner(System.in);
		//init the ArrayList with zeros
		for(int i = 0; i < 99; i++) {
			addresses.add(0);
		}
	}
	//falls die Eingabe valide ist startet das programm
	public void input(String input) {
		System.out.println("Random Acces Machine try to run programm: " + input);
		if(isCorrectInput(input)) {
			createProgrammStorage(input);
			runProgramm();
		}else {
			System.out.println("[ERROR] Wrong input! The instruction has to contain 0A99 to end the programm and every single instruction has to be 4 characters.");
		}
	}
	//Erstellt den Programmspeicher, fasst 4 chars zu einer Zeile und fügt anschließend die Zeilennummer davor
	private void createProgrammStorage(String input) {
		String s = "";
		for(int i = 0; i < input.length()/4; i++) {
			if(i < 9) {
				s = "0"+(i+1);
			}else {
				s = ""+(i+1);
			}
			programmStorage.add(s+input.substring(i*4, (i*4) + 4));
		}
		printProgrammStorage();
	}
	//Führt die Befehle aus bis ein HLT kommt
	private void runProgramm() {
		for(int i = 0; i < programmStorage.size()+1; i++) {
			if(isRunning) {
				String programmLine = programmStorage.get(programmCounter-1); 
				int addressIndex = getAdress(programmLine);
				Instruction instruction = decodeInstruction(getInstruction(programmLine));
				programmCounter++;
				i = programmCounter - 1;
				calculateInstruction(instruction, addressIndex);
			}
		}
	}
	//Je nach instruction wird diese auch ausgeführt
	private void calculateInstruction(Instruction instruction, int addressIndex) {
		switch(instruction) {
		case ADD:
			System.out.println("[ADD] I calculate " + getAccumulator() + " + " + addresses.get(addressIndex) + ".");
			setAccumulator(getAccumulator() + addresses.get(addressIndex));
			break;
		case DIV:
			System.out.println("[DIV] I calculate " + getAccumulator() + " / " + addresses.get(addressIndex) + ".");
			setAccumulator(getAccumulator() / addresses.get(addressIndex));
			break;
		case HLT:
			System.out.println("[HLT] stopps the programm.");
			isRunning = false;
			break;
		case INP:
			System.out.println("[INP] Let the user input a value and set it to address " + addressIndex + ".");
			System.out.println("Please enter a value for address " + addressIndex + ".");
			addresses.set(addressIndex, sc.nextInt());
			break;
		case JEZ:
			System.out.println("[JEZ] if accumulator is 0 then set the programm counter to " + addressIndex + ".");
			if(getAccumulator() == 0) {
				programmCounter = addressIndex;
			}
			break;
		case JGE:
			System.out.println("[JGE] if accumulator is equal or greater than zero then set programm counter to " + addressIndex + ".");
			if(getAccumulator() >= 0) {
				programmCounter = addressIndex;
			}
			break;
		case JGZ:
			System.out.println("[JGZ] if accumulator is greater than zero then set programm counter to " + addressIndex + ".");
			if(getAccumulator() > 0) {
				programmCounter = addressIndex;
			}
			break;
		case JLE:
			System.out.println("[JLE] if accumulator is equal or less than zero then set programm counter to " + addressIndex + ".");
			if(getAccumulator() <= 0) {
				programmCounter = addressIndex;
			}
			break;
		case JLZ:
			System.out.println("[JLZ] if accumulator is less than zero then set programm counter to " + addressIndex + ".");
			if(getAccumulator() < 0) {
				programmCounter = addressIndex;
			}
			break;
		case JMP:
			System.out.println("[JMP] Set the programm counter to " + addressIndex + ".");
			programmCounter = addressIndex;
			break;
		case JNE:
			System.out.println("[JNE] if accumulator is not 0 then set the programm counter to " + addressIndex + ".");
			if(getAccumulator() != 0) {
				programmCounter = addressIndex;
			}
			break;
		case LDA:
			System.out.println("[LDA] Loaded address " + addressIndex + " into accumulator.");
			setAccumulator(addresses.get(addressIndex));
			break;
		case LDK:
			System.out.println("[LDK] Set the value of accumulator to " + addressIndex + ".");
			setAccumulator(addressIndex);
			break;
		case MUL:
			System.out.println("[MUL] I calculate " + getAccumulator() + " * " + addresses.get(addressIndex) + ".");
			setAccumulator(getAccumulator() * addresses.get(addressIndex));
			break;
		case OUT:
			System.out.println("[OUT] Output the value of address " + addressIndex + ".");
			System.out.println("The value of address " + addressIndex + " is " + addresses.get(addressIndex) + ".");
			break;
		case STA:
			System.out.println("[STA] Store accumulators value into address " + addressIndex + ".");
			addresses.set(addressIndex, getAccumulator());
			break;
		case SUB:
			System.out.println("[SUB] I calculate " + getAccumulator() + " - " + addresses.get(addressIndex) + ".");
			setAccumulator(getAccumulator() - addresses.get(addressIndex));
			break;
		case INC:
			System.out.println("[INC] I increment the value of the address " + addressIndex + ".");
			addresses.set(addressIndex, addresses.get(addressIndex) + 1);
			break;
		case DEC:
			System.out.println("[DEC] I decrement the value of the address " + addressIndex + ".");
			addresses.set(addressIndex, addresses.get(addressIndex) - 1);
			break;
		default:
			break; 
		}
	}
	//wandelt den Anweisungscode in eine Instruction um
	private Instruction decodeInstruction(String input) {
		switch(input) {
		case "01": 
			return Instruction.ADD;
		case "02":
			return Instruction.SUB;
		case "03":
			return Instruction.MUL;
		case "04":
			return Instruction.DIV;
		case "05":
			return Instruction.LDA;
		case "06":
			return Instruction.LDK;
		case "07":
			return Instruction.STA;
		case "08":
			return Instruction.INP;
		case "09": 
			return Instruction.OUT;
		case "0A":
			return Instruction.HLT;
		case "0B":
			return Instruction.JMP;
		case "0C":
			return Instruction.JEZ;
		case "0D":
			return Instruction.JNE;
		case "0E":
			return Instruction.JLZ;
		case "0F":
			return Instruction.JLE;
		case "10":
			return Instruction.JGZ;
		case "11":
			return Instruction.JGE;
		case "12":
			return Instruction.INC;
		case "13":
			return Instruction.DEC;
		default:
			return null;
		}
	}

	private void setAccumulator(int value) {
		addresses.set(0, value);
	}

	private int getAccumulator() {
		return addresses.get(0);
	}

	private boolean isCorrectInput(String input) {
		if(input.length() % 4 == 0 && input.contains("0A99")) {
			return true;
		}
		return false;
	}

	private void printProgrammStorage() {
		System.out.println("---------------------------------\nProgramm storage:");
		for(String s : programmStorage) {
			System.out.println(s);
		}
		System.out.println("---------------------------------");
	}
	//char 2-4 enthält die Information für die Arbeitsanweisung
	private String getInstruction(String programmLine) {
		return programmLine.substring(2, 4);
	}
	//char 4-6 enthält die Zieladresse
	private int getAdress(String programmLine) {
		return Integer.parseInt(programmLine.substring(4, 6));
	}

}
