import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class SliceOHaven {
    private String orderID;
    private String pizzaIngredients;
    private double orderTotal;
    private String sides;
    private String drinks;
    private String extraCheese;
    private String pizzaSize;
    private boolean wantDiscount;
    private String birthdate;
    private long cardNumber;
    private String expiryDate;
    private int cvv;

    public static final String DEF_ORDER_ID = "DEF - SOH - 099";
    public static final String DEF_PIZZA_INGREDIENTS = "Mozzarella Cheese";
    public static final double DEF_ORDER_TOTAL = 15.00;

    public SliceOHaven() {
        this.orderID = DEF_ORDER_ID;
        this.pizzaIngredients = DEF_PIZZA_INGREDIENTS;
        this.orderTotal = DEF_ORDER_TOTAL;
        this.sides = "";
        this.drinks = "";
    }

    public SliceOHaven(String orderID, String pizzaIngredients, double orderTotal) {
        this.orderID = orderID;
        this.pizzaIngredients = pizzaIngredients;
        this.orderTotal = orderTotal;
        this.sides = "";
        this.drinks = "";
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPizzaIngredients() {
        return pizzaIngredients;
    }

    public void setPizzaIngredients(String pizzaIngredients) {
        this.pizzaIngredients = pizzaIngredients;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    private void printReceipt() {
        System.out.println("Order ID: " + orderID);
        System.out.println("Pizza Ingredients: " + pizzaIngredients);
        System.out.println("Order Total: " + orderTotal);
        System.out.println("Sides: " + sides);
        System.out.println("Drinks: " + drinks);
        System.out.println("Extra Cheese: " + extraCheese);
        System.out.println("Pizza Size: " + pizzaSize);
    }

    public void displayReceipt() {
        printReceipt();
    }

    public void processCardPayment(long cardNumber, String expiryDate, int cvv) {
        String cardStr = Long.toString(cardNumber);
        if (cardStr.length() == 14) {
            System.out.println("Card accepted");
        } else {
            System.out.println("Invalid card");
            return;
        }
        long blacklistedNumber = 12345;
        if (cardNumber == blacklistedNumber) {
            System.out.println("Card is blacklisted. Please use another card");
            return;
        }
        String lastFourDigits = cardStr.substring(cardStr.length() - 4);
        String cardNumberToDisplay = cardStr.substring(0, 1) + "****" + lastFourDigits;
        System.out.println("Card Number to Display: " + cardNumberToDisplay);
    }

    public void specialofTheDay(String pizzaOfTheDay, String sideOfTheDay, String specialPrice) {
        StringBuilder details = new StringBuilder();
        details.append("Pizza of the Day: ").append(pizzaOfTheDay).append("\n")
             .append("Side of the Day: ").append(sideOfTheDay).append("\n")
             .append("Special Price: ").append(specialPrice);
        System.out.println(details.toString());
    }

    public void takeOrder() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter three ingredients for your pizza (use spaces to separate ingredients):");
        String[] ingArr = scanner.nextLine().split(" ");
        pizzaIngredients = String.join(", ", ingArr);
        System.out.println("Enter size of pizza (Small, Medium, Large):");
        pizzaSize = scanner.nextLine();
        System.out.println("Do you want extra cheese (Yes/No):");
        extraCheese = scanner.nextLine();
        System.out.println("Enter one side dish (Calzone, Garlic bread, None):");
        sides = scanner.nextLine();
        System.out.println("Enter drinks(Cold Coffee, Cocoa drink, Coke, None):");
        drinks = scanner.nextLine();
        System.out.println("Would you like the chance to pay only half for your order? (Yes/No):");
        String input = scanner.nextLine();
        wantDiscount = input.equalsIgnoreCase("Yes");
        if (wantDiscount) {
            isItYourBirthday(scanner);
        } else {
            makeCardPayment(scanner);
        }
        scanner.close();
    }

    public void isItYourBirthday(Scanner scanner) {
        System.out.println("Enter your birthday (format: yyyy - MM - dd):");
        birthdate = scanner.nextLine();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy - MM - dd");
        Date birthd = null;
        try {
            birthd = dateFormat.parse(birthdate);
        } catch (ParseException e) {
            System.out.println("Invalid date format");
            return;
        }
        Date now = new Date();
        long diff = now.getTime() - birthd.getTime();
        long years = diff / (1000 * 60 * 60 * 24 * 365);
        if (years < 18 && now.getDate() == birthd.getDate() && now.getMonth() == birthd.getMonth()) {
            System.out.println("Congratulations! You pay only half the price for your order");
        } else {
            System.out.println("Too bad! You do not meet the conditions to get our 50% discount");
        }
    }

    public void makeCardPayment(Scanner scanner) {
        System.out.println("Enter your card number:");
        cardNumber = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Enter the card's expiry date:");
        expiryDate = scanner.nextLine();
        System.out.println("Enter the card's cvv number:");
        cvv = scanner.nextInt();
        processCardPayment(cardNumber, expiryDate, cvv);
    }
    public static void main(String[] args) {
        SliceOHaven order = new SliceOHaven();
        order.takeOrder();
        order.displayReceipt();
        order.specialofTheDay("Durian Pizza", "beef", "$9.99");
    }
}