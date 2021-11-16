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
            System.out.println("Выберите опцию\n"
                    + "0) Выход\n"
                    + "1) Добавить товар\n"
                    + "2) Вывести список товаров\n"
                    + "3) Добавить покупателя\n"
                    + "4) Вывести список покупателей\n"
                    + "5) Совершить покупку\n"
                    + "6) Вывести список покупок");
            
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
                    productsArray.add(addProduct());
                    keeping.saveProducts(productsArray);
                    break;
                    
                case 2:
                    //Вывести список товаров
                    if (!productsArray.isEmpty()) {
                        System.out.println("---------- Список товаров ----------");
                        for (int i = 0; i < productsArray.size(); i++) {
                            System.out.println(productsArray.get(i).toString());
                        }
                        System.out.println("---------- Список товаров ----------");
                        
                    } else {
                        System.out.println("\nНет добавленных товаров\n");
                    }
                    break;
                    
                case 3:
                    //Добавить покупателя
                    customersArray.add(addCustomer());
                    keeping.saveCustomers(customersArray);
                    break;
                    
                case 4:
                    //Вывести список покупателей
                    if (!customersArray.isEmpty()) {
                        System.out.println("---------- Список покупателей ----------");
                        for (int i = 0; i < customersArray.size(); i++) {
                            System.out.println(customersArray.get(i).toString());
                        }
                        System.out.println("---------- Список покупателей ----------");
                    } else {
                        System.out.println("\nНет добавленных покупателей\n");
                    }
                    break;
                    
                case 5:
                    //Совершить покупку
                    if (!productsArray.isEmpty() || !customersArray.isEmpty()) {
                        historysArray.add(addHistory());
                        keeping.saveHistorys(historysArray);
                    } else {  
                        System.out.println("\nОперация невозможна\n");
                    }
                    break;
                    
                case 6:
                    //Вывести список покупок
                    if (!historysArray.isEmpty()) {
                        System.out.println("---------- Список историй покупок ----------");
                        for (int i = 0; i < historysArray.size(); i++) {
                            System.out.println(historysArray.get(i).toString());
                        }
                        System.out.println("---------- Список историй покупок ----------");
                    } else {
                        System.out.println("\nНет добавленных историй покупок\n");
                    }
                    break;
            }
        }
    }
    
    private Product addProduct(){
        Product product = new Product();
        
        System.out.print("Название: ");
        product.setTitle(scanner.next());
        System.out.print("Категория: ");
        product.setCategory(scanner.next());
        System.out.print("Цена: ");
        product.setPrice(scanner.nextDouble());
        
        return product;
    }
    
    private Customer addCustomer(){
        Customer customer = new Customer();
        
        System.out.print("Имя: ");
        customer.setFirstname(scanner.next());
        System.out.print("Фамилия: ");
        customer.setSurename(scanner.next());
        System.out.print("Телефон: ");
        customer.setphoneNumber(scanner.next());
        System.out.print("Счет: ");
        customer.setWallet(scanner.nextDouble());
        
        return customer;
    }
    
    private History addHistory(){
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
            
            customersArray.get(customerChoise).setWallet( customersArray.get(customerChoise).getWallet() -  productsArray.get(productChoise).getPrice());
        } else{
            System.out.println("Недостаточно денег");
        }
        
        return history;
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
