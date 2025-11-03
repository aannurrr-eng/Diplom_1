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
    private Ingredient ingredient;
    @Mock
    Ingredient ingredient1;

    @Test
    public void setBunsShouldSetBuns()
    {
        burger.setBuns(bun);
        assertEquals(bun, burger.bun);
    }

    @Test
    public void addIngredientShouldAddIngredient()
    {
        burger.addIngredient(ingredient);
        assertEquals("Количество ингредиентов в бургере отличается от количества добавленных ингредиентов", 1, burger.ingredients.size());
        assertEquals("В бургер добавляется неверный ингредиент", ingredient, burger.ingredients.get(0));
    }

    @Test
    public void removeIngredientShouldRemoveIngredient()
    {
        burger.addIngredient(ingredient);
        burger.removeIngredient(0);
        assertTrue("Не удаляется ингредиент из бургера", burger.ingredients.isEmpty());
    }

    @Test
    public void moveIngredientShouldMoveIngredient()
    {
        burger.addIngredient(ingredient);
        burger.addIngredient(ingredient1);
        burger.moveIngredient(1, 0);
        List<Ingredient> expectedIngredients = new ArrayList<>();
        expectedIngredients.add(ingredient1);
        expectedIngredients.add(ingredient);
        assertEquals("Ингредиент неправильно переставляется в списке ингредиентов", expectedIngredients, burger.ingredients);
    }

    @Test
    public void getPriceShouldReturnCorrectPrice()
    {
        burger.setBuns(bun);
        burger.addIngredient(ingredient);
        burger.addIngredient(ingredient1);
        Mockito.when(bun.getPrice()).thenReturn(2.f);
        Mockito.when(ingredient.getPrice()).thenReturn(3.f);
        Mockito.when(ingredient1.getPrice()).thenReturn(4.f);
        assertEquals("Цена бургера рассчитывается неверно", 11.f, burger.getPrice(), 0.0001f);
    }

    @Test
    public void getReceiptShouldReturnCorrectReceipt()
    {
        String bunName = "Обычная булка";
        String ingName = "Обычный соус";
        String ing1Name = "Обычная начинка";
        IngredientType ingType = IngredientType.SAUCE;
        IngredientType ing1Type = IngredientType.FILLING;
        float price = 11.f;

        burger.setBuns(bun);
        burger.addIngredient(ingredient);
        burger.addIngredient(ingredient1);
        Mockito.when(bun.getName()).thenReturn(bunName);
        Mockito.when(ingredient.getType()).thenReturn(IngredientType.SAUCE);
        Mockito.when(ingredient.getName()).thenReturn(ingName);
        Mockito.when(ingredient1.getType()).thenReturn(IngredientType.FILLING);
        Mockito.when(ingredient1.getName()).thenReturn(ing1Name);
        Mockito.when(burger.getPrice()).thenReturn(price);

        StringBuilder expReceipt = new StringBuilder(String.format("(==== %s ====)%n", bunName));
        expReceipt.append(String.format("= %s %s =%n", ingType.toString().toLowerCase(), ingName));
        expReceipt.append(String.format("= %s %s =%n", ing1Type.toString().toLowerCase(), ing1Name));
        expReceipt.append(String.format("(==== %s ====)%n", bunName));
        expReceipt.append(String.format("%nPrice: %f%n", price));

        assertEquals("Возвращается некорректный чек", expReceipt.toString(), burger.getReceipt());
    }

}

