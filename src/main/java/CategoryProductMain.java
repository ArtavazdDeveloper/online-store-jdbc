import manager.CategoryManager;
import manager.ProductManager;


import eshop.model.Category;
import eshop.model.Product;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class CategoryProductMain {
    private static Scanner scanner = new Scanner(System.in);
    private static CategoryManager categoryManager = new CategoryManager();
    private static ProductManager productManager = new ProductManager();

    public static void main(String[] args) {
        boolean isRun = true;


        while (isRun) {
            System.out.println("Please input 0 for exit");
            System.out.println("Please input 1 for add category");
            System.out.println("Please input 2 for edit category");
            System.out.println("Please input 3 for delete category by id");
            System.out.println("Please input 4 for add product");
            System.out.println("Please input 5 for edit product by id");
            System.out.println("Please input 6 for delete product by id");
            System.out.println("Please input 7 print sum of products");
            System.out.println("Please input 8 print max of price product ");
            System.out.println("Please input 9 print min of price product ");
            System.out.println("Please input 10 print avg of price product ");
            String command = scanner.nextLine();
            switch (command) {
                case "0":
                    isRun = false;
                    break;
                case "1":
                    addCategory();
                    break;
                case "2":
                    updateCategoryById();
                    break;
                case "3":
                    deleteCategoryById();
                    break;
                case "4":
                    addProduct();
                    break;
                case "5":
                    updateProductById();
                    break;
                case "6":
                    deleteProductById();
                    break;
                case "7":
                    productManager.printSumOfProduct();
                    break;
                case "8":
                    productManager.printMaxOfPrice();
                    break;
                case "9":
                    productManager.printMinOfPrice();
                    break;
                case "10":
                    productManager.printAvgOfPrice();
                    break;
            }
        }
    }

    private static void deleteProductById() {
        List<Product> productList = productManager.all();
        for (Product product : productList) {
            System.out.println(product);
        }
        System.out.println("please choose id");
        int id = Integer.parseInt(scanner.nextLine());
        if (productManager.getById(id) != null) {
            System.out.println("please input id");
            int a = Integer.parseInt(scanner.nextLine());
            Product product = new Product();
            product.setId(a);
            productManager.delete(product);
        }
    }

    private static void updateProductById() {
        List<Product> categoryList = productManager.all();
        for (Product product : categoryList) {
            System.out.println(product);
        }
        System.out.println("please choose id");
        int id = Integer.parseInt(scanner.nextLine());
        if (productManager.getById(id) != null) {
            System.out.println("please input name,description,price,quantity");
            String productDataStr = scanner.nextLine();
            String[] productData = productDataStr.split(",");
            Product product = new Product();
            product.setId(id);
            product.setName(productData[0]);
            product.setDescription(productData[1]);
            product.setPrice(Double.parseDouble(productData[2]));
            product.setQuantity(Integer.parseInt(productData[3]));
            productManager.edit(product);
        }
    }

    private static void addProduct() {
        List<Category> categoryList = categoryManager.all();
        for (Category category : categoryList) {
            System.out.println(category);
        }
        System.out.println("please choose category id");
        int id = Integer.parseInt(scanner.nextLine());
        Category category = categoryManager.getById(id);
        if (category != null) {
            System.out.println("please input name,description,price,quantity");
            String productDataStr = scanner.nextLine();
            String[] productData = productDataStr.split(",");
            Product product = new Product();
            product.setName(productData[0]);
            product.setDescription(productData[1]);
            product.setPrice(Double.parseDouble(productData[2]));
            product.setQuantity(Integer.parseInt(productData[3]));
            product.setCategory(category);
            productManager.save(product);
        }
    }

    private static void deleteCategoryById() {
        List<Category> categoryList = categoryManager.all();
        for (Category category : categoryList) {
            System.out.println(category);
        }
        System.out.println("please choose id");
        int id = Integer.parseInt(scanner.nextLine());
        if (categoryManager.getById(id) != null) {
            System.out.println("please input id");
            int a = Integer.parseInt(scanner.nextLine());
            Category category = new Category();
            category.setId(a);
            categoryManager.delete(category);
        }
    }

    private static void updateCategoryById() {
        List<Category> categoryList = categoryManager.all();
        for (Category category : categoryList) {
            System.out.println(category);
        }
        System.out.println("please choose id");
        int id = Integer.parseInt(scanner.nextLine());
        if (categoryManager.getById(id) != null) {
            System.out.println("please input name");
            String name = scanner.nextLine();
            Category category = new Category();
            category.setId(id);
            category.setName(name);
            categoryManager.edit(category);
            System.out.println("Category was update");
        } else {
            System.out.println("Category dose not exist");
        }

    }

    private static void addCategory() {
        System.out.println("Please input name");
        String name = scanner.nextLine();
        Category category = new Category();
        category.setName(name);
        categoryManager.save(category);

    }
}