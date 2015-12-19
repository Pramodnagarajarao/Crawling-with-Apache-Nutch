__author__ = 'CSCI572 Team 33 Team'

import nutchpy
import os

// Input path for the crawldb data, and output file for the imageTypes
input_path  = "/Users/pramodnagarajarao/IR/nutch/runtime/local/crawl/crawldb/current/part-00000/data"
output_path = "/Users/pramodnagarajarao/IR/nutch/runtime/local/imageTypes.out"
#input_path = "/Users/pramodnagarajarao/Desktop/crawl_one/data2"
#output_path = "/Users/pramodnagarajarao/IR/nutch/runtime/local/imageTypes2.out"

#Counter variable hold the number of lines present in the read binary file
counter = nutchpy.sequence_reader.count(input_path)
#numRound = (counter/1000)-1

s1 = list();

for k in range(1, (counter/1000)-1):
    docs = nutchpy.sequence_reader.slice(((k-1)*1000) +1, k*1000, input_path)
    for type in docs:
        for i in range(1,len(docs)):
            head=type[1].split("\n")
            for j in range(0,len(head)):
                if "Content-Type=image" in str(head[j]):
                    if str(head[j]) not in s1:
                        s1.append(str(head[j]))
    print(k)
    print(s1)
