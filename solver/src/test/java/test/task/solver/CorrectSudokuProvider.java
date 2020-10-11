package test.task.solver;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class CorrectSudokuProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        Integer[][] easyInput = new Integer[][] {
                { null,    1,    3,    8, null, null,    4, null,    5 },
                { null,    2,    4,    6, null,    5, null, null, null },
                { null,    8,    7, null, null, null,    9,    3, null },
                {    4,    9, null,    3, null,    6, null, null, null },
                { null, null,    1, null, null, null,    5, null, null },
                { null, null, null,    7, null,    1, null,    9,    3 },
                { null,    6,    9, null, null, null,    7,    4, null },
                { null, null, null,    2, null,    7,    6,    8, null },
                {    1, null,    2, null, null,    8,    3,    5, null }
        };
        int[][] easyOutput = new int[][] {
                {    6,    1,    3,    8,    7,    9,    4,    2,    5 },
                {    9,    2,    4,    6,    3,    5,    1,    7,    8 },
                {    5,    8,    7,    1,    2,    4,    9,    3,    6 },
                {    4,    9,    8,    3,    5,    6,    2,    1,    7 },
                {    7,    3,    1,    9,    8,    2,    5,    6,    4 },
                {    2,    5,    6,    7,    4,    1,    8,    9,    3 },
                {    8,    6,    9,    5,    1,    3,    7,    4,    2 },
                {    3,    4,    5,    2,    9,    7,    6,    8,    1 },
                {    1,    7,    2,    4,    6,    8,    3,    5,    9 }
        };
        Integer[][] difficultInput = new Integer[][] {
                { null, null,    2, null, null, null, null,    4,    1 },
                { null, null, null, null,    8,    2, null,    7, null },
                { null, null, null, null,    4, null, null, null,    9 },
                {    2, null, null, null,    7,    9,    3, null, null },
                { null,    1, null, null, null, null, null,    8, null },
                { null, null,    6,    8,    1, null, null, null,    4 },
                {    1, null, null, null,    9, null, null, null, null },
                { null,    6, null,    4,    3, null, null, null, null },
                {    8,    5, null, null, null, null,    4, null, null }
        };
        int[][] difficultOutput = new int[][] {
                {    6,    3,    2,    9,    5,    7,    8,    4,    1 },
                {    4,    9,    1,    6,    8,    2,    5,    7,    3 },
                {    7,    8,    5,    3,    4,    1,    2,    6,    9 },
                {    2,    4,    8,    5,    7,    9,    3,    1,    6 },
                {    3,    1,    9,    2,    6,    4,    7,    8,    5 },
                {    5,    7,    6,    8,    1,    3,    9,    2,    4 },
                {    1,    2,    4,    7,    9,    5,    6,    3,    8 },
                {    9,    6,    7,    4,    3,    8,    1,    5,    2 },
                {    8,    5,    3,    1,    2,    6,    4,    9,    7 }
        };
        Integer[][] unsolvableInput = new Integer[][] {
                {    5,    1,    6,    8,    4,    9,    7,    3,    2 },
                {    3, null,    7,    6, null,    5, null, null, null },
                {    8, null,    9,    7, null, null, null,    6,    5 },
                {    1,    3,    5, null,    6, null,    9, null,    7 },
                {    4,    7,    2,    5,    9,    1, null, null,    6 },
                {    9,    6,    8,    3,    7, null, null,    5, null },
                {    2,    5,    3,    1,    8,    6, null,    7,    4 },
                {    6,    8,    4,    2, null,    7,    5, null, null },
                {    7,    9,    1, null,    5, null,    6, null,    8 }
        };
        int[][] unsolvableOutput = new int[][] {};


        return Stream.of(
                Arguments.of(easyInput, easyOutput),
                Arguments.of(difficultInput, difficultOutput),
                Arguments.of(unsolvableInput, unsolvableOutput)
        );
    }
}
