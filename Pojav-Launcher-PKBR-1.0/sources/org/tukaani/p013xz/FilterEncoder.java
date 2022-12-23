package org.tukaani.p013xz;

/* renamed from: org.tukaani.xz.FilterEncoder */
interface FilterEncoder extends FilterCoder {
    long getFilterID();

    byte[] getFilterProps();

    FinishableOutputStream getOutputStream(FinishableOutputStream finishableOutputStream, ArrayCache arrayCache);

    boolean supportsFlushing();
}
