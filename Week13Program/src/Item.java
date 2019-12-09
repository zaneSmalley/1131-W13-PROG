import com.sun.deploy.ui.DialogTemplate;

/**
 * Date last modified: 12/9/2019
 *
 * @author Zane Smalley
 * CS1131 Fall 2019
 * Lab Section 2
 */
public class Item {
    private String itemName;
    private String itemDescription;
    private String tooltip;

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

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

}
