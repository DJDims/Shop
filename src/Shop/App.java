package Shop;

import Classes.Customer;
import Classes.History;
import Classes.Product;
import Facade.CustomerFacade;
import Facade.HistoryFacade;
import Facade.ProductFacade;
import Interfaces.Keeping;
import Tools.SaverToBase;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class App {
    boolean appRunning = true;
    
    Scanner scanner = new Scanner(System.in);

    private ProductFacade productFacade;
    private CustomerFacade customerFacade;
    private HistoryFacade historyFacade;

    public App() {
        init();
    }
    
    private void init(){
        customerFacade = new CustomerFacade(Customer.class);
        historyFacade = new HistoryFacade(History.class);
        productFacade = new ProductFacade(Product.class);
    }
        
    public void run(){
        while (appRunning) {
            showHints();
            
            System.out.print("Опция -->");
            int choise = inputInt();

            switch (choise){
                case 0:
                    //Выход
                    System.out.println("Досвидания. Приходите еще!");
                    appRunning = false;
                    break;
                    
                case 1:
                    //Добавить товар
                    addProduct();
                    break;
                    
                case 2:
                    //Вывести список товаров
                    showProducts();
                    break;
                    
                case 3:
                    //Добавить покупателя
                    addCustomer();
                    break;
                    
                case 4:
                    //Вывести список покупателей
                    showCustomers();
                    break;
                    
                case 5:
                    //Совершить покупку
                    addHistory();
                    break;
                    
                case 6:
                    //Вывести список покупок
                    showHistorys();
                    break;
            }
        }
    }
    
    private void showHints(){
        System.out.println("Выберите опцию");
        System.out.println("0) Выход");
        System.out.println("1) Добавить товар");
        System.out.println("2) Вывести список товаров");
        System.out.println("3) Добавить покупателя");
        System.out.println("4) Вывести список покупателей");
        System.out.println("5) Совершить покупку");
        System.out.println("6) Вывести список покупок");
    }
    
    private void addProduct(){
        Product product = new Product();
        
        System.out.print("Название: ");
        product.setTitle(scanner.nextLine());
        System.out.print("Категория: ");
        product.setCategory(scanner.nextLine());
        System.out.print("Цена: ");
        product.setPrice(inputDouble());
        
        productFacade.create(product);
    }
    
    private void showProducts(){
        List<Product> productsArray = productFacade.findAll();
        
        if (!productsArray.isEmpty()) {
            System.out.println("---------- Список товаров ----------");
            for (int i = 0; i < productsArray.size(); i++) {
                System.out.println(i+1 + ") " + productsArray.get(i).toString());
            }
            System.out.println("---------- Список товаров ----------");

        } else {
            System.out.println("\nНет добавленных товаров\n");
        }
    }
    
    private void addCustomer(){
        Customer customer = new Customer();
        
        System.out.print("Имя: ");
        customer.setFirstname(scanner.nextLine());
        System.out.print("Фамилия: ");
        customer.setSurename(scanner.nextLine());
        System.out.print("Телефон: ");
        customer.setphoneNumber(scanner.nextLine());
        System.out.print("Счет: ");
        customer.setWallet(inputDouble());
        
        customerFacade.create(customer);
    }
    
    private void showCustomers(){
        List<Customer> customersArray = customerFacade.findAll();
        
        if (!customersArray.isEmpty()) {
            System.out.println("---------- Список покупателей ----------");
            for (int i = 0; i < customersArray.size(); i++) {
                System.out.println(i+1 + ") " + customersArray.get(i).toString());
            }
            System.out.println("---------- Список покупателей ----------");
        } else {
            System.out.println("\nНет добавленных покупателей\n");
        }
    }
    
    private void addHistory(){
        List<Customer> customersArray = customerFacade.findAll();
        List<Product> productsArray = productFacade.findAll();
        
        if (!productsArray.isEmpty() || !customersArray.isEmpty()) {
            History history = new History();

            //----- Выбор товара -----
            System.out.println("Выберите товар");
            for (int i = 0; i < productsArray.size(); i++) {
                System.out.println(i+1 + ") " + productsArray.get(i).getTitle() + productsArray.get(i).getPrice()+"€");
            }
            int productChoise = inputInt();
            Product product = productFacade.findById((long)productChoise);
            //----- Выбор товара -----

            //----- Выбор покупателя -----
            System.out.println("Выберите покупателя");
            for (int i = 0; i < customersArray.size(); i++) {
                System.out.println(i+1 + ") " + customersArray.get(i).getFirstname() + customersArray.get(i).getWallet()+"€");
            }
            int customerChoise = inputInt();
            Customer customer = customerFacade.findById((long)customerChoise);
            //----- Выбор покупателя -----

            if (customer.getWallet() >= product.getPrice()) {
                history.setCustomer(customer);
                history.setProduct(product);
                history.setPurchase(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

                customer.setWallet(customer.getWallet() -  product.getPrice());

                customerFacade.edit(customer);
                historyFacade.edit(history);
            } else{
                System.out.println("Недостаточно денег");
            }
        } else {  
            System.out.println("\nОперация невозможна\n");
        }
    }
    
    private void showHistorys(){
        List<History> historysArray = historyFacade.findAll();
        
        if (!historysArray.isEmpty()) {
            System.out.println("---------- Список историй покупок ----------");
            for (int i = 0; i < historysArray.size(); i++) {
                System.out.println(i+1 + ") " + historysArray.get(i).toString());
            }
            System.out.println("---------- Список историй покупок ----------");
        } else {
            System.out.println("\nНет добавленных историй покупок\n");
        }
    }
    
    private int inputInt() {
	do {
            try {
                String inputedNumber = scanner.nextLine();
                return Integer.parseInt(inputedNumber);
            } catch(Exception e) {
                System.out.println("Введены неверные данные.");
                System.out.print("Попробуйте еще раз -->");
            }
	} while(true);
    }
    
    private double inputDouble() {
	do {
            try {
                String inputedNumber = scanner.nextLine();
                return Double.parseDouble(inputedNumber);
            } catch(Exception e) {
                System.out.println("Введены неверные данные.");
                System.out.print("Попробуйте еще раз -->");
            }
	} while(true);
    }
}
