package test.task.solver;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class IncorrectSudokuProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        Integer[][] unevenInput = new Integer[][] {
                { null,    1,    3,    8, null, null,    4, null       },
                { null,    2,    4,    6, null,    5, null, null, null },
                { null,    8,    7, null, null, null,    9,    3, null, null },
                {    4,    9, null,    3, null,    6, null, null, null },
                { null, null,    1, null, null, null,    5, null, null },
                { null, null, null,    7, null,    1, null,    9,    3 },
                { null,    6,    9, null, null, null,    7,    4, null },
                { null, null, null,    2, null,    7,    6,    8, null }
        };
        Integer[][] negativeElementInput = new Integer[][] {
                { null,    1,    3,    8, null, null,    4, null,    5 },
                { null,    2,   -1,    6, null,    5, null, null, null },
                { null,    8,    7, null, null, null,    9,    3, null },
                {    4,    9, null,    3, null,    6, null, null, null },
                { null, null,    1, null, null,    2,    5, null, null },
                { null, null, null,    7, null,    1, null,    9,    3 },
                { null,    1,    9, null, null, null,    7,    4, null },
                { null, null, null,    2, null,    7,    6,    8, null },
                {    1, null,    2, null, null,    8,    3,    5, null }
        };
        Integer[][] greaterThanNineElementInput = new Integer[][] {
                { null,    1,    3,    8, null, null,    4, null,    5 },
                { null,    2,   -1,    6, null,    5, null, null, null },
                { null,    8,    7, null, null, null,   10,    3, null },
                {    4,    9, null,    3, null,    6, null, null, null },
                { null, null,    1, null, null,    2,    5, null, null },
                { null, null, null,    7, null,    1, null,    9,    3 },
                { null,    1,    9, null, null, null,    7,    4, null },
                { null, null, null,    2, null,    7,    6,    8, null },
                {    1, null,    2, null, null,    8,    3,    5, null }
        };
        Integer[][] zeroElementInput = new Integer[][] {
                {    5,    1,    6,    8,    4,    9,    7,    3,    2 },
                {    3, null,    7,    6, null,    5, null, null, null },
                {    8, null,    9,    7, null, null, null,    6,    5 },
                {    1,    3,    5, null,    6, null,    9, null,    7 },
                {    4,    7,    0,    5,    9,    1, null, null,    6 },
                {    9,    6,    8,    3,    7, null, null,    5, null },
                {    2,    5,    3,    1,    8,    6, null,    7,    4 },
                {    6,    8,    4,    2, null,    7,    5, null, null },
                {    7,    9,    1, null,    5, null,    6, null,    8 }
        };

        return Stream.of(
                Arguments.of((Object) unevenInput),
                Arguments.of((Object) negativeElementInput),
                Arguments.of((Object) greaterThanNineElementInput),
                Arguments.of((Object) zeroElementInput)
        );
    }
}
