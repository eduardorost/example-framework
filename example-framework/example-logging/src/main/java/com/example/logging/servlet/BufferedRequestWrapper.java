package com.example.logging.servlet;

import com.example.logging.exceptions.InitBufferException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.*;

public class BufferedRequestWrapper extends HttpServletRequestWrapper {

    private byte[] buffer = null;

    public BufferedRequestWrapper(HttpServletRequest req) {
        super(req);
    }

    private void initBuffer() throws IOException {
        // Read InputStream and store its content in a buffer.
        InputStream is = getRequest().getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int read;
        while ((read = is.read(buf)) > 0) {
            baos.write(buf, 0, read);
        }
        this.buffer = baos.toByteArray();
    }

    @Override
    public ServletInputStream getInputStream() {
        if (buffer == null) {
            try {
                initBuffer();
            } catch (IOException e) {
                throw new InitBufferException(e.getMessage(), e);
            }
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(this.buffer);
        return new BufferedServletInputStream(bais);
    }

    public String getRequestBody() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.getInputStream()));
        String line;
        StringBuilder inputBuffer = new StringBuilder();
        do {
            line = reader.readLine();
            if (null != line) {
                inputBuffer.append(line.trim());
            }
        } while (line != null);
        reader.close();
        return inputBuffer.toString().trim();
    }
}