package jadwaliGenetics.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String name;
    private List<Section> sections;

    public Course(String name) {
        this.name = name;
        this.sections = new ArrayList<Section>();
    }
    public void addSection(Section section){
        this.sections.add(section);
    }
    public int sectionLength(){
        return sections.size();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}
