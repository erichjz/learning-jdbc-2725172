package com.erich.lil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.erich.lil.data.dao.CustomerDao;
import com.erich.lil.data.dao.ProductDao;
import com.erich.lil.data.dao.ServiceDao;
import com.erich.lil.data.dao.SimpleProductDao;
import com.erich.lil.data.entity.Customer;
import com.erich.lil.data.entity.Product;
import com.erich.lil.data.entity.Service;
import com.erich.lil.data.entity.Vendor;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        ServiceDao serviceDao = new ServiceDao();
        CustomerDao customerDao = new CustomerDao();
        // showServices(serviceDao);
        // showCustomers(customerDao);
        // showProducts();
        storedProcedures();
        orderAndLimit(serviceDao);
        paging(customerDao);
    }

    public static void showServices(ServiceDao serviceDao) {
        List<Service> services = serviceDao.getAll();

        System.out.println("**** SERVICES ****");
        System.out.println("\n*** GET_ALL ***");
        services.forEach(System.out::println);

        Optional<Service> service = serviceDao.getOne(services.get(0).getServiceId());
        System.out.println("\n*** GET ONE ***\n" + service.get());

        Service newService = new Service();
        newService.setName("FooBarBaz" + System.currentTimeMillis());
        newService.setPrice(new BigDecimal(4.35));
        newService = serviceDao.create(newService);
        System.out.println("\n*** CREATE ***\n" + newService);
        newService.setPrice(new BigDecimal("13.45"));
        newService = serviceDao.update(newService);
        System.out.println("\n*** UPDATE ***\n" + newService);
        serviceDao.delete(newService.getServiceId());
        System.out.println("\n*** DELETE ***\n");

    }

    public static void showCustomers(CustomerDao customerDao) {
        List<Customer> customers = customerDao.getAll();

        System.out.println("**** CUSTOMERS ****");
        System.out.println("\n*** GET_ALL ***");
        customers.forEach(System.out::println);

        Optional<Customer> customer = customerDao.getOne(customers.get(0).getCustomerId());
        System.out.println("\n*** GET ONE ***\n" + customer.get());

        Customer newCustomer = new Customer();
        newCustomer.setFirstName("Bob");
        newCustomer.setLastName("Smith");
        newCustomer.setEmailAddress("bob" + System.currentTimeMillis() + "@example.com");
        newCustomer.setPhoneNumber(("+1 (206) 555-3112"));
        newCustomer.setMailingAddress("1235 5th Avenue");
        newCustomer = customerDao.create(newCustomer);
        System.out.println("\n*** CREATE ***\n" + newCustomer);
        newCustomer.setPhoneNumber("+1 (509) 555-9875");
        newCustomer = customerDao.update(newCustomer);
        System.out.println("\n*** UPDATE ***\n" + newCustomer);
        customerDao.delete(newCustomer.getCustomerId());
        System.out.println("\n*** DELETE ***\n");
    }

    public static void showProducts() {
        ProductDao productDao = new ProductDao();
        List<Product> products = productDao.getAll();

        System.out.println("**** PRODUCTS ****");
        System.out.println("\n*** GET_ALL ***");
        products.forEach(System.out::println);

        Optional<Product> product = productDao.getOne(products.get(0).getProductId());
        System.out.println("\n*** GET ONE ***\n" + product.get());

        Product newProduct = new Product();
        Vendor newVendor = new Vendor();
        newProduct.setName("CoolTrap" + System.currentTimeMillis());
        newProduct.setPrice(new BigDecimal(4.35));
        newVendor.setName("ACME Corporation");
        newVendor.setContact("Wile E Coyote");
        newVendor.setEmail("wilecoyote" + System.currentTimeMillis() + "@example.com");
        newVendor.setPhone("(206) 555-9131");
        newVendor.setAddress("1234 Lonely Desert Road");
        newProduct.setVendor(newVendor);

        newProduct = productDao.create(newProduct);
        System.out.println("\n*** CREATE ***\n" + newProduct);
        // newService.setPrice(new BigDecimal("13.45"));
        // newService = serviceDao.update(newService);
        // System.out.println("\n*** UPDATE ***\n" + newService);
        // serviceDao.delete(newService.getServiceId());
        // System.out.println("\n*** DELETE ***\n");

    }

    public static void storedProcedures() {
        System.out.println("\n\n*** SIMPLE PRODUCT ***");
        SimpleProductDao spdao = new SimpleProductDao();
        UUID productId = spdao.createProduct("foobarbaz" + System.currentTimeMillis(), new BigDecimal(45.67), "Jaloo");
        System.out.println(productId);
    }

    public static void orderAndLimit(ServiceDao serviceDao) {
        System.out.println("\n\n*** LIMIT ***");
        serviceDao.getAllLimit(2).forEach(System.out::println);
    }

    public static void paging(CustomerDao customerDao) {
        System.out.println("\n\n*** PAGED ***");
        for (int i = 1; i < 11; i++) {
            System.out.println("Page number: " + i);
            customerDao.getAllPaged(i, 10).forEach(System.out::println);
        }
    }
}
