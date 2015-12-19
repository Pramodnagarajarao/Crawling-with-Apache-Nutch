__author__ = 'TEAM 33 CSCI 572'
from nutch.nutch import Nutch
from nutch.nutch import SeedClient
from nutch.nutch import Server
from nutch.nutch import JobClient
import nutch
import io

sv=Server('http://localhost:8081')
sc=SeedClient(sv)
seed_urls=[]

# Creating custom configuration for nutch crawl
custom_configFile={}
custom_configFile['http.agent.name']="Team 33 : CSCI 572"
custom_configFile['http.agent.email']="mlunawat@usc.edu"
custom_configFile['http.agent.rotate']="true"
custom_configFile['db.url.normalizers']="true"
custom_configFile['generate.update.crawldb']="true"
custom_configFile['http.content.limit']="-1"
custom_configFile['http.enable.if.modified.since.header']="false"
custom_configFile['http.timeout']="20000"
custom_configFile['fetcher.threads.fetch']="20"
custom_configFile['fetcher.threads.per.queue']="10"
custom_configFile['http.max.delays']="1000"

# reading seed urls from file
fopen=open("urls.txt","r")
urlList=fopen.readlines()
for i in range(0,len(urlList)):
    seed_urls.append(urlList[i])

# crawling seed urls with custom configured nutch
sd= sc.create('crawl-seed', seed_urls)
nt = Nutch(custom_configFile)
jc = JobClient(sv, 'test', 'default')
cc = nt.Crawl(sd, sc, jc)
while True:
    job = cc.progress() # gets the current job if no progress, else iterates and makes progress
    if job == None:
        break
