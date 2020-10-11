package net.test.task.solver.model;

import lombok.Data;
import org.ojalgo.optimisation.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Solution {

    private final Map<Integer, Variable> variables = new HashMap<>();
    private final int row;
    private final int column;
}
