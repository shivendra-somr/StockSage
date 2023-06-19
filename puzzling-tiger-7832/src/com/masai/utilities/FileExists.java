package com.masai.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.masai.entities.Broker;
import com.masai.entities.Stock;
import com.masai.entities.Trader;
import com.masai.entities.Transaction;

public class FileExists {
	public static Map<Integer, Stock> stockFile(){
		Map<Integer, Stock> sFile = null;

		File f = new File("Stock.ser");
		boolean flag = false;
		try {
			if (!f.exists()) {
				f.createNewFile();
				flag = true;
			}

			if (flag) {

				sFile = new LinkedHashMap<>();
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
				oos.writeObject(sFile);
				oos.flush();
				oos.close();
				return sFile;

			} else {

				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				sFile = (Map<Integer, Stock>) ois.readObject();
				ois.close();
				
				return sFile;

			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return sFile;
	}
	public static Map<String, Broker> brokerFile(){
		Map<String, Broker> bFile = null;

		File f = new File("broker.ser");
		boolean flag = false;
		try {
			if (!f.exists()) {
				f.createNewFile();
				flag = true;
			}

			if (flag) {

				bFile = new LinkedHashMap<>();
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
				oos.writeObject(bFile);
				oos.flush();
				oos.close();
				return bFile;

			} else {

				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				bFile = (Map<String, Broker>) ois.readObject();
				ois.close();
				return bFile;

			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return bFile;
	}
	public static Map<String, Trader> traderFile(){
		Map<String, Trader> tFile = null;

		File f = new File("trader.ser");
		boolean flag = false;
		try {
			if (!f.exists()) {
				f.createNewFile();
				flag = true;
			}

			if (flag) {

				tFile = new LinkedHashMap<>();
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
				oos.writeObject(tFile);
				oos.flush();
				oos.close();
				return tFile;

			} else {

				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				tFile = (Map<String, Trader>) ois.readObject();
				ois.close();
				return tFile;

			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return tFile;
	}
	public static List<Transaction> transactionFile() {

		List<Transaction> transFile = new ArrayList<>();

		File f = new File("Transactions.ser");
		boolean flag = false;
		try {
			if (!f.exists()) {
				f.createNewFile();
				flag = true;
			}

			if (flag) {
				transFile =  new ArrayList<>();
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
				oos.writeObject(transFile);
				oos.flush();
				oos.close();
				return transFile;

			} else {

				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				transFile = (List<Transaction>) ois.readObject();
				ois.close();
				return transFile;

			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

		return transFile;

	}

	public static void saveBrokersToFile(Map<String, Broker> brokers) {
        try (FileOutputStream fileOut = new FileOutputStream("broker.ser");
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(brokers);
            System.out.println("Brokers saved to file: broker.ser");
        } catch (IOException e) {
            System.out.println("Error saving brokers to file: " + e.getMessage());
        }
    }
	
	public static void saveTradersToFile(Map<String, Trader> traders) {
        try (FileOutputStream fileOut = new FileOutputStream("trader.ser");
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(traders);
            System.out.println("Traders saved to file: trader.ser");
        } catch (IOException e) {
            System.out.println("Error saving traders to file: " + e.getMessage());
        }
    }

    public static void saveTransactionsToFile(List<Transaction> transactions) {
        try (FileOutputStream fileOut = new FileOutputStream("Transactions.ser");
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(transactions);
            System.out.println("Transactions saved to file: Transactions.ser");
        } catch (IOException e) {
            System.out.println("Error saving transactions to file: " + e.getMessage());
        }
    }
}
