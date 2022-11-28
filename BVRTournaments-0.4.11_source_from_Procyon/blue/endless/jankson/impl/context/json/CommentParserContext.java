// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.impl.context.json;

import blue.endless.jankson.api.SyntaxError;
import blue.endless.jankson.api.Jankson;
import blue.endless.jankson.impl.context.ParserContext;

public class CommentParserContext implements ParserContext<String>
{
    int firstChar;
    int secondChar;
    StringBuilder result;
    int prevChar;
    boolean startOfLine;
    boolean multiLine;
    boolean done;
    
    public CommentParserContext(final int codePoint) {
        this.firstChar = -1;
        this.secondChar = -1;
        this.result = new StringBuilder();
        this.prevChar = -1;
        this.startOfLine = true;
        this.multiLine = false;
        this.done = false;
        this.firstChar = codePoint;
    }
    
    @Override
    public boolean consume(final int codePoint, final Jankson loader) throws SyntaxError {
        if (this.done) {
            return false;
        }
        if (this.firstChar == -1) {
            if (codePoint != 47 && codePoint != 35) {
                throw new SyntaxError("Was expecting the start of a comment, but found '" + (char)codePoint + "' instead.");
            }
            this.firstChar = codePoint;
            if (this.firstChar == 35) {
                this.multiLine = false;
            }
            return true;
        }
        else if (this.secondChar == -1 && this.firstChar != 35) {
            if ((this.secondChar = codePoint) == 42) {
                return this.multiLine = true;
            }
            if (codePoint == 47) {
                this.multiLine = false;
                return true;
            }
            if (Character.isWhitespace(codePoint)) {
                throw new SyntaxError("Was expecting the start of a comment, but found whitespace instead.");
            }
            throw new SyntaxError("Was expecting the start of a comment, but found '" + (char)codePoint + "' instead.");
        }
        else if (this.multiLine) {
            if (codePoint != 10 && Character.isWhitespace(codePoint)) {
                if (this.startOfLine) {
                    return true;
                }
            }
            else if (codePoint == 10) {
                this.startOfLine = true;
            }
            else if (this.startOfLine) {
                this.startOfLine = false;
            }
            if (codePoint == 47 && this.prevChar == 42) {
                this.result.deleteCharAt(this.result.length() - 1);
                return this.done = true;
            }
            this.prevChar = codePoint;
            this.result.append((char)codePoint);
            return true;
        }
        else {
            if (codePoint == 10) {
                return this.done = true;
            }
            this.prevChar = codePoint;
            this.result.append((char)codePoint);
            return true;
        }
    }
    
    @Override
    public void eof() throws SyntaxError {
        if (this.multiLine) {
            throw new SyntaxError("Unexpected end-of-file while reading a multiline comment.");
        }
    }
    
    @Override
    public boolean isComplete() {
        return this.done;
    }
    
    @Override
    public String getResult() throws SyntaxError {
        return this.result.toString().trim();
    }
}
