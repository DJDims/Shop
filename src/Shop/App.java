package Shop;

import Classes.Customer;
import Classes.History;
import Classes.Product;
import Interfaces.Keeping;
import Tools.SaverToBase;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class App {
    boolean appRunning = true;
    
    Scanner scanner = new Scanner(System.in);
    Keeping keeping = new SaverToBase();

    List<Product> productsArray = new ArrayList();
    List<Customer> customersArray = new ArrayList();
    List<History> historysArray = new ArrayList();

    public App() {
        productsArray = keeping.loadProducts();
        customersArray = keeping.loadCustomers();
        historysArray = keeping.loadHistorys();
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
        product.setTitle(scanner.next());
        System.out.print("Категория: ");
        product.setCategory(scanner.next());
        System.out.print("Цена: ");
        product.setPrice(scanner.nextDouble());
        
        productsArray.add(product);
        keeping.saveProducts(productsArray);
    }
    
    private void showProducts(){
        if (!productsArray.isEmpty()) {
            System.out.println("---------- Список товаров ----------");
            for (int i = 0; i < productsArray.size(); i++) {
                System.out.println(productsArray.get(i).toString());
            }
            System.out.println("---------- Список товаров ----------");

        } else {
            System.out.println("\nНет добавленных товаров\n");
        }
    }
    
    private void addCustomer(){
        Customer customer = new Customer();
        
        System.out.print("Имя: ");
        customer.setFirstname(scanner.next());
        System.out.print("Фамилия: ");
        customer.setSurename(scanner.next());
        System.out.print("Телефон: ");
        customer.setphoneNumber(scanner.next());
        System.out.print("Счет: ");
        customer.setWallet(scanner.nextDouble());
        
        customersArray.add(customer);
        keeping.saveCustomers(customersArray);
    }
    
    private void showCustomers(){
        if (!customersArray.isEmpty()) {
            System.out.println("---------- Список покупателей ----------");
            for (int i = 0; i < customersArray.size(); i++) {
                System.out.println(customersArray.get(i).toString());
            }
            System.out.println("---------- Список покупателей ----------");
        } else {
            System.out.println("\nНет добавленных покупателей\n");
        }
    }
    
    private void addHistory(){
        if (!productsArray.isEmpty() || !customersArray.isEmpty()) {
            History history = new History();

            //----- Выбор товара -----
            System.out.println("Выберите товар");
            for (int i = 0; i < productsArray.size(); i++) {
                System.out.println(i + productsArray.get(i).getTitle() + productsArray.get(i).getPrice()+"€");
            }
            int productChoise = inputInt();
            //----- Выбор товара -----

            //----- Выбор покупателя -----
            System.out.println("Выберите покупателя");
            for (int i = 0; i < customersArray.size(); i++) {
                System.out.println(customersArray.get(i).getFirstname() + customersArray.get(i).getWallet()+"€");
            }
            int customerChoise = inputInt();
            //----- Выбор покупателя -----

            if (customersArray.get(customerChoise).getWallet() >= productsArray.get(productChoise).getPrice()) {
                history.setCustomer(customersArray.get(customerChoise));
                history.setProduct(productsArray.get(productChoise));
                history.setPurchase(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

                customersArray.get(customerChoise).setWallet(customersArray.get(customerChoise).getWallet() -  productsArray.get(productChoise).getPrice());

                historysArray.add(history);
                keeping.saveHistorys(historysArray);
                keeping.saveCustomers(customersArray);
            } else{
                System.out.println("Недостаточно денег");
            }
        } else {  
            System.out.println("\nОперация невозможна\n");
        }
    }
    
    private void showHistorys(){
        if (!historysArray.isEmpty()) {
            System.out.println("---------- Список историй покупок ----------");
            for (int i = 0; i < historysArray.size(); i++) {
                System.out.println(historysArray.get(i).toString());
            }
            System.out.println("---------- Список историй покупок ----------");
        } else {
            System.out.println("\nНет добавленных историй покупок\n");
        }
    }
    
    private int inputInt() {
	do {
            try {
                String inputedNumber = scanner.next();
                return Integer.parseInt(inputedNumber);
            } catch(Exception e) {
                System.out.println("Введены неверные данные.");
                System.out.print("Попробуйте еще раз -->");
            }
	} while(true);
    }
}
