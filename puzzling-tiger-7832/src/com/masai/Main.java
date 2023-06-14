package com.masai;



import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.masai.entities.Broker;
import com.masai.entities.Stock;
import com.masai.entities.Trader;
import com.masai.entities.Transaction;
import com.masai.utilities.FileExists;

public class Main {
	// Administration Side
	private static void administratorFunctionality(Scanner sc, Map<Integer, Stock> stocks, Map<String, Broker> broker, Map<String, Trader> trader, List<Transaction> transactions) {
		
	}

	// Broker Side
	public static void broker() {

	}

	// Trader Side
	public static void trader() {

	}

	public static void main(String[] args) {
		// Welcome Message
		{
			System.out.println("----------------Welcome-to-StockSage----------------");
			System.out.println();
			System.out.println("***** ***** ***** ***** *   * *****  ***  ***** ****");
			System.out.println("*       *   *   * *     *  *  *     *   * *     *   ");
			System.out.println("*       *   *   * *     * *   *     *   * *     *   ");
			System.out.println("*****   *   *   * *     **    ***** ***** *     ****");
			System.out.println("    *   *   *   * *     * *       * *   * *  ** *   ");
			System.out.println("    *   *   *   * *     *  *      * *   * *   * *   ");
			System.out.println("*****   *   ***** ***** *   * ***** *   * ***** ****");
			System.out.println();
			System.out.println("----------------------------------------------------");
		}

		Map<Integer, Stock> stocks = FileExists.stockFile();
		Map<String, Broker> broker = FileExists.brokerFile();
		Map<String, Trader> trader = FileExists.traderFile();
		List<Transaction> transactions = FileExists.transactionFile();
		
		Scanner sc = new Scanner(System.in);

		try {
			int preference = 0;
			do {
				System.out.println(
						"Please enter you preference\n1 --> Admin Login \n2 --> Broker Login \n3 --> Broker Signup \n4 --> Trader Login \n5 --> Trader Signup \n0 --> Exit");
				preference = sc.nextInt();
				switch (preference) {
					case 1: //call administrator functionality
						administratorFunctionality(sc, stocks, broker, trader, transactions);
						break;
					case 2: //call broker functionality
						break;
					case 3: //call trader functionality
						break;
					case 4: break;
					case 5: break;
					default: //throw new exception here
						break;
				}
			} while (preference != 0);

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}

	}
}
