package test.task.solver;

import net.test.task.solver.LinearProgrammingBasedSolver;
import net.test.task.solver.Solver;
import net.test.task.solver.SudokuGameSolver;
import net.test.task.validator.CompositeValidator;
import net.test.task.validator.DimensionEqualityValidator;
import net.test.task.validator.NumberRangeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SudokuGameSolverTest {

    private Solver solver;

    @BeforeEach
    public void setup() {
        final CompositeValidator validator = new CompositeValidator.Builder()
                .addValidator(new DimensionEqualityValidator())
                .addValidator(new NumberRangeValidator())
                .build();
        solver = new SudokuGameSolver(new LinearProgrammingBasedSolver(), validator);
    }

    @ParameterizedTest
    @ArgumentsSource(CorrectSudokuProvider.class)
    public void testGivenInputMatrixWhenSolveThenReturnCorrectResult(Integer[][] input, int[][] expectedOutput) {

        int[][] solution = solver.solve(input);

        assertEquals(expectedOutput.length, solution.length);
        for (int i = 0; i < expectedOutput.length; i++) {
            assertArrayEquals(expectedOutput[i], solution[i]);
        }
    }

    @ParameterizedTest
    @ArgumentsSource(IncorrectSudokuProvider.class)
    public void testGivenIncorrectInputWhenSolveThenValidationFails(Integer[][] incorrectInput) {
        assertThrows(IllegalArgumentException.class, () -> solver.solve(incorrectInput));
    }

    @Test
    public void testGivenAmbiguousInputWhenSolveThenSolutionExists() {
        Integer[][] ambiguousInput = new Integer[][] {
                { null,    8, null, null, null,    9,    7,    4,    3 },
                { null,    5, null, null, null,    8, null,    1, null },
                { null,    1, null, null, null, null, null, null, null },
                {    8, null, null, null, null,    5, null, null, null },
                { null, null, null,    8, null,    4, null, null, null },
                { null, null, null,    3, null, null, null, null,    6 },
                { null, null, null, null, null, null, null,    7, null },
                { null,    3, null,    5, null, null, null,    8, null },
                {    9,    7,    2,    4, null, null, null,    5, null }
        };

        int[][] solution = solver.solve(ambiguousInput);

        assertNotEquals(0, solution.length);
    }
}
