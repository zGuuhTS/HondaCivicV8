package org.apache.commons.codec.cli;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Locale;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.commons.codec.language.p008bm.Rule;
import top.defaults.checkerboarddrawable.BuildConfig;

public class Digest {
    private final String algorithm;
    private final String[] args;
    private final String[] inputs;

    public static void main(String[] args2) throws IOException {
        new Digest(args2).run();
    }

    private Digest(String[] args2) {
        if (args2 == null) {
            throw new IllegalArgumentException("args");
        } else if (args2.length != 0) {
            this.args = args2;
            this.algorithm = args2[0];
            if (args2.length <= 1) {
                this.inputs = null;
                return;
            }
            String[] strArr = new String[(args2.length - 1)];
            this.inputs = strArr;
            System.arraycopy(args2, 1, strArr, 0, strArr.length);
        } else {
            throw new IllegalArgumentException(String.format("Usage: java %s [algorithm] [FILE|DIRECTORY|string] ...", new Object[]{Digest.class.getName()}));
        }
    }

    private void println(String prefix, byte[] digest) {
        println(prefix, digest, (String) null);
    }

    private void println(String prefix, byte[] digest, String fileName) {
        System.out.println(prefix + Hex.encodeHexString(digest) + (fileName != null ? "  " + fileName : BuildConfig.FLAVOR));
    }

    private void run() throws IOException {
        if (this.algorithm.equalsIgnoreCase(Rule.ALL) || this.algorithm.equals("*")) {
            run(MessageDigestAlgorithms.values());
            return;
        }
        MessageDigest messageDigest = DigestUtils.getDigest(this.algorithm, (MessageDigest) null);
        if (messageDigest != null) {
            run(BuildConfig.FLAVOR, messageDigest);
        } else {
            run(BuildConfig.FLAVOR, DigestUtils.getDigest(this.algorithm.toUpperCase(Locale.ROOT)));
        }
    }

    private void run(String[] digestAlgorithms) throws IOException {
        for (String messageDigestAlgorithm : digestAlgorithms) {
            if (DigestUtils.isAvailable(messageDigestAlgorithm)) {
                run(messageDigestAlgorithm + " ", messageDigestAlgorithm);
            }
        }
    }

    private void run(String prefix, MessageDigest messageDigest) throws IOException {
        String[] strArr = this.inputs;
        if (strArr == null) {
            println(prefix, DigestUtils.digest(messageDigest, System.in));
            return;
        }
        for (String source : strArr) {
            File file = new File(source);
            if (file.isFile()) {
                println(prefix, DigestUtils.digest(messageDigest, file), source);
            } else if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                if (listFiles != null) {
                    run(prefix, messageDigest, listFiles);
                }
            } else {
                println(prefix, DigestUtils.digest(messageDigest, source.getBytes(Charset.defaultCharset())));
            }
        }
    }

    private void run(String prefix, MessageDigest messageDigest, File[] files) throws IOException {
        for (File file : files) {
            if (file.isFile()) {
                println(prefix, DigestUtils.digest(messageDigest, file), file.getName());
            }
        }
    }

    private void run(String prefix, String messageDigestAlgorithm) throws IOException {
        run(prefix, DigestUtils.getDigest(messageDigestAlgorithm));
    }

    public String toString() {
        return String.format("%s %s", new Object[]{super.toString(), Arrays.toString(this.args)});
    }
}
