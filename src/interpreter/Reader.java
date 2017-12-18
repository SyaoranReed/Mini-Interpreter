package interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

public class Reader {
	private BufferedReader in;
	private String line;
	
	public Reader(String filePath) throws FileNotFoundException {
		File file = new File(filePath);
		in = new BufferedReader(new FileReader(file));
	}	
	
	public String nextLine() throws IOException {
		if (this.line == null) {
			getLine();
		}
		return this.line;
	}
	
	public String getLine() throws IOException {
		String auxLine = this.line;
		this.line = this.in.readLine();
		
		return auxLine;
	}
}
