package net.andreinc.mockneat.unit.objects;

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

import net.andreinc.mockneat.Constants;
import net.andreinc.mockneat.types.enums.StringType;
import net.andreinc.mockneat.unit.objects.model.FactoryMethods;
import net.andreinc.mockneat.utils.LoopsUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FactoryTest {

    @Test(expected = NullPointerException.class)
    public void testFactoryNullClass() throws Exception {
        Constants.M.factory(null, String.class).method("some").params("abc").val();
    }

    @Test(expected = NullPointerException.class)
    public void testFactorNullFactoryClass() throws Exception {
        Constants.M.factory(String.class, null).method("some").params("abc").val();
    }

    @Test(expected = NullPointerException.class)
    public void testFactoryForgetMethod() throws Exception {
        Constants.M.factory(StringBuilder.class, FactoryMethods.class)
                .params("ABC")
                .val();
    }

    @Test(expected = NullPointerException.class)
    public void testFactoryNullMethod() throws Exception {
        Constants.M.factory(StringBuilder.class, FactoryMethods.class)
                .method(null)
                .params("ABC")
                .val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFactoryForgetParams() throws Exception {
        Constants.M.factory(StringBuilder.class, FactoryMethods.class)
                .method("buffBuilder")
                .val();
    }

    @Test(expected = NullPointerException.class)
    public void testFactoryNullParams() throws Exception {
        Object[] params = null;
        Constants.M.factory(StringBuilder.class, FactoryMethods.class)
                .method("buffBuilder")
                .params(params)
                .val();
    }

    @Test(expected = ClassCastException.class)
    public void testFactoryBuilderClassCastException() throws Exception {
        String val = Constants.M.factory(String.class, FactoryMethods.class)
                      .method("buffBuilder")
                      .params("ABC")
                      .val();
        System.out.println(val);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFactoryBuilderInvalidMethod() throws Exception {
        Constants.M.factory(StringBuilder.class, FactoryMethods.class)
                      .method("xxx")
                      .params("ABC")
                      .val();
    }

    @Test
    public void testFactoryBuilderConstantParam() throws Exception {
        StringBuilder buff = Constants.M.factory(StringBuilder.class, FactoryMethods.class)
                                .method("buffBuilder")
                                .params("ABC")
                                .val();
        assertTrue(buff.toString().equals("ABC"));
    }

    @Test
    public void testFactoryBuilderMockParam() throws Exception {
        LoopsUtils.loop(
                Constants.OBJS_CYCLES,
                Constants.MOCKS,
                m -> m.factory(StringBuilder.class, FactoryMethods.class)
                        .method("buffBuilder")
                        .params(Constants.M.strings().size(10).type(StringType.ALPHA_NUMERIC))
                        .val(),
                s -> {
                    assertTrue(s instanceof StringBuilder);
                    assertTrue(s.length() == 10);
                    assertTrue(StringUtils.isAlphanumeric(s.toString()));
                }
        );
    }
}
