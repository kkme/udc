package com.koudai.udc.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.CanNotCreateFileException;

public final class FileUtil {

	private static final Logger LOGGER = Logger.getLogger(FileUtil.class);

	public static void createDirectory(String path) throws CanNotCreateFileException {
		File directory = new File(path);
		if (!directory.exists()) {
			if (!directory.mkdir()) {
				throw new CanNotCreateFileException("Create directory failed");
			}
		}
	}

	public static void write(String path, String content) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(path);
			fw.write(content);
		} catch (Exception e) {
			LOGGER.error("Write file failed", e);
		} finally {
			try {
				if (fw != null) {
					fw.close();
				}
			} catch (Exception e1) {
				LOGGER.error("Close file writer failed", e1);
			}
		}
	}

	public static List<String> readMultipleLine(String path) {
		List<String> content = new ArrayList<String>();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				content.add(line);
			}
			return content;
		} catch (Exception e) {
			LOGGER.error("Read file failed", e);
			return null;
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();
				}
			} catch (Exception e1) {
				LOGGER.error("Close file reader failed", e1);
			}
		}
	}

}
