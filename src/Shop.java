import java.time.LocalDate;
import java.util.Scanner;

public class Shop {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        Product[] products = new Product[10];
        int countOfProducts = 0;
        Customer[] customers = new Customer[10];
        int countOfCustomers = 0;
        History[] historys = new History[10];
        int countOfHistorys = 0;

        while (true) {
            System.out.println("Выберите опцию\n0) Выход\n1) Добавить товар\n2) Вывести список товаров\n3) Добавить покупателя\n4) Вывести список покупателей\n5) Совершить покупку\n6) Вывести список покупок");
            int choise = scanner.nextInt();

            if (choise == 0) {
                break;
            }else if (choise == 1){
                //Добавить товар
                if (countOfProducts != products.length+1) {
                    System.out.print("Название: ");
                    String title = scanner.nextLine();
                    System.out.print("Категория: ");
                    String category = scanner.nextLine();
                    System.out.print("Цена: ");
                    Double price = scanner.nextDouble();
                    
                    products[countOfProducts] = new Product(title, category, price);
                    countOfProducts++;

                } else {
                    System.out.println("\nМаксимальное количество товаров\n");
                }
                
            }else if (choise == 2){
                //Вывести список товаров
                if (products[0] == null) {
                    System.out.println("\nНет добавленных товаров\n");
                } else {
                    System.out.println("---------- Список товаров ----------");
                    for (int i = 0; i < products.length; i++) {
                        System.out.println(products[i].toString());
                    }
                    System.out.println("---------- Список товаров ----------");
                }

            }else if (choise == 3){
                //Добавить покупателя
                if (countOfCustomers != customers.length+1) {
                    System.out.print("Имя: ");
                    String firstname = scanner.nextLine();
                    System.out.print("Фамилия: ");
                    String surename = scanner.nextLine();
                    System.out.print("Телефон: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Счет: ");
                    Double wallet = scanner.nextDouble();
                    
                    customers[countOfCustomers] = new Customer(firstname, surename, phoneNumber, wallet);
                    countOfCustomers++;

                } else {
                    System.out.println("\nМаксимальное количество покупателей\n");
                }

            }else if (choise == 4){
                //Вывести список покупателей
                if (customers[0] == null) {
                    System.out.println("\nНет добавленных покупателей\n");
                } else {
                    System.out.println("---------- Список покупателей ----------");
                    for (int i = 0; i < customers.length; i++) {
                        System.out.println(customers[i].toString());
                    }
                    System.out.println("---------- Список покупателей ----------");
                }
            }else if (choise == 5){
                //Совершить покупку
                if (products[0] == null || customers[0] == null) {
                    System.out.println("\nОперация невозможна\n");
                }else{
                    if (countOfHistorys != historys.length+1) {
                        //----- Выбор товара -----
                        System.out.println("Выберите товар");
                        for (int i = 0; i < products.length; i++) {
                            System.out.println(i + products[i].getTitle() + products[i].getPrice());
                        }
                        int productChoise = scanner.nextInt();
                        //----- Выбор товара -----

                        //----- Выбор покупателя -----
                        System.out.println("Выберите покупателя");
                        for (int i = 0; i < customers.length; i++) {
                            System.out.println(customers[i].getFirstname() + customers[i].getWallet()+"€");
                        }
                        int customerChoise = scanner.nextInt();
                        //----- Выбор покупателя -----
                        if (customers[customerChoise].getWallet() >= products[productChoise].getPrice()) {
                            historys[countOfHistorys] = new History(products[productChoise], customers[customerChoise]);
                            customers[customerChoise].setWallet(customers[customerChoise].getWallet() - products[productChoise].getPrice());
                            countOfHistorys++;
                        } else{
                            System.out.println("Недостаточно денег");
                        }
                    } else {
                        System.out.println("\nМаксимальное количество покупок\n");
                    }
                }

            }else if (choise == 6){
                //Вывести список покупок
                if (historys[0] == null) {
                    System.out.println("\nНет добавленных историй покупок\n");
                } else {
                    System.out.println("---------- Список историй покупок ----------");
                    for (int i = 0; i < historys.length; i++) {
                        System.out.println(historys[i].toString());
                    }
                    System.out.println("---------- Список историй покупок ----------");
                }
            }
        }
    }
}
