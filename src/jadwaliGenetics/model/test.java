package jadwaliGenetics.model;

import java.time.LocalTime;
import java.util.*;

public class test {
    public static void main(String[] args) {
        ArrayList<int[]> list = new ArrayList<>();
        list.add(new int[]{1, 2, 3});
        list.add(new int[]{1,2,322});
        list.add(new int[]{1, 22, 3});
        list.add(new int[]{5, 3, 1});
        List<int[]> list2= (List<int[]>) list.clone();
        List<int[]>distinctList= new ArrayList<>();
        for(int j=0;j<list.size()-1;j++){
            for(int i=j+1;i<list.size();i++){
                    if(isEquil(list.get(j),list.get(i))){
                        distinctList.add(list.get(i));
                    }
            }
        }

        for(int j=0;j<distinctList.size();j++){
            list2.remove(distinctList.get(j));
        }
        System.out.println(list2.size());


    }


    private static boolean isEquil(int[] first, int[] second) {
        for(int j=0;j<first.length;j++){
            if(first[j]!=second[j])return false;
        }
        return true;
    }


}
