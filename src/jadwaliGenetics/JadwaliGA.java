package jadwaliGenetics;

import jadwaliGenetics.model.Course;
import jadwaliGenetics.model.Population;
import jadwaliGenetics.model.Schedule;
import jadwaliGenetics.model.Section;

import java.time.DayOfWeek;
import java.util.*;

public class JadwaliGA {
    public static List<Course> courses = new ArrayList<>();
    public static Schedule fittest;
    public static Schedule secondFittest;
    public static int generation = 0;
    public static Random random = new Random();
    public static Population population;
    public static int numberOfSchedules=50;

    public static void main(String[] args) {
        addCoursesAndSections();

        long startTime = System.nanoTime();
        //initializing courses info (mocking database call)

        //generating first set of schedules
        population = new Population(numberOfSchedules, courses);
        population.calculateFitness();

        System.out.println("\nGeneration: " + generation + " Fittest: " + population.getFittestScore()+" Target is "+courses.size());
        showGeneticPool(population);
        int counter = 0;
        while (getDistinctFitnessCounter()!=21) {
            generation++;
            selection();
            crossover();
            //Do mutation under a random probability
            if (random.nextInt() % 7 < 5) {
                mutation();
            }
            addFittestOffspring();
            System.out.println("\nGeneration: " + generation + " Fittest: " +
                    population.getFittestScore()+"| Target is "+courses.size()+" | distinct perfect fitness is : "+getDistinctFitnessCounter());
            showGeneticPool(population);

            System.out.println("counter = " + counter);
            System.out.println("distinc = "+getDistinctFitnessCounter());

            counter++;
        }

        //print time taken
        long endTime = System.nanoTime();
        System.out.println((endTime-startTime)*1e-9);

    }
    private static int getDistinctFitnessCounter() {
        List<int[]> list= new ArrayList<>();
        population.getSchedules().forEach(schedule -> list.add(schedule.getSections()));
        List<int[]> list2 = (List<int[]>) ((ArrayList<int[]>) list).clone();
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

       return(list2.size());

    }
    private static boolean isEquil(int[] first, int[] second) {
        for(int j=0;j<first.length;j++){
            if(first[j]!=second[j])return false;
        }
        return true;
    }
    private static void selection() {
        fittest = population.selectFittest();
        secondFittest = population.selectSecondFittest();
    }

    private static void crossover() {
        int crossOverPoint = random.nextInt(courses.size());

        for (int i = 0; i < crossOverPoint; i++) {
            int temp = fittest.getSections()[i];
            fittest.getSections()[i] = secondFittest.getSections()[i];
            secondFittest.getSections()[i] = temp;
        }
    }

    public static void mutation() {
        //Select a random mutation point
        int mutationPoint = random.nextInt(courses.size());
        //change values at the mutation point
        fittest.getSections()[mutationPoint] = random.nextInt(1, courses.get(mutationPoint).sectionLength() + 1);
        mutationPoint = random.nextInt(courses.size());
        secondFittest.getSections()[mutationPoint] = random.nextInt(1, courses.get(mutationPoint).sectionLength() + 1);
    }

    public static void addFittestOffspring() {
        //Update fitness values of offspring
        fittest.calculateFitness();
        secondFittest.calculateFitness();
        //Get index of least fit individual
        int leastFittestIndex = population.getLeastFittestIndex();
        //Replace least fittest individual from most fittest offspring
        population.getSchedules().set(leastFittestIndex,getFittestOffspring())  ;
    }

    static void showGeneticPool(Population population) {
        System.out.println("==Genetic Pool==");
        int increment = 0;
        population.sort();
        for (Schedule schedule : population.getSchedules()) {
            System.out.println("> Schedule  " + increment + " | " + schedule.toString() + " |");
            increment++;
        }
        System.out.println("================");
    }
    public static Schedule getFittestOffspring(){
        if(fittest.getFitness()>secondFittest.getFitness())
            return fittest;
        return secondFittest;
    }
    public static void addCoursesAndSections() {
        Course course = new Course("Software Testing");
        course.addSection(new Section(1, 13, 14.5,
                new ArrayList<>() {{
                    add(DayOfWeek.SUNDAY);
                }}));
        courses.add(course);

        course = new Course("software architecture");
        course.addSection(new Section(1, 10, 11.5
                , new ArrayList<>() {{
            add(DayOfWeek.SUNDAY);
            add(DayOfWeek.TUESDAY);
        }}));
        course.addSection(new Section(2, 11.5, 13
                , new ArrayList<>() {{
            add(DayOfWeek.SUNDAY);
            add(DayOfWeek.TUESDAY);
        }}));
        course.addSection(new Section(3, 10, 11.5
                , new ArrayList<>() {{
            add(DayOfWeek.MONDAY);
            add(DayOfWeek.WEDNESDAY);
        }}));
        courses.add(course);

        course = new Course("HCI");
        course.addSection(new Section(1, 8.5, 10
                , new ArrayList<>() {{
            add(DayOfWeek.MONDAY);
        }}));
        course.addSection(new Section(2, 10, 11.5
                , new ArrayList<>() {{
            add(DayOfWeek.MONDAY);
        }}));

        courses.add(course);



        course = new Course("lab 2");
        course.addSection(new Section(1, 13.5, 16.5
                , new ArrayList<>() {{
            add(DayOfWeek.SUNDAY);
        }}));
        course.addSection(new Section(2, 13.5, 16.5
                , new ArrayList<>() {{
            add(DayOfWeek.MONDAY);
        }}));
        course.addSection(new Section(3, 10.5, 12.5
                , new ArrayList<>() {{
            add(DayOfWeek.THURSDAY);
        }}));
        course.addSection(new Section(4, 13.5, 16.5
                , new ArrayList<>() {{
            add(DayOfWeek.THURSDAY);
        }}));
        courses.add(course);


        course = new Course("Software Requirements");
        course.addSection(new Section(2, 11.5, 13
                , new ArrayList<>() {{
            add(DayOfWeek.MONDAY);add(DayOfWeek.WEDNESDAY);
        }}));
        course.addSection(new Section(3, 10, 11.5
                , new ArrayList<>() {{
            add(DayOfWeek.MONDAY);add(DayOfWeek.WEDNESDAY);
        }}));


        courses.add(course);





    }
}
