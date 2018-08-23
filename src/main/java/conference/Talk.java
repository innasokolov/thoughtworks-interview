package conference;

/**
 * Created by innasokolov on 6/29/16.
 */
public class Talk {
    private String name;
    private int durationInMinutes;

    public Talk(String name, int durationInMinutes) {
        super();
        this.name = name;
        this.durationInMinutes = durationInMinutes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Talk)) return false;

        Talk talk = (Talk) o;

        if (durationInMinutes != talk.durationInMinutes) return false;
        return !(name != null ? !name.equals(talk.name) : talk.name != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + durationInMinutes;
        return result;
    }

    @Override
    public String toString() {
        return "Talk{" +
                "name='" + name + '\'' +
                ", durationInMinutes=" + durationInMinutes +
                '}';
    }
}

