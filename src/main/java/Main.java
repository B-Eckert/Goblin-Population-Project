import PeopleStuff.*;
import Misc.*;
import DataStructs.*;
import TownStuff.*;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.function.Predicate;

public class Main {

    public static void doTimePass(){
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Goblin> begin = new ArrayList<>();
        for(int i = 0; i < Dice.roll(10, 25); i++){
            Goblin g = Goblin.randomGoblin();
            begin.add(g);
            System.out.println(g.toString());
            //Thread.sleep(1000);
        }
        for(Goblin i : begin){
            if(!i.isMarried()){
                for(Goblin j : begin){
                    if(j.getSex() == !i.getSex() && i.getAge() >= 18 && j.getAge() >= 18 && !i.isMarried() && !j.isMarried()){
                        i.getMarried(j);
                        System.out.println(i.getFullName() + " married " + j.getFullName() + " in Scorpion Matrimony!");
                        break;
                    }
                }
            }
        }
        ArrayList<Goblin> secondGen = new ArrayList<>();
        for(Goblin i : begin){
            if(!i.isMarried()){
                System.out.println(i.getFullName() + " remained unmarried.");
            }
            else{
                secondGen.add(Goblin.intercourse(i.getPartner(), i));
            }
        }
        for(Goblin i : secondGen){
            System.out.println(i.toString());
            //Thread.sleep(1500);
        }
    }
}
