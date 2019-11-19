# codechallenge
1. to run the program need to pass 4 commandline arguments to CodeChallengeMain class.

parameter 1  fromDate in dd/MM/yyyy hh:mm:ss format
parameter 2  toDate in dd/MM/yyyy hh:mm:ss format
parameter 3  merchant Name
parameter 4  input transaction file
e.x : 
"20/08/2018 00:00:00" "25/08/2018 13:13:00" "Kwik-E-Mart" "C:\Users\Hervin Krishna\Downloads\transaction_data.csv" 

2.Need to specify log filepath in log4j.properties file
e.g : 
log4j.appender.file.File=C:\\Users\\Hervin Krishna\\Downloads\\log4j-application.log

