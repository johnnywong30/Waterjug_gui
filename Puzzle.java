import java.util.*;

public class Puzzle {
    int cap_a;
    int cap_b;
    int cap_c;
    int goal_a;
    int goal_b;
    int goal_c;
    State[][] visited;
    
    public Puzzle(int _cap_a, int _cap_b, int _cap_c, int _goal_a, int _goal_b, int _goal_c) {
        cap_a = _cap_a;
        cap_b = _cap_b;
        cap_c = _cap_c;
        goal_a = _goal_a;
        goal_b = _goal_b;
        goal_c = _goal_c;
        visited = new State[cap_a + 1][cap_b + 1];  // all initialized to null
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