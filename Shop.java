import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Product Class
class Product {
    private int id;
    private String name;
    private double price;
    private String description;

    public Product(int id, String name, double price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}

// Cart Item Class
class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

// Cart Class
class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void addProduct(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    public void removeProduct(Product product) {
        items.removeIf(item -> item.getProduct().getId() == product.getId());
    }

    public double calculateTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public List<CartItem> getItems() {
        return items;
    }
}

// Shop Class
public class Shop {
    private Cart cart = new Cart();
    private List<Product> products = new ArrayList<>();

    public Shop() {
        products.add(new Product(1, "Laptop", 999.99, "High-performance laptop"));
        products.add(new Product(2, "Smartphone", 499.99, "Latest model smartphone"));
        // Additional products can be added here
    }

    public void displayProducts() {
        System.out.println("Available Products:");
        for (Product product : products) {
            System.out.println(product.getId() + " - " + product.getName() + " - $" + product.getPrice());
        }
    }

    public void showCart() {
        if (cart.getItems().isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Items in your cart:");
            for (CartItem item : cart.getItems()) {
                System.out.println(item.getProduct().getName() + " - Quantity: " + item.getQuantity() + " - Subtotal: $" + item.getTotalPrice());
            }
        }
    }

    public void checkout() {
        if (cart.getItems().isEmpty()) {
            System.out.println("Your cart is empty. Add some products before checking out.");
        } else {
            System.out.println("Total amount: $" + cart.calculateTotal());
            System.out.println("Checkout complete.");
            cart = new Cart(); // Clear cart after checkout
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Display Products");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. Remove Product from Cart");
            System.out.println("4. Show Cart");
            System.out.println("5. Checkout");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayProducts();
                    break;
                case 2:
                    System.out.print("Enter product ID: ");
                    int productIdToAdd = scanner.nextInt();
                    System.out.print("Enter quantity: ");
                    int quantityToAdd = scanner.nextInt();
                    Product productToAdd = products.stream().filter(p -> p.getId() == productIdToAdd).findFirst().orElse(null);
                    if (productToAdd != null) {
                        cart.addProduct(productToAdd, quantityToAdd);
                        System.out.println("Product added to cart.");
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter product ID to remove: ");
                    int productIdToRemove = scanner.nextInt();
                    Product productToRemove = products.stream().filter(p -> p.getId() == productIdToRemove).findFirst().orElse(null);
                    if (productToRemove != null) {
                        cart.removeProduct(productToRemove);
                        System.out.println("Product removed from cart.");
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                case 4:
                    showCart();
                    break;
                case 5:
                    checkout();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        new Shop().run();
    }
}
