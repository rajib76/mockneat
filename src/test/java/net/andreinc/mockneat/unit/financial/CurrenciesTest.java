package net.andreinc.mockneat.unit.financial;

/**
 * Copyright 2017, Andrei N. Ciobanu

 Permission is hereby granted, free of charge, to any user obtaining a copy of this software and associated
 documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 persons to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 OTHERWISE, ARISING FROM, FREE_TEXT OF OR PARAM CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS PARAM THE SOFTWARE.
 */

import net.andreinc.mockneat.types.enums.CurrencySymbolType;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static net.andreinc.mockneat.Constants.CURRENCIES_CYCLES;
import static net.andreinc.mockneat.Constants.MOCKS;
import static net.andreinc.mockneat.utils.LoopsUtils.loop;
import static org.junit.Assert.assertTrue;

public class CurrenciesTest {
    private static final Set<String> CODES = new HashSet<>();
    private static final Set<String> SYMBOLS = new HashSet<>();
    private static final Set<String> NAMES = new HashSet<>();

    static {
        Arrays.stream(CurrencySymbolType.values()).forEach(v -> {
            CODES.add(v.getCode());
            SYMBOLS.add(v.getSymbol());
            NAMES.add(v.getName());
        });
    }

    @Test
    public void testCodes() throws Exception {
        loop(CURRENCIES_CYCLES, MOCKS, r -> r.currencies().code().val(), c -> assertTrue(CODES.contains(c)));
    }

    @Test
    public void testSymbol() throws Exception {
        loop(CURRENCIES_CYCLES, MOCKS, r -> r.currencies().symbol().val(), c -> assertTrue(SYMBOLS.contains(c)));
    }

    @Test
    public void testName() throws Exception {
        loop(CURRENCIES_CYCLES, MOCKS, r -> r.currencies().name().val(), c -> assertTrue(NAMES.contains(c)));
    }

    @Test
    public void testForexPair() throws Exception {
        loop(CURRENCIES_CYCLES, MOCKS, r -> r.currencies().forexPair().val(), c -> {
            String[] pairs = c.split("/");
            assertTrue(CODES.contains(pairs[0]));
            assertTrue(CODES.contains(pairs[1]));
        });
    }
}