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
import xml.sax
from bs4 import BeautifulSoup

	
class NewsHandler( xml.sax.ContentHandler ):
	def __init__(self):
		self.CurrentData = ""
		self.DOCNO = ""
		self.title = ""
		self.body = ""
	 # begin with elements' processing
	def startElement(self, tag, attributes):
      		self.CurrentData = tag
      		if tag == "DOC":
         		print "*****NEWS*****"
         		#content1 = attributes["content"]
         		#print "Content:", content1

   # end with elements' processing
	def endElement(self, tag):
		if self.CurrentData == "DOCNO":
			print "DOCNO:", self.DOCNO
		elif self.CurrentData == "title":
			print "title:", self.title
		elif self.CurrentData == "body":
			print "body:", self.body
		self.CurrentData = ""

   # context processing
	def characters(self, content):
		if self.CurrentData == "DOCNO":
			self.DOCNO = content
		elif self.CurrentData == "title":
			self.title = content
		elif self.CurrentData == "body":
			self.body = content

def get_file_path(filepath,listfile):
	#listfile.write(filepath + '//')
	fielnum = 0
	lists = os.listdir(filepath)  #list of all doucuments and paths
	for line in lists:
		filepath_01 = os.path.join(filepath,line)
		#print filepath_01
		if os.path.isdir(filepath_01):  #if filepath is path,then list all doucuments in paths
			for li in os.listdir(filepath_01):
				listfile.write(filepath_01+'//'+li + '\n')
				fielnum = fielnum + 1
		elif os.path:   #if filepath is document,list document name
			listfile.write(filepath_01 + '\n') 
			fielnum = fielnum + 1	
	listfile.write('all the file num is '+ str(fielnum))
	print "path of files is finished"


  
def read_gz_write_xml_file(fin,fout):
    	#print ("start ...")

	context = gzip.GzipFile(fin, 'rb')
	lines = context.read()
	fout.write(lines)
	#fout.close()

    	#print("end...")

def read_xml_file(filename):
	# construct XMLReader
	parser = xml.sax.make_parser()
   	# turn off namepsaces
	parser.setFeature(xml.sax.handler.feature_namespaces, 0)

   	# rewrite ContextHandler
	Handler = NewsHandler()
	parser.setContentHandler( Handler )
   
	parser.parse(filename)
	''''#content = open(filename,"r")
	soup = BeautifulSoup(open(filename))
	#print soup.find_all(DOCNO = True)
	#print soup.find_all('DOCNO')
	for link in soup.find_all('DOCNO'):
		print link.get_text(strip = True)
	print soup.title
	tag_body = soup.body
	#tag_body.b.decompose()
	#soup.p.decompose()
	print tag_body.get_text(strip = True)'''
		


infilePath = "//home//echo//Documents//WT2G"
outfilePath = "Outfiles_WT2G"
outfile = open("WT2G_all.xml","wb")
listfile = open("listfile_org.txt","w")

	

if __name__ == '__main__':
	''''if os.path.exists(infilePath):
		print "File path exists \n"
		get_file_path(infilePath,listfile)
	else:
		print "File path does not exist\n"
		
	finpaths = open("listfile.txt","r")
	finpath = finpaths.readlines()
	filenums = 0
	for filepath in finpath:
		filepath = filepath.strip('\n')
		if os.path.exists(filepath):
			filenums = filenums + 1
			outfile_new = open(filepath.replace(".GZ",".xml"),"w")
			read_gz_write_xml_file(filepath,outfile_new)
		else:
			print "does not exist the GZ file \n"
			break
	print "the num of existing GZ files is :"+ str(filenums)
	print "get xml files of all GZ files\n"

	finpaths.close()'''

	xml_name= "body.xml"
	read_xml_file(xml_name)
