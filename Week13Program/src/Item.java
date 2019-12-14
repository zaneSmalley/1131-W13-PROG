import com.sun.deploy.ui.DialogTemplate;

import java.io.Serializable;

/**
 * Date last modified: 12/14/2019
 *
 * @author Zane Smalley, Cooper Tyson, Jack Grantham, Tom Clark
 * CS1131 Fall 2019
 * Lab Section 2
 */
public class Item implements Serializable {
    private String itemName;
    private String itemDescription;

    public Item(String itemName) {
        setItemName(itemName);
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    @Override
    public String toString() {
        return itemName.toUpperCase();
    }
}
