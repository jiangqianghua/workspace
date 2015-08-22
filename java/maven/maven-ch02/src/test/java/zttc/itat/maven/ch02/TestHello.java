/*************************************************************************
	> File Name: Hello.java
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Fri 21 Aug 2015 08:45:20 PM PDT
 ************************************************************************/
package zttc.itat.maven.ch02;

import org.junit.* ;
import static org.junit.Assert.* ;
public class TestHello
{
	@Test
	public void testSay()
	{
		Hello hello = new Hello() ;
		String str = hello.say("jiang");
		assertEquals(str,"hello:jiang");
	}
}
