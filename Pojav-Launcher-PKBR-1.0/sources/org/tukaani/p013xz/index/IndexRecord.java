package org.tukaani.p013xz.index;

/* renamed from: org.tukaani.xz.index.IndexRecord */
class IndexRecord {
    final long uncompressed;
    final long unpadded;

    IndexRecord(long j, long j2) {
        this.unpadded = j;
        this.uncompressed = j2;
    }
}
