// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.api;

public class SyntaxError extends Exception
{
    int startLine;
    int startColumn;
    int line;
    int column;
    
    public SyntaxError(final String message) {
        super(message);
        this.startLine = -1;
        this.startColumn = -1;
        this.line = -1;
        this.column = -1;
    }
    
    public SyntaxError(final String message, final int line, final int column) {
        super(message);
        this.startLine = -1;
        this.startColumn = -1;
        this.line = -1;
        this.column = -1;
        this.startLine = line;
        this.startColumn = column;
        this.line = line;
        this.column = column;
    }
    
    public String getCompleteMessage() {
        final StringBuilder message = new StringBuilder();
        if (this.startLine != -1 && this.startColumn != -1) {
            message.append("Started at line ");
            message.append(this.startLine + 1);
            message.append(", column ");
            message.append(this.startColumn + 1);
            message.append("; ");
        }
        if (this.line != -1 && this.column != -1) {
            message.append("Errored at line ");
            message.append(this.line + 1);
            message.append(", column ");
            message.append(this.column + 1);
            message.append("; ");
        }
        message.append(super.getMessage());
        return message.toString();
    }
    
    public String getLineMessage() {
        final StringBuilder message = new StringBuilder();
        final boolean hasStart = this.startLine != -1 && this.startColumn != -1;
        final boolean hasEnd = this.line != -1 && this.column != -1;
        if (hasStart) {
            message.append("Started at line ");
            message.append(this.startLine + 1);
            message.append(", column ");
            message.append(this.startColumn + 1);
        }
        if (hasStart && hasEnd) {
            message.append("; ");
        }
        if (hasEnd) {
            message.append("Errored at line ");
            message.append(this.line + 1);
            message.append(", column ");
            message.append(this.column + 1);
        }
        return message.toString();
    }
    
    public void setStartParsing(final int line, final int column) {
        this.startLine = line;
        this.startColumn = column;
    }
    
    public void setEndParsing(final int line, final int column) {
        this.line = line;
        this.column = column;
    }
}
