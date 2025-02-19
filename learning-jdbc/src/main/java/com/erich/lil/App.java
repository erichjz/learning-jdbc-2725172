package com.erich.lil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.erich.lil.data.dao.CustomerDao;
import com.erich.lil.data.dao.ServiceDao;
import com.erich.lil.data.entity.Customer;
import com.erich.lil.data.entity.Service;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        // showServices();
        showCustomers();
    }

    public static void showServices() {
        ServiceDao serviceDao = new ServiceDao();
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

    public static void showCustomers() {
        CustomerDao customerDao = new CustomerDao();
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
}
