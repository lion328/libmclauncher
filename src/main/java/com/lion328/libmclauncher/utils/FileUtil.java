/**
 * Copyright (c) 2014 Waritnan Sookbuntherng
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.lion328.libmclauncher.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FileUtil {

	public static String readFile(File f) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(f));
		StringBuilder sb = new StringBuilder();
		String str;
		while((str = br.readLine()) != null){
			sb.append(str);
			sb.append("\n");
		}
		br.close();
		return sb.toString();
	}
	
	public static byte[] readBytes(File f) throws IOException {
		return Files.readAllBytes(f.toPath());
	}
	
	public static File[] listAllFiles(File dir){
		if(!dir.exists()) return null;
		Set<File> fs = new HashSet<File>(Arrays.asList(dir.listFiles()));
		for(File f : fs.toArray(new File[fs.size()])){
			if(f.isDirectory()) fs.addAll(Arrays.asList(listAllFiles(f)));
			fs.add(f);
		}
		return fs.toArray(new File[fs.size()]);
	}
	
	public static void delete(File f) {
		if(f.exists()) {
			if(f.isDirectory()) for(File f2 : listAllFiles(f)) delete(f2);
			f.delete();
		}
	}

}
