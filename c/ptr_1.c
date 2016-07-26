#include <stdio.h>
/* 
 Can you say without compilation what is the output?
*/

int main()
{
	int a;
	int *ptr1;
	int **ptr2;
	int ***ptr3;
	int ****ptr4;
	int *****ptr5;

	a = 0;
	ptr1 = &a;
	ptr2 = &ptr1;
	ptr3 = &ptr2;
	ptr4 = &ptr3;
	ptr5 = &ptr4;

	printf("%d\n",a);
	++*ptr1;
	printf("%d\n",a);
	++**ptr2;
	printf("%d\n",a);
	++***ptr3;
	printf("%d\n",a);
	++****ptr4;
	printf("%d\n",a);
	++*****ptr5;
	printf("%d\n",a);
	return 0;
}
