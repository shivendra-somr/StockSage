package com.masai;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.masai.entities.Broker;
import com.masai.entities.Stock;
import com.masai.entities.Trader;
import com.masai.entities.Transaction;
import com.masai.entities.TransactionType;
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
					System.out.print("Filter By - date/price : ");
					String filter = sc.next();
					transactions = FileExists.transactionFile();
					filterTransactionHistory(tService, transactions, filter);
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

	private static void approveRejectBrokerAcc(Scanner sc, Map<String, Broker> brokers, brokerService bService)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub

		try {
			System.out.println("---------Approve/Reject-Broker-Account----------");

			List<Broker> registeredBrokers = new ArrayList<>(brokers.values());
			if (registeredBrokers.size() == 0) {
				System.out.println("No registered account found!");
				return;
			}
			System.out.println("Registered Brokers:");
			for (int i = 0; i < registeredBrokers.size(); i++) {
				Broker broker = registeredBrokers.get(i);
				System.out.println((i + 1) + ". Username: " + broker.getUsername());
				System.out.println("   Approval Status: " + (broker.isApproved() ? "Approved" : "Pending"));
				System.out.println();
			}

			System.out.print("Select a broker account to approve/reject: ");
			int choice = sc.nextInt();
			sc.nextLine();

			if (choice < 1 || choice > registeredBrokers.size()) {
				System.out.println("Invalid choice. Please try again.");
				return;
			}

			Broker selectedBroker = registeredBrokers.get(choice - 1);

			if (selectedBroker.isApproved()) {
				System.out.println("The broker account is already approved.");
				return;
			}

			System.out.print("Do you want to approve or reject the broker account? (approve/reject): ");
			String decision = sc.nextLine();

			if (!decision.equalsIgnoreCase("approve") && !decision.equalsIgnoreCase("reject")) {
				System.out.println("Invalid decision. Please enter 'approve' or 'reject'.");
				return;
			}

			if (decision.equalsIgnoreCase("approve")) {
				selectedBroker.setApproved(true);
				brokers.put(selectedBroker.getUsername(), selectedBroker);
				FileExists.saveBrokersToFile(brokers);
				System.out.println("The broker account has been approved successfully.");
			} 
			else if (decision.equalsIgnoreCase("reject")) {
				selectedBroker.setApproved(false);
				brokers.put(selectedBroker.getUsername(), selectedBroker);
				FileExists.saveBrokersToFile(brokers);
				System.out.println("The broker account has been rejected successfully.");
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void filterTransactionHistory(transService tService, List<Transaction> transactions,
			String filterType) {
		// TODO Auto-generated method stub
		switch (filterType) {
		case "date":
			// Filter transactions by date
			Collections.sort(transactions, Comparator.comparing(Transaction::getTimestamp));
			break;
		case "price":
			// Filter transactions by price
			Collections.sort(transactions, Comparator.comparing(Transaction::getPrice));
			break;
		default:
			System.out.println("Invalid filter type. Please try again.");
			return;
		}
//		transactions = FileExists.transactionFile();
		if (transactions.isEmpty()) {
			System.out.println("Transaction history is empty.");
		} else {
//    		transactions = FileExists.transactionFile();
			if (transactions.isEmpty()) {
				System.out.println("Transaction history is empty.");
				return;
			}
			System.out.println("Transaction History:");
			System.out.println();
			int count = 1;
			for (Transaction t : transactions) {
				System.out.println("-------------Transaction No." + count++ + "-----------------");

				System.out.println("\tTraderName\t---->\t" + t.getTraderUsername());
				System.out.println("\tStockSymbol\t---->\t" + t.getStockSymbol());
				System.out.println("\tQuantity\t---->\t" + t.getQuantity());
				System.out.println("\tPrice\t\t---->\tRs." + t.getPrice());
				double total = t.getQuantity() * t.getPrice();
				System.out.println("\tTotalPrice\t---->\tRs." + Math.round(total));
				System.out.println("----------------------------------------------");
			}
		}
	}

	private static void viewTransactionHistory(transService tService, List<Transaction> transactions) {
		tService.viewTransactionHistory(transactions);
	}

	private static void viewActiveAccounts(Scanner sc, stockService sService, brokerService bService,
			traderService tradService) {
		// TODO Auto-generated method stub
		System.out.println("-----------------Active-Accounts----------------");

		Map<String, Broker> brokers = FileExists.brokerFile();
		Map<String, Trader> traders = FileExists.traderFile();

		if (brokers == null) {
			System.out.println("No active broker accounts found!");
		}
		if (traders ==  null) {
			System.out.println("No active trader accounts found!");
			return;
		}
		List<Broker> activeBrokers = bService.getActiveBrokers(brokers);
		System.out.println("Active Broker Accounts:");
		for (Broker broker : activeBrokers) {
			System.out.println("Username: " + broker.getUsername());
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

		System.out.println("------------------Broker-Login------------------");
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
					addTraders(sc, broker, trader);
					break;
				case 2:
					manageTraderAccounts(sc, broker, trader);
					break;
				case 3:
					executeTrades(sc, stocks, broker, trader, transactions);
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
		System.out.println("-----------Apply-for-Brokerage-Account----------");
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
		System.out.println("Brokerage account created successfully. Please reload the console.");
	}

	private static void viewTransactionHistoryForTraders() {
		// TODO Auto-generated method stub

	}

	private static void executeTrades(Scanner sc, Map<Integer, Stock> stocks, Map<String, Broker> brokers,
			Map<String, Trader> traders, List<Transaction> transactions) {
		System.out.println("----------------Execute-Trades------------------");
		System.out.println("Traders:");
		for (String username : traders.keySet()) {
			System.out.println(username);
		}

		System.out.print("Select a trader to execute trades on behalf of: ");
		String selectedTrader = sc.next();

		if (!traders.containsKey(selectedTrader)) {
			System.out.println("Invalid trader. Please try again.");
			return;
		}

		Trader trader = traders.get(selectedTrader);

		stocks = Stock.getStocks();
		System.out.println("Available Stocks:");
	    for (Map.Entry<Integer, Stock> entry : stocks.entrySet()) {
	        Integer stockId = entry.getKey();
	        Stock stock = entry.getValue();
	        System.out.println(stockId + ". " + stock.getStockName()+"\t\tRs." +stock.getStockPrice());
	    }
	    if(stocks.isEmpty()) {
	    	System.out.println("No Stocks Available");
	    	return;
	    }

	    System.out.print("Enter the number of trades to execute: ");
	    int numTrades = sc.nextInt();

	    System.out.println("Broker " + trader.getName() + " will now select the stocks to trade.");
	    for (int i = 0; i < numTrades; i++) {
	        System.out.print("Enter the stock ID to trade: ");
	        int stockId = sc.nextInt();

	        if (!stocks.containsKey(stockId)) {
	            System.out.println("Invalid stock ID. Please try again.");
	            return;
	        }

	        Stock stock = stocks.get(stockId);

	        System.out.print("Enter the quantity of stocks to trade: ");
	        int quantity = sc.nextInt();

	        double price = stock.getStockPrice();

	        LocalDateTime timestamp = LocalDateTime.now();
	        TransactionType type = TransactionType.SELL; // Assuming the broker is selling the stocks
	        Transaction transaction = new Transaction(timestamp, trader.getUsername(), stock.getStockSymbol(), type,
	                quantity, price);

	        transactions.add(transaction);
	        FileExists.saveTransactionsToFile(transactions);
	        System.out.println("Trade executed successfully: " + quantity + " stocks of " + stock.getStockName() +
	                " traded at a price of Rs." + price);
	    }
	}

	private static void manageTraderAccounts(Scanner sc, Map<String, Broker> brokers, Map<String, Trader> traders) {
		System.out.println("------------Manage-Trader-Accounts--------------");

		System.out.println("Traders:");
		for (String username : traders.keySet()) {
			System.out.println(username);
		}

		System.out.print("Select a trader to manage the account: ");
		String selectedTrader = sc.next();

		if (!traders.containsKey(selectedTrader)) {
			System.out.println("Invalid trader. Please try again.");
			return;
		}

		System.out.println("Trader account managed successfully.");
	}

	private static void addTraders(Scanner sc, Map<String, Broker> brokers, Map<String, Trader> traders) {
		System.out.println("------------------Add-Traders-------------------");

		System.out.println("Enter trader name:");
		String name = sc.next();

		System.out.println("Enter trader address:");
		String address = sc.next();

		System.out.println("Enter trader contact number:");
		String contactNumber = sc.next();

		System.out.println("Enter trader username:");
		String username = sc.next();

		System.out.println("Enter trader password:");
		String password = sc.next();

		Trader newTrader = new Trader(name, address, contactNumber, username, password);

		traders.put(username, newTrader);

		System.out.println("Trader added successfully.");
	}

	// Trader Side

	private static void traderFunctionality(Scanner sc, Map<Integer, Stock> stocks, Map<String, Trader> trader,
			List<Transaction> transactions) throws InvalidDetailsException {
		// TODO Auto-generated method stub
		traderService tradService = new traderServiceImpl();
		transService tService = new transServiceImpl();

		trader = FileExists.traderFile();

		System.out.println("------------------Trader-Login------------------");
		System.out.println("Please enter the following details to login");
		System.out.print("Enter username: ");
		String username = sc.nextLine();
		System.out.print("Enter password: ");
		String password = sc.nextLine();

		traderLogin(username, password, trader, tradService);

		System.out.println("-------------Trader-Menu-------------");
		System.out.println("1 --> View Market Trends and Stock Prices");
		System.out.println("2 --> Buy Stocks");
		System.out.println("3 --> Sell Stocks");
		System.out.println("4 --> View Transaction History");
		System.out.println("5 --> View Portfolio History");
		System.out.println("6 --> Delete Account");
		System.out.println("7 --> Add Funds");
		System.out.println("8 --> Exit to Main Menu");
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
					buyStocks(sc, stocks, trader, transactions, username);
					break;
				case 3:
					sellStocks(sc, stocks, trader, transactions, username);
					break;
				case 4:
					viewTransactionHistory(username, transactions, trader);
					break;
				case 5:
					viewPortfolioHistory(trader);
					break;
				case 6:
					deleteAccount(sc, trader, transactions);
					break;
				case 7:
					addFunds(sc, trader, username);
					break;
				case 8:
					System.out.println("Exiting Trader Menu...");
					break;
				default:
					System.out.println("Invalid choice! Please try again.");
				}
			} while (choice <= 8);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		System.out.println(); // Add an empty line for readability
	}

	private static void addFunds(Scanner sc, Map<String, Trader> traders, String username) {
		System.out.print("Enter funds to add: ");
		double amount = sc.nextDouble();

		if (!traders.containsKey(username)) {
			System.out.println("Invalid trader username. Please try again.");
			return;
		}

		Trader trader = traders.get(username);
		double currentFunds = trader.getFunds();
		System.out.println("Current Funds: " + currentFunds);
		double newFunds = currentFunds + amount;
		trader.setFunds(newFunds);

		traders.put(username, trader);

		FileExists.saveTradersToFile(traders);

		System.out.println("Funds added successfully. Updated funds: " + newFunds);
	}

	private static void viewTransactionHistory(String username, List<Transaction> transactions,
			Map<String, Trader> traders) {

		// Retrieve the trader from the traders map
		Trader trader = traders.get(username);
		transactions = FileExists.transactionFile();
		List<Transaction> traderTransactions = transactions.stream()
				.filter(t -> t.getTraderUsername().equals(trader.getUsername())).collect(Collectors.toList());
		if (traderTransactions.isEmpty()) {
			System.out.println("Transaction history is empty.");
			return;
		}
		System.out.println("Transaction History:");
		System.out.println();
		System.out.println("TraderName\tStockSymbol\tQuantity\tPrice\tTotalPrice");
		for (Transaction t : traderTransactions) {
//			System.out.println(t);
			System.out.print(t.getTraderUsername() + "\t");
			System.out.print(t.getStockSymbol() + "\t");
			System.out.print(t.getQuantity() + "\t");
			System.out.print(t.getPrice() + "\t");
			System.out.print(t.getQuantity() * t.getPrice());
		}
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

	private static void deleteAccount(Scanner sc, Map<String, Trader> traders, List<Transaction> transactions) {

		traders = FileExists.traderFile();

		System.out.print("Enter your username: ");
		String username = sc.next();

		// Check if the trader exists or not
		if (!traders.containsKey(username)) {
			System.out.println("Invalid username. Please try again.");
			return;
		}

		Trader trader = traders.get(username);

		// Remove the trader from the traders map
		traders.remove(username);

		// Remove the trader's transactions from the transactions list
		transactions.removeIf(t -> t.getTraderUsername().equals(trader.getUsername()));

		// Save the updated traders and transactions to the respective files
		FileExists.saveTradersToFile(traders);
		FileExists.saveTransactionsToFile(transactions);

		System.out.println("Account deleted successfully.");
	}

	private static void viewPortfolioHistory(Map<String, Trader> traders) {
		// ...
		Scanner sc = new Scanner(System.in);
		// Prompt the trader to enter their username
		System.out.print("Enter your username: ");
		String username = sc.next();

		// Check if the trader exists in the traders map
		if (!traders.containsKey(username)) {
			System.out.println("Invalid username. Please try again.");
			return;
		}

		// Retrieve the trader from the traders map
		Trader trader = traders.get(username);

		// Display the portfolio history for the trader
		System.out.println("Portfolio History:");

		List<Stock> portfolio = new ArrayList<>();

		Map<Integer, Stock> stocks = Stock.getStocks();
		for (Stock stock : stocks.values()) {
			portfolio.add(stock);
		}
		for (Stock stock : portfolio) {
			System.out.println(stock.getStockName() + " - Symbol: " + stock.getStockSymbol() + " - Average Price: "
					+ stock.getStockPrice());
		}
	}

	private static void sellStocks(Scanner sc, Map<Integer, Stock> stocks, Map<String, Trader> traders,
			List<Transaction> transactions, String username) {
		transactions = FileExists.transactionFile();
		if (transactions.isEmpty()) {
			System.out.println("No stocks available to sell!");
			return;
		}
		System.out.println("StockSymbol\tStockQuantity");
		for (Transaction t : transactions) {

			System.out.println(t.getStockSymbol() + "\t\t" + t.getQuantity() + "\t" + t.getTraderUsername());
		}
		System.out.println();

		// Prompt the trader to select a stock
		System.out.print("Enter the stock symbol to sell: ");
		String stockSym = sc.next();

		int id = getStockIdBySymbol(stocks, stockSym);

		if (id == 0) {
			System.out.println("Invalid stock symbol. Please try again.");
			return;
		}

		Stock stock = stocks.get(id);

		if (stock == null) {
			System.out.println("Invalid stock ID. Please try again.");
			return;
		}

		// Check if the selected stock exists in the stocks map

		if (!stocks.containsKey(id)) {
			System.out.println("Invalid stock ID. Please try again.");
			return;
		}

		String traderUsername = username;
		System.out.print("Enter quantity to sell: ");
		int quantity = sc.nextInt();

		LocalDateTime timestamp = LocalDateTime.now();
		TransactionType type = TransactionType.SELL;
		double price = stock.getStockPrice();
		Transaction transaction = new Transaction(timestamp, traderUsername, stock.getStockSymbol(), type, quantity,
				price);

		transactions.add(transaction);

		FileExists.saveTransactionsToFile(transactions);
		FileExists.saveTradersToFile(traders);

		System.out.println("Stocks sold successfully.");
	}

	private static int getStockIdBySymbol(Map<Integer, Stock> stocks, String stockSymbol) {
		for (Map.Entry<Integer, Stock> entry : stocks.entrySet()) {
			Stock stock = entry.getValue();
			System.out.println(stock.getStockSymbol());
			if (stock.getStockSymbol().equalsIgnoreCase(stockSymbol.trim())) {
				return entry.getKey();
			}
		}
		return 0;
	}

	private static void buyStocks(Scanner sc, Map<Integer, Stock> stocks, Map<String, Trader> traders,
			List<Transaction> transactions, String username) {

		stocks = Stock.getStocks();
		System.out.println("Stocks List:");
		System.out.println("stockId\tStockPrice\tStockName");
		for (Map.Entry<Integer, Stock> me : stocks.entrySet()) {
			Integer stockId = me.getKey();
			Stock st = me.getValue();

			System.out.println(stockId + "\t" + "Rs. " + st.getStockPrice() + "\t" + st.getStockName());
		}
		System.out.println();

		// Prompt the trader to select a stock
		System.out.print("Enter the stock ID to buy: ");
		int stockId = sc.nextInt();
		Stock stock = stocks.get(stockId);

		if (stock == null) {
			System.out.println("Invalid stock ID. Please try again.");
			return;
		}

		double price = stock.getStockPrice();
		sc.nextLine();

		System.out.println("Enter quantity of stocks to buy: ");
		int quantity = sc.nextInt();

		traders = FileExists.traderFile();

		if (!traders.containsKey(username)) {
			System.out.println("Invalid trader username. Please try again.");
			return;
		}

		Trader trader = traders.get(username);

		double totalCost = price * quantity;
		if (trader.getFunds() < totalCost) {
			System.out.println("Insufficient funds to buy stocks.");
		}

		System.out.println("Want to add funds: Y/N ");
		String ans = sc.next();
		if (ans.equalsIgnoreCase("Y")) {
			System.out.println("Enter amount to add to your account: ");
			double money = sc.nextDouble();
			trader.setFunds(trader.getFunds() + money);
		} else {
			System.out.println("You need to add funds to your account to buy stocks.");
			return;
		}

		// Create a new transaction for buying stocks
		LocalDateTime timestamp = LocalDateTime.now();
		TransactionType type = TransactionType.BUY;
		Transaction transaction = new Transaction(timestamp, trader.getUsername(), stock.getStockSymbol(), type,
				quantity, price);

		// Update the trader's funds
		trader.setFunds(trader.getFunds() - totalCost);

		// Add the transaction to the list of transactions
		transactions = FileExists.transactionFile();
		transactions.add(transaction);
		FileExists.saveTransactionsToFile(transactions);

		// Save the updated trader information to the file
		traders.put(username, trader);
		FileExists.saveTradersToFile(traders);

		System.out.println("Stocks bought successfully.");
	}

	private static List<Transaction> filterTransactionsByUsername(List<Transaction> transactions, String username) {
		List<Transaction> filteredTransactions = new ArrayList<>();
		for (Transaction transaction : transactions) {
			if (transaction.getTraderUsername().equals(username)) {
				filteredTransactions.add(transaction);
			}
		}
		return filteredTransactions;
	}

	private static void viewMarketTrendsAndStockPrices() throws ClassNotFoundException {
		System.out.println("----Market-Trends-and-Stock-Prices----");

//		stocks = FileExists.stockFile();

		try {

			System.out.println();

			Stock marketTrends = Stock.getStocks().get(1);
			System.out.print("Market Trends:\t ");
			System.out.println(marketTrends.getStockName() + "----" + "Rs. " + marketTrends.getStockPrice());

			System.out.println();
			Map<Integer, Stock> stockPrices = Stock.getStocks();
			System.out.println("Top 5 Stocks and their Prices:");
			System.out.println();
			System.out.println("StockId\tStockPrice\tStockName");
			for (Map.Entry<Integer, Stock> me : stockPrices.entrySet()) {
				Integer stockId = me.getKey();
				Stock st = me.getValue();
				System.out.println(stockId + "\t" + "Rs. " + st.getStockPrice() + "\t" + st.getStockName());
			}
			System.out.println();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("No market trends");
		}
	}

	private static void traderRegistration(Scanner sc, Map<String, Trader> traders) {
		System.out.println("----------------Trader-Registration------------------");

		Map<String, Trader> existingTraders = FileExists.traderFile();
		if (existingTraders != null) {
			traders.putAll(existingTraders);
		}
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

		System.out.println("Trader registration successful. Please reload the console.");
		System.out.println();
	}

	public static void displayMainMenu() {
		System.out.println("--------------------Main-Menu-------------------");
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
