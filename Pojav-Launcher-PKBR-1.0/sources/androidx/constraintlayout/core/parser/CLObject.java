package androidx.constraintlayout.core.parser;

import java.util.Iterator;
import org.apache.commons.p012io.IOUtils;

public class CLObject extends CLContainer implements Iterable<CLKey> {
    public CLObject(char[] content) {
        super(content);
    }

    public static CLObject allocate(char[] content) {
        return new CLObject(content);
    }

    public String toJSON() {
        StringBuilder json = new StringBuilder(getDebugName() + "{ ");
        boolean first = true;
        Iterator it = this.mElements.iterator();
        while (it.hasNext()) {
            CLElement element = (CLElement) it.next();
            if (!first) {
                json.append(", ");
            } else {
                first = false;
            }
            json.append(element.toJSON());
        }
        json.append(" }");
        return json.toString();
    }

    public String toFormattedJSON() {
        return toFormattedJSON(0, 0);
    }

    public String toFormattedJSON(int indent, int forceIndent) {
        StringBuilder json = new StringBuilder(getDebugName());
        json.append("{\n");
        boolean first = true;
        Iterator it = this.mElements.iterator();
        while (it.hasNext()) {
            CLElement element = (CLElement) it.next();
            if (!first) {
                json.append(",\n");
            } else {
                first = false;
            }
            json.append(element.toFormattedJSON(BASE_INDENT + indent, forceIndent - 1));
        }
        json.append(IOUtils.LINE_SEPARATOR_UNIX);
        addIndent(json, indent);
        json.append("}");
        return json.toString();
    }

    public Iterator iterator() {
        return new CLObjectIterator(this);
    }

    private class CLObjectIterator implements Iterator {
        int index = 0;
        CLObject myObject;

        public CLObjectIterator(CLObject clObject) {
            this.myObject = clObject;
        }

        public boolean hasNext() {
            return this.index < this.myObject.size();
        }

        public Object next() {
            CLKey key = (CLKey) this.myObject.mElements.get(this.index);
            this.index++;
            return key;
        }
    }
}
