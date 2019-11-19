package com.code.challenge.utilitiy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.code.challenge.constants.Constants;
import com.code.challenge.dto.TransactionData;

public class FileUtility {

	final static Logger logger = Logger.getLogger(FileUtility.class);

	private static Function<String, TransactionData> mapLineToObejct = (line) -> {
		String[] p = line.split(Constants.COMMA);
		TransactionData item = new TransactionData();
		item.setId(getData(p, 0));
		item.setDate(convertToDate(getData(p, 1)));
		item.setAmount(convertToAmount(getData(p, 2)));
		item.setMerchant(getData(p, 3));
		item.setType(getData(p, 4));
		item.setRelatedTransaction(getData(p, 5));

		return item;
	};

	public static List<TransactionData> loadData(File inputFile) {

		List<TransactionData> dataList = null;
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(inputFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			dataList = br.lines().skip(1).map(mapLineToObejct).collect(Collectors.toList());
			br.close();

		} catch (IOException e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {

			if (ObjectUtils.isNotEmpty(inputStream))
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(ExceptionUtils.getStackTrace(e));
				}
		}

		return dataList;
	}

	private static float convertToAmount(String data) {
		if (ObjectUtils.isNotEmpty(data))
			return Float.parseFloat(Constants.DecimalFormat.format(Float.parseFloat(data)));
		return 0;
	}

	private static Date convertToDate(String data) {
		if (ObjectUtils.isNotEmpty(data))
			return checkDateFormat(data);
		return null;
	}

	private static String getData(String[] p, int index) {
		if (ObjectUtils.isNotEmpty(p)) {
			if (index < p.length) {
				return p[index].trim();
			}
		}
		return null;
	}

	private static Date checkDateFormat(String inputDate) {
		try {
			return Constants.TRANSACTION_DATE_FORMATTER.parse(inputDate);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

}
