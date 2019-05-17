/*-------------------------------------------------*/
/* Warren Quattrocchi                              */
/* Lab 4                                           */
/* Figure the perimeter and area of a polygon      */
/* surrounded by a circle                          */

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
//#define FILENAME "lab4sample.dat"
#define FILENAME "lab4.dat"

int main(void)
{
	double radius,nsides,perimeter,area;
	FILE * input_file;
	FILE * answer_file;
	   input_file = fopen (FILENAME, "r");
	   if(input_file == NULL)
	   {
		printf("Error on opening the data file\n");
		exit (EXIT_FAILURE);
	   }
	   answer_file = fopen("lab4.txt", "w");
	   if(answer_file == NULL)
	   {
		printf("Error on opening the output file\n");
		exit (EXIT_FAILURE);
	   }

	fprintf(answer_file, "\nWarren Quattrocchi.  Lab 4.\n\n");
	fprintf(answer_file, "            Number      Perimeter      Area Of  \n");
	fprintf(answer_file, " Radius    Of Sides    Of Polygon      Polygon  \n");
	fprintf(answer_file, "--------   --------   ------------   ----------- \n");
	while((fscanf(input_file, "%lf%lf", &radius, &nsides)) == 2)
	{
	     perimeter = 2*nsides*radius*sin(M_PI/nsides);
	     area = .5*nsides*pow(radius,2) * sin((2*M_PI)/nsides);
		
	     fprintf(answer_file, "%7.2lf   %8.2lf   %12.4lf   %11.4lf \n",radius,nsides,perimeter,area);
	}
	fclose(input_file);
	fclose(answer_file);
	return EXIT_SUCCESS;
}
/*------------------------------------------------------------------*/
