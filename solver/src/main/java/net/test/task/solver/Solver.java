package net.test.task.solver;

import net.test.task.solver.model.Solution;

import java.util.List;

public interface Solver {

    List<Solution> solve(Integer[][] gameInput);
}
