For Deduplication we have used to approaches :

1. MD5 			for 	Exact Duplicates
2. Jaccard Similarity 	for 	Near Duplicates

We have one folder for each. Just copy all the three files in the same folder.
You need to execute following statements before executing the java files :

1. Merge the segments of the crawl :

<path to nutch\runtime\local>\bin\nutch mergesegs merged <path to crawl folder>\segments\*

This will create a folder with the name 'merged' in your current folder.


2. Read the Merged segments :

<path to nutch\runtime\local>\bin\nutch readseg -dump merged\* result_out -nocontent -nofetch -nogenerate -noparse

This will create a folder with the name 'result_out' in your current folder. This folder will contain a file named dump, which contains all the data of the segments in a human readable format.


3. Getting Exact Duplicates :

After executing above commands if you wish to get exact duplicates, compile the ExactDuplicates.java file and run it with the following command. See to it that your java file is in the same folder as result_out and merged which were created in the previous steps.

javac ExactDuplicates.java
java ExactDuplicates

This will execute the MD5 algorithm on the Parse Metadata and Content Metadata of the images. It will output the statistics in your standard output. 
You can redirect the result in a file as :

java ExactDuplicates > result.txt

(If you wish to run it without Content Metadata then you can look for further information in the .java file and change it accordingly.)


4. Getting Near Duplicates :

The NearDuplicates.java file depends on FetchedRecord.java file, so do compile it before compiling the main java file. Again see to it that your java file is in the same folder as result_out and merged which were created in the previous steps.

javac FetchedRecord.java
javac NearDuplicates.java
java NearDuplicates

This will create a new file named results_near.txt in which you can look at the statistics for NearDupicates.
