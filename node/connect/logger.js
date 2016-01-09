// 自带的connect中间件
var  connect = require("connect");
var app = connect();
app.use(connect.logger()); // err ?
app.listen(3000);