package com.masai;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

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
					approveRejectBrokerAcc(sc, sService, bService, tradService, tService);
					break;
				case 2:
					viewActiveAccounts();
					break;
				case 3:
					viewTransactionHistory();
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

	private static void approveRejectBrokerAcc(Scanner sc, stockService sService, brokerService bService,
			traderService tradService, transService tService) {
		// TODO Auto-generated method stub

	}

	private static void filterTransactionHistory() {
		// TODO Auto-generated method stub

	}

	private static void viewTransactionHistory() {
		// TODO Auto-generated method stub

	}

	private static void viewActiveAccounts() {
		// TODO Auto-generated method stub

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

	// Broker Side
	private static void brokerFunctionality(Scanner sc, Map<Integer, Stock> stocks, Map<String, Broker> broker,
			Map<String, Trader> trader, List<Transaction> transactions) {
		// TODO Auto-generated method stub
		brokerService bService = new brokerServiceImpl();
		traderService tradService = new traderServiceImpl();
		transService tService = new transServiceImpl();

		brokerLogin(sc);
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

	private static void brokerLogin(Scanner sc) {
		// TODO Auto-generated method stub

	}

	private static void applyForBrokerageAccount(Scanner sc, Map<String, Broker> broker) {
		// TODO Auto-generated method stub
		System.out.println("-------Apply-for-Brokerage-Account-------");
		System.out.print("Enter your username: ");
		String username = sc.next();
		System.out.print("Enter your password: ");
		String password = sc.next();

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

	private static void traderFunctionality(Scanner sc, Map<Integer, Stock> stocks, List<Transaction> transactions) {
		// TODO Auto-generated method stub
		traderService tradService = new traderServiceImpl();
		transService tService = new transServiceImpl();
		
		traderLogin(sc);

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
					viewTransactionHistory();
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

	private static void traderLogin(Scanner sc) {
		// TODO Auto-generated method stub
		
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
					traderFunctionality(sc, stocks, transactions);
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
		}

	}

	private static void traderRegistration(Scanner sc, Map<String, Trader> traders) {
		System.out.println("----------------Trader-Registration------------------");

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
	}

}
