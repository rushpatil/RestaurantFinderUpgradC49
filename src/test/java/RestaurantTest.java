import com.RestaurantFinder.Restaurant;
import com.RestaurantFinder.Item;
import com.RestaurantFinder.itemNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;

    //menuItem selected by user
    List<Item> menuItemSelected = new ArrayList<Item>();

    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void addNewRestaurant(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        addNewRestaurant();
        restaurant = Mockito.mock(Restaurant.class);
        LocalTime tNow = LocalTime.parse("21:00:00");
        Mockito.when(restaurant.getCurrentTime()).thenReturn(tNow);
        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        addNewRestaurant();
        restaurant = Mockito.mock(Restaurant.class);
        LocalTime tNow = LocalTime.parse("22:20:00");
        Mockito.when(restaurant.getCurrentTime()).thenReturn(tNow);
        assertFalse(restaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<<<<<<ORDER TOTAL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void total_order_value_should_be_returned_when_items_from_list_selected(){
        addNewRestaurant();
        menuItemSelected = restaurant.getMenu();
        assertEquals(506, restaurant.getItemTotal(menuItemSelected));
    }

    @Test
    public void total_order_value_should_decrease_when_items_from_list_are_deselected(){
        addNewRestaurant();
        int itemTotal = restaurant.getItemTotal(menuItemSelected);
        int afterTotal = menuItemSelected.get(1).getPrice();
        menuItemSelected.remove(1);
        assertEquals(itemTotal-afterTotal, restaurant.getItemTotal(menuItemSelected));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<ORDER TOTAL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        addNewRestaurant();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        addNewRestaurant();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        addNewRestaurant();

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}