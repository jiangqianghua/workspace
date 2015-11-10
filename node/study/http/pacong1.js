var http = require('http');
var cheerio = require('cheerio');
var url = 'http://www.imooc.com/learn/348';

function filterChapters(html){
	var $ = cheerio.load(html);
	var chapters = $('.chapter');
	// 构建数据结构如下
	//console.log(chapters);
	/*[{
		chapterTitle:'',
		videos:[
			title:'',
			id:''
		]
	}]*/
	var courseData = [] ;
	chapters.each(function(item){
		var chapter = $(this);
		//console.log(chapter);
		var chapterTitle = chapter.find('strong').text();
		//console.log(chapterTitle);
		var  videos = chapter.find('.video').children('li');
		//var videoss = videos.find('a').text();
		//console.log(videoss);
		var chapterData = {
			chapterTitle:chapterTitle,
			videos:[]
		}
		videos.each(function(item){
			var video = $(this).find('a');
			//console.log(video);
			var videoTitle = video.text();
			//console.log(videoTitle);
			var id = video.attr('href').split('video/')[1];
			//console.log(id);
			chapterData.videos.push({
				title:videoTitle,
				id:id
			});
		});
		courseData.push(chapterData);
	});
	return courseData ;
}

function printCourseInfo(courseData){
	courseData.forEach(function(item){
		var chapterTitle = item.chapterTitle ;
		console.log(chapterTitle+'\n');
		item.videos.forEach(function(video){
			console.log(' [' + video.id + ']' + video.title +'\n');
		})
	});
}
http.get(url , function(res){
	var html = '';

	res.on('data',function(data){
		html +=data ;
	})

	res.on('end',function(){
		var courseData = filterChapters(html);
		//console.log(courseData);
		printCourseInfo(courseData);
	})
}).on('error',function(){
	console.log('get learn data error');
})
