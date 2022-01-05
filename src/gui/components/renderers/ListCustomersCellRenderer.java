
package gui.components.renderers;

import entitys.Customer;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.UIManager;

public class ListCustomersCellRenderer extends DefaultListCellRenderer{
    private final Color background = new Color(0, 100, 255, 15);
    private final Color defaultBackground = (Color) UIManager.get("List.background");
    
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
        Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if(component instanceof JLabel){
            JLabel label = (JLabel) component;
            Customer customer = (Customer) value;
            label.setText(String.format("%d) %s %s %.2f€",
                    customer.getId(),
                    customer.getFirstname(),
                    customer.getSurename(),
                    customer.getWallet()
            ));
            if(!isSelected){
                label.setBackground(index % 2 == 0 ? background : defaultBackground);
            }
        }
        return component;
    }
}