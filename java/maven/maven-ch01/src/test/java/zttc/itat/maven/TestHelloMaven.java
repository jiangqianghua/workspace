/*************************************************************************
	> File Name: TestHelloMaven.java
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Fri 21 Aug 2015 09:16:10 PM PDT
 ************************************************************************/
package zttc.itat.maven ;

import org.junit.* ;
import static org.junit.Assert.* ;
public class TestHelloMaven
{
	@Test
	public void testSayHello()
	{
		HelloMaven hm = new HelloMaven() ;
		String str = hm.sayHello("maven");
		assertEquals(str,"hello:maven");
	}
}
