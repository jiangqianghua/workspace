from django.conf.urls import patterns, include, url
#from blog.views import index
# Uncomment the next two lines to enable the admin:
from django.contrib import admin
admin.autodiscover()

urlpatterns = patterns('blog.views',
    # Examples:
    # url(r'^$', 'usermanager.views.home', name='home'),
    # url(r'^usermanager/', include('usermanager.foo.urls')),

    # Uncomment the admin/doc line below to enable admin documentation:
    # url(r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    url(r'^admin/', include(admin.site.urls)),
	#url(r'^blog/index$',index)
	#	url(r'^blog/index/$',"index"),
	#url(r'^blog/index/(?P<id>\d{2}/$)','index')
	url(r'^blog/index/$','index'),
	url(r'^blog/index1/$','index1'),
	url(r'^blog/employee/$','employee'),
	url(r'^blog/show_author/$','show_author'),
	url(r'^blog/show_book/$','show_book'),
	url(r'^blog/register/$','register'),
	url(r'^blog/upload/$','upload'),
	url(r'^blog/blogregist/$','BlogRegist'),
	url(r'^blog/bloglogin/$','BlogLogin'),
	url(r'^blog/blogindex/$','BlogIndex'),
	url(r'^blog/bloglogout/$','BlogLogout')
)


