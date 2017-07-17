package Model;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;

public class Cart {

    private ArrayList<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addToCart(CartItem item) {
        items.add(item);
    }

    public void removeFromCart(int item) {
        items.remove(item);
    }

    public static int getCurrentCartItemCount(HttpSession session) {
        Cart c = (Cart) session.getAttribute("CURRENT_CART");
        int count = 0;
        if (c != null) {
            count = c.getItems().size();
        }

        return count;
    }

    /**
     * @return the items
     */
    public ArrayList<CartItem> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(ArrayList<CartItem> items) {
        this.items = items;
    }

}
