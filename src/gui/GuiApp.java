
package gui;

import Classes.Customer;
import Classes.History;
import Classes.Product;
import Facade.CustomerFacade;
import Facade.HistoryFacade;
import Facade.ProductFacade;
import gui.components.ButtonComponent;
import gui.components.EditComponent;
import gui.components.LabelComponent;
import gui.components.ListCustomersComponent;
import gui.components.ListProductsComponent;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class GuiApp extends JFrame{
    public static final int WINDOW_WIDTH = 1100;
    public static final int WINDOW_HEIGHT = 700;
    
    LabelComponent addProductCaption;
    LabelComponent addCustomerCaption;
    LabelComponent buyProductCaption;
    
    ButtonComponent addProductButton;
    ButtonComponent addCustomerButton;
    ButtonComponent buyProductButton;
    
    EditComponent productName;
    EditComponent productCategory;
    EditComponent productPrice;
    
    EditComponent customerFirstname;
    EditComponent customerSurename;
    EditComponent customerPhone;
    EditComponent customerWallet;
    
    ListCustomersComponent customersList;
    ListProductsComponent productsList;

    public GuiApp() {
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void initComponents(){
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        this.setTitle("Library");
        
        JTabbedPane tabs = new JTabbedPane();
        tabs.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        tabs.setMinimumSize(tabs.getPreferredSize());
        tabs.setMaximumSize(tabs.getPreferredSize());
        this.add(tabs);
        
        JPanel addProductPanel = new JPanel();
        tabs.addTab("Добавить товар", addProductPanel);
            addProductCaption = new LabelComponent(WINDOW_WIDTH, 30, "Добавление товара");
            addProductPanel.add(addProductCaption);
            productName = new EditComponent(200, "Название", WINDOW_WIDTH, 30);
            addProductPanel.add(productName);
            productCategory = new EditComponent(200, "Категория", WINDOW_WIDTH, 30);
            addProductPanel.add(productCategory);
            productPrice = new EditComponent(200, "Цена", WINDOW_WIDTH, 30);
            addProductPanel.add(productPrice);
            addProductButton = new ButtonComponent("Добавить товар", WINDOW_WIDTH, 30, 150);
            addProductPanel.add(addProductButton);
            addProductButton.getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    Product product = new Product();
                    
                    if (productName.getEditor().getText().isEmpty()) {
                        return;
                    }
                    product.setTitle(productName.getEditor().getText());
                    
                    if (productCategory.getEditor().getText().isEmpty()) {
                        return;
                    }
                    product.setCategory(productCategory.getEditor().getText());
                    
                    try {
                        product.setPrice(Double.parseDouble(productCategory.getEditor().getText()));
                    } catch (Exception e) {
                    }
                    
                    ProductFacade productFacade = new ProductFacade(Product.class);
                    try {
                        productFacade.create(product);
                        productName.getEditor().setText("");
                        productCategory.getEditor().setText("");
                        productPrice.getEditor().setText("");
                    } catch (Exception e) {
                    }
                }
                });
        
        JPanel addCustomerPanel = new JPanel();
        tabs.addTab("Добавить покупателя", addCustomerPanel);
            addCustomerCaption = new LabelComponent(WINDOW_WIDTH, 30, "Добавление покупателя");
            addCustomerPanel.add(addCustomerCaption);
            customerFirstname = new EditComponent(200, "Имя", WINDOW_WIDTH, 30);
            addCustomerPanel.add(customerFirstname);
            customerSurename = new EditComponent(200, "Фамилия", WINDOW_WIDTH, 30);
            addCustomerPanel.add(customerSurename);
            customerPhone = new EditComponent(200, "Телефон", WINDOW_WIDTH, 30);
            addCustomerPanel.add(customerPhone);
            customerWallet = new EditComponent(200, "Кошелек", WINDOW_WIDTH, 30);
            addCustomerPanel.add(customerWallet);
            addCustomerButton = new ButtonComponent("Добавить покупателя", WINDOW_WIDTH, 30, 150);
            addCustomerPanel.add(addCustomerButton);
            addCustomerButton.getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    Customer customer = new Customer();
                    
                    if (customerFirstname.getEditor().getText().isEmpty()) {
                        return;
                    }
                    customer.setFirstname(customerFirstname.getEditor().getText());
                    
                    if (customerSurename.getEditor().getText().isEmpty()) {
                        return;
                    }
                    customer.setSurename(customerSurename.getEditor().getText());
                    
                    if (customerPhone.getEditor().getText().isEmpty()) {
                        return;
                    }
                    customer.setPhoneNumber(customerPhone.getEditor().getText());
                    try {
                        customer.setWallet(Double.parseDouble(customerWallet.getEditor().getText()));
                    } catch (Exception e) {
                    }
                    
                    CustomerFacade customerFacade = new CustomerFacade(Customer.class);
                    try {
                        customerFacade.create(customer);
                        customerFirstname.getEditor().setText("");
                        customerSurename.getEditor().setText("");
                        customerPhone.getEditor().setText("");
                        customerWallet.getEditor().setText("");
                    } catch (Exception e) {
                    }
                }
                });
        
        JPanel buyProductPanel = new JPanel();
        tabs.addTab("Купить товар", buyProductPanel);
            buyProductCaption = new LabelComponent(WINDOW_WIDTH, 30, "Покупка товара");
            buyProductPanel.add(buyProductCaption);
            customersList = new ListCustomersComponent(350, "Покупатели", WINDOW_WIDTH, 150);
            buyProductPanel.add(customersList);
            productsList = new ListProductsComponent(350, "Товары", WINDOW_WIDTH, 150);
            buyProductPanel.add(productsList);
            buyProductButton = new ButtonComponent("Купить товар", WINDOW_WIDTH, 30, 150);
            buyProductPanel.add(buyProductButton);
            buyProductButton.getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    History history = new History();
                    
                    history.setCustomer(customersList.getList().getSelectedValue());
                    history.setProduct(productsList.getList().getSelectedValue());
                    
                    HistoryFacade historyFacade = new HistoryFacade(History.class);
                    CustomerFacade customerFacade = new CustomerFacade(Customer.class);
                    
                    historyFacade.edit(history);
                    history.getCustomer().setWallet(history.getCustomer().getWallet() - history.getProduct().getPrice());
                    customerFacade.edit(history.getCustomer());
                    customersList.getList().clearSelection();
                    productsList.getList().clearSelection();
                }
                });
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new GuiApp().setVisible(true);
            }
        });
    }
}
