package com.apakhomov.game.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InOutPlayerInterfaceTest {
    StringWriter outSink;
    PrintWriter out;
    BufferedReader in;
    InOutPlayerInterface io;

    String nextString;

    @BeforeEach
    void setUp() {
        in = new BufferedReader(new TestReader());

        outSink = new StringWriter();
        out = new PrintWriter(outSink);

        io = new InOutPlayerInterface(in, out);
    }

    @Test
    void getUsername() {
        nextString = "John";
        assertEquals("John", io.prompt(new PromptMsg("Enter your name", PromptType.ENTER_USERNAME)).content());
    }

    class TestReader extends Reader {

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            if (nextString == null) {
                return -1;
            }
            char[] chars = nextString.toCharArray();
            System.arraycopy(chars, 0, cbuf, off, chars.length);
            if (chars.length < len) {
                nextString = null;
            } else {
                nextString = nextString.substring(len);
            }

            return chars.length;
        }

        @Override
        public void close() throws IOException {

        }
    }
}
