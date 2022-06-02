package jadwaliGenetics;

import jadwaliGenetics.model.Course;
import jadwaliGenetics.model.Population;
import jadwaliGenetics.model.Schedule;
import jadwaliGenetics.model.Section;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class JadwaliGA {
    public static List<Course> courses = new ArrayList<>();
    public static Schedule fittest;
    public static Schedule secondFittest;
    public static int generation = 0;
    public static Random random = new Random();
    public static Population population;
    public static int numberOfSchedules=567;

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        //initializing courses info (mocking database call)
        addCoursesAndSections();

        //generating first set of schedules
        population = new Population(numberOfSchedules, courses);
        population.calculateFitness();

        System.out.println("\nGeneration: " + generation + " Fittest: " + population.getFittestScore()+" Target is "+courses.size());
        showGeneticPool(population);
        int counter = 50;
        while (counter != 0) {
            generation++;
            selection();
            crossover();

            //Do mutation under a random probability
            if (random.nextInt() % 7 < 5) {
                mutation();
            }
            addFittestOffspring();
            System.out.println("\nGeneration: " + generation + " Fittest: " +
                    population.getFittestScore()+"| Target is "+courses.size()+" | distinct perfect fitness is : ");
            showGeneticPool(population);
            counter--;

        }
        //print time taken
        long endTime = System.nanoTime();
        System.out.println((endTime-startTime)*1e-9);
    }


    private static int getDistinctFitnessCounter() {
        int counter=0;
        int [][]arr= new int[numberOfSchedules][courses.size()];

        for(int j=0;j<numberOfSchedules;j++){
            for(int i=0;i<numberOfSchedules;i++)
                arr[j]=population.getSchedules().get(j).getSections();
        }
        return (int) Arrays.stream(arr).distinct().count();
    }

    public static boolean compareArray(int[]firstArr,int[]secondArr){
        for(int j=0;j<firstArr.length;j++){
            if(!(firstArr[j]==secondArr[j]))return false;
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
        Integer increment = 0;
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
                new ArrayList<DayOfWeek>() {{
                    add(DayOfWeek.SUNDAY);
                }}));
        course.addSection(new Section(2, 13, 14.5,
                new ArrayList<DayOfWeek>() {{
                    add(DayOfWeek.TUESDAY);
                }}));
        courses.add(course);

        course = new Course("software architecture");
        course.addSection(new Section(1, 10, 11.5
                , new ArrayList<DayOfWeek>() {{
            add(DayOfWeek.SUNDAY);
            add(DayOfWeek.TUESDAY);
        }}));
        course.addSection(new Section(2, 11.5, 13
                , new ArrayList<DayOfWeek>() {{
            add(DayOfWeek.SUNDAY);
            add(DayOfWeek.TUESDAY);
        }}));
        course.addSection(new Section(3, 10, 11.5
                , new ArrayList<DayOfWeek>() {{
            add(DayOfWeek.MONDAY);
            add(DayOfWeek.WEDNESDAY);
        }}));
        courses.add(course);

        course = new Course("HCI");
        course.addSection(new Section(1, 8.5, 10
                , new ArrayList<DayOfWeek>() {{
            add(DayOfWeek.MONDAY);
        }}));
        course.addSection(new Section(2, 10, 11.5
                , new ArrayList<DayOfWeek>() {{
            add(DayOfWeek.MONDAY);
        }}));
        course.addSection(new Section(3, 11.5, 13
                , new ArrayList<DayOfWeek>() {{
            add(DayOfWeek.SUNDAY);
        }}));
        courses.add(course);
        course = new Course("software architecture");
        course.addSection(new Section(1, 10, 11.5
                , new ArrayList<DayOfWeek>() {{
            add(DayOfWeek.SUNDAY);
            add(DayOfWeek.TUESDAY);
        }}));
        course.addSection(new Section(2, 11.5, 13
                , new ArrayList<DayOfWeek>() {{
            add(DayOfWeek.SUNDAY);
            add(DayOfWeek.TUESDAY);
        }}));
        course.addSection(new Section(3, 10, 11.5
                , new ArrayList<DayOfWeek>() {{
            add(DayOfWeek.MONDAY);
            add(DayOfWeek.WEDNESDAY);
        }}));
        courses.add(course);

        course = new Course("HCI");
        course.addSection(new Section(1, 8.5, 10
                , new ArrayList<DayOfWeek>() {{
            add(DayOfWeek.MONDAY);
        }}));
        course.addSection(new Section(2, 10, 11.5
                , new ArrayList<DayOfWeek>() {{
            add(DayOfWeek.MONDAY);
        }}));
        course.addSection(new Section(3, 11.5, 13
                , new ArrayList<DayOfWeek>() {{
            add(DayOfWeek.SUNDAY);
        }}));
        courses.add(course);

        course = new Course("Client/server programming");
        course.addSection(new Section(1, 17, 18.5
                , new ArrayList<DayOfWeek>() {{
            add(DayOfWeek.MONDAY);
        }}));
        course.addSection(new Section(2, 18.5, 20
                , new ArrayList<DayOfWeek>() {{
            add(DayOfWeek.MONDAY);
        }}));
        courses.add(course);


        course = new Course("lab 2");
        course.addSection(new Section(1, 13.5, 15.5
                , new ArrayList<DayOfWeek>() {{
            add(DayOfWeek.SUNDAY);
        }}));
        course.addSection(new Section(2, 13.5, 15.5
                , new ArrayList<DayOfWeek>() {{
            add(DayOfWeek.MONDAY);
        }}));
        course.addSection(new Section(3, 10.5, 12.5
                , new ArrayList<DayOfWeek>() {{
            add(DayOfWeek.THURSDAY);
        }}));
        course.addSection(new Section(4, 13.5, 15.5
                , new ArrayList<DayOfWeek>() {{
            add(DayOfWeek.THURSDAY);
        }}));
        courses.add(course);


        course = new Course("Software Requirements");
        course.addSection(new Section(2, 11.5, 13
                , new ArrayList<DayOfWeek>() {{
            add(DayOfWeek.MONDAY);
        }}));
        course.addSection(new Section(3, 10, 11.5
                , new ArrayList<DayOfWeek>() {{
            add(DayOfWeek.WEDNESDAY);
        }}));
        course.addSection(new Section(4, 11.5, 13
                , new ArrayList<DayOfWeek>() {{
            add(DayOfWeek.WEDNESDAY);
        }}));
        course.addSection(new Section(4, 13.5, 15.5
                , new ArrayList<DayOfWeek>() {{
            add(DayOfWeek.THURSDAY);
        }}));
        courses.add(course);





    }
}
