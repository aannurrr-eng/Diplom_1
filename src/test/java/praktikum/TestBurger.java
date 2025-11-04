package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class TestBurger {

    Burger burger = new Burger();

    @Mock
    private Bun bun;
    @Mock
    private Ingredient firstIngredient;
    @Mock
    private Ingredient secondIngredient;

    @Test
    public void setBunsShouldSetBuns()
    {
        burger.setBuns(bun);
        assertEquals(bun, burger.bun);
    }

    @Test
    public void addIngredientShouldAddIngredient()
    {
        burger.addIngredient(firstIngredient);
        List<Ingredient> expectedIngredients = new ArrayList<>();
        expectedIngredients.add(firstIngredient);
        assertEquals("Ингредиент не добавляется в бургер", expectedIngredients, burger.ingredients);
    }

    @Test
    public void removeIngredientShouldRemoveIngredient()
    {
        burger.addIngredient(firstIngredient);
        burger.removeIngredient(0);
        assertTrue("Не удаляется ингредиент из бургера", burger.ingredients.isEmpty());
    }

    @Test
    public void moveIngredientShouldMoveIngredient()
    {
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);
        burger.moveIngredient(1, 0);
        List<Ingredient> expectedIngredients = new ArrayList<>();
        expectedIngredients.add(secondIngredient);
        expectedIngredients.add(firstIngredient);
        assertEquals("Ингредиент неправильно переставляется в списке ингредиентов", expectedIngredients, burger.ingredients);
    }

    @Test
    public void getPriceShouldReturnCorrectPrice()
    {
        burger.setBuns(bun);
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);
        Mockito.when(bun.getPrice()).thenReturn(2.f);
        Mockito.when(firstIngredient.getPrice()).thenReturn(3.f);
        Mockito.when(secondIngredient.getPrice()).thenReturn(4.f);
        assertEquals("Цена бургера рассчитывается неверно", 11.f, burger.getPrice(), 0.0001f);
    }

}

