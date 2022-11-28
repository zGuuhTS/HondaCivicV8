// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.impl.serializer;

import java.io.IOException;
import blue.endless.jankson.api.JsonGrammar;
import java.io.Writer;

public class CommentSerializer
{
    public static void print(final Writer writer, final String comment, final int indent, final JsonGrammar grammar) throws IOException {
        if (comment == null || comment.trim().isEmpty()) {
            return;
        }
        final StringBuilder b = new StringBuilder(comment.length());
        print(b, comment, indent, grammar);
        writer.append((CharSequence)b);
    }
    
    public static void print(final StringBuilder builder, final String comment, final int indent, final JsonGrammar grammar) {
        final boolean comments = grammar.hasComments();
        final boolean whitespace = grammar.shouldOutputWhitespace();
        print(builder, comment, indent, comments, whitespace);
    }
    
    public static void print(final StringBuilder builder, final String comment, final int indent, final boolean comments, final boolean whitespace) {
        if (!comments) {
            return;
        }
        if (comment == null || comment.trim().isEmpty()) {
            return;
        }
        if (whitespace) {
            if (comment.contains("\n")) {
                builder.append("/* ");
                final String[] lines = comment.split("\\n");
                for (int i = 0; i < lines.length; ++i) {
                    final String line = lines[i];
                    if (i != 0) {
                        builder.append("   ");
                    }
                    builder.append(line);
                    builder.append('\n');
                    for (int j = 0; j < indent + 1; ++j) {
                        builder.append('\t');
                    }
                }
                builder.append("*/\n");
                for (int i = 0; i < indent + 1; ++i) {
                    builder.append('\t');
                }
            }
            else {
                builder.append("// ");
                builder.append(comment);
                builder.append('\n');
                for (int k = 0; k < indent + 1; ++k) {
                    builder.append('\t');
                }
            }
        }
        else if (comment.contains("\n")) {
            final String[] lines = comment.split("\\n");
            for (int i = 0; i < lines.length; ++i) {
                final String line = lines[i];
                builder.append("/* ");
                builder.append(line);
                builder.append(" */ ");
            }
        }
        else {
            builder.append("/* ");
            builder.append(comment);
            builder.append(" */ ");
        }
    }
}
