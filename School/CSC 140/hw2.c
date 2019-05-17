/*
Warren Quattrocchi
2025
hw2
csc 140
*/

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

//fuction headers
void selectionSort(int *arr ,int size);

//main
int main(void)
{
	int c;
	int i;
	int size;
	clock_t timer;
	//seed rand
	srand(0);

	//delcare array size increments of 1000
	for(c = 0; c <= 40; c++)
	{
		size = c * 1000;
		int arr[size];

		//fill array with random numbers in rang 0-999
		for(i =0; i <size; i++)
		{
			arr[i] = (rand() %1000);
		}
	//start clock
        timer = clock();
	//run selection sort
	selectionSort(arr, size);
	//get total time elapsed
	timer  = clock() - timer;
	double time = ((double)timer)/CLOCKS_PER_SEC;
	printf("Time to sort array of size %i was %lf seconds \n", size,time);
	}
}

//selection sort function
void selectionSort(int *arr, int size)
{
	//declare variables
	int i, min, j,temp;
	//loop from i to size - 1
	for (i = 0; i< size-1; i++)
	{
		//set minimum index to i
		min = i;
		//loop from i to array size
		for(j = i+1; j <size; j++)
		{
			/*if value in j < value in min
			  set new min to j
			*/
			if(arr[j] < arr[min])
				min = j;
		}
		//swap the elements
		temp = arr[i];
		arr[i] = arr[min];
		arr[min] = temp;
	}
}

