package com.masai;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.masai.entities.Broker;
import com.masai.entities.Stock;
import com.masai.entities.Trader;
import com.masai.entities.Transaction;
import com.masai.exceptions.InvalidDetailsException;
import com.masai.services.brokerService;
import com.masai.services.brokerServiceImpl;
import com.masai.services.stockService;
import com.masai.services.stockServiceImpl;
import com.masai.services.traderService;
import com.masai.services.traderServiceImpl;
import com.masai.services.transService;
import com.masai.services.transServiceImpl;
import com.masai.utilities.AdminCredentials;
import com.masai.utilities.FileExists;

public class Main {
	// Administration Side
	private static void administratorFunctionality(Scanner sc, Map<Integer, Stock> stocks, Map<String, Broker> broker,
			Map<String, Trader> trader, List<Transaction> transactions) throws InvalidDetailsException {
		adminLogin(sc);

		stockService sService = new stockServiceImpl();
		brokerService bService = new brokerServiceImpl();
		traderService tradService = new traderServiceImpl();
		transService tService = new transServiceImpl();

		int choice = 0;
		try {
			do {
				System.out.println("---------------Administrator-Menu---------------");
				System.out.println("1 --> Approve/Reject Broker Accounts");
				System.out.println("2 --> View Active Broker and Trader Accounts");
				System.out.println("3 --> View Transaction History");
				System.out.println("4 --> Filter Transaction History");
				System.out.println("5 --> Exit to Main Menu");
				System.out.print("Enter your choice: ");
				choice = sc.nextInt();
				switch (choice) {
				case 1:
					approveRejectBrokerAcc(sc, broker, bService);
					break;
				case 2:
					viewActiveAccounts(sc, sService, bService, tradService);
					break;
				case 3:
					transactions = FileExists.transactionFile();
					viewTransactionHistory(tService, transactions);
					break;
				case 4:
					filterTransactionHistory();
					break;
				case 5:
					System.out.println("Admin log out successfully");
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + choice);
				}
			} while (choice <= 4);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	public static void adminLogin(Scanner sc) throws InvalidDetailsException {

		System.out.println("Enter the user name");
		String userName = sc.next();
		System.out.println("Enter the password");
		String password = sc.next();
		if (userName.equals(AdminCredentials.username) && password.equals(AdminCredentials.password)) {
			System.out.println("Successfully login");
		} else {
			throw new InvalidDetailsException("Invalid Admin Credentials");
		}
	}

	private static void approveRejectBrokerAcc(Scanner sc, Map<String, Broker> brokers, brokerService bService) {
		// TODO Auto-generated method stub

		try {
			System.out.println("------Approve/Reject Broker Account------");

			System.out.println("Registered Brokers:");
			List<Broker> registeredBrokers = new ArrayList<>(brokers.values());
			for (int i = 0; i < registeredBrokers.size(); i++) {
				Broker broker = registeredBrokers.get(i);
				System.out.println((i + 1) + ". Username: " + broker.getUsername());
				System.out.println("   Approval Status: " + (broker.isApproved() ? "Approved" : "Pending"));
				System.out.println("   Rejection Status: " + (broker.isRejected() ? "Rejected" : "Pending"));
				System.out.println();
			}

			// Prompt user to select a broker account
			System.out.print("Select a broker account to approve/reject: ");
			int choice = sc.nextInt();
			sc.nextLine();

			// Validate the user's choice
			if (choice < 1 || choice > registeredBrokers.size()) {
				System.out.println("Invalid choice. Please try again.");
				return;
			}

			// Retrieve the selected broker
			Broker selectedBroker = registeredBrokers.get(choice - 1);

			// Check if the broker is already approved or rejected
			if (selectedBroker.isApproved()) {
				System.out.println("The broker account is already approved.");
				return;
			} else if (selectedBroker.isRejected()) {
				System.out.println("The broker account is already rejected.");
				return;
			}

			// Prompt user to approve or reject the broker account
			System.out.print("Do you want to approve or reject the broker account? (approve/reject): ");
			String decision = sc.nextLine();

			// Validate the user's decision
			if (!decision.equalsIgnoreCase("approve") && !decision.equalsIgnoreCase("reject")) {
				System.out.println("Invalid decision. Please enter 'approve' or 'reject'.");
				return;
			}

			// Update the approval/rejection status of the broker
			if (decision.equalsIgnoreCase("approve")) {
				selectedBroker.setApproved(true);
				selectedBroker.setRejected(false);
				System.out.println("The broker account has been approved successfully.");
			} else if (decision.equalsIgnoreCase("reject")) {
				selectedBroker.setRejected(true);
				selectedBroker.setApproved(false);
				System.out.println("The broker account has been rejected successfully.");
			}
			brokers.put(selectedBroker.getUsername(), selectedBroker);

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void filterTransactionHistory() {
		// TODO Auto-generated method stub

	}

	private static void viewTransactionHistory(transService tService, List<Transaction> transactions) {
		// TODO Auto-generated method stub
		tService.viewTransactionHistory(transactions);
	}

	private static void viewActiveAccounts(Scanner sc, stockService sService, brokerService bService,
			traderService tradService) {
		// TODO Auto-generated method stub
		System.out.println("-------Active-Accounts-------");

		Map<String, Broker> brokers = FileExists.brokerFile();
		Map<String, Trader> traders = FileExists.traderFile();

		List<Broker> activeBrokers = bService.getActiveBrokers(brokers);
		System.out.println("Active Broker Accounts:");
		for (Broker broker : activeBrokers) {
			System.out.println("Username: " + broker.getUsername());
			// Display other broker details as needed
		}

		List<Trader> activeTraders = tradService.getActiveTraders(traders);
		System.out.println("Active Trader Accounts:");
		for (Trader trader : activeTraders) {
			System.out.println("Username: " + trader.getUsername());
		}
	}

	// Broker Side
	private static void brokerFunctionality(Scanner sc, Map<Integer, Stock> stocks, Map<String, Broker> broker,
			Map<String, Trader> trader, List<Transaction> transactions) throws InvalidDetailsException {
		// TODO Auto-generated method stub
		brokerService bService = new brokerServiceImpl();
		traderService tradService = new traderServiceImpl();
		transService tService = new transServiceImpl();

		broker = FileExists.brokerFile();

		System.out.println("----------------Broker Login------------------");
		System.out.println("Please enter the following details to login");
		System.out.println("Enter username");
		String username = sc.next();
		System.out.println("Enter password");
		String password = sc.next();

		brokerLogin(username, password, broker, bService);

		int choice = 0;
		try {
			do {
				System.out.println("-------------Broker-Menu-------------");
				System.out.println("1 --> Add Traders");
				System.out.println("2 --> Manage Trader Accounts");
				System.out.println("3 --> Execute Trades on Behalf of Traders");
				System.out.println("4 --> View Transaction History for Associated Traders");
				System.out.println("5 --> Exit to Main Menu");
				System.out.print("Enter your choice: ");

				choice = sc.nextInt();
				switch (choice) {
				case 1:
					addTraders();
					break;
				case 2:
					manageTraderAccounts();
					break;
				case 3:
					executeTrades();
					break;
				case 4:
					viewTransactionHistoryForTraders();
					break;
				case 5:
					System.out.println("Exiting Broker Menu...");
					break;
				default:
					System.out.println("Invalid choice! Please try again.");
				}
				System.out.println();
			} while (choice <= 4);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	private static void brokerLogin(String username, String password, Map<String, Broker> broker,
			brokerService bService) throws InvalidDetailsException {
		// TODO Auto-generated method stub

		if (bService.login(username, password, broker)) {
			return;
		} else {
			throw new InvalidDetailsException("Invalid details, please provide correct credentials");
		}

	}

	private static void applyForBrokerageAccount(Scanner sc, Map<String, Broker> broker) {
		// TODO Auto-generated method stub
		System.out.println("-------Apply-for-Brokerage-Account-------");
		System.out.print("Enter your username: ");
		String username = sc.next();
		System.out.print("Enter your password: ");
		String password = sc.next();

		Map<String, Broker> existingBrokers = FileExists.brokerFile();
		if (existingBrokers != null) {
			broker.putAll(existingBrokers);
		}
		System.out.println(broker);
		if (broker.containsKey(username)) {
			System.out.println("Username already exists. Registration failed.");
			return;
		}
		Broker newBroker = new Broker(username, password);

		broker.put(username, newBroker);
		System.out.println("Brokerage account created successfully.");
	}

	private static void viewTransactionHistoryForTraders() {
		// TODO Auto-generated method stub

	}

	private static void executeTrades() {
		// TODO Auto-generated method stub

	}

	private static void manageTraderAccounts() {
		// TODO Auto-generated method stub

	}

	private static void addTraders() {
		// TODO Auto-generated method stub

	}

	// Trader Side

	private static void traderFunctionality(Scanner sc, Map<Integer, Stock> stocks, Map<String, Trader> trader,
			List<Transaction> transactions) throws InvalidDetailsException {
		// TODO Auto-generated method stub
		traderService tradService = new traderServiceImpl();
		transService tService = new transServiceImpl();

		trader = FileExists.traderFile();

		System.out.println("----------------Trader-Login------------------");
		System.out.println("Please enter the following details to login");
		System.out.println("Enter username");
		String username = sc.next();
		System.out.println("Enter password");
		String password = sc.next();

		traderLogin(username, password, trader, tradService);

		System.out.println("-------------Trader-Menu-------------");
		System.out.println("1 --> View Market Trends and Stock Prices");
		System.out.println("2 --> Buy Stocks");
		System.out.println("3 --> Sell Stocks");
		System.out.println("4 --> View Transaction History");
		System.out.println("5 --> View Portfolio History");
		System.out.println("6 --> Delete Account");
		System.out.println("7 --> Exit to Main Menu");
		System.out.print("Enter your choice: ");

		int choice = 0;
		try {
			do {
				choice = sc.nextInt();
				switch (choice) {
				case 1:
					viewMarketTrendsAndStockPrices();
					break;
				case 2:
					buyStocks();
					break;
				case 3:
					sellStocks();
					break;
				case 4:
					viewTransactionHistoryForTraders();
					break;
				case 5:
					viewPortfolioHistory();
					break;
				case 6:
					deleteAccount();
					break;
				case 7:
					System.out.println("Exiting Trader Menu...");
					break;
				default:
					System.out.println("Invalid choice! Please try again.");
				}
			} while (choice <= 7);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		System.out.println(); // Add an empty line for readability
	}

	private static void traderLogin(String username, String password, Map<String, Trader> trader,
			traderService tradService) throws InvalidDetailsException {
		// TODO Auto-generated method stub
		if (tradService.login(username, password, trader)) {
			return;
		} else {
			throw new InvalidDetailsException("Invalid details, please provide correct credentials");
		}
	}

	private static void deleteAccount() {
		// TODO Auto-generated method stub

	}

	private static void viewPortfolioHistory() {
		// TODO Auto-generated method stub

	}

	private static void sellStocks() {
		// TODO Auto-generated method stub

	}

	private static void buyStocks() {
		// TODO Auto-generated method stub

	}

	private static void viewMarketTrendsAndStockPrices() {
		System.out.println("----Market-Trends-and-Stock-Prices----");

		Stock marketTrends = Stock.getStocks().get(1);
		System.out.print("Market Trends: ");
		System.out.println(marketTrends.getStockName() + "----" + "Rs. " + marketTrends.getStockPrice());

		Map<Integer, Stock> stockPrices = Stock.getStocks();
		System.out.println("Stock Prices:");
		for (Map.Entry<Integer, Stock> me : stockPrices.entrySet()) {
			Integer stockId = me.getKey();
			Stock st = me.getValue();
			System.out.println(stockId + "--|--" + st.getStockName() + "--|--" + "Rs. " + st.getStockPrice());
		}
	}

	private static void traderRegistration(Scanner sc, Map<String, Trader> traders) {
		System.out.println("----------------Trader-Registration------------------");

		Map<String, Trader> existingTraders = FileExists.traderFile();
		if (existingTraders != null) {
			traders.putAll(existingTraders);
		}
		System.out.println(traders);
		System.out.println("Enter name");
		String name = sc.next();
		if (name.isEmpty()) {
			throw new IllegalArgumentException("Please enter a valid name.");
		}
		sc.nextLine();
		System.out.println("Enter address: ");
		String address = sc.next();
		if (address.isEmpty()) {
			throw new IllegalArgumentException("Please enter a valid address.");
		}
		sc.nextLine();

		System.out.println("Enter contact number: ");
		String contactNumber = sc.next();
		if (contactNumber.isEmpty()) {
			throw new IllegalArgumentException("Please enter a valid contact number.");
		}
		sc.nextLine();
		System.out.println("Enter username: ");
		String username = sc.next();

		if (username.isEmpty()) {
			throw new IllegalArgumentException("Please enter a valid username.");
		}
		sc.nextLine();
		if (traders.containsKey(username)) {
			throw new IllegalArgumentException("Username already exists. Registration failed.");
		}

		System.out.println("Enter password: ");
		String password = sc.next();
		if (password.isEmpty()) {
			throw new IllegalArgumentException("Please enter a valid password.");
		}
		sc.nextLine();

		Trader newTrader = new Trader(name, address, contactNumber, username, password);
		traders.put(username, newTrader);

		System.out.println("Trader registration successful.");
		System.out.println();
	}

	public static void displayMainMenu() {
		System.out.println("----------------------Main-Menu---------------------");
		System.out.println("1 --> Admin Login");
		System.out.println("2 --> Broker Login");
		System.out.println("3 --> Apply For Brokerage Account");
		System.out.println("4 --> Trader Login");
		System.out.println("5 --> Trader Register");
		System.out.println("0 --> Exit");
		System.out.print("Please enter your preference: ");
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
				displayMainMenu();
				preference = sc.nextInt();
				switch (preference) {
				case 1: // call administrator functionality
					administratorFunctionality(sc, stocks, broker, trader, transactions);
					break;
				case 2: // call broker functionality
					brokerFunctionality(sc, stocks, broker, trader, transactions);
					break;
				case 3:
					applyForBrokerageAccount(sc, broker);
					break;
				case 4: // call trader functionality
					traderFunctionality(sc, stocks, trader, transactions);
					break;
				case 5:
					traderRegistration(sc, trader);
					break;
				case 0:
					System.out.println("Successfully exited from the system");
					break;
				default: // throw new exception here
					throw new IllegalArgumentException("Invalid Selection");
				}
			} while (preference != 0);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				ObjectOutputStream poos = new ObjectOutputStream(new FileOutputStream("Stock.ser"));
				poos.writeObject(stocks);
				ObjectOutputStream coos = new ObjectOutputStream(new FileOutputStream("broker.ser"));
				coos.writeObject(broker);
				ObjectOutputStream tradoos = new ObjectOutputStream(new FileOutputStream("trader.ser"));
				tradoos.writeObject(trader);
				ObjectOutputStream toos = new ObjectOutputStream(new FileOutputStream("Transactions.ser"));
				toos.writeObject(transactions);
				poos.close();
				coos.close();
				tradoos.close();
				toos.close();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
		}

	}

}
