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

    private IngredientType type1, type2;

    Burger burger = new Burger();

    @Mock
    private Bun bun;
    @Mock
    private Ingredient ingredient1;
    @Mock
    private Ingredient ingredient2;

    public TestReceipt(IngredientType type1, IngredientType type2) {
        this.type1 = type1;
        this.type2 = type2;
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
        String ingName1 = "Ингредиент1";
        String ingName2 = "Ингредиент2";
        float price = 11.f;

        burger.setBuns(bun);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
        Mockito.when(bun.getName()).thenReturn(bunName);
        Mockito.when(ingredient1.getType()).thenReturn(type1);
        Mockito.when(ingredient1.getName()).thenReturn(ingName1);
        Mockito.when(ingredient2.getType()).thenReturn(type2);
        Mockito.when(ingredient2.getName()).thenReturn(ingName2);
        Mockito.when(burger.getPrice()).thenReturn(price);

        StringBuilder expReceipt = new StringBuilder(String.format("(==== %s ====)%n", bunName));
        expReceipt.append(String.format("= %s %s =%n", type1.toString().toLowerCase(), ingName1));
        expReceipt.append(String.format("= %s %s =%n", type2.toString().toLowerCase(), ingName2));
        expReceipt.append(String.format("(==== %s ====)%n", bunName));
        expReceipt.append(String.format("%nPrice: %f%n", price));

        assertEquals("Возвращается некорректный чек", expReceipt.toString(), burger.getReceipt());
    }

}
