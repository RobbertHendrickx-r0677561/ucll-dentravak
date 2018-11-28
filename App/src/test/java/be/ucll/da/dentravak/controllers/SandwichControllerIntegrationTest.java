package be.ucll.da.dentravak.controllers;

import be.ucll.da.dentravak.Application;
import be.ucll.da.dentravak.model.db.SandwichRepository;
import be.ucll.da.dentravak.model.domain.Sandwich;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

        String actualSandwichAsJson = httpPost("/sandwiches", sandwich);
        String expectedSandwichAsJson = "{\"id\":\"${json-unit.ignore}\",\"name\":\"Americain\",\"ingredients\":\"Vlees\",\"price\":4}";

        assertThatJson(actualSandwichAsJson).isEqualTo(expectedSandwichAsJson);
    }

    @Test
    public void testPutSandwich() throws JSONException {
        Sandwich sandwich = aSandwich().withName("Americain").withIngredients("Vlees").withPrice(4.0).build();
        String out = httpPost("/sandwiches", sandwich);
        Sandwich updatedSandwich = ObjectMapper(out, Sandwich);
        System.out.println(out);

        sandwich.setPrice(new BigDecimal("3.2"));
        System.out.println(sandwich.toString());

        String updatedSandwichAsJson = httpPut("/sandwiches/"+sandwich.getId(), sandwich);


        String expectedSandwichAsJson = "{\"id\":\"${json-unit.ignore}\",\"name\":\"Americain\",\"ingredients\":\"Vlees\",\"price\":3.2}";

        assertThatJson(updatedSandwichAsJson).isEqualTo(expectedSandwichAsJson);
    }

    @Test
    public void testGetSandwiches_WithSavedSandwiches_ListWithSavedSandwich() throws JSONException {
        Sandwich sandwich1 = aSandwich().withName("Americain").withIngredients("Vlees").withPrice(4.0).build();
        Sandwich sandwich2 = aSandwich().withName("Smos").withIngredients("hesp, kaas, groentjes").withPrice(3.6).build();
        String post1 = httpPost("/sandwiches", sandwich1);
        String post2 = httpPost("/sandwiches", sandwich2);
        String actualSandwichesAsJson = httpGet("/sandwiches");
        String expectedSandwichesAsJson = "[{\"id\":\"${json-unit.ignore}\",\"name\":\"Americain\",\"ingredients\":\"Vlees\",\"price\":4.0},{\"id\":\"${json-unit.ignore}\",\"name\":\"Smos\",\"ingredients\":\"hesp, kaas, groentjes\",\"price\":3.6}]";

        assertThatJson(actualSandwichesAsJson).isEqualTo(expectedSandwichesAsJson);
    }
}
