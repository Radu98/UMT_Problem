package com.umt;
import java.util.*;

            /*Pentru a forma un dreptunghi, trebuie sa avem 2 perechi a cate 2 puncte cu aceeasi coordonata x, ambele perechi avand aceleasi doua coordonate y in plan.
            Prin urmare, am ales sa formez initial o lista cu puncte si sa le transpun intr-un hashmap, grupand toate coordonatele y in functie de x.*/

public class CountRectangles {
    public static void main(String[] args) {
        {
            //test
            List points = new ArrayList();

            points.add(new Point(1, 1));
            points.add(new Point(1, 3));
            points.add(new Point(2, 3));
            points.add(new Point(2, 1));
            points.add(new Point(3, 1));
            points.add(new Point(3, 3));

            System.out.println(countRectangles(points));
        }
    }

    private static Map createSortedMap(List<Point> list) {
        Map<Integer, List<Integer>> map = new HashMap<>();

        //iterating through the list to create a hashmap, grouping points by x coordinate, so each x coordinate corresponds to a list of y coordinates
        for (Point point : list) {
            map.computeIfAbsent(point.getX(), p -> new ArrayList<>()).add(point.getY());
        }

        //sorting all the lists (of y coordinates) present in the hashmap
        sortLists(map);

        //return final, sorted hashmap
        return map;
    }


    private static void sortLists(Map<Integer, List<Integer>> map) {
        //sorting each list from a map value, in particular
        for (Integer i : map.keySet()) {
            List temporaryList;

            //saving initial list to a temporary one + sorting
            temporaryList = map.get(i);
            Collections.sort(temporaryList);

            //replacing initial list with the sorted one
            map.put(i, temporaryList);
        }
    }

    //intersecting 2 lists into a single one
    private static List<Integer> intersection(List<Integer> firstList, List<Integer> secondList) {
        List<Integer> returnList = new ArrayList<>();
        for (Integer i : firstList) {
            if (secondList.contains(i)) {
                returnList.add(i);
            }
        }
        return returnList;
    }

    public static int countRectangles(List<Point> list) {
        Map<Integer, List<Integer>> myMap;

        //creating a sorted hashmap from list of points
        myMap = createSortedMap(list);
        int number = 0;

        //converting hashmap's keyset to an array so we can iterate trough indexes
        Object[] keysArray = myMap.keySet().toArray();

        //iterating through the map to find pairs of 2 lists
        for (int i = 0; i < myMap.size() - 1; i++) {
            for (int j = i + 1; j < myMap.size(); j++) {

                //creating the intersection list of each pair ( of 2 lists )
                List<Integer> intersection = intersection(myMap.get(keysArray[i]), myMap.get(keysArray[j]));

                int n = intersection.size();

                //calculating the number of rectangles using the formula of combinations because each pair of 2 integers from the current intersection creates a rectangle
                if (n > 1) number = number + (factorial(n) / (2 * factorial(n - 2)));
            }
        }

        return number;
    }

    private static int factorial(int n) {
        if (n == 0)
            return 1;
        else
            return (n * factorial(n - 1));
    }
}
