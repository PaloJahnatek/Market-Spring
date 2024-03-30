package com.palo.market;

import com.palo.market.db.service.api.CustomerService;
import com.palo.market.db.service.api.MerchantService;
import com.palo.market.db.service.api.ProductService;
import com.palo.market.db.service.api.request.UpdateProductRequest;
import com.palo.market.domain.Customer;
import com.palo.market.domain.Merchant;
import com.palo.market.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DirtiesContext(classMode =  DirtiesContext.ClassMode.BEFORE_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DBServiceTests {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ProductService productService;

    private Merchant merchant;

    @BeforeEach
    public void createMerchant() {
        if (merchant == null) {
            merchant = new Merchant("name", "email", "address");
            Integer id = merchantService.add(merchant);
            assert id != null;
            merchant.setId(id);

        }
    }

    @Test
    public void customer() {
        Customer customer = new Customer("Jano", "Perez", "mail@perez", "Kolumbia", 20, "458");
        Integer id = customerService.add(customer);
        assert id != null;
        customer.setId(id);

        /* vytvorili sme si customer, potom sme si zavolali funkciu add,id ktore nam funkcia add vrati,
        sme si vratili do premennej id, overili sme si ci nie je null ze sa nam v to v databaze ulozilo
        potom si id setneme do customera
        */

        Customer fromDb = customerService.get(id);
        assertEquals(customer, fromDb);

        /* getneme si customera na zaklade toho id ulozime si to do noveho objektu fromDb,
        a porovname si tento objekt s objektom (customer, fromDb), ktory sme vytvorili a ked toto prejde
        to znamena ze ked si getneme toho custormera tak je to uplne rovnaky objekt ako ten ktory sme si vytvorili Customer customer = new Customer()
        vdaka tomuto customer.setId(id); sa rovnaju kebyze tot tam nie je tak sa nerovnaju.resp. vyhodi chybu

       */

        List<Customer> customers = customerService.getCustomers();
        assertEquals(1, customers.size());
        assertEquals(customer, customers.get(0));

        /* otestovali sme ti to ze sme vyuzili funkciu  getCustomers, otestovali sme ze size toho listu je prave 1
        a ked is getneme prveho customer a tom liste tak sa rovna s tym customerom
        ktoreho sme vlozili

         */
    }

    @Test
    public void merchant() {
        // uz mame vytvoreny merchant v createMerchant funkcii

        Merchant fromDB = merchantService.get(merchant.getId());
        assertEquals(merchant, fromDB);

        List<Merchant> merchants = merchantService.getMerchants();
        assertEquals(1, merchants.size());
        assertEquals(merchant, merchants.get(0));

    }

    @Test
    public void product() {
        Product product = new Product(merchant.getId(), "name", "description", 8, 3);
        Integer id = productService.add(product); // vytvorili sme si novy produkt dali sme ho do databazy
        assert id != null; // assertli sme si ze id nie je null aby sa podaril ten insert
        product.setId(id); // id sme setli k produktu

        Product fromDB = productService.get(id); // getli sme produkt na zaklade  id  a ulozili sme si getnuty objekt do fromDB
        assertEquals(product, fromDB); // porovnali sme si tieto dva objekty ci sa rovnaju

        List<Product> products = productService.getProducts(); //getli sme si vsetky produkty overili sme  ci velkost listu je prave 1
        assertEquals(1, products.size());
        assertEquals(product, products.get(0)); // taktiez ci ten jeden je rovnaky s nasim produktom

        product.setAvailable(12); // isli sme updatovat produkt najprv sme si setli  nase produkty available 12, vytvorili sme produkt request
        UpdateProductRequest productRequest = new UpdateProductRequest(product.getName(), product.getDescription(), product.getPrice(), product.getAvailable());

        productService.update(id, productRequest); // v udate sme tento produkt request pouzili
        Product fromDBAfterUpdate = productService.get(id);// potom sme si znovu getli produkt  a dali si ho do novej premennej fromDBAfterUpdate
        assertEquals(product, fromDBAfterUpdate);// asertli sme si tento nas produkt aj so setnutym Available aj s fromDBAfterUpdate, ak su rovnake mozme si byt isty ze sa nam ten update podaril
        assertNotEquals(fromDB, fromDBAfterUpdate); // a potom sme urobili kontrolu ze fromDB nie je rovnake fromDBAfterUpdate, fromDBAfterUpdate je obohateny o ten available

        productService.delete(id); // funkcia delete
        assertEquals(0, productService.getProducts().size());// overili sme to tak ze getProducts ma size nula

        // a v Product class sme upravili metodu equals createdAt.getTime() == product.createdAt.getTime(); aby nam fungoval Timestamp, funkcnost sme nezmelili iba porovnanie sme urobili troska ine
    }
}
