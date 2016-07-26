#include <stdio.h>
/* 
 Can you say without compilation what is the output?
*/

int main()
{
	int a;
	int *ptr;
	a = 100;
	ptr = &a;
	printf("%d\n",++(*ptr));
	printf("%d\n",(*ptr)++);
	return 0;
}
