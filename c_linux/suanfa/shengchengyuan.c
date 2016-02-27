// 生成元
#include<stdio.h>
#include<string.h>
#define maxn 100

int ans[maxn];

int main()
{
	int T , n ; 
	memset(ans , 0 ,sizeof(ans));
	int m ; 
	for(m = 1 ; m < maxn ; m++)
	{
		int x = m , y = m ;
		while(x > 0) {y += x%10; x/=10 ;}
		if(ans[y] == 0 || m < ans[y]) ans[y] = m;
	}
	printf("---------\n");
	scanf("%d" , &T);
	while(T--){
		scanf("%d",&n);
		printf("%d\n", ans[n]);
	}
	return 0 ;
}