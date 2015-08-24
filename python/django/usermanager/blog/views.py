# Create your views here.
from django.http import HttpResponse , HttpResponseRedirect
from django.template import loader , Context
from django.shortcuts import render_to_response
from blog.models import Employee , Author , Book,BlogUser
from django import forms
def index(req):
	return HttpResponse("<h1>hello world djiang<h1>")

class Person(object):
	def __init__(self , name , age , sex):
		self.name = name 
		self.age = age
		self.sex = sex
	
	def say(self):
		return 'I am' + self.name


def index1(req):
#	t = loader.get_template('index1.html')
#	c = Context({})
#	return HttpResponse(t.render(c))
#	return render_to_response('index1.html',{'title':'my page','user':'jiangqianghua'})
	userdict = {'name':'tom','age':27,'sex':'male'}
#	return render_to_response('index2.html',{'user':user})

	user = Person('jiang',28,'male')
	book_list = ['python' , 'java','php']

	return render_to_response('index4.html',{'user':user , 'book_list':book_list,'userdict':userdict})

def employee(req):
	emps = Employee.objects.all();
	return render_to_response('employee.html',{'emps':emps})

def show_author(req):
	authors = Author.objects.all()
	return render_to_response('show_author.html',{'authors':authors})

def show_book(req):
	books = Book.objects.all()
	return render_to_response('show_book.html',{'books':books})

class UseForm(forms.Form):
	name = forms.CharField()

def register(req):
	print req.method
#	return render_to_response('register.html',{})
	if req.method == 'POST':
		form = UseForm(req.POST)
		if form.is_valid():
			print form.cleaned_data
			return HttpResponse('ok');
	else:
		form = UseForm()
		return render_to_response('register.html',{'form':form})

class uploadUserForm(forms.Form):
	username = forms.CharField()
	headImg = forms.FileField()


def upload(req):
#	print req.GET['username']
	if req.method == 'POST':
		form = uploadUserForm(req.POST,req.FILES)
		if form.is_valid():
			#print form.cleaned_data['username']
			print req.POST['username']
			#print req.POST['headImg']  is wrong
			#print req.FILES['headImg']
			print form.cleaned_data['headImg'].name
			print form.cleaned_data['headImg'].size
			# start read upload file 
			fp = file('./uploadfile/'+form.cleaned_data['headImg'].name,'wb')
			s = form.cleaned_data['headImg'].read()
			fp.write(s)
			fp.close()
			return HttpResponse('ok')
	else:
		uf = uploadUserForm()
	return render_to_response('upload.html',{'uf':uf})

#########  login ########
class BlogUserForm(forms.Form):
	username = forms.CharField()
	password = forms.CharField(widget=forms.PasswordInput)

def BlogRegist(req):
	if req.method == 'POST':
		uf = BlogUserForm(req.POST)
		if uf.is_valid():
			username1 = uf.cleaned_data['username']
			password1 = uf.cleaned_data['password']
			print username1 , password1
			# save user
			BlogUser.objects.create(username=username1,password=password1)
			return HttpResponseRedirect('/blog/bloglogin/');
			#return HttpResponse('ok')
	else:
		uf = BlogUserForm()
	return render_to_response('BlogRegist.html',{'uf':uf})

def BlogLogin(req):
	if req.method == 'POST':
		uf = BlogUserForm(req.POST)
		if uf.is_valid():
			username1 = uf.cleaned_data['username']
			password1 = uf.cleaned_data['password']
			print username1 , password1
			# check user
			users = BlogUser.objects.filter(username__exact=username1,password__exact=password1)
			if users:
				response = HttpResponseRedirect('/blog/blogindex/')
				response.set_cookie('username',username1,3600)
				return response
			else:
				return HttpResponseRedirect('/blog/bloglogin/')
	else:
		uf = BlogUserForm()
	return render_to_response('BlogLogin.html',{'uf':uf})

def BlogIndex(req):
	username = req.COOKIES.get('username','')
	print 'cookie:',username
	return render_to_response('BlogIndex.html',{'username':username})

def BlogLogout(req):
	print 'delete cookie'
	response = HttpResponse('logout')
	response.delete_cookie('username')
	return response
