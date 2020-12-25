public class State {
    private int a;
    private int b;
    private int c;
    private String directions;
    private State parent;

    public State(int _a, int _b, int _c, String _directions) {
        a = _a;
        b = _b; 
        c = _c;
        directions = _directions;
        parent = null;
    }

    public State(int _a, int _b, int _c, String _directions, State _parent) {
        a = _a;
        b = _b; 
        c = _c;
        directions = _directions;
        parent = _parent;
    }

    int[] getVals() {
        int[] vals = new int[3];
        vals[0] = this.a;
        vals[1] = this.b;
        vals[2] = this.c;
        return vals;
    }

    boolean equals(State s) {
        int[] vals = s.getVals();
        return a == vals[0] && b == vals[1] && c == vals[2];
    }

    State getParent() {
        return this.parent;
    }

    String getDirections() {
        return this.directions;
    }

    String to_String() {
        StringBuilder state = new StringBuilder();
        state.append("(");
        state.append(this.a);
        state.append(",");
        state.append(this.b);
        state.append(",");
        state.append(this.c);
        state.append(")");
        return state.toString();
    }

    public static void main(String[] args) {

    }

}