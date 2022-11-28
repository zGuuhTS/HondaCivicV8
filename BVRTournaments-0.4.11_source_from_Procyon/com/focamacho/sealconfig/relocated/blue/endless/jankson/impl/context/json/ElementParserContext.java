// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.context.json;

import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element.JsonNull;
import java.util.Locale;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element.JsonPrimitive;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element.JsonElement;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.SyntaxError;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.Jankson;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.AnnotatedElement;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.context.ParserContext;

public class ElementParserContext implements ParserContext<AnnotatedElement>
{
    String comment;
    AnnotatedElement result;
    boolean childActive;
    
    public ElementParserContext() {
        this.comment = null;
        this.result = null;
        this.childActive = false;
    }
    
    @Override
    public boolean consume(final int codePoint, final Jankson loader) throws SyntaxError {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokestatic    java/lang/Character.isWhitespace:(I)Z
        //     4: ifeq            9
        //     7: iconst_1       
        //     8: ireturn        
        //     9: iload_1         /* codePoint */
        //    10: lookupswitch {
        //               34: 104
        //               35: 84
        //               39: 104
        //               47: 84
        //               91: 154
        //               93: 216
        //              123: 129
        //              125: 178
        //          default: 234
        //        }
        //    84: aload_2         /* loader */
        //    85: new             Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/CommentParserContext;
        //    88: dup            
        //    89: iload_1         /* codePoint */
        //    90: invokespecial   com/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/CommentParserContext.<init>:(I)V
        //    93: aload_0         /* this */
        //    94: invokedynamic   BootstrapMethod #0, accept:(Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/ElementParserContext;)Ljava/util/function/Consumer;
        //    99: invokevirtual   com/focamacho/sealconfig/relocated/blue/endless/jankson/api/Jankson.push:(Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/ParserContext;Ljava/util/function/Consumer;)V
        //   102: iconst_1       
        //   103: ireturn        
        //   104: aload_2         /* loader */
        //   105: new             Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/StringParserContext;
        //   108: dup            
        //   109: iload_1         /* codePoint */
        //   110: invokespecial   com/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/StringParserContext.<init>:(I)V
        //   113: aload_0         /* this */
        //   114: invokedynamic   BootstrapMethod #1, accept:(Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/ElementParserContext;)Ljava/util/function/Consumer;
        //   119: invokevirtual   com/focamacho/sealconfig/relocated/blue/endless/jankson/api/Jankson.push:(Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/ParserContext;Ljava/util/function/Consumer;)V
        //   122: aload_0         /* this */
        //   123: iconst_1       
        //   124: putfield        com/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/ElementParserContext.childActive:Z
        //   127: iconst_1       
        //   128: ireturn        
        //   129: aload_2         /* loader */
        //   130: new             Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/ObjectParserContext;
        //   133: dup            
        //   134: iconst_0       
        //   135: invokespecial   com/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/ObjectParserContext.<init>:(Z)V
        //   138: aload_0         /* this */
        //   139: invokedynamic   BootstrapMethod #2, accept:(Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/ElementParserContext;)Ljava/util/function/Consumer;
        //   144: invokevirtual   com/focamacho/sealconfig/relocated/blue/endless/jankson/api/Jankson.push:(Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/ParserContext;Ljava/util/function/Consumer;)V
        //   147: aload_0         /* this */
        //   148: iconst_1       
        //   149: putfield        com/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/ElementParserContext.childActive:Z
        //   152: iconst_0       
        //   153: ireturn        
        //   154: aload_2         /* loader */
        //   155: new             Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/ArrayParserContext;
        //   158: dup            
        //   159: invokespecial   com/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/ArrayParserContext.<init>:()V
        //   162: aload_0         /* this */
        //   163: invokedynamic   BootstrapMethod #3, accept:(Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/ElementParserContext;)Ljava/util/function/Consumer;
        //   168: invokevirtual   com/focamacho/sealconfig/relocated/blue/endless/jankson/api/Jankson.push:(Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/ParserContext;Ljava/util/function/Consumer;)V
        //   171: aload_0         /* this */
        //   172: iconst_1       
        //   173: putfield        com/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/ElementParserContext.childActive:Z
        //   176: iconst_1       
        //   177: ireturn        
        //   178: aload_2         /* loader */
        //   179: new             Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/api/SyntaxError;
        //   182: dup            
        //   183: new             Ljava/lang/StringBuilder;
        //   186: dup            
        //   187: invokespecial   java/lang/StringBuilder.<init>:()V
        //   190: ldc             "Found '"
        //   192: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   195: iload_1         /* codePoint */
        //   196: i2c            
        //   197: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   200: ldc             "' while parsing an element - this shouldn't happen!"
        //   202: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   205: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   208: invokespecial   com/focamacho/sealconfig/relocated/blue/endless/jankson/api/SyntaxError.<init>:(Ljava/lang/String;)V
        //   211: invokevirtual   com/focamacho/sealconfig/relocated/blue/endless/jankson/api/Jankson.throwDelayed:(Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/api/SyntaxError;)V
        //   214: iconst_0       
        //   215: ireturn        
        //   216: aload_0         /* this */
        //   217: new             Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/impl/AnnotatedElement;
        //   220: dup            
        //   221: aconst_null    
        //   222: aload_0         /* this */
        //   223: getfield        com/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/ElementParserContext.comment:Ljava/lang/String;
        //   226: invokespecial   com/focamacho/sealconfig/relocated/blue/endless/jankson/impl/AnnotatedElement.<init>:(Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/api/element/JsonElement;Ljava/lang/String;)V
        //   229: putfield        com/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/ElementParserContext.result:Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/impl/AnnotatedElement;
        //   232: iconst_0       
        //   233: ireturn        
        //   234: iload_1         /* codePoint */
        //   235: invokestatic    java/lang/Character.isDigit:(I)Z
        //   238: ifne            259
        //   241: iload_1         /* codePoint */
        //   242: bipush          45
        //   244: if_icmpeq       259
        //   247: iload_1         /* codePoint */
        //   248: bipush          43
        //   250: if_icmpeq       259
        //   253: iload_1         /* codePoint */
        //   254: bipush          46
        //   256: if_icmpne       284
        //   259: aload_2         /* loader */
        //   260: new             Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/NumberParserContext;
        //   263: dup            
        //   264: iload_1         /* codePoint */
        //   265: invokespecial   com/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/NumberParserContext.<init>:(I)V
        //   268: aload_0         /* this */
        //   269: invokedynamic   BootstrapMethod #1, accept:(Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/ElementParserContext;)Ljava/util/function/Consumer;
        //   274: invokevirtual   com/focamacho/sealconfig/relocated/blue/endless/jankson/api/Jankson.push:(Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/ParserContext;Ljava/util/function/Consumer;)V
        //   277: aload_0         /* this */
        //   278: iconst_1       
        //   279: putfield        com/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/ElementParserContext.childActive:Z
        //   282: iconst_1       
        //   283: ireturn        
        //   284: aload_2         /* loader */
        //   285: new             Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/TokenParserContext;
        //   288: dup            
        //   289: iload_1         /* codePoint */
        //   290: invokespecial   com/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/TokenParserContext.<init>:(I)V
        //   293: aload_0         /* this */
        //   294: invokedynamic   BootstrapMethod #4, accept:(Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/ElementParserContext;)Ljava/util/function/Consumer;
        //   299: invokevirtual   com/focamacho/sealconfig/relocated/blue/endless/jankson/api/Jankson.push:(Lcom/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/ParserContext;Ljava/util/function/Consumer;)V
        //   302: aload_0         /* this */
        //   303: iconst_1       
        //   304: putfield        com/focamacho/sealconfig/relocated/blue/endless/jankson/impl/context/json/ElementParserContext.childActive:Z
        //   307: iconst_1       
        //   308: ireturn        
        //    Exceptions:
        //  throws com.focamacho.sealconfig.relocated.blue.endless.jankson.api.SyntaxError
        //    StackMapTable: 00 0A 09 FB 00 4A 13 18 18 17 25 11 18 18
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.generateNameForVariable(NameVariables.java:264)
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.assignNamesToVariables(NameVariables.java:198)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:276)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void setResult(final JsonElement elem) {
        this.result = new AnnotatedElement(elem, this.comment);
    }
    
    @Override
    public void eof() throws SyntaxError {
        if (!this.childActive) {
            throw new SyntaxError("Unexpected end-of-file while looking for a json element!");
        }
    }
    
    @Override
    public boolean isComplete() {
        return this.result != null;
    }
    
    @Override
    public AnnotatedElement getResult() throws SyntaxError {
        return this.result;
    }
}
