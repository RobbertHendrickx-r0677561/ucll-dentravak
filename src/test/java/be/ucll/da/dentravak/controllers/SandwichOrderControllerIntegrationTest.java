package be.ucll.da.dentravak.controllers;

import be.ucll.da.dentravak.Application;
import be.ucll.da.dentravak.model.Sandwich;
import be.ucll.da.dentravak.model.SandwichOrder;
import be.ucll.da.dentravak.model.SandwichTestBuilder;
import be.ucll.da.dentravak.repositories.SandwichOrderRepository;
import be.ucll.da.dentravak.repositories.SandwichRepository;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import static be.ucll.da.dentravak.model.SandwichOrderTestBuilder.aSandwichOrder;
import static be.ucll.da.dentravak.model.SandwichTestBuilder.aSandwich;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SandwichOrderControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Autowired
    private SandwichRepository sandwichRepository;
    @Autowired
    private SandwichOrderRepository sandwichOrderRepository;

    private Sandwich savedSandwich;

    @Before
    public void setUpASavedSandwich() {
        sandwichRepository.deleteAll();
        sandwichOrderRepository.deleteAll();
        savedSandwich = sandwichRepository.save(SandwichTestBuilder.aSandwich().withName("Americain").withIngredients("vlees").withPrice(3.5).build());
    }

    @Test
    public void testGetSandwichOrders_NoOrdersSaved_EmptyList() throws JSONException {
        String actualSandwiches = httpGet("/orders");
        String expectedSandwiches = "[]";

        assertThatJson(actualSandwiches).isEqualTo(expectedSandwiches);
    }

    @Test
    public void testPostSandwichOrder() throws JSONException {
        SandwichOrder sandwichOrder = aSandwichOrder().forSandwich(savedSandwich).withBreadType(SandwichOrder.BreadType.BOTERHAMMEKES).withMobilePhoneNumber("0487/123456").build();
        String actualSandwiches = httpRequest("/orders", sandwichOrder, HttpMethod.POST);
        String expectedSandwiches = "{\"id\":\"${json-unit.ignore}\",\"sandwichId\":\"" + savedSandwich.getId() + "\",\"name\":\"Americain\",\"breadType\":\"BOTERHAMMEKES\",\"creationDate\":\"${json-unit.ignore}\",\"price\":3.5,\"mobilePhoneNumber\":\"0487/123456\"}";

        assertThatJson(actualSandwiches).isEqualTo(expectedSandwiches);
    }

    @Test
    public void testGetSandwichOrders_WithOrdersSaved_ReturnsListWithOrders() throws JSONException {
        Sandwich sandwich1 = aSandwich().withName("Americain").withIngredients("Vlees").withPrice(4.0).build();
        Sandwich sandwich2 = aSandwich().withName("Smos").withIngredients("hesp, kaas, groentjes").withPrice(3.6).build();
        sandwichRepository.save(sandwich1);
        sandwichRepository.save(sandwich2);
        SandwichOrder o1 = aSandwichOrder().forSandwich(sandwich1).withBreadType(SandwichOrder.BreadType.WRAP).withMobilePhoneNumber("0487/123456").build();
        SandwichOrder o2 = aSandwichOrder().forSandwich(sandwich2).withBreadType(SandwichOrder.BreadType.BOTERHAMMEKES).withMobilePhoneNumber("0478/123456").build();
        String post1 = httpRequest("/orders", o1, HttpMethod.POST);
        String post2 = httpRequest("/orders", o2, HttpMethod.POST);
        String actualOrdersAsJson = httpGet("/orders");
        String expectedOrders = "[{\"id\":\"${json-unit.ignore}\",\"sandwichId\":\"" + sandwich1.getId() + "\",\"name\":\"Americain\",\"breadType\":\"WRAP\",\"creationDate\":\"${json-unit.ignore}\",\"price\":4.0,\"mobilePhoneNumber\":\"0487/123456\"}," +
                "{\"id\":\"${json-unit.ignore}\",\"sandwichId\":\"" + sandwich2.getId() + "\",\"name\":\"Smos\",\"breadType\":\"BOTERHAMMEKES\",\"creationDate\":\"${json-unit.ignore}\",\"price\":3.6,\"mobilePhoneNumber\":\"0478/123456\"}]";

        assertThatJson(actualOrdersAsJson).isEqualTo(expectedOrders);
    }

}
