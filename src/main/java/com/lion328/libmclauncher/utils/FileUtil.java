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

	public static String readFile(File f) throws IOException{
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
