from django.db import models

# Create your models here.
class Employee(models.Model):
	name = models.CharField(max_length=20)

	def __unicode__(self):
		return self.name

class Entry(models.Model):
	name = models.CharField(max_length=30)

	def __unicode__(self):
		return self.name

class Blog(models.Model):
	name = models.CharField(max_length=30)
	entery = models.ForeignKey(Entry)

	def __unicode__(self):
		return self.name

sex_choices = (('f','famale'),('m','male'))
class Author(models.Model):
	name = models.CharField(max_length=30)
	sex = models.CharField(max_length=1,choices=sex_choices)

	def __unicode__(self):
		return self.name

class Book(models.Model):
	name = models.CharField(max_length=30)
	author = models.ManyToManyField(Author)

	def __unicode__(self):
		return self.name

class AdminFile(models.Model):
	adminname = models.CharField(max_length=30)
	filename = models.FileField(upload_to='./adminupload')

	def __unicode__(self):
		return self.adminname

class BlogUser(models.Model):
	username = models.CharField(max_length=20)
	password = models.CharField(max_length=200)

	def __unicode__(self):
		return username


