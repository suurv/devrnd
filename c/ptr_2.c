#include <stdio.h>
/* 
 Can you say without compilation what is the output?
*/

int main()
{
	int a;
	int *ptr;
	int size;
	ptr = &a;
	a = 100;
	printf("%d\n",++*ptr);
	printf("%d\n",++*(++ptr));
	return 0;
}
