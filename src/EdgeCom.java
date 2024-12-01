import java.util.*;

public class EdgeCom implements Comparator<Edge> {
    public int compare(Edge u, Edge v) {
        return u.destination.compareTo(v.destination);
    }
}