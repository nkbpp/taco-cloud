package sia.tacocloud.tacos.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import sia.tacocloud.tacos.domain.Ingredient;
import sia.tacocloud.tacos.domain.MyUser;
import sia.tacocloud.tacos.domain.Taco;
import sia.tacocloud.tacos.domain.Type;
import sia.tacocloud.tacos.repository.IngredientRepository;
import sia.tacocloud.tacos.repository.MyUserRepository;
import sia.tacocloud.tacos.repository.OrderRepository;
import sia.tacocloud.tacos.repository.TacoRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DesignTacoController.class)
class DesignTacoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private List<Ingredient> ingredients;

    private Taco taco;

    @MockBean
    private TacoRepository tacoRepository;

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private MyUserRepository userRepository;

    @BeforeEach
    public void setup() {
        ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );

        when(ingredientRepository.findAll())
                .thenReturn(ingredients);

        when(ingredientRepository.findById("FLTO")).thenReturn(Optional.of(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP)));
        when(ingredientRepository.findById("GRBF")).thenReturn(Optional.of(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN)));
        when(ingredientRepository.findById("CHED")).thenReturn(Optional.of(new Ingredient("CHED", "Cheddar", Type.CHEESE)));

        taco = new Taco();
        taco.setName("Test Taco");

        taco.setIngredients(
                Arrays.asList(
                        new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                        new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                        new Ingredient("CHED", "Cheddar", Type.CHEESE))
        );

        when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(new MyUser(1L, "testuser", "testpass", "Test User", "123 Street", "Someville", "CO", "12345", "123-123-1234")));

    }

    @Test
    @WithMockUser(username = "testuser", password = "testpass")
    public void testShowDesignForm() throws Exception {
        mockMvc.perform(get("/design"))
                .andExpect(status().isOk())
                .andExpect(view().name("design"))
                .andExpect(model().attribute("wrap", ingredients.subList(0, 2)))
                .andExpect(model().attribute("protein", ingredients.subList(2, 4)))
                .andExpect(model().attribute("veggies", ingredients.subList(4, 6)))
                .andExpect(model().attribute("cheese", ingredients.subList(6, 8)))
                .andExpect(model().attribute("sauce", ingredients.subList(8, 10)));
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpass", authorities = "ROLE_USER")
    public void processTaco() throws Exception {
        when(tacoRepository.save(taco))
                .thenReturn(taco);

        mockMvc.perform(post("/design")
                        .with(csrf())//без этого не работает даже если отллючен в приложении
                        .content("name=Test+Taco&ingredients=FLTO,GRBF,CHED")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().stringValues("Location", "/orders/current"));
    }

}