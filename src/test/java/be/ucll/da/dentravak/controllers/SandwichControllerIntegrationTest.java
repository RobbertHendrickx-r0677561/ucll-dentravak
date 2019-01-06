package be.ucll.da.dentravak.controllers;

import be.ucll.da.dentravak.Application;
import be.ucll.da.dentravak.model.Sandwich;
import be.ucll.da.dentravak.repositories.SandwichRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;

import static be.ucll.da.dentravak.model.SandwichTestBuilder.aSandwich;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SandwichControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Autowired
    private SandwichRepository sandwichRepository;

    @Before
    public void setUpASavedSandwich() {
        sandwichRepository.deleteAll();
    }

    @Test
    public void testGetSandwiches_NoSavedSandwiches_EmptyList() throws JSONException {
        String actualSandwiches = httpGet("/sandwiches");
        String expectedSandwiches = "[]";

        assertThatJson(actualSandwiches).isEqualTo(expectedSandwiches);
    }

    @Test
    public void testPostSandwich() throws JSONException {
        Sandwich sandwich = aSandwich().withName("Americain").withIngredients("Vlees").withPrice(4.0).build();

        String actualSandwichAsJson = httpRequest("/sandwiches", sandwich, HttpMethod.POST);
        String expectedSandwichAsJson = "{\"id\":\"${json-unit.ignore}\",\"name\":\"Americain\",\"ingredients\":\"Vlees\",\"price\":4}";

        assertThatJson(actualSandwichAsJson).isEqualTo(expectedSandwichAsJson);
    }

    @Test
    public void testPutSandwich() throws JSONException, IOException {
        Sandwich sandwich = aSandwich().withName("Americain").withIngredients("Vlees").withPrice(4.0).build();
        String out = httpRequest("/sandwiches", sandwich, HttpMethod.POST);
        Sandwich updatedSandwich = new ObjectMapper().readValue(out, Sandwich.class);

        updatedSandwich.setPrice(new BigDecimal("3.2"));

        String updatedSandwichAsJson = httpRequest("/sandwiches/"+updatedSandwich.getId(), updatedSandwich, HttpMethod.PUT);

        String expectedSandwichAsJson = "{\"id\":\"${json-unit.ignore}\",\"name\":\"Americain\",\"ingredients\":\"Vlees\",\"price\":3.2}";

        assertThatJson(updatedSandwichAsJson).isEqualTo(expectedSandwichAsJson);
    }

    @Test
    public void testGetSandwiches_WithSavedSandwiches_ListWithSavedSandwich() throws JSONException {
        Sandwich sandwich1 = aSandwich().withName("Americain").withIngredients("Vlees").withPrice(4.0).build();
        Sandwich sandwich2 = aSandwich().withName("Smos").withIngredients("hesp, kaas, groentjes").withPrice(3.6).build();
        String post1 = httpRequest("/sandwiches", sandwich1, HttpMethod.POST);
        String post2 = httpRequest("/sandwiches", sandwich2, HttpMethod.POST);
        String actualSandwichesAsJson = httpGet("/sandwiches");
        String expectedSandwichesAsJson = "[{\"id\":\"${json-unit.ignore}\",\"name\":\"Americain\",\"ingredients\":\"Vlees\",\"price\":4.0},{\"id\":\"${json-unit.ignore}\",\"name\":\"Smos\",\"ingredients\":\"hesp, kaas, groentjes\",\"price\":3.6}]";

        assertThatJson(actualSandwichesAsJson).isEqualTo(expectedSandwichesAsJson);
    }
}
