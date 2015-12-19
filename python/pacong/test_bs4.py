# -*- coding: utf-8 -*-

from bs4 import BeautifulSoup
import re


html_doc = """
<html><head><title>The Dormouse's story</title></head>
<body>
<p class="title"><b>The Dormouse's story</b></p>

<p class="story">Once upon a time there were three little sisters; and their names were
<a href="http://example.com/elsie" class="sister" id="link1">Elsie</a>,
<a href="http://example.com/lacie" class="sister" id="link2">Lacie</a> and
<a href="http://example.com/tillie" class="sister" id="link3">Tillie</a>;
and they lived at the bottom of a well.</p>

<p class="story">...</p>
"""

soup = BeautifulSoup(html_doc , "html.parser" , from_encoding='utf-8')

print '获取所有的连接'

links = soup.find_all('a')
for link in links:
	print link.name , link['href'],link.get_text()

'''
获取所有的连接
a http://example.com/elsie Elsie
a http://example.com/lacie Lacie
a http://example.com/tillie Tillie
'''

print '获取lacie的连接'
link_node = soup.find('a' , href='http://example.com/lacie')
print link_node.name , link_node['href'],link_node.get_text()

print '正则匹配'
link_node1 = soup.find('a',href=re.compile(r"ill"))
print link_node1.name , link_node1['href'],link_node1.get_text()

print '获取p段落文字'
p_node = soup.find('p' , class_="title")
print p_node.name , p_node['class'],p_node.get_text()

'''
获取lacie的连接
a http://example.com/lacie Lacie
正则匹配
a http://example.com/tillie Tillie
获取p段落文字
p [u'title'] The Dormouse's story
'''