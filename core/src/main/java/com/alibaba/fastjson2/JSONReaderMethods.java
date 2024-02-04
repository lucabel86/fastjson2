package com.alibaba.fastjson2;

import com.alibaba.fastjson2.util.JDKUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static com.alibaba.fastjson2.JSONFactory.*;
import static com.alibaba.fastjson2.JSONFactory.INCUBATOR_VECTOR_READER_CREATOR_UTF8;
import static com.alibaba.fastjson2.util.JDKUtils.*;
import static com.alibaba.fastjson2.util.JDKUtils.JVM_VERSION;

public interface JSONReaderMethods {

    public static JSONReader of(byte[] utf8Bytes) {
        boolean ascii = false;
        if (PREDICATE_IS_ASCII != null) {
            ascii = PREDICATE_IS_ASCII.test(utf8Bytes);
        }

        JSONReader.Context context = createReadContext();
        if (ascii) {
            if (INCUBATOR_VECTOR_READER_CREATOR_ASCII != null) {
                return INCUBATOR_VECTOR_READER_CREATOR_ASCII.create(context, null, utf8Bytes, 0, utf8Bytes.length);
            }

            return new JSONReaderASCII(context, null, utf8Bytes, 0, utf8Bytes.length);
        }

        if (INCUBATOR_VECTOR_READER_CREATOR_UTF8 != null) {
            return INCUBATOR_VECTOR_READER_CREATOR_UTF8.create(context, null, utf8Bytes, 0, utf8Bytes.length);
        } else {
            return new JSONReaderUTF8(context, null, utf8Bytes, 0, utf8Bytes.length);
        }
    }


    @Deprecated
    public static JSONReader of(JSONReader.Context context, byte[] utf8Bytes) {
        boolean ascii = false;
        if (PREDICATE_IS_ASCII != null) {
            ascii = PREDICATE_IS_ASCII.test(utf8Bytes);
        }

        if (ascii) {
            if (INCUBATOR_VECTOR_READER_CREATOR_ASCII != null) {
                return INCUBATOR_VECTOR_READER_CREATOR_ASCII.create(context, null, utf8Bytes, 0, utf8Bytes.length);
            }

            return new JSONReaderASCII(context, null, utf8Bytes, 0, utf8Bytes.length);
        }

        if (INCUBATOR_VECTOR_READER_CREATOR_UTF8 != null) {
            return INCUBATOR_VECTOR_READER_CREATOR_UTF8.create(context, null, utf8Bytes, 0, utf8Bytes.length);
        } else {
            return new JSONReaderUTF8(context, null, utf8Bytes, 0, utf8Bytes.length);
        }
    }

    public static JSONReader of(byte[] utf8Bytes, JSONReader.Context context) {
        boolean ascii = false;
        if (PREDICATE_IS_ASCII != null) {
            ascii = PREDICATE_IS_ASCII.test(utf8Bytes);
        }

        if (ascii) {
            if (INCUBATOR_VECTOR_READER_CREATOR_ASCII != null) {
                return INCUBATOR_VECTOR_READER_CREATOR_ASCII.create(context, null, utf8Bytes, 0, utf8Bytes.length);
            }

            return new JSONReaderASCII(context, null, utf8Bytes, 0, utf8Bytes.length);
        }

        if (INCUBATOR_VECTOR_READER_CREATOR_UTF8 != null) {
            return INCUBATOR_VECTOR_READER_CREATOR_UTF8.create(context, null, utf8Bytes, 0, utf8Bytes.length);
        } else {
            return new JSONReaderUTF8(context, null, utf8Bytes, 0, utf8Bytes.length);
        }
    }


    public static JSONReader of(byte[] bytes, int offset, int length, Charset charset) {
        JSONReader.Context context = JSONFactory.createReadContext();

        if (charset == StandardCharsets.UTF_8) {
            if (INCUBATOR_VECTOR_READER_CREATOR_UTF8 != null) {
                return INCUBATOR_VECTOR_READER_CREATOR_UTF8.create(context, null, bytes, offset, length);
            } else {
                return new JSONReaderUTF8(context, null, bytes, offset, length);
            }
        }

        if (charset == StandardCharsets.UTF_16) {
            return new JSONReaderUTF16(context, bytes, offset, length);
        }

        if (charset == StandardCharsets.US_ASCII || charset == StandardCharsets.ISO_8859_1) {
            if (INCUBATOR_VECTOR_READER_CREATOR_ASCII != null) {
                return INCUBATOR_VECTOR_READER_CREATOR_ASCII.create(context, null, bytes, offset, length);
            } else {
                return new JSONReaderASCII(context, null, bytes, offset, length);
            }
        }

        throw new JSONException("not support charset " + charset);
    }

    public static JSONReader of(byte[] bytes, int offset, int length, Charset charset, JSONReader.Context context) {
        if (charset == StandardCharsets.UTF_8) {
            if (offset == 0 && bytes.length == length) {
                return of(bytes, context);
            }

            boolean hasNegative = true;
            if (METHOD_HANDLE_HAS_NEGATIVE != null) {
                try {
                    hasNegative = (Boolean) METHOD_HANDLE_HAS_NEGATIVE.invoke(bytes, 0, bytes.length);
                } catch (Throwable ignored) {
                    // ignored
                }
            }

            if (!hasNegative) {
                if (INCUBATOR_VECTOR_READER_CREATOR_ASCII != null) {
                    return INCUBATOR_VECTOR_READER_CREATOR_ASCII.create(context, null, bytes, offset, length);
                } else {
                    return new JSONReaderASCII(context, null, bytes, offset, length);
                }
            }

            if (INCUBATOR_VECTOR_READER_CREATOR_UTF8 != null) {
                return INCUBATOR_VECTOR_READER_CREATOR_UTF8.create(context, null, bytes, offset, length);
            } else {
                return new JSONReaderUTF8(context, null, bytes, offset, length);
            }
        }

        if (charset == StandardCharsets.UTF_16) {
            return new JSONReaderUTF16(context, bytes, offset, length);
        }

        if (charset == StandardCharsets.US_ASCII || charset == StandardCharsets.ISO_8859_1) {
            if (INCUBATOR_VECTOR_READER_CREATOR_ASCII != null) {
                return INCUBATOR_VECTOR_READER_CREATOR_ASCII.create(context, null, bytes, offset, length);
            } else {
                return new JSONReaderASCII(context, null, bytes, offset, length);
            }
        }

        throw new JSONException("not support charset " + charset);
    }

    public static JSONReader of(byte[] bytes, int offset, int length) {
        JSONReader.Context context = createReadContext();
        if (INCUBATOR_VECTOR_READER_CREATOR_UTF8 != null) {
            return INCUBATOR_VECTOR_READER_CREATOR_UTF8.create(context, null, bytes, offset, length);
        } else {
            return new JSONReaderUTF8(context, null, bytes, offset, length);
        }
    }

    public static JSONReader of(byte[] bytes, int offset, int length, JSONReader.Context context) {
        if (INCUBATOR_VECTOR_READER_CREATOR_UTF8 != null) {
            return INCUBATOR_VECTOR_READER_CREATOR_UTF8.create(context, null, bytes, offset, length);
        } else {
            return new JSONReaderUTF8(context, null, bytes, offset, length);
        }
    }


    public static JSONReader of(char[] chars, int offset, int length) {
        JSONReader.Context context = createReadContext();

        if (INCUBATOR_VECTOR_READER_CREATOR_UTF16 != null) {
            return INCUBATOR_VECTOR_READER_CREATOR_UTF16.create(
                    context,
                    null,
                    chars,
                    offset,
                    length);
        }

        return new JSONReaderUTF16(context, null, chars, offset, length);
    }

    public static JSONReader of(char[] chars, int offset, int length, JSONReader.Context context) {
        if (INCUBATOR_VECTOR_READER_CREATOR_UTF16 != null) {
            return INCUBATOR_VECTOR_READER_CREATOR_UTF16.create(
                    context,
                    null,
                    chars,
                    offset,
                    length);
        }

        return new JSONReaderUTF16(context, null, chars, offset, length);
    }

    public static JSONReader of(URL url, JSONReader.Context context) throws IOException {
        try (InputStream is = url.openStream()) {
            return of(is, StandardCharsets.UTF_8, context);
        }
    }

    public static JSONReader of(InputStream is, Charset charset) {
        JSONReader.Context context = JSONFactory.createReadContext();
        return of(is, charset, context);
    }

    public static JSONReader of(InputStream is, Charset charset, JSONReader.Context context) {
        if (charset == StandardCharsets.UTF_8 || charset == null) {
            return new JSONReaderUTF8(context, is);
        }

        if (charset == StandardCharsets.UTF_16) {
            return new JSONReaderUTF16(context, is);
        }

        throw new JSONException("not support charset " + charset);
    }

    public static JSONReader of(java.io.Reader is) {
        return new JSONReaderUTF16(
                JSONFactory.createReadContext(),
                is
        );
    }

    public static JSONReader of(java.io.Reader is, JSONReader.Context context) {
        return new JSONReaderUTF16(
                context,
                is
        );
    }

    public static JSONReader of(ByteBuffer buffer, Charset charset) {
        JSONReader.Context context = JSONFactory.createReadContext();

        if (charset == StandardCharsets.UTF_8 || charset == null) {
            return new JSONReaderUTF8(context, buffer);
        }

        throw new JSONException("not support charset " + charset);
    }

    public static JSONReader of(ByteBuffer buffer, Charset charset, JSONReader.Context context) {
        if (charset == StandardCharsets.UTF_8 || charset == null) {
            return new JSONReaderUTF8(context, buffer);
        }

        throw new JSONException("not support charset " + charset);
    }

    @Deprecated
    public static JSONReader of(JSONReader.Context context, String str) {
        return of(str, context);
    }

    public static JSONReader of(String str) {
        if (str == null) {
            throw new NullPointerException();
        }

        JSONReader.Context context = JSONFactory.createReadContext();
        if (STRING_VALUE != null && STRING_CODER != null && PREDICATE_IS_ASCII != null) {
            try {
                final int LATIN1 = 0;
                int coder = STRING_CODER.applyAsInt(str);
                if (coder == LATIN1) {
                    byte[] bytes = STRING_VALUE.apply(str);
                    if (PREDICATE_IS_ASCII.test(bytes)) {
                        if (INCUBATOR_VECTOR_READER_CREATOR_ASCII != null) {
                            return INCUBATOR_VECTOR_READER_CREATOR_ASCII.create(context, str, bytes, 0, bytes.length);
                        } else {
                            return new JSONReaderASCII(context, str, bytes, 0, bytes.length);
                        }
                    }
                }
            } catch (Exception e) {
                throw new JSONException("unsafe get String.coder error");
            }
        }

        final int length = str.length();
        if (JVM_VERSION == 8) {
            char[] chars = JDKUtils.getCharArray(str);
            return new JSONReaderUTF16(context, str, chars, 0, length);
        }

        if (INCUBATOR_VECTOR_READER_CREATOR_UTF16 != null) {
            return INCUBATOR_VECTOR_READER_CREATOR_UTF16.create(
                    context,
                    str,
                    null,
                    0,
                    length);
        }

        return new JSONReaderUTF16(context, str, 0, length);
    }

    public static JSONReader of(String str, JSONReader.Context context) {
        if (str == null || context == null) {
            throw new NullPointerException();
        }

        if (STRING_VALUE != null && STRING_CODER != null) {
            try {
                final int LATIN1 = 0;
                int coder = STRING_CODER.applyAsInt(str);
                if (coder == LATIN1) {
                    byte[] bytes = STRING_VALUE.apply(str);
                    if (INCUBATOR_VECTOR_READER_CREATOR_ASCII != null) {
                        return INCUBATOR_VECTOR_READER_CREATOR_ASCII.create(context, str, bytes, 0, bytes.length);
                    } else {
                        return new JSONReaderASCII(context, str, bytes, 0, bytes.length);
                    }
                }
            } catch (Exception e) {
                throw new JSONException("unsafe get String.coder error");
            }
        }

        final int length = str.length();
        char[] chars;
        if (JVM_VERSION == 8) {
            chars = JDKUtils.getCharArray(str);
        } else {
            chars = str.toCharArray();
        }

        if (INCUBATOR_VECTOR_READER_CREATOR_UTF16 != null) {
            return INCUBATOR_VECTOR_READER_CREATOR_UTF16.create(
                    context,
                    str,
                    chars,
                    0,
                    length);
        }

        return new JSONReaderUTF16(context, str, chars, 0, length);
    }

    public static JSONReader of(String str, int offset, int length) {
        if (str == null) {
            throw new NullPointerException();
        }

        JSONReader.Context context = JSONFactory.createReadContext();
        if (STRING_VALUE != null) {
            try {
                final int LATIN1 = 0;
                int coder = STRING_CODER.applyAsInt(str);
                if (coder == LATIN1) {
                    byte[] bytes = STRING_VALUE.apply(str);
                    if (INCUBATOR_VECTOR_READER_CREATOR_ASCII != null) {
                        return INCUBATOR_VECTOR_READER_CREATOR_ASCII.create(context, str, bytes, offset, length);
                    } else {
                        return new JSONReaderASCII(context, str, bytes, offset, length);
                    }
                }
            } catch (Exception e) {
                throw new JSONException("unsafe get String.coder error");
            }
        }

        char[] chars;
        if (JVM_VERSION == 8) {
            chars = JDKUtils.getCharArray(str);
        } else {
            chars = str.toCharArray();
        }

        if (INCUBATOR_VECTOR_READER_CREATOR_UTF16 != null) {
            return INCUBATOR_VECTOR_READER_CREATOR_UTF16.create(
                    context,
                    str,
                    chars,
                    offset,
                    length);
        }

        return new JSONReaderUTF16(context, str, chars, offset, length);
    }

    public static JSONReader of(String str, int offset, int length, JSONReader.Context context) {
        if (str == null || context == null) {
            throw new NullPointerException();
        }

        if (STRING_VALUE != null && STRING_CODER != null) {
            try {
                final int LATIN1 = 0;
                int coder = STRING_CODER.applyAsInt(str);
                if (coder == LATIN1) {
                    byte[] bytes = STRING_VALUE.apply(str);
                    if (INCUBATOR_VECTOR_READER_CREATOR_ASCII != null) {
                        return INCUBATOR_VECTOR_READER_CREATOR_ASCII.create(context, str, bytes, offset, length);
                    } else {
                        return new JSONReaderASCII(context, str, bytes, offset, length);
                    }
                }
            } catch (Exception e) {
                throw new JSONException("unsafe get String.coder error");
            }
        }

        char[] chars;
        if (JVM_VERSION == 8) {
            chars = JDKUtils.getCharArray(str);
        } else {
            chars = str.toCharArray();
        }

        if (INCUBATOR_VECTOR_READER_CREATOR_UTF16 != null) {
            return INCUBATOR_VECTOR_READER_CREATOR_UTF16.create(
                    context,
                    str,
                    chars,
                    offset,
                    length);
        }

        return new JSONReaderUTF16(context, str, chars, offset, length);
    }


    public static JSONReader of(char[] chars) {
        JSONReader.Context context = createReadContext();

        if (INCUBATOR_VECTOR_READER_CREATOR_UTF16 != null) {
            return INCUBATOR_VECTOR_READER_CREATOR_UTF16.create(
                    context,
                    null,
                    chars,
                    0,
                    chars.length);
        }

        return new JSONReaderUTF16(
                context,
                null,
                chars,
                0,
                chars.length);
    }

    @Deprecated
    public static JSONReader of(JSONReader.Context context, char[] chars) {
        if (INCUBATOR_VECTOR_READER_CREATOR_UTF16 != null) {
            return INCUBATOR_VECTOR_READER_CREATOR_UTF16.create(
                    context,
                    null,
                    chars,
                    0,
                    chars.length);
        }

        return new JSONReaderUTF16(
                context,
                null,
                chars,
                0,
                chars.length
        );
    }

    public static JSONReader of(char[] chars, JSONReader.Context context) {
        return new JSONReaderUTF16(
                context,
                null,
                chars,
                0,
                chars.length
        );
    }

}
