/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
#include<stdio.h>
#include"MyNative.h"
/*
 * Class:     MyNative
 * Method:    showParms0
 * Signature: (Ljava/lang/String;IZ)V
 */
JNIEXPORT void JNICALL Java_MyNative_showParms0
  (JNIEnv *env, jobject obj, jstring s, jint i, jboolean b)
{
	const char	* szStr = (*env)->GetStringUTFChars(env,s,0);
	printf("String = [%s]\n",szStr);
	printf("int = %d\n",i);
	printf("boolean = %s\n",(b==JNI_TRUE?"true":"false"));
	(*env)->ReleaseStringUTFChars(env , s , szStr);
}
