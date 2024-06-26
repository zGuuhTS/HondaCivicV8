package org.apache.commons.codec.digest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;

public class DigestUtils {
    private static final int STREAM_BUFFER_LENGTH = 1024;
    private final MessageDigest messageDigest;

    public static byte[] digest(MessageDigest messageDigest2, byte[] data) {
        return messageDigest2.digest(data);
    }

    public static byte[] digest(MessageDigest messageDigest2, ByteBuffer data) {
        messageDigest2.update(data);
        return messageDigest2.digest();
    }

    public static byte[] digest(MessageDigest messageDigest2, File data) throws IOException {
        return updateDigest(messageDigest2, data).digest();
    }

    public static byte[] digest(MessageDigest messageDigest2, InputStream data) throws IOException {
        return updateDigest(messageDigest2, data).digest();
    }

    public static byte[] digest(MessageDigest messageDigest2, Path data, OpenOption... options) throws IOException {
        return updateDigest(messageDigest2, data, options).digest();
    }

    public static byte[] digest(MessageDigest messageDigest2, RandomAccessFile data) throws IOException {
        return updateDigest(messageDigest2, data).digest();
    }

    public static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static MessageDigest getDigest(String algorithm, MessageDigest defaultMessageDigest) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            return defaultMessageDigest;
        }
    }

    public static MessageDigest getMd2Digest() {
        return getDigest(MessageDigestAlgorithms.MD2);
    }

    public static MessageDigest getMd5Digest() {
        return getDigest(MessageDigestAlgorithms.MD5);
    }

    public static MessageDigest getSha1Digest() {
        return getDigest(MessageDigestAlgorithms.SHA_1);
    }

    public static MessageDigest getSha256Digest() {
        return getDigest(MessageDigestAlgorithms.SHA_256);
    }

    public static MessageDigest getSha3_224Digest() {
        return getDigest(MessageDigestAlgorithms.SHA3_224);
    }

    public static MessageDigest getSha3_256Digest() {
        return getDigest(MessageDigestAlgorithms.SHA3_256);
    }

    public static MessageDigest getSha3_384Digest() {
        return getDigest(MessageDigestAlgorithms.SHA3_384);
    }

    public static MessageDigest getSha3_512Digest() {
        return getDigest(MessageDigestAlgorithms.SHA3_512);
    }

    public static MessageDigest getSha384Digest() {
        return getDigest(MessageDigestAlgorithms.SHA_384);
    }

    public static MessageDigest getSha512_224Digest() {
        return getDigest(MessageDigestAlgorithms.SHA_512_224);
    }

    public static MessageDigest getSha512_256Digest() {
        return getDigest(MessageDigestAlgorithms.SHA_512_256);
    }

    public static MessageDigest getSha512Digest() {
        return getDigest(MessageDigestAlgorithms.SHA_512);
    }

    @Deprecated
    public static MessageDigest getShaDigest() {
        return getSha1Digest();
    }

    public static boolean isAvailable(String messageDigestAlgorithm) {
        return getDigest(messageDigestAlgorithm, (MessageDigest) null) != null;
    }

    public static byte[] md2(byte[] data) {
        return getMd2Digest().digest(data);
    }

    public static byte[] md2(InputStream data) throws IOException {
        return digest(getMd2Digest(), data);
    }

    public static byte[] md2(String data) {
        return md2(StringUtils.getBytesUtf8(data));
    }

    public static String md2Hex(byte[] data) {
        return Hex.encodeHexString(md2(data));
    }

    public static String md2Hex(InputStream data) throws IOException {
        return Hex.encodeHexString(md2(data));
    }

    public static String md2Hex(String data) {
        return Hex.encodeHexString(md2(data));
    }

    public static byte[] md5(byte[] data) {
        return getMd5Digest().digest(data);
    }

    public static byte[] md5(InputStream data) throws IOException {
        return digest(getMd5Digest(), data);
    }

    public static byte[] md5(String data) {
        return md5(StringUtils.getBytesUtf8(data));
    }

    public static String md5Hex(byte[] data) {
        return Hex.encodeHexString(md5(data));
    }

    public static String md5Hex(InputStream data) throws IOException {
        return Hex.encodeHexString(md5(data));
    }

    public static String md5Hex(String data) {
        return Hex.encodeHexString(md5(data));
    }

    @Deprecated
    public static byte[] sha(byte[] data) {
        return sha1(data);
    }

    @Deprecated
    public static byte[] sha(InputStream data) throws IOException {
        return sha1(data);
    }

    @Deprecated
    public static byte[] sha(String data) {
        return sha1(data);
    }

    public static byte[] sha1(byte[] data) {
        return getSha1Digest().digest(data);
    }

    public static byte[] sha1(InputStream data) throws IOException {
        return digest(getSha1Digest(), data);
    }

    public static byte[] sha1(String data) {
        return sha1(StringUtils.getBytesUtf8(data));
    }

    public static String sha1Hex(byte[] data) {
        return Hex.encodeHexString(sha1(data));
    }

    public static String sha1Hex(InputStream data) throws IOException {
        return Hex.encodeHexString(sha1(data));
    }

    public static String sha1Hex(String data) {
        return Hex.encodeHexString(sha1(data));
    }

    public static byte[] sha256(byte[] data) {
        return getSha256Digest().digest(data);
    }

    public static byte[] sha256(InputStream data) throws IOException {
        return digest(getSha256Digest(), data);
    }

    public static byte[] sha256(String data) {
        return sha256(StringUtils.getBytesUtf8(data));
    }

    public static String sha256Hex(byte[] data) {
        return Hex.encodeHexString(sha256(data));
    }

    public static String sha256Hex(InputStream data) throws IOException {
        return Hex.encodeHexString(sha256(data));
    }

    public static String sha256Hex(String data) {
        return Hex.encodeHexString(sha256(data));
    }

    public static byte[] sha3_224(byte[] data) {
        return getSha3_224Digest().digest(data);
    }

    public static byte[] sha3_224(InputStream data) throws IOException {
        return digest(getSha3_224Digest(), data);
    }

    public static byte[] sha3_224(String data) {
        return sha3_224(StringUtils.getBytesUtf8(data));
    }

    public static String sha3_224Hex(byte[] data) {
        return Hex.encodeHexString(sha3_224(data));
    }

    public static String sha3_224Hex(InputStream data) throws IOException {
        return Hex.encodeHexString(sha3_224(data));
    }

    public static String sha3_224Hex(String data) {
        return Hex.encodeHexString(sha3_224(data));
    }

    public static byte[] sha3_256(byte[] data) {
        return getSha3_256Digest().digest(data);
    }

    public static byte[] sha3_256(InputStream data) throws IOException {
        return digest(getSha3_256Digest(), data);
    }

    public static byte[] sha3_256(String data) {
        return sha3_256(StringUtils.getBytesUtf8(data));
    }

    public static String sha3_256Hex(byte[] data) {
        return Hex.encodeHexString(sha3_256(data));
    }

    public static String sha3_256Hex(InputStream data) throws IOException {
        return Hex.encodeHexString(sha3_256(data));
    }

    public static String sha3_256Hex(String data) {
        return Hex.encodeHexString(sha3_256(data));
    }

    public static byte[] sha3_384(byte[] data) {
        return getSha3_384Digest().digest(data);
    }

    public static byte[] sha3_384(InputStream data) throws IOException {
        return digest(getSha3_384Digest(), data);
    }

    public static byte[] sha3_384(String data) {
        return sha3_384(StringUtils.getBytesUtf8(data));
    }

    public static String sha3_384Hex(byte[] data) {
        return Hex.encodeHexString(sha3_384(data));
    }

    public static String sha3_384Hex(InputStream data) throws IOException {
        return Hex.encodeHexString(sha3_384(data));
    }

    public static String sha3_384Hex(String data) {
        return Hex.encodeHexString(sha3_384(data));
    }

    public static byte[] sha3_512(byte[] data) {
        return getSha3_512Digest().digest(data);
    }

    public static byte[] sha3_512(InputStream data) throws IOException {
        return digest(getSha3_512Digest(), data);
    }

    public static byte[] sha3_512(String data) {
        return sha3_512(StringUtils.getBytesUtf8(data));
    }

    public static String sha3_512Hex(byte[] data) {
        return Hex.encodeHexString(sha3_512(data));
    }

    public static String sha3_512Hex(InputStream data) throws IOException {
        return Hex.encodeHexString(sha3_512(data));
    }

    public static String sha3_512Hex(String data) {
        return Hex.encodeHexString(sha3_512(data));
    }

    public static byte[] sha384(byte[] data) {
        return getSha384Digest().digest(data);
    }

    public static byte[] sha384(InputStream data) throws IOException {
        return digest(getSha384Digest(), data);
    }

    public static byte[] sha384(String data) {
        return sha384(StringUtils.getBytesUtf8(data));
    }

    public static String sha384Hex(byte[] data) {
        return Hex.encodeHexString(sha384(data));
    }

    public static String sha384Hex(InputStream data) throws IOException {
        return Hex.encodeHexString(sha384(data));
    }

    public static String sha384Hex(String data) {
        return Hex.encodeHexString(sha384(data));
    }

    public static byte[] sha512(byte[] data) {
        return getSha512Digest().digest(data);
    }

    public static byte[] sha512(InputStream data) throws IOException {
        return digest(getSha512Digest(), data);
    }

    public static byte[] sha512(String data) {
        return sha512(StringUtils.getBytesUtf8(data));
    }

    public static byte[] sha512_224(byte[] data) {
        return getSha512_224Digest().digest(data);
    }

    public static byte[] sha512_224(InputStream data) throws IOException {
        return digest(getSha512_224Digest(), data);
    }

    public static byte[] sha512_224(String data) {
        return sha512_224(StringUtils.getBytesUtf8(data));
    }

    public static String sha512_224Hex(byte[] data) {
        return Hex.encodeHexString(sha512_224(data));
    }

    public static String sha512_224Hex(InputStream data) throws IOException {
        return Hex.encodeHexString(sha512_224(data));
    }

    public static String sha512_224Hex(String data) {
        return Hex.encodeHexString(sha512_224(data));
    }

    public static byte[] sha512_256(byte[] data) {
        return getSha512_256Digest().digest(data);
    }

    public static byte[] sha512_256(InputStream data) throws IOException {
        return digest(getSha512_256Digest(), data);
    }

    public static byte[] sha512_256(String data) {
        return sha512_256(StringUtils.getBytesUtf8(data));
    }

    public static String sha512_256Hex(byte[] data) {
        return Hex.encodeHexString(sha512_256(data));
    }

    public static String sha512_256Hex(InputStream data) throws IOException {
        return Hex.encodeHexString(sha512_256(data));
    }

    public static String sha512_256Hex(String data) {
        return Hex.encodeHexString(sha512_256(data));
    }

    public static String sha512Hex(byte[] data) {
        return Hex.encodeHexString(sha512(data));
    }

    public static String sha512Hex(InputStream data) throws IOException {
        return Hex.encodeHexString(sha512(data));
    }

    public static String sha512Hex(String data) {
        return Hex.encodeHexString(sha512(data));
    }

    @Deprecated
    public static String shaHex(byte[] data) {
        return sha1Hex(data);
    }

    @Deprecated
    public static String shaHex(InputStream data) throws IOException {
        return sha1Hex(data);
    }

    @Deprecated
    public static String shaHex(String data) {
        return sha1Hex(data);
    }

    public static MessageDigest updateDigest(MessageDigest messageDigest2, byte[] valueToDigest) {
        messageDigest2.update(valueToDigest);
        return messageDigest2;
    }

    public static MessageDigest updateDigest(MessageDigest messageDigest2, ByteBuffer valueToDigest) {
        messageDigest2.update(valueToDigest);
        return messageDigest2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0019, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001a, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001d, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0014, code lost:
        r2 = move-exception;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.security.MessageDigest updateDigest(java.security.MessageDigest r4, java.io.File r5) throws java.io.IOException {
        /*
            java.io.BufferedInputStream r0 = new java.io.BufferedInputStream
            java.io.FileInputStream r1 = new java.io.FileInputStream
            r1.<init>(r5)
            r0.<init>(r1)
            java.security.MessageDigest r1 = updateDigest((java.security.MessageDigest) r4, (java.io.InputStream) r0)     // Catch:{ all -> 0x0012 }
            r0.close()
            return r1
        L_0x0012:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0014 }
        L_0x0014:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0019 }
            goto L_0x001d
        L_0x0019:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x001d:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.digest.DigestUtils.updateDigest(java.security.MessageDigest, java.io.File):java.security.MessageDigest");
    }

    private static MessageDigest updateDigest(MessageDigest digest, FileChannel data) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (data.read(buffer) > 0) {
            buffer.flip();
            digest.update(buffer);
            buffer.clear();
        }
        return digest;
    }

    public static MessageDigest updateDigest(MessageDigest digest, InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int read = inputStream.read(buffer, 0, 1024);
        while (read > -1) {
            digest.update(buffer, 0, read);
            read = inputStream.read(buffer, 0, 1024);
        }
        return digest;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0018, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0019, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001c, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0013, code lost:
        r2 = move-exception;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.security.MessageDigest updateDigest(java.security.MessageDigest r4, java.nio.file.Path r5, java.nio.file.OpenOption... r6) throws java.io.IOException {
        /*
            java.io.BufferedInputStream r0 = new java.io.BufferedInputStream
            java.io.InputStream r1 = java.nio.file.Files.newInputStream(r5, r6)
            r0.<init>(r1)
            java.security.MessageDigest r1 = updateDigest((java.security.MessageDigest) r4, (java.io.InputStream) r0)     // Catch:{ all -> 0x0011 }
            r0.close()
            return r1
        L_0x0011:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0013 }
        L_0x0013:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0018 }
            goto L_0x001c
        L_0x0018:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x001c:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.digest.DigestUtils.updateDigest(java.security.MessageDigest, java.nio.file.Path, java.nio.file.OpenOption[]):java.security.MessageDigest");
    }

    public static MessageDigest updateDigest(MessageDigest digest, RandomAccessFile data) throws IOException {
        return updateDigest(digest, data.getChannel());
    }

    public static MessageDigest updateDigest(MessageDigest messageDigest2, String valueToDigest) {
        messageDigest2.update(StringUtils.getBytesUtf8(valueToDigest));
        return messageDigest2;
    }

    @Deprecated
    public DigestUtils() {
        this.messageDigest = null;
    }

    public DigestUtils(MessageDigest digest) {
        this.messageDigest = digest;
    }

    public DigestUtils(String name) {
        this(getDigest(name));
    }

    public byte[] digest(byte[] data) {
        return updateDigest(this.messageDigest, data).digest();
    }

    public byte[] digest(ByteBuffer data) {
        return updateDigest(this.messageDigest, data).digest();
    }

    public byte[] digest(File data) throws IOException {
        return updateDigest(this.messageDigest, data).digest();
    }

    public byte[] digest(InputStream data) throws IOException {
        return updateDigest(this.messageDigest, data).digest();
    }

    public byte[] digest(Path data, OpenOption... options) throws IOException {
        return updateDigest(this.messageDigest, data, options).digest();
    }

    public byte[] digest(String data) {
        return updateDigest(this.messageDigest, data).digest();
    }

    public String digestAsHex(byte[] data) {
        return Hex.encodeHexString(digest(data));
    }

    public String digestAsHex(ByteBuffer data) {
        return Hex.encodeHexString(digest(data));
    }

    public String digestAsHex(File data) throws IOException {
        return Hex.encodeHexString(digest(data));
    }

    public String digestAsHex(InputStream data) throws IOException {
        return Hex.encodeHexString(digest(data));
    }

    public String digestAsHex(Path data, OpenOption... options) throws IOException {
        return Hex.encodeHexString(digest(data, options));
    }

    public String digestAsHex(String data) {
        return Hex.encodeHexString(digest(data));
    }

    public MessageDigest getMessageDigest() {
        return this.messageDigest;
    }
}
