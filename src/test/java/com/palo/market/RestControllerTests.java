package com.palo.market;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.palo.market.db.service.api.request.UpdateProductRequest;
import com.palo.market.domain.Customer;
import com.palo.market.domain.Merchant;
import com.palo.market.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestControllerTests {


    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Merchant merchant;

    @BeforeEach
    public void createMerchant() throws Exception {
        if (merchant == null) {
            merchant = new Merchant("name", "email", "address");

            String id = mockMvc.perform(post("/merchant")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(merchant)))
                    .andExpect(status().isCreated())
                    .andReturn().getResponse().getContentAsString();
            merchant.setId(objectMapper.readValue(id, Integer.class));
        }
    }


    @Test
    public void product() throws Exception {
        Product product = new Product(merchant.getId(), "tablet", "low tablet", 1, 10);

        // Add product
        String id = mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        product.setId(objectMapper.readValue(id, Integer.class));

        // Get product
        String returnedProduct = mockMvc.perform(get("/product/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Product productFromRest = objectMapper.readValue(returnedProduct, Product.class);
        assertEquals(product, productFromRest);

        // Get all products

        String listJason = mockMvc.perform(get("/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<Product> products = objectMapper.readValue(listJason, new TypeReference<List<Product>>() {
        });

        assert products.size() == 1;
        assertEquals(product, products.get(0));

        // Update product
        double updatePrice = product.getPrice() + 1;
        int updateAvailable = product.getAvailable() + 5;
        UpdateProductRequest updateProductRequest = new UpdateProductRequest(product.getName(), product.getDescription(), updatePrice, updateAvailable);

        mockMvc.perform(patch("/product/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateProductRequest)))
                .andExpect(status().isOk());


        String returnedUpdatedProduct = mockMvc.perform(get("/product/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Product updatedProduct = objectMapper.readValue(returnedUpdatedProduct, Product.class);
        assert updatePrice == updatedProduct.getPrice();
        assert updateAvailable == updatedProduct.getAvailable();

        // Delete product

        mockMvc.perform(delete("/product/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        // kontrola delete cez get
        mockMvc.perform(get("/product/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // druhy sposob konroly cez vymazanie vsetkych

        String listJason2 = mockMvc.perform(get("/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<Product> products2 = objectMapper.readValue(listJason2, new TypeReference<List<Product>>() {
        });

        assert products2.size() == 0;

    }


    @Test
    public void customer() throws Exception {
        Customer customer = new Customer("Jano", "Perez", "email", "address", 25, "0584");

        // Add customer
        String id = mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        customer.setId(objectMapper.readValue(id, Integer.class));


        // Get customer
        String customerJson = mockMvc.perform(get("/customer/" + customer.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Customer returnedCustomer = objectMapper.readValue(customerJson, Customer.class);
        assertEquals(customer, returnedCustomer);


        // Get All Customers
        String listJson = mockMvc.perform(get("/customer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<Customer> customers = objectMapper.readValue(listJson, new TypeReference<List<Customer>>() {
        });

        assert customers.size() == 1;
        assertEquals(customer, customers.get(0));

    }


    @Test
    public void merchant() throws Exception {
        // Merchant is already created

        // Get Merchant
        String merchantJson = mockMvc.perform(get("/merchant/" + merchant.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Merchant returnedMerchant = objectMapper.readValue(merchantJson, Merchant.class);
        assertEquals(merchant, returnedMerchant);

        // Get all merchants

        String listJson = mockMvc.perform(get("/merchant")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<Merchant> merchants = objectMapper.readValue(listJson, new TypeReference<List<Merchant>>() {
        });

        assert merchants.size() == 1;
        assertEquals(merchant, merchants.get(0));
    }
}
