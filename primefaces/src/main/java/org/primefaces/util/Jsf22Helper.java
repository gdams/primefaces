/*
 * The MIT License
 *
 * Copyright (c) 2009-2022 PrimeTek
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.primefaces.util;

import java.io.IOException;
import java.util.Map;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

// helper to avoid java.lang.NoClassDefFoundError's in older environments
public class Jsf22Helper {

    private Jsf22Helper() {
    }

    public static void renderPassThroughAttributes(FacesContext context, UIComponent component) throws IOException {
        Map<String, Object> passthroughAttributes = component.getPassThroughAttributes(false);

        if (passthroughAttributes != null && !passthroughAttributes.isEmpty()) {
            ResponseWriter writer = context.getResponseWriter();

            for (Map.Entry<String, Object> attribute : passthroughAttributes.entrySet()) {
                Object attributeValue = attribute.getValue();
                if (attributeValue != null) {
                    String value = null;

                    if (attributeValue instanceof ValueExpression) {
                        Object expressionValue = ((ValueExpression) attributeValue).getValue(context.getELContext());
                        if (expressionValue != null) {
                            value = expressionValue.toString();
                        }
                    }
                    else {
                        value = attributeValue.toString();
                    }

                    if (value != null) {
                        writer.writeAttribute(attribute.getKey(), value, null);
                    }
                }
            }
        }
    }
}
