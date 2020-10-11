package test.task.solver;

import net.test.task.solver.LinearProgrammingBasedSolver;
import net.test.task.solver.Solver;
import net.test.task.solver.SudokuGameSolver;
import net.test.task.validator.CompositeValidator;
import net.test.task.validator.DimensionLengthValidator;
import net.test.task.validator.NumberRangeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LinearProgrammingBasedSolverTest {

    private Solver solver;

    @BeforeEach
    public void setup() {
        solver = new SudokuGameSolver(new LinearProgrammingBasedSolver(), new CompositeValidator.Builder()
                .addValidator(new DimensionLengthValidator())
                .addValidator(new NumberRangeValidator())
                .build());
    }

    @Test
    public void test() {
        Integer[][] input = new Integer[][] {
                { null,    1,    3,    8, null, null,    4, null, 5    },
                { null,    2,    4,    6, null,    5, null, null, null },
                { null,    8,    7, null, null, null,    9,    3, null },
                {    4,    9, null,    3, null,    6, null, null, null },
                { null, null,    1, null, null, null,    5, null, null },
                { null, null, null,    7, null,    1, null,    9,    3 },
                { null,    6,    9, null, null, null,    7,    4, null },
                { null, null, null,    2, null,    7,    6,    8, null },
                {    1, null,    2, null, null,    8,    3,    5, null }
        };
        solver.solve(input);
    }
}
