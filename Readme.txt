CSCI 572 Assignment 1
Crawling and Deduplication of Weapons Images Using Nutch and Tika

Submitted By: 
Gaurav Shenoy		(Email – gvshenoy@usc.edu)
Mahesh Kumar Lunawat	(Email – mlunawat@usc.edu)
Pramod Nagarajarao	(Email – pramodna@usc.edu)
Presha Thakkar		(Email – pbthakka@usc.edu)
Karthik Kini 		(Email – kkini@usc.edu)

The submitted files are as follows:

1. Shenoy_Gaurav_NUTCH.pdf - contains the main report for assignment 1.
2. Extra_Credit_Work.pdf - contains the results obtained for extra credit task for assignment 1.
3. ImageMimeTypes.out - contain MIME types performed crawls.

4. Configuration_Files:
	contains the configuration files for nutch and changes required for contruction of deduplication plugins.
	i. Config_files_for_nutch (Modified) - contains the following files (for nutch configuration)
			nutch-site.xml
			prefix-urlfilter.txt
			regex-urlfilter.txt
			suffix-urlfilter.txt
			adaptive-mimetypes.txt
			automaton-urlfilter.txt
			mimetype-filter.txt
			regex-normalize.xml
	ii. Config_files_for_nutch (Added)
			agents.txt
			goldstandard.txt
			stopword.txt

5. Deduplication_Code_and_Results:
	i. Exact_Duplicates detection
		It contains the following files/folder -
		#1 ExactDuplicates.java - contains code for finding exact-duplicates.

	ii. Near_Duplicates detection 
		It contains the following files/folder -
		#1 NearDuplicates.java - contains code to detect near-duplicates.
		#2 FetchedRecord.java - contains the structure of URLs gropued under a class.

6. Scripts:
	printImageMimeType.py is a python script to fetch MIME types from the readdb statistics.


