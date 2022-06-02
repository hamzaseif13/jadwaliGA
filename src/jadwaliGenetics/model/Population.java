package jadwaliGenetics.model;

import fr.dieul.lab.geneticalgorithm.model.Individual;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

//Population class
public class Population {
    //number of schedules in a population
    private int populationSize;
    //how many courses in a schedule
    private int coursesLength;
    //best score in a population
    private int fittestScore = 0;
    //
    private List<Schedule> schedules;

    public Population(int size, List<Course> courses){
        this.schedules= new ArrayList<>();
        this.populationSize=size;
        this.coursesLength=courses.size();
        //create first population
        for(int j=0;j<populationSize;j++){
            schedules.add(new Schedule(courses));
        }
    }
    public void calculateFitness(){
        for(Schedule schedule:schedules){
            schedule.calculateFitness();
        }
    }
    public List<Schedule> getSchedules() {
        return schedules;
    }

    public int getFittestScore() {
        int max=Integer.MIN_VALUE;
        for(Schedule schedule:schedules){
            if(schedule.getFitness()>max)
                max = schedule.getFitness();
        }
        return max;
    }

    public Schedule selectFittest() {
        int min= Integer.MIN_VALUE;
        int fittestIndex=0;
        //getting minimum fitness (the smaller the fitness the better the schedule is fitness= number of conflicts)

        for(int j=0;j<schedules.size();j++){
            if(schedules.get(j).getFitness()>=min){
                min=schedules.get(j).getFitness();
                fittestIndex=j;
            }
        }
        //updating the best fitness
        fittestScore=schedules.get(fittestIndex).getFitness();

        //try to return the fittest individual
        try {
            return (Schedule) schedules.get(fittestIndex).clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Schedule selectSecondFittest() {

        int maxFit1 = 0;
        int maxFit2 = 0;
        for (int i = 0; i < schedules.size(); i++) {
            if (schedules.get(i).getFitness() > schedules.get(maxFit1).getFitness()) {
                maxFit2 = maxFit1;
                maxFit1 = i;
            } else if (schedules.get(i).getFitness() > schedules.get(maxFit2).getFitness()) {
                maxFit2 = i;
            }
        }
        try {
            return (Schedule) schedules.get(maxFit2).clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public int getLeastFittestIndex() {
        int minFitVal = Integer.MAX_VALUE;
        int minFitIndex = 0;
        for (int i = 0; i < schedules.size(); i++) {
            if (schedules.get(i).getFitness()<=minFitVal) {
                minFitVal = schedules.get(i).getFitness();
                minFitIndex = i;
            }
        }
        return minFitIndex;


    }

    public void sort() {
        Collections.sort(schedules);
    }
}