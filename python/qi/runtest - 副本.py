import gzip  
import math
import sys
import time
import datetime
import string
import re
import operator
import pickle
import os
import math
reload(sys)
sys.setdefaultencoding('gbk')
from bs4 import BeautifulSoup

# the docdict key is docid, and value is text of html
docDict = {}
# read one xml file
def getDictFormFile(filename):
	fileContext = gzip.GzipFile(filename, 'rb')
	lines = fileContext.readlines()

	docno = ''
	isReadHtml = False 
	htmlContent = ''
	for line in lines:
		line = line.strip('\n') 
		if line.find("<DOCNO>") != -1 and line.find("</DOCNO>") != -1:
			docno = getDocNoValue(line)
			# init data 
			isReadHtml = False
			htmlContent = ''
			continue 
		# tag start Html
		if line.find("<html>") != -1:
			#print "****",line
			isReadHtml = True 
			htmlContent = htmlContent + line
			continue
		# read html Content
		if isReadHtml == True and line.find("</html>") == -1 :
			#print "isReadHtml = true",htmlContent
			htmlContent = htmlContent + line
			continue
		# tag end Html
		if isReadHtml == True and line.find("</html>") != -1:
			#print ">>>>",htmlContent
			isReadHtml = False
			htmlContent = htmlContent + line
			#create dict , key is docno  ,and value is value of html
			docDict[docno] = getTextByHtml(htmlContent)
			#print docno , '--- ' ,docDict[docno]
	return docDict


# get docno for docno tag
def getDocNoValue(value):
	value = value.replace('<DOCNO>','')
	value = value.replace('</DOCNO>','')
	return value

# get text from html
def getTextByHtml(htmlContent):
	#print '>>>>>',htmlContent
	soup = BeautifulSoup(htmlContent)
	tag_body = soup.body
	return tag_body.get_text()

if __name__ == '__main__':

	getDictFormFile("B01.GZ");
	print docDict