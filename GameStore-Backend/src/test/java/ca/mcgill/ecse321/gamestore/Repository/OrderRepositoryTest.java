package ca.mcgill.ecse321.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gamestore.dao.OrderRepository;
import ca.mcgill.ecse321.gamestore.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.gamestore.dao.PaymentInformationRepository;
import ca.mcgill.ecse321.gamestore.dao.CartRepository;
import ca.mcgill.ecse321.gamestore.dao.AddressRepository;
import ca.mcgill.ecse321.gamestore.model.Order;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.model.PaymentInformation;
import ca.mcgill.ecse321.gamestore.model.Cart;
import ca.mcgill.ecse321.gamestore.model.Address;
import ca.mcgill.ecse321.gamestore.model.PaymentInformation.CardType;

@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @Autowired
    private PaymentInformationRepository paymentInformationRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AddressRepository addressRepository;

    @AfterEach
    public void clearDatabase() {
        // clear everything after each test
        orderRepository.deleteAll();
        customerAccountRepository.deleteAll();
        paymentInformationRepository.deleteAll();
        cartRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    void testPersistOrder() {
        // set up customer
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("password123");
        customerAccountRepository.save(someCustomer);

        // set up payment info
        PaymentInformation paymentInfo = new PaymentInformation();
        paymentInfo.setCardholderName("someCustomer");
        paymentInfo.setCardNumber(123456789);
        paymentInfo.setExpirationDate(Date.valueOf("2025-12-31"));
        paymentInfo.setCvc(123);
        paymentInfo.setCardType(CardType.Visa);
        paymentInfo.setCustomerAccount(someCustomer);
        paymentInformationRepository.save(paymentInfo);

        // create a cart
        Cart cart = new Cart();
        cart.setCustomerAccount(someCustomer);
        cartRepository.save(cart);

        // create an address
        Address address = new Address();
        address.setAddress("123 Main St");
        address.setCity("Montreal");
        address.setProvince("Quebec");
        address.setCountry("Canada");
        address.setPostalCode("H3A1B2");
        address.setCustomerAccount(someCustomer);
        addressRepository.save(address);

        // create order and link it with customer, payment info, cart, and address
        Order order = new Order();
        order.setTotalPrice(100.0);
        order.setIsPaid(true);
        order.setDeliveryStatus(false);
        order.setPromotionCode("PROMO123");
        order.setUserAgreementCheck(true);
        order.setCustomerAccount(someCustomer);
        order.setPaymentInformation(paymentInfo);
        order.setCart(cart);
        order.setAddress(address);
        order = orderRepository.save(order);

        // make sure the order was saved correctly
        assertNotNull(order);
        assertEquals(100.0, order.getTotalPrice());
        assertEquals(true, order.getIsPaid());
        assertEquals(false, order.getDeliveryStatus());
        assertEquals("PROMO123", order.getPromotionCode());
        assertEquals(someCustomer.getUsername(), order.getCustomerAccount().getUsername());

        // get the order from the database and make sure it matches
        Optional<Order> retrievedOrder = orderRepository.findById(order.getOrderId());
        assertTrue(retrievedOrder.isPresent());
        assertEquals(100.0, retrievedOrder.get().getTotalPrice());
        assertEquals(someCustomer.getUsername(), retrievedOrder.get().getCustomerAccount().getUsername());
    }

    @Test
    void testFindOrderByCustomerAccountId() {
        // create a customer
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("password456");
        customerAccountRepository.save(someCustomer);

        // create payment info
        PaymentInformation paymentInfo = new PaymentInformation();
        paymentInfo.setCardholderName("someCustomer");
        paymentInfo.setCardNumber(987654321);
        paymentInfo.setExpirationDate(Date.valueOf("2026-06-30"));
        paymentInfo.setCvc(456);
        paymentInfo.setCardType(CardType.Mastercard);
        paymentInfo.setCustomerAccount(someCustomer);
        paymentInformationRepository.save(paymentInfo);

        // create a cart
        Cart cart = new Cart();
        cart.setCustomerAccount(someCustomer);
        cartRepository.save(cart);

        // create an address
        Address address = new Address();
        address.setAddress("456 Another St");
        address.setCity("Montreal");
        address.setProvince("Quebec");
        address.setCountry("Canada");
        address.setPostalCode("H4B1A1");
        address.setCustomerAccount(someCustomer);
        addressRepository.save(address);

        // create an order
        Order order = new Order();
        order.setTotalPrice(150.0);
        order.setIsPaid(true);
        order.setDeliveryStatus(false);
        order.setPromotionCode("DISCOUNT123");
        order.setUserAgreementCheck(true);
        order.setCustomerAccount(someCustomer);
        order.setPaymentInformation(paymentInfo);
        order.setCart(cart);
        order.setAddress(address);
        orderRepository.save(order);

        // find the order by customer account ID
        Iterable<Order> orders = orderRepository.findByCustomerAccount_CustomerAccountId(someCustomer.getId());
        assertTrue(orders.iterator().hasNext());

        Order retrievedOrder = orders.iterator().next();
        assertEquals(150.0, retrievedOrder.getTotalPrice());
        assertEquals(someCustomer.getUsername(), retrievedOrder.getCustomerAccount().getUsername());
    }

    @Test
    void testUpdateOrder() {
        // create a customer
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("agent007");
        customerAccountRepository.save(someCustomer);

        // create payment info
        PaymentInformation paymentInfo = new PaymentInformation();
        paymentInfo.setCardholderName("someCustomer");
        paymentInfo.setCardNumber(700007);
        paymentInfo.setExpirationDate(Date.valueOf("2027-07-07"));
        paymentInfo.setCvc(777);
        paymentInfo.setCardType(CardType.AmericanExpress);
        paymentInfo.setCustomerAccount(someCustomer);
        paymentInformationRepository.save(paymentInfo);

        // create a cart
        Cart cart = new Cart();
        cart.setCustomerAccount(someCustomer);
        cartRepository.save(cart);

        // create an address
        Address address = new Address();
        address.setAddress("789 Spy St");
        address.setCity("London");
        address.setProvince("England");
        address.setCountry("UK");
        address.setPostalCode("W1A1AA");
        address.setCustomerAccount(someCustomer);
        addressRepository.save(address);

        // create order
        Order order = new Order();
        order.setTotalPrice(200.0);
        order.setIsPaid(true);
        order.setDeliveryStatus(true);
        order.setPromotionCode("TOPSECRET123");
        order.setUserAgreementCheck(true);
        order.setCustomerAccount(someCustomer);
        order.setPaymentInformation(paymentInfo);
        order.setCart(cart);
        order.setAddress(address);
        orderRepository.save(order);

        // update the order's total price
        Optional<Order> retrievedOrder = orderRepository.findById(order.getOrderId());
        assertTrue(retrievedOrder.isPresent());
        retrievedOrder.get().setTotalPrice(250.0);
        orderRepository.save(retrievedOrder.get());

        // verify the order was updated
        Optional<Order> updatedOrder = orderRepository.findById(retrievedOrder.get().getOrderId());
        assertTrue(updatedOrder.isPresent());
        assertEquals(250.0, updatedOrder.get().getTotalPrice());
    }

    @Test
    void testDeleteOrder() {
        // create a customer
        CustomerAccount someCustomer = new CustomerAccount();
        someCustomer.setUsername("someCustomer");
        someCustomer.setEmail("someCustomer@example.com");
        someCustomer.setPasswordHash("delete123");
        customerAccountRepository.save(someCustomer);

        // create payment info
        PaymentInformation paymentInfo = new PaymentInformation();
        paymentInfo.setCardholderName("someCustomer");
        paymentInfo.setCardNumber(101010101);
        paymentInfo.setExpirationDate(Date.valueOf("2028-10-10"));
        paymentInfo.setCvc(101);
        paymentInfo.setCardType(CardType.Interact);
        paymentInfo.setCustomerAccount(someCustomer);
        paymentInformationRepository.save(paymentInfo);

        // create a cart
        Cart cart = new Cart();
        cart.setCustomerAccount(someCustomer);
        cartRepository.save(cart);

        // create an address
        Address address = new Address();
        address.setAddress("123 Deletion St");
        address.setCity("Montreal");
        address.setProvince("Quebec");
        address.setCountry("Canada");
        address.setPostalCode("H3Z2Y7");
        address.setCustomerAccount(someCustomer);
        addressRepository.save(address);

        // create order
        Order order = new Order();
        order.setTotalPrice(175.0);
        order.setIsPaid(true);
        order.setDeliveryStatus(false);
        order.setPromotionCode("DELETE123");
        order.setUserAgreementCheck(true);
        order.setCustomerAccount(someCustomer);
        order.setPaymentInformation(paymentInfo);
        order.setCart(cart);
        order.setAddress(address);
        orderRepository.save(order);

        // verify the order exists
        Optional<Order> retrievedOrder = orderRepository.findById(order.getOrderId());
        assertTrue(retrievedOrder.isPresent());

        // delete the order
        orderRepository.deleteById(order.getOrderId());

        // verify the order was deleted
        Optional<Order> deletedOrder = orderRepository.findById(order.getOrderId());
        assertTrue(deletedOrder.isEmpty());
    }
}
