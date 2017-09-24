import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GAKnapsack {
    public double[] weight;
    public double[] value;
    public double weight_limit;
    public double total_value;
    ArrayList<GAKnapsackSolution> solution_list;

    public GAKnapsack(double[] weight, double[] value, double weight_limit) {
        this.weight = weight;
        this.value = value;
        this.weight_limit = weight_limit;
        double total_value = 0;
        for(int i = 0; i < value.length; i++) {
            total_value += value[i];
        }
        this.total_value = total_value;
        solution_list = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            solution_list.add(new GAKnapsackSolution(gen_picks(), this));
        }
    }

    public void start_roll(int gen, int gen_limit) {
        Collections.sort(solution_list);
        GAKnapsackSolution offspring = new GAKnapsackSolution(gen_offspring(solution_list.get(0).pick, solution_list.get(1).pick), this);
        solution_list.add(offspring);
        Collections.sort(solution_list);
        solution_list.remove(solution_list.size() - 1);

        if(gen == gen_limit) {
            if(is_acceptable(solution_list)) {
                return;
            }
            gen_limit *= 2;
        }
        start_roll(gen + 1, gen_limit);
        return;
    }

    public boolean is_acceptable(ArrayList<GAKnapsackSolution> solution_list) {
        Collections.sort(solution_list);
        return Math.abs(solution_list.get(0).getFitness() - (this.weight_limit + this.total_value)) < (this.weight_limit + this.total_value) / 0.05;
    }

    private boolean[] gen_offspring(boolean[] father, boolean[] mother) {
        Random pick_gen = new Random();
        Random mut_enable = new Random();
        boolean[] offspring = new boolean[weight.length];
        for(int i = 0; i < offspring.length; i++) {
            if(pick_gen.nextBoolean()) {
                offspring[i] = father[i];
            } else {
                offspring[i] = mother[i];
            }
        }

        if(mut_enable.nextInt(19) == 10) {
            int mut = 0;
            for(int i = 0; i < mut_enable.nextInt(weight.length); i++) {
                mut = mut_enable.nextInt(weight.length) - 1;
                offspring[mut] = !offspring[mut];
            }
        }
        return offspring;
    }

    private boolean[] gen_picks() {
        Random pick_gen = new Random();
        boolean[] pick = new boolean[weight.length];
        for(int i = 0; i < pick.length; i++) {
            pick[i] = pick_gen.nextBoolean();
        }
        return pick;
    }

    public static void main(String[] args) {
        GAKnapsack gaKnapsack = new GAKnapsack(new double[] {2, 3, 4, 5, 9}, new double[] {3, 4, 5, 8, 10}, 20);
        gaKnapsack.start_roll(1, 2);
        System.out.println("Weight : " + gaKnapsack.solution_list.get(0).get_weight() + " Value : " + gaKnapsack.solution_list.get(0).get_value());
    }
}
