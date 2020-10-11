package net.test.task.solver.model;

import lombok.Getter;
import org.ojalgo.optimisation.Variable;

import java.util.HashMap;
import java.util.Map;

@Getter
public class MissingCell {

    private final Map<Integer, Variable> possibleValues = new HashMap<>();
    private final int row;
    private final int column;

    public MissingCell(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
