package org.seasar.cubby.tags;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.jsp.JspWriter;

public class MockJspWriter extends JspWriter {

	StringWriter writer = new StringWriter();
	PrintWriter printWriter = new PrintWriter(writer);
	
	public MockJspWriter() {
		this(0, true);
	}
	
	protected MockJspWriter(int bufferSize, boolean autoFlush) {
		super(bufferSize, autoFlush);
	}
	
	public String getResult() {
		return writer.toString();
	}

	@Override
	public void clear() throws IOException {
		writer = new StringWriter();
		printWriter = new PrintWriter(writer);
	}

	@Override
	public void clearBuffer() throws IOException {
		writer = new StringWriter();
		printWriter = new PrintWriter(writer);
	}

	@Override
	public void close() throws IOException {
		writer.close();
	}

	@Override
	public void flush() throws IOException {
		writer.flush();
	}

	@Override
	public int getRemaining() {
		return 0;
	}

	@Override
	public void newLine() throws IOException {
		printWriter.println();
	}

	@Override
	public void print(boolean b) throws IOException {
		printWriter.print(b);
	}

	@Override
	public void print(char c) throws IOException {
		printWriter.print(c);
	}

	@Override
	public void print(int i) throws IOException {
		printWriter.print(i);
	}

	@Override
	public void print(long l) throws IOException {
		printWriter.print(l);
	}

	@Override
	public void print(float f) throws IOException {
		printWriter.print(f);
	}

	@Override
	public void print(double d) throws IOException {
		printWriter.print(d);
	}

	@Override
	public void print(char[] s) throws IOException {
		printWriter.print(s);
	}

	@Override
	public void print(String s) throws IOException {
		printWriter.print(s);
	}

	@Override
	public void print(Object obj) throws IOException {
		printWriter.print(obj);
	}

	@Override
	public void println() throws IOException {
		printWriter.println();
	}

	@Override
	public void println(boolean x) throws IOException {
		printWriter.println(x);
	}

	@Override
	public void println(char x) throws IOException {
		printWriter.println(x);
	}

	@Override
	public void println(int x) throws IOException {
		printWriter.println(x);
	}

	@Override
	public void println(long x) throws IOException {
		printWriter.println(x);
	}

	@Override
	public void println(float x) throws IOException {
		printWriter.println(x);
	}

	@Override
	public void println(double x) throws IOException {
		printWriter.println(x);
	}

	@Override
	public void println(char[] x) throws IOException {
		printWriter.println(x);
	}

	@Override
	public void println(String x) throws IOException {
		printWriter.println(x);
	}

	@Override
	public void println(Object x) throws IOException {
		printWriter.println(x);
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		printWriter.write(cbuf, off, len);
	}

}
