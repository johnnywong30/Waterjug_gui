import java.util.*;

public class Puzzle {
    private int[] caps;
    private int goal_a;
    private int goal_b;
    private int goal_c;
    private State[][] visited;
    
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

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(visited[0][0] == null);
        return string.toString();
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

    void getPossibleStates(State current, Queue<State> q) {
        int[] curr_jugs = current.getVals();
        // pour from C -> A
        // C has to have water and A has to have room
        if (curr_jugs[2] > 0 && curr_jugs[0] < caps[0]) {   
            State move = doPour(current, curr_jugs, 2, 0);
            int[] newJugs = move.getVals();
            if (visited[newJugs[0]][newJugs[1]] == null) {
                visited[newJugs[0]][newJugs[1]] = move;
                q.add(move);
            }
        }
        // pour from B -> A
        // B has to have water and A has to have room
        if (curr_jugs[1] > 0 && curr_jugs[0] < caps[0]) {   
            State move = doPour(current, curr_jugs, 1, 0);
            int[] newJugs = move.getVals();
            if (visited[newJugs[0]][newJugs[1]] == null) {
                visited[newJugs[0]][newJugs[1]] = move;
                q.add(move);
            }
        }
        // pour from C -> B
        // C has to have water and B has to have room
        if (curr_jugs[2] > 0 && curr_jugs[1] < caps[1]) {   
            State move = doPour(current, curr_jugs, 2, 1);
            int[] newJugs = move.getVals();
            if (visited[newJugs[0]][newJugs[1]] == null) {
                visited[newJugs[0]][newJugs[1]] = move;
                q.add(move);
            }
        }
        // pour from A -> B
        // A has to have water and B has to have room
        if (curr_jugs[0] > 0 && curr_jugs[1] < caps[1]) {   
            State move = doPour(current, curr_jugs, 0, 1);
            int[] newJugs = move.getVals();
            if (visited[newJugs[0]][newJugs[1]] == null) {
                visited[newJugs[0]][newJugs[1]] = move;
                q.add(move);
            }
        }
        // pour from B -> C
        // B has to have water and C has to have room
        if (curr_jugs[1] > 0 && curr_jugs[2] < caps[2]) {   
            State move = doPour(current, curr_jugs, 1, 2);
            int[] newJugs = move.getVals();
            if (visited[newJugs[0]][newJugs[1]] == null) {
                visited[newJugs[0]][newJugs[1]] = move;
                q.add(move);
            }
        }
        // pour from A -> C
        // A has to have water and C has to have room
        if (curr_jugs[0] > 0 && curr_jugs[2] < caps[2]) {   
            State move = doPour(current, curr_jugs, 0, 2);
            int[] newJugs = move.getVals();
            if (visited[newJugs[0]][newJugs[1]] == null) {
                visited[newJugs[0]][newJugs[1]] = move;
                q.add(move);
            }
        }
    }

    String getPath(State path) {
        String result = path.getDirections() + " " + path.to_String();
        path = path.getParent();
        while (path != null) {
            result = path.getDirections() + " " + path.to_String() + "\n" + result;
            path = path.getParent();
        }
        return result;
    }

    void getSolution() {
        State initial = new State(0, 0, caps[2], "Initial state.");
        State goal = new State(goal_a, goal_b, goal_c, "Goal State");
        Queue<State> q = new LinkedList<>();
        q.add(initial);
        State path = null;
        boolean found = false;
        while (! q.isEmpty()) {
            State front = q.remove();
            if (front.equals(goal)) {
                found = true;
                path = front;
                break;
            }
            getPossibleStates(front, q);
        }
        if (! found) {
            System.out.println("No solution.\n");
        } else {
            System.out.println(getPath(path));
        }
    }

    public static void main(String[] args) {
        Puzzle solve = new Puzzle(4, 17, 22, 2, 5, 15); // no solution
        Puzzle solver = new Puzzle(8, 17, 20, 0, 10, 10); // there is!
        solver.getSolution();
        
    }

}