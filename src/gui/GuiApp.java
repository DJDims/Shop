
package gui;

import entitys.Customer;
import entitys.History;
import entitys.Product;
import facade.CustomerFacade;
import facade.HistoryFacade;
import facade.ProductFacade;
import gui.components.ButtonComponent;
import gui.components.EditComponent;
import gui.components.LabelComponent;
import gui.components.ListCustomersComponent;
import gui.components.ListProductsComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class GuiApp extends JFrame{
    public static final int WINDOW_WIDTH = 1100;
    public static final int WINDOW_HEIGHT = 700;
    
    LabelComponent addProductCaption;
    LabelComponent addProductInfo;
    LabelComponent addCustomerCaption;
    LabelComponent addCustomerInfo;
    LabelComponent buyProductCaption;
    LabelComponent buyProductInfo;
    
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
            addProductCaption = new LabelComponent(WINDOW_WIDTH, 30, "Добавление товара", 18, 1);
            addProductPanel.add(addProductCaption);
            addProductInfo = new LabelComponent(WINDOW_WIDTH, 30, "Добавление товара", 14, 0);
            addProductPanel.add(addProductInfo);
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
                    
                    if (productName.getEditor().getText().trim().isEmpty()) {
                        addProductInfo.getCaption().setText("Введите название продукта");
                        addProductInfo.getCaption().setForeground(Color.red);
                        return;
                    }
                    if (productCategory.getEditor().getText().trim().isEmpty()) {
                        addProductInfo.getCaption().setText("Введите категорию продукта");
                        addProductInfo.getCaption().setForeground(Color.red);
                        return;
                    }
                    if (productPrice.getEditor().getText().trim().isEmpty()) {
                        addProductInfo.getCaption().setText("Введите стоимость продукта");
                        addProductInfo.getCaption().setForeground(Color.red);
                        return;
                    }
                    
                    product.setTitle(productName.getEditor().getText().trim());
                    product.setCategory(productCategory.getEditor().getText().trim());
                    
                    try {
                        product.setPrice(Double.parseDouble(productPrice.getEditor().getText().trim()));
                    } catch (Exception e) {
                        addProductInfo.getCaption().setText("Введите стоимость продукта цифрами");
                        addProductInfo.getCaption().setForeground(Color.red);
                        return;
                    }
                    
                    ProductFacade productFacade = new ProductFacade(Product.class);
                    try {
                        productFacade.create(product);
                        addProductInfo.getCaption().setText("Продукт успешно добавлен");
                        addProductInfo.getCaption().setForeground(Color.green);
                        productName.getEditor().setText("");
                        productCategory.getEditor().setText("");
                        productPrice.getEditor().setText("");
                    } catch (Exception e) {
                        addProductInfo.getCaption().setText("Что-то пошло не так");
                        addProductInfo.getCaption().setForeground(Color.red);
                    }
                }
                });
        
        JPanel addCustomerPanel = new JPanel();
        tabs.addTab("Добавить покупателя", addCustomerPanel);
            addCustomerCaption = new LabelComponent(WINDOW_WIDTH, 30, "Добавление покупателя", 18, 1);
            addCustomerPanel.add(addCustomerCaption);
            addCustomerInfo = new LabelComponent(WINDOW_WIDTH, 30, "Добавление покупателя", 14, 0);
            addCustomerPanel.add(addCustomerInfo);
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
                    
                    if (customerFirstname.getEditor().getText().trim().isEmpty()) {
                        addCustomerInfo.getCaption().setText("Введите имя покупателя");
                        addCustomerInfo.getCaption().setForeground(Color.red);
                        return;
                    }
                    if (customerSurename.getEditor().getText().trim().isEmpty()) {
                        addCustomerInfo.getCaption().setText("Введите фамилию покупателя");
                        addCustomerInfo.getCaption().setForeground(Color.red);
                        return;
                    }
                    if (customerPhone.getEditor().getText().trim().isEmpty()) {
                        addCustomerInfo.getCaption().setText("Введите телефон покупателя");
                        addCustomerInfo.getCaption().setForeground(Color.red);
                        return;
                    }
                    if (customerWallet.getEditor().getText().trim().isEmpty()) {
                        addCustomerInfo.getCaption().setText("Введите счет покупателя");
                        addCustomerInfo.getCaption().setForeground(Color.red);
                        return;
                    }
                    
                    customer.setFirstname(customerFirstname.getEditor().getText().trim());
                    customer.setSurename(customerSurename.getEditor().getText().trim());
                    customer.setPhoneNumber(customerPhone.getEditor().getText().trim());
                    
                    try {
                        customer.setWallet(Double.parseDouble(customerWallet.getEditor().getText().trim()));
                    } catch (Exception e) {
                        addCustomerInfo.getCaption().setText("Введите счет покупателя цифрами");
                        addCustomerInfo.getCaption().setForeground(Color.red);
                        return;
                    }
                    
                    CustomerFacade customerFacade = new CustomerFacade(Customer.class);
                    try {
                        customerFacade.create(customer);
                        addCustomerInfo.getCaption().setText("Покупатель успешно добавлен");
                        addCustomerInfo.getCaption().setForeground(Color.green);
                        customerFirstname.getEditor().setText("");
                        customerSurename.getEditor().setText("");
                        customerPhone.getEditor().setText("");
                        customerWallet.getEditor().setText("");
                    } catch (Exception e) {
                        addCustomerInfo.getCaption().setText("Что-то пошло не так");
                        addCustomerInfo.getCaption().setForeground(Color.red);
                    }
                }
                });
        
        JPanel buyProductPanel = new JPanel();
        tabs.addTab("Купить товар", buyProductPanel);
            buyProductCaption = new LabelComponent(WINDOW_WIDTH, 30, "Покупка товара", 18, 1);
            buyProductPanel.add(buyProductCaption);
            buyProductInfo = new LabelComponent(WINDOW_WIDTH, 30, "Покупка товара", 14, 0);
            buyProductPanel.add(buyProductInfo);
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
                    
                    if (customersList.getList().isSelectionEmpty()) {
                        buyProductInfo.getCaption().setText("Выберите покупателя");
                        buyProductInfo.getCaption().setForeground(Color.red);
                        return;
                    }
                    if (productsList.getList().isSelectionEmpty()) {
                        buyProductInfo.getCaption().setText("Выберите товар");
                        buyProductInfo.getCaption().setForeground(Color.red);
                        return;
                    }
                    if (history.getCustomer().getWallet() < history.getProduct().getPrice()) {
                        buyProductInfo.getCaption().setText("У покупателя недостаточно денег");
                        buyProductInfo.getCaption().setForeground(Color.red);
                        return;
                    }
                    
                    history.setCustomer(customersList.getList().getSelectedValue());
                    history.setProduct(productsList.getList().getSelectedValue());
                    history.getCustomer().setWallet(history.getCustomer().getWallet() - history.getProduct().getPrice());
                    history.setPurchase(localdateToDate(LocalDate.now()));
                        
                    HistoryFacade historyFacade = new HistoryFacade(History.class);
                    CustomerFacade customerFacade = new CustomerFacade(Customer.class);
                    
                    try {
                        historyFacade.edit(history);
                        buyProductInfo.getCaption().setText("Покупка успешно добавлена");
                        buyProductInfo.getCaption().setForeground(Color.green);
                        customerFacade.edit(history.getCustomer());
                        customersList.getList().clearSelection();
                        productsList.getList().clearSelection();
                    } catch (Exception e) {
                        buyProductInfo.getCaption().setText("Что-то пошло не так");
                        buyProductInfo.getCaption().setForeground(Color.red);
                    }
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
    
    private Date localdateToDate(LocalDate dateToConvert){
        return Date.from(dateToConvert.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    private LocalDate dateToLocaldate(Date dateToConvert){
	return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
