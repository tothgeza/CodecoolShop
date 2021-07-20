package com.codecool.shop.service;

import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.model.*;

import java.util.List;

public class ProductService {
    private ProductDao productDao;
    private ProductCategoryDao productCategoryDao;
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao = UserDaoMem.getInstance();
    private OrderDao orderDao = OrderDaoMem.getInstance();

    public ProductService(){
    }

    public ProductService(ProductDao productDao, ProductCategoryDao productCategoryDao, ShoppingCartDao shoppingCartDao) {
        this.productDao = productDao;
        this.productCategoryDao = productCategoryDao;
        this.shoppingCartDao = shoppingCartDao;
    }


    public ProductCategory getProductCategory(int categoryId) {
        return productCategoryDao.find(categoryId);
    }

    public List<Product> getProductsForCategory(int categoryId) {
        var category = productCategoryDao.find(categoryId);
        return productDao.getBy(category);
    }
    public ShoppingCart getShoppingCartByUserId(String userID){
        return shoppingCartDao.find(userID);
    }

    public List<ProductCategory> getAllCategory() {
        return productCategoryDao.getAll();
    }

    public List<Product> getSupplierDao(String supplier) {
        return productDao.getBy(supplier);
    }

    public Product getProductById(int productId){
        return productDao.find(productId);
    }

    public void addProductToCart(String userId, int productId){
        Product product = getProductById(productId);
        ShoppingCart shoppingCart;
        if (shoppingCartDao.getAll().containsKey(userId)){
            shoppingCart = shoppingCartDao.find(userId);
            shoppingCart.addProduct(product);
        } else {
            shoppingCart = new ShoppingCart(product);
        }
        shoppingCartDao.addShoppingCart(userId, shoppingCart);
    }

    public void addUser(User user) {
        userDao.addUser(user);
    }

    public User getUserById(String userId) {
        return userDao.getUser(userId);
    }

    public void addOrder(Order order){
        orderDao.addOrder(order);
    }
    public List<Order> getOrderByUserId(String userId){
        return orderDao.getOrderByUserId(userId);
    }

    public void removeProductFromCart(String userId, int productId){
        Product product = getProductById(productId);
        ShoppingCart shoppingCart;
        shoppingCart = shoppingCartDao.find(userId);
        shoppingCart.removeLineItem(productId);
        shoppingCartDao.addShoppingCart(userId, shoppingCart);
    }
    public void decreaseProduct(String userId, int productId) {
        ShoppingCart shoppingCart;
        shoppingCart = shoppingCartDao.find(userId);
        //TODO create decrease method to ShoppingCart to modify subTotal
        shoppingCart.decreaseLineItem(productId);
        shoppingCartDao.addShoppingCart(userId, shoppingCart);
    }
    public void increaseProduct(String userId, int productId){
        ShoppingCart shoppingCart;
        shoppingCart = shoppingCartDao.find(userId);
        //TODO create increase method to ShoppingCart to modify subTotal
        shoppingCart.increaseLineItem(productId);
        shoppingCartDao.addShoppingCart(userId, shoppingCart);
    }
}