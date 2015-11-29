import urllib2
import re
import os
req = urllib2.urlopen("http://item.jd.com/1753267982.html")

buf = req.read()

listurl = re.findall(r'src=.+\.jpg|src=.+\.png',buf)


#print listurl
#save image

i = 0 
for url in listurl:
	#print url 
	#print url.find(r'\\')
  	#portion = os.path.splitext(url)
  	#print portion
	#f = open(url
	splits = re.split(r'src=\'',url)
	print splits
	#f = open(splits[1],'w')
	'''
	f = open(str(i)+'.jpg','w')
	req = urllib2.urlopen(splits[1])
	data = req.read()
	f.write(data)
	f.close()
	i = i + 1
	'''