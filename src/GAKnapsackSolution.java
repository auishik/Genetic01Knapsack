public class GAKnapsackSolution implements Comparable {
    public boolean[] pick;
    public GAKnapsack problem;

    public GAKnapsackSolution(boolean[] pick, GAKnapsack problem) {
        this.pick = pick;
        this.problem = problem;
    }

    public double getFitness() {
        double total_weight = 0;
        double total_value = 0;
        for(int i = 0; i < pick.length; i++) {
            if(pick[i]) {
                total_weight += problem.weight[i];
                total_value += problem.value[i];
            }
        }

        double multiplier = 1;
        if(problem.weight_limit - total_weight < 0) {
            multiplier = -1;
        }
        double weight_diff = Math.abs(total_weight - problem.weight_limit);
        if(((int)weight_diff) == 0) {
            weight_diff = 1;
        }

        double value_diff = Math.abs(total_value - problem.total_value);
        if(((int)value_diff) == 0) {
            value_diff = 1;
        }
        return multiplier * (problem.weight_limit / weight_diff + problem.total_value / value_diff);
    }

    @Override
    public int compareTo(Object o) {
        return (int)(100 * (((GAKnapsackSolution)o).getFitness() - this.getFitness()));
    }

    public double get_weight() {
        double total_weight = 0;
        for (int i = 0; i < pick.length; i++) {
            if (pick[i]) {
                total_weight += problem.weight[i];
            }
        }
        return total_weight;
    }

    public double get_value() {
        double total_value = 0;
        for (int i = 0; i < pick.length; i++) {
            if (pick[i]) {
                total_value += problem.value[i];
            }
        }
        return total_value;
    }
}
