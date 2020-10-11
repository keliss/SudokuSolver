package net.test.task.solver.model;

import lombok.Data;

@Data
public class CellSolution {

    private final int value;
    private final MissingCell cell;
}
