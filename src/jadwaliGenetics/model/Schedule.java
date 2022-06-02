package jadwaliGenetics.model;

import fr.dieul.lab.geneticalgorithm.model.Individual;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

//Individual class
public class Schedule implements Cloneable,Comparable<Schedule> {
    private int sectionsSize;
    private int fitness ;
    private int[] sections;

    public int[] getSections() {
        return sections;
    }

    public int getFitness() {
        return fitness;
    }

    private List<Course> courses;

    public Schedule(List<Course> courses) {
        this.courses = courses;
        fitness=courses.size();
        Random random = new Random();
        sectionsSize = courses.size();
        sections = new int[sectionsSize];
        //giving a random sections to a schedule
        for (int j = 0; j < courses.size(); j++) {
            sections[j] = random.nextInt(1, courses.get(j).getSections().size() + 1);
        }
    }

    private boolean checkConflicts(Section first, Section second) {
        for (DayOfWeek day : first.getDays()) {
            if (second.getDays().contains(day)) {
                if (first.getStartTime() == second.getStartTime()) return true;
                else if (first.getStartTime() < second.getStartTime()) {
                    if (first.getEndTime() > second.getStartTime()) return true;
                    else return false;
                } else {
                    if (second.getEndTime() > first.getStartTime()) return true;
                    else return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    public void calculateFitness() {
        for (int j = 0; j < sectionsSize - 1; j++) {
            for (int i = j + 1; i < sectionsSize; i++) {
                //checking conflicts between a schedule sections
                if (checkConflicts(
                        courses.get(j).getSections().get(sections[j] - 1),
                        courses.get(i).getSections().get(sections[i] - 1)
                )) {
                    fitness--;
                }
            }
        }

    }

    @Override
    public String toString() {
        return "fitness : " + fitness + " | [sections=" + Arrays.toString(sections) + "]";
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Schedule schedule = (Schedule) super.clone();
        schedule.sections = new int[sectionsSize];
        for (int i = 0; i < schedule.sections.length; i++) {
            schedule.sections[i] = this.sections[i];
        }
        return schedule;
    }

    @Override
    public int compareTo(Schedule o) {
        int compareage
                = ((Schedule)o).getFitness();

        //  For Ascending order
        return compareage - this.getFitness() ;

    }
}