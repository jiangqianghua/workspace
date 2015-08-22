/*************************************************************************
	> File Name: Hello.java
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Fri 21 Aug 2015 08:45:20 PM PDT
 ************************************************************************/
package zttc.itat.maven.ch02;

import zttc.itat.maven.HelloMaven ;
public class Hello
{
	public String say(String name)
	{
		HelloMaven hm = new HelloMaven();
		return hm.sayHello(name);
		
	}
}
