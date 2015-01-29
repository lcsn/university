package de.lsn.fh.msc.mining;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class CSV extends File {

	private static final long serialVersionUID = 7095568872868723862L;

	private static final String DELIMITER = ";";

	private static final String UTF_8 = "UTF-8";
	
	private boolean contentContainsHeader = false;
	private String[] headers;
	private Object[][] data;
	private String content;
	private Long lines = 0l;

	public CSV(String pathname, boolean contentContainsHeader) {
		super(pathname);
		this.contentContainsHeader = contentContainsHeader;
	}

	public static CSV read(String pathname, boolean contentContainsHeader) throws IOException {
		CSV csv = new CSV(pathname, contentContainsHeader);
		String content = new String(FileUtils.readFileToByteArray(csv), UTF_8);
		if (contentContainsHeader) {
			BufferedReader br = new BufferedReader(new StringReader(content));
			String headers = br.readLine();
			csv.setHeaders(headers.split(DELIMITER));
			csv.setLines(br.lines().count());
			br = new BufferedReader(new StringReader(content));
			br.readLine();
			content = br.lines().reduce((t, u) -> t+"\r\n"+u).get();
		}
		csv.setContent(content);
		return csv;
	}
	
	public Object[][] toArray() {
		if (null == data) {
			try {
				BufferedReader br = new BufferedReader(new StringReader(content));
				data = new Object[lines.intValue()][];
				int i = 0;
				for (String line = br.readLine(); StringUtils.isNotEmpty(line); line = br.readLine()) {
					data[i++] = encode(line.split(DELIMITER), UTF_8);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
	
	private String[] encode(String[] a, String encoding) throws UnsupportedEncodingException {
		for (int i = 0; i < a.length; i++) {
			a[i] = new String(a[i].getBytes(), encoding);
		}
		return a;
	}
	
	public void setContentContainsHeader(boolean contentContainsHeader) {
		this.contentContainsHeader = contentContainsHeader;
	}
	
	public boolean isContentContainsHeader() {
		return contentContainsHeader;
	}

	public void setHeaders(String[] headers) {
		try {
			this.headers = encode(headers, UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public String[] getHeaders() {
		return headers;
	}

	
	public String getContent() {
		return content;
	}

	private void setContent(String content) {
		this.content = content;
	}
	
	public Long getLines() {
		return lines;
	}
	
	public void setLines(Long lines) {
		this.lines = lines;
	}

	public void print() {
		if (null == data) {
			toArray();
		}
		System.out.println(Arrays.stream(headers).reduce((u, v) -> u+" | "+v).get());
		for (int i = 0; i < data.length; i++) {
			int length = headers.length;
			for (int j = 0; j < data[i].length; j++) {
				System.out.print(data[i][j]);
				length--;
				if (length > 0) {
					System.out.print(", ");
				}
			}
			System.out.println();
		}
	}

}
