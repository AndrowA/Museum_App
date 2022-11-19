package com.mcgill.mymuseum.controller;
import com.mcgill.mymuseum.repository.MuseumPassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MuseumPassControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MuseumPassRepository passRepository;

    @BeforeEach
    public void setup() {
        MockMvcWebClientBuilder.webAppContextSetup(webApplicationContext);
        this.passRepository.deleteAll();
    }
}
//
//    @AfterEach
//    public void cleanup() {
//        RestAssuredMockMvc.reset();
//    }
//
//    @Test
//    public void testGetAllItems() {
//        when().get("/items/all").then()
//                .statusCode(200)
//                .body("$", empty());
//    }
//
//    @Test
//    public void testCreateAndQueryItemID() {
//        final int id = given()
//                .param("name", "cheeseburger")
//                .param("itemPrice", "20")
//                .param("inventoryAmount", "3")
//                .param("isDeliverable", "true")
//                .param("portionUnit", "20g")
//                .param("inventoryType", "perishable")
//                .post("/item/create")
//                .then().statusCode(200)
//                .body("name", equalTo("cheeseburger"))
//                .body("itemPrice", equalTo(20))
//                .body("inventoryAmount", equalTo(3))
//                .body("portionUnit", equalTo("20g"))
//                .body("inventoryType", equalTo(InventoryType.perishable.name()))
//                .extract().response().body().path("itemID");
//
//        when().get("/item/get/id?id=" + id)
//                .then().statusCode(200)
//                .body("name", equalTo("cheeseburger"))
//                .body("itemPrice", equalTo(20))
//                .body("inventoryAmount", equalTo(3))
//                .body("portionUnit", equalTo("20g"))
//                .body("inventoryType", equalTo(InventoryType.perishable.name()))
//                .body("itemID", equalTo(id));
//    }
//}
