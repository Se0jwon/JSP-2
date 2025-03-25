package study.jsp2.ch08;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService {

    Map<String, Product> products = new HashMap<>();

    public ProductService() {
        Product product1 = new Product("101", "아이폰", "Apple", 1200000, "2020.12.12");
        Product product2 = new Product("102", "갤럭시", "Samsung", 1000000, "2021.2.2");
        Product product3 = new Product("103", "LG폰", "LG", 900000, "2021.3.2");

        products.put("101", product1);
        products.put("102", product2);
        products.put("103", product3);
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public Product findById(String id) {
        return products.get(id);
    }


}
