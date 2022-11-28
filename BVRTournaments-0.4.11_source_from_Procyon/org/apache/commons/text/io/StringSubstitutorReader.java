// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.text.io;

import java.io.IOException;
import org.apache.commons.text.matcher.StringMatcherFactory;
import java.util.Objects;
import java.io.Reader;
import org.apache.commons.text.StringSubstitutor;
import org.apache.commons.text.matcher.StringMatcher;
import org.apache.commons.text.TextStringBuilder;
import java.io.FilterReader;

public class StringSubstitutorReader extends FilterReader
{
    private static final int EOS = -1;
    private final TextStringBuilder buffer;
    private boolean eos;
    private final StringMatcher prefixEscapeMatcher;
    private final char[] read1CharBuffer;
    private final StringSubstitutor stringSubstitutor;
    private int toDrain;
    
    public StringSubstitutorReader(final Reader reader, final StringSubstitutor stringSubstitutor) {
        super(reader);
        this.buffer = new TextStringBuilder();
        this.read1CharBuffer = new char[] { '\0' };
        this.stringSubstitutor = Objects.requireNonNull(stringSubstitutor);
        this.prefixEscapeMatcher = StringMatcherFactory.INSTANCE.charMatcher(stringSubstitutor.getEscapeChar()).andThen(stringSubstitutor.getVariablePrefixMatcher());
    }
    
    private int buffer(final int requestReadCount) throws IOException {
        final int actualReadCount = this.buffer.readFrom(super.in, requestReadCount);
        this.eos = (actualReadCount == -1);
        return actualReadCount;
    }
    
    private int bufferOrDrainOnEos(final int requestReadCount, final char[] target, final int targetIndex, final int targetLength) throws IOException {
        final int actualReadCount = this.buffer(requestReadCount);
        return this.drainOnEos(actualReadCount, target, targetIndex, targetLength);
    }
    
    private int drain(final char[] target, final int targetIndex, final int targetLength) {
        final int actualLen = Math.min(this.buffer.length(), targetLength);
        final int drainCount = this.buffer.drainChars(0, actualLen, target, targetIndex);
        this.toDrain -= drainCount;
        if (this.buffer.isEmpty() || this.toDrain == 0) {
            this.toDrain = 0;
        }
        return drainCount;
    }
    
    private int drainOnEos(final int readCountOrEos, final char[] target, final int targetIndex, final int targetLength) {
        if (readCountOrEos != -1) {
            return readCountOrEos;
        }
        if (this.buffer.isNotEmpty()) {
            this.toDrain = this.buffer.size();
            return this.drain(target, targetIndex, targetLength);
        }
        return -1;
    }
    
    private boolean isBufferMatchAt(final StringMatcher stringMatcher, final int pos) {
        return stringMatcher.isMatch(this.buffer, pos) == stringMatcher.size();
    }
    
    private boolean isDraining() {
        return this.toDrain > 0;
    }
    
    @Override
    public int read() throws IOException {
        int count = 0;
        do {
            count = this.read(this.read1CharBuffer, 0, 1);
            if (count == -1) {
                return -1;
            }
        } while (count < 1);
        return this.read1CharBuffer[0];
    }
    
    @Override
    public int read(final char[] target, final int targetIndexIn, final int targetLengthIn) throws IOException {
        if (this.eos && this.buffer.isEmpty()) {
            return -1;
        }
        if (targetLengthIn <= 0) {
            return 0;
        }
        int targetIndex = targetIndexIn;
        int targetLength = targetLengthIn;
        if (this.isDraining()) {
            final int drainCount = this.drain(target, targetIndex, Math.min(this.toDrain, targetLength));
            if (drainCount == targetLength) {
                return targetLength;
            }
            targetIndex += drainCount;
            targetLength -= drainCount;
        }
        final int minReadLenPrefix = this.prefixEscapeMatcher.size();
        int readCount = this.buffer(this.readCount(minReadLenPrefix, 0));
        if (this.buffer.length() < minReadLenPrefix && targetLength < minReadLenPrefix) {
            final int drainCount2 = this.drain(target, targetIndex, targetLength);
            targetIndex += drainCount2;
            final int targetSize = targetIndex - targetIndexIn;
            return (this.eos && targetSize <= 0) ? -1 : targetSize;
        }
        if (this.eos) {
            this.stringSubstitutor.replaceIn(this.buffer);
            this.toDrain = this.buffer.size();
            final int drainCount2 = this.drain(target, targetIndex, targetLength);
            targetIndex += drainCount2;
            final int targetSize = targetIndex - targetIndexIn;
            return (this.eos && targetSize <= 0) ? -1 : targetSize;
        }
        int balance = 0;
        final StringMatcher prefixMatcher = this.stringSubstitutor.getVariablePrefixMatcher();
        int pos = 0;
        while (targetLength > 0) {
            if (this.isBufferMatchAt(prefixMatcher, 0)) {
                balance = 1;
                pos = prefixMatcher.size();
                break;
            }
            if (this.isBufferMatchAt(this.prefixEscapeMatcher, 0)) {
                balance = 1;
                pos = this.prefixEscapeMatcher.size();
                break;
            }
            final int drainCount3 = this.drain(target, targetIndex, 1);
            targetIndex += drainCount3;
            targetLength -= drainCount3;
            if (this.buffer.size() >= minReadLenPrefix) {
                continue;
            }
            readCount = this.bufferOrDrainOnEos(minReadLenPrefix, target, targetIndex, targetLength);
            if (this.eos || this.isDraining()) {
                if (readCount != -1) {
                    targetIndex += readCount;
                    targetLength -= readCount;
                }
                final int actual = targetIndex - targetIndexIn;
                return (actual > 0) ? actual : -1;
            }
        }
        if (targetLength <= 0) {
            return targetLengthIn;
        }
        final StringMatcher suffixMatcher = this.stringSubstitutor.getVariableSuffixMatcher();
        final int minReadLenSuffix = Math.max(minReadLenPrefix, suffixMatcher.size());
        readCount = this.buffer(this.readCount(minReadLenSuffix, pos));
        if (this.eos) {
            this.stringSubstitutor.replaceIn(this.buffer);
            this.toDrain = this.buffer.size();
            final int drainCount4 = this.drain(target, targetIndex, targetLength);
            return targetIndex + drainCount4 - targetIndexIn;
        }
        do {
            if (this.isBufferMatchAt(suffixMatcher, pos)) {
                --balance;
                ++pos;
                if (balance == 0) {
                    break;
                }
            }
            else if (this.isBufferMatchAt(prefixMatcher, pos)) {
                ++balance;
                pos += prefixMatcher.size();
            }
            else if (this.isBufferMatchAt(this.prefixEscapeMatcher, pos)) {
                ++balance;
                pos += this.prefixEscapeMatcher.size();
            }
            else {
                ++pos;
            }
            readCount = this.buffer(this.readCount(minReadLenSuffix, pos));
        } while (readCount != -1 || pos < this.buffer.size());
        final int endPos = pos + 1;
        final int leftover = Math.max(0, this.buffer.size() - pos);
        this.stringSubstitutor.replaceIn(this.buffer, 0, Math.min(this.buffer.size(), endPos));
        pos = this.buffer.size() - leftover;
        final int drainLen = Math.min(targetLength, pos);
        this.toDrain = pos;
        this.drain(target, targetIndex, drainLen);
        return targetIndex - targetIndexIn + drainLen;
    }
    
    private int readCount(final int count, final int pos) {
        final int avail = this.buffer.size() - pos;
        return (avail >= count) ? 0 : (count - avail);
    }
}
