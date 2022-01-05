
package interfaces;

import entitys.Customer;
import entitys.History;
import entitys.Product;
import java.util.List;

public interface Keeping {
    public void saveCustomers(List<Customer> customersArray);
    public List<Customer> loadCustomers();
    
    public void saveHistorys(List<History> historysArray);
    public List<History> loadHistorys();
    
    public void saveProducts(List<Product> productsArray);
    public List<Product> loadProducts();
}
