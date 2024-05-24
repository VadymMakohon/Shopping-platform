import java.util.*

data class Product(val id: String, val name: String, val price: Double) {
    var quantity: Int = 0
}

data class Customer(val id: String, val name: String, val email: String) {
    val orders = mutableListOf<Order>()
    val shoppingCart = ShoppingCart()

    fun placeOrder() {
        val order = Order(UUID.randomUUID().toString(), shoppingCart.items.toList())
        orders.add(order)
        shoppingCart.clear()
    }
}

data class Order(val id: String, val items: List<Product>)

class ShoppingCart {
    val items = mutableMapOf<Product, Int>()

    fun addItem(product: Product, quantity: Int) {
        items[product] = items.getOrDefault(product, 0) + quantity
    }

    fun removeItem(product: Product, quantity: Int) {
        val currentQuantity = items.getOrDefault(product, 0)
        if (quantity >= currentQuantity) {
            items.remove(product)
        } else {
            items[product] = currentQuantity - quantity
        }
    }

    fun clear() {
        items.clear()
    }

    fun totalCost(): Double {
        return items.entries.sumByDouble { it.key.price * it.value }
    }
}

class OnlineShop {
    private val products = mutableListOf<Product>()
    private val customers = mutableListOf<Customer>()

    fun addProduct(product: Product) {
        products.add(product)
    }

    fun searchProduct(name: String): List<Product> {
        return products.filter { it.name.contains(name, ignoreCase = true) }
    }

    fun registerCustomer(customer: Customer) {
        customers.add(customer)
    }

    fun findCustomerById(id: String): Customer? {
        return customers.find { it.id == id }
    }
}

fun main() {
    val onlineShop = OnlineShop()

    // Add some products to the online shop
    val product1 = Product("P001", "Smartphone", 599.99)
    product1.quantity = 100
    val product2 = Product("P002", "Laptop", 899.99)
    product2.quantity = 50
    val product3 = Product("P003", "Tablet", 299.99)
    product3.quantity = 80
    onlineShop.addProduct(product1)
    onlineShop.addProduct(product2)
    onlineShop.addProduct(product3)

    // Search for products
    println("Search Results for 'phone':")
    val searchResults = onlineShop.searchProduct("phone")
    searchResults.forEach { println("${it.name} - $${it.price}") }

    // Register customers
    val customer1 = Customer(UUID.randomUUID().toString(), "Alice", "alice@example.com")
    val customer2 = Customer(UUID.randomUUID().toString(), "Bob", "bob@example.com")
    onlineShop.registerCustomer(customer1)
    onlineShop.registerCustomer(customer2)

    // Customer shopping
    customer1.shoppingCart.addItem(product1, 2)
    customer1.shoppingCart.addItem(product2, 1)
    customer2.shoppingCart.addItem(product3, 3)

    // Place orders
    customer1.placeOrder()
    customer2.placeOrder()

    // Display customer orders
    println("Customer Orders:")
    customer1.orders.forEachIndexed { index, order ->
        println("Order ${index + 1}: ${order.items.joinToString(", ") { it.name }}")
    }
    customer2.orders.forEachIndexed { index, order ->
        println("Order ${index + 1}: ${order.items.joinToString(", ") { it.name }}")
    }
}
