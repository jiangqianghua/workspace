from django.contrib import admin
from blog.models import Entry , Blog, Author , Book, AdminFile

admin.site.register(Entry)
admin.site.register(Blog)
admin.site.register(Author)
admin.site.register(Book)
admin.site.register(AdminFile)

