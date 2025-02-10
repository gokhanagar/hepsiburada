package stepDefs.UI_StepDefs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.SearchResultsPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static stepDefs.Hooks.driver;

public class Deneme {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<Integer>();
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        Random random = new Random();

        int n = random.nextInt(list.size());


        System.out.println(n);


    }


}
