//  该部分实现对日志的增删改查操作
//												|------------------>timetrack 模块
//																	|-->Add function	
//																	|-->Archive function
//																	|-->Delete function
//	

//																|-->Show function
// javascript 语言 , nodejs 代码


// 加入qs操作，主要功能对url进行拆分和分析
var qs = require('querystring');

// 发送对html的响应  ，exports 标志表示对外部是可见的
exports.sendHtml = function (res , html) {
	// 发送头部信息
	res.setHeader('Content-Type' , 'text/html ; charset=utf-8'); // 设置html的内容类别 html格式， utf-8 编码
	res.setHeader('Content-Length' , 'Buffer.byteLength(html)'); // 设置发送内容长度，字节为单位
	res.end(html);
};

// 解析html post的数据
exports.parseReceivedData = function(req , cb) {
	var body = '' ;
	req.setEncoding('utf-8');
	req.on('data', function(chunk){
		body += chunk ; // 接受post数据
	} );
	req.on('end' , function(){
		var data = qs.parse(body); // 使用qs对body进行解析
		cb(data);
	} );
};

// 渲染简单表单
exports.actionForm = function(id , path , label) {
	// 创建 form表单
	var html = '<form method="POST" action="'+path+'"> '+
	'<input type="hidden" name="id" value="'+id+'"/> '+
	'<input type="submit" value="'+label+'"/></form>' ;
	return html ;
};
// 添加数据
exports.add = function(db , req , res){
	exports.parseReceivedData(req, function(work){
		// 执行sql语言，添加一条记录
		db.query(
			"insert into work(hours , date , description)" +
			"values(?,?,?)",[work.hours , work.date , work.description],
			function(err) {
				if(err){
					console.log(err);
				}
				exports.show(db ,res);
			}
			);
	}); // 解析htpp的post信息
};

// 删除工作记录
exports.delete = function(db , req ,res){
	exports.parseReceivedData(req, function(work){
		console.log('start delete ' + work.id);
		// 执行sql语言，添加一条记录
		db.query(
			"delete from work where id=?" ,[work.id],
			function(err) {
				if(err){
					console.log(err);
				}
				exports.show(db ,res);
			}
			);
	}); // 解析htpp的post信息
};

exports.Archive = function(db , req , res){
	exports.parseReceivedData(req , function(work){
		// 执行sql语言，更新操作
		db.query(
			"delete work set Archived=1 where id=?" ,[work.id],
			function(err) {
				if(err){
					console.log(err);
				}
				exports.show(db ,res);
			}
			);
	});
};

// 显示数据
exports.show = function (db ,res , showArchived){
	var query  = "select * from work where Archived=? order by date desc";
	var archiveValue = (showArchived)?1:0 ;
	console.log("show...");
	db.query(
		query ,[archiveValue],
		function(err , rows) {
			var html = (showArchived)
			?''
			: '<a href="/Archived">Archived Work</a><br/>';
			html += exports.workHitlistHtm(rows);
			html += exports.workFormHtml();
			exports.sendHtml(res , html);
		}
	);
};

exports.showArchived = function(db ,res){
	exports.show(db ,res , true);  //只显示归档信息
}

exports.workHitlistHtm = function(rows){
	var html = '<table>';
	for(var i in rows){
		html += '<br>' ;
		html += '<td>' + rows[i].date + '</date>';
		html +='<td>' + rows[i].hours + '</td>' ;
		html += '<td>' + rows[i].description + '</td>';
		if (rows[i].Archived){
			html += '<td>' + exports.workArchiveForm(rows[i].id) + '</td>' ;
		}
		html += '<td>' + exports.workDeleteForm(rows[i].id) + '</td>' ;
		html += '</tr>';
	}
	html += '</table>';
	return html ;
}

exports.workFormHtml = function(){
	var html = '<form method="post" action="/">'+
	'<p> Data (YYYY-MM-DD) : <br/><input name="date" type="text"></p>'+
	'Hours worked:<br/><input name="hours" type="text"></p>'+
	'<p>Description:<br/>'+
	'<textarea name="description"></textarea></p>'+
	'<input type="submit" value="Add" />'+
	'</form>';
	return html ;
};

exports.workArchiveForm = function(id){
	return exports.actionForm(id , '/archive' , 'Archive');
}
exports.workDeleteForm = function(id){
	return exports.actionForm(id , '/delete' , 'Delete');
}
