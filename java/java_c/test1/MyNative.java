/*************************************************************************
	> File Name: MyNative.java
	> Author: qianghua jiang
	> Mail: 240437339@qq.com
	> Created Time: Sun 02 Aug 2015 04:19:46 AM PDT
 ************************************************************************/
public class MyNative{

	public void showParms(String s , int i , boolean b){
		showParms0(s,i,b);
	}
	private native void showParms0(String s , int i , boolean b);
	static {
		System.loadLibrary("MyNative");
	}
	public static void main(String[] args){
		MyNative obj = new MyNative();
		obj.showParms("hello",23,true);
	}
}
