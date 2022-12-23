package org.tukaani.p013xz;

import java.io.InputStream;

/* renamed from: org.tukaani.xz.FilterDecoder */
interface FilterDecoder extends FilterCoder {
    InputStream getInputStream(InputStream inputStream, ArrayCache arrayCache);

    int getMemoryUsage();
}
