package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestReceipt {

    private IngredientType firstType, secondType;

    Burger burger = new Burger();

    @Mock
    private Bun bun;
    @Mock
    private Ingredient firstIngredient;
    @Mock
    private Ingredient secondIngredient;

    public TestReceipt(IngredientType firstType, IngredientType secondType) {
        this.firstType = firstType;
        this.secondType = secondType;
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Parameterized.Parameters(name="{0}, {1}")
    public static Object[][] getData()
    {
        return new Object[][] {
                {IngredientType.SAUCE, IngredientType.SAUCE},
                {IngredientType.FILLING, IngredientType.FILLING},
                {IngredientType.SAUCE, IngredientType.FILLING},
                {IngredientType.FILLING, IngredientType.SAUCE}
        };
    }

    @Test
    public void getReceiptShouldReturnCorrectReceipt()
    {
        String bunName = "Обычная булка";
        String firstIngName = "Первый ингредиент";
        String secondIngName = "Второй ингредиент";
        float price = 11.f;

        burger.setBuns(bun);
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);
        Mockito.when(bun.getName()).thenReturn(bunName);
        Mockito.when(firstIngredient.getType()).thenReturn(firstType);
        Mockito.when(firstIngredient.getName()).thenReturn(firstIngName);
        Mockito.when(secondIngredient.getType()).thenReturn(secondType);
        Mockito.when(secondIngredient.getName()).thenReturn(secondIngName);
        Mockito.when(burger.getPrice()).thenReturn(price);

        StringBuilder expReceipt = new StringBuilder(String.format("(==== %s ====)%n", bunName));
        expReceipt.append(String.format("= %s %s =%n", firstType.toString().toLowerCase(), firstIngName));
        expReceipt.append(String.format("= %s %s =%n", secondType.toString().toLowerCase(), secondIngName));
        expReceipt.append(String.format("(==== %s ====)%n", bunName));
        expReceipt.append(String.format("%nPrice: %f%n", price));

        assertEquals("Возвращается некорректный чек", expReceipt.toString(), burger.getReceipt());
    }

}
