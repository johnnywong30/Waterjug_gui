import java.util.*;

public class Puzzle {
    int[] caps;
    int goal_a;
    int goal_b;
    int goal_c;
    State[][] visited;
    
    public Puzzle(int _cap_a, int _cap_b, int _cap_c, int _goal_a, int _goal_b, int _goal_c) {
        caps = new int[3];
        caps[0] = _cap_a;
        caps[1] = _cap_b;
        caps[2] = _cap_c;
        goal_a = _goal_a;
        goal_b = _goal_b;
        goal_c = _goal_c;
        visited = new State[_cap_a + 1][_cap_b + 1];  // all initialized to null
    }

    State doPour(State currState, int[] current_jugs, int from, int to) {
        String[] letters = {"A", "B", "C"};
        int[] newJugs = {current_jugs[0], current_jugs[1], current_jugs[2]};
        int space = caps[to] - current_jugs[to];
        int pour = current_jugs[from] <= space ? current_jugs[from] : space;
        newJugs[to] += pour;
        newJugs[from] -= pour;
        StringBuilder builder = new StringBuilder();
        builder.append("Pour " + pour);
        builder.append((pour > 1 ? " gallons " : " gallon "));
        builder.append("from " + letters[from] + " to " + letters[to] + ".");
        return new State(newJugs[0], newJugs[1], newJugs[2], builder.toString(), currState);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(visited[0][0] == null);
        return string.toString();
    }
    

    public static void main(String[] args) {
        Puzzle solve = new Puzzle(3, 5, 8, 0, 4, 4);
        System.out.println(solve.toString());
        
    }

}