package com.code.challenge;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.code.challenge.constants.Constants;
import com.code.challenge.dto.TransactionData;
import com.code.challenge.utilitiy.FileUtility;

public class CodeChallengeMain {

	final static Logger logger = Logger.getLogger(CodeChallengeMain.class);

	public static void main(String[] args) {

		try {
			logger.info("No.of Input Parameters : " + args.length);
			if (args.length < 4) {
				logger.error(
						"Incorrect Input Parameters,Should be 3 and startDate,endDate,Merchant and Input File Full Path");
				return;
			}

			String transactionStartDate = args[0].trim();
			String transactionendDate = args[1].trim();
			String merchant = args[2].trim();
			String inputFilePath = args[3].trim();

			logger.info("transactionStartDate : " + transactionStartDate);
			logger.info("transactionendDate : " + transactionendDate);
			logger.info("merchant : " + merchant);
			logger.info("inputFilePath : " + inputFilePath);

			boolean isTransactionStartDateInValidFormat = checkDateFormat(transactionStartDate);
			boolean isTransactionEndInValidFormat = checkDateFormat(transactionStartDate);

			if (!isTransactionStartDateInValidFormat || !isTransactionEndInValidFormat) {
				logger.error(
						"Transaction Start Date or Transaction End Date format is incorrect,Please input in dd/MM/yyyy hh:mm:ss format.");
				return;
			}

			File inputFile = new File(inputFilePath);
			if (!inputFile.exists()) {
				logger.error("Please specify Valid FilePath ");
			}

			Date fromDate = Constants.TRANSACTION_DATE_FORMATTER.parse(transactionStartDate);
			Date toDate = Constants.TRANSACTION_DATE_FORMATTER.parse(transactionendDate);

			List<TransactionData> list = FileUtility.loadData(inputFile);

			Set<String> reversalList = list.stream()
					.filter(record -> (StringUtils.isNotBlank(record.getRelatedTransaction())
							&& StringUtils.equalsIgnoreCase("REVERSAL", record.getType())))
					.map(mapper -> mapper.getRelatedTransaction().toUpperCase()).collect(Collectors.toSet());

			List<TransactionData> finalList = list.stream()
					.filter(record -> !reversalList.contains(record.getId().toUpperCase()))
					.collect(Collectors.toList());

			List<TransactionData> merchantList = finalList.stream()
					.filter(isMerchantAndDateRangeMatched(fromDate, toDate, merchant)).collect(Collectors.toList());

			if (ObjectUtils.isNotEmpty(merchantList)) {
				DoubleSummaryStatistics statistics = merchantList.stream().mapToDouble(t -> t.getAmount())
						.summaryStatistics();

				logger.info("Average Transaction Volume : " + Constants.DecimalFormat.format(statistics.getAverage()));
				logger.info("Total Transactions : " + merchantList.size());

			} else {
				logger.info("No Match Found for thegiven Input ");
			}

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}

	}

	public static Predicate<TransactionData> isMerchantAndDateRangeMatched(Date fromDate, Date toDate,
			String merchant) {

		return transaction -> (StringUtils.equalsIgnoreCase(merchant, transaction.getMerchant())
				&& (ObjectUtils.isNotEmpty(transaction.getDate()) && transaction.getDate().after(fromDate))
				&& (ObjectUtils.isNotEmpty(transaction.getDate()) && transaction.getDate().before(toDate)));
	}

	private static boolean checkDateFormat(String inputDate) {
		boolean validFormat = true;
		try {
			Constants.TRANSACTION_DATE_FORMATTER.parse(inputDate);
		} catch (ParseException e) {
			logger.error(e.getMessage());
			validFormat = false;
		}
		return validFormat;
	}
}
