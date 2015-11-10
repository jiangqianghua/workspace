// http模块
var http = require("http");
//文件系统模块
var fs = require("fs");
//提供文件系统路径模块
var path = require("path");
//附加的mime模块由根据文件扩展名得出mime类型能力
var mime = require("mime");
//cache用来缓存文件内容对象
var cache = {};
