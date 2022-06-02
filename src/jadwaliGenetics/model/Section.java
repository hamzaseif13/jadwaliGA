package jadwaliGenetics.model;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class Section {
    private int number;
    private double startTime;
    private double endTime;
    private List<DayOfWeek> days=new ArrayList<DayOfWeek>();;


    public Section(int sectionNumber,double startTime, double endTime,List<DayOfWeek> dayss) {
        this.startTime = startTime;
        this.endTime = endTime;
        dayss.forEach(day->{days.add(day);});
        this.number=sectionNumber;

    }

    public int getNumber() {
        return number;
    }

    public List<DayOfWeek> getDays() {
        return days;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public void setDay(List<DayOfWeek> day) {
        this.days = day;
    }

}
