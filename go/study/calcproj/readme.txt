go 模拟构建一个项目
1. 目录结构
2. 代码 （附件）
3. 把该项目添加到环境变量
export GOPATH=~./calcproj
$source ~/.bashrc

4.开始构建
cd bin  进入到bin目录下
go build src

5.运行程序
./calc add 1 2

6 单元测试
我们  add_test.go  和 aqrt_test.go是测试程序，那么如何测试呢？
>> go test simplemath
ok  	simplemath	0.007s

如果单元测试运行有错误，错误信息如下
--- FAIL: TestAdd1 (0.00 seconds)
 add_test.go:8: Add(1,2) failed. GO 3 ,expected 3.
FAIL
FAIL	simplemath	0.004s

7 调试 gdb calc