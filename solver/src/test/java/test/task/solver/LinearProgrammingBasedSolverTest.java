package test.task.solver;

import net.test.task.solver.LinearProgrammingBasedSolver;
import net.test.task.solver.Solver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LinearProgrammingBasedSolverTest {

    private Solver solver;

    @BeforeEach
    public void setup() {
        solver = new LinearProgrammingBasedSolver();
    }


}
