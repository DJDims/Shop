import java.util.Date;

public class Product {
    private String title;
    private String category;
    private double price;

    public Product(String title, String category, double price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    @Override
    public String toString(){
        return "Название " + title + "\nКатегория " + category + "\nЦена " + price;
    }
}
