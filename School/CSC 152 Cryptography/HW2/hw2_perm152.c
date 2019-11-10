/*Warren Quattrocchi
  CSC 152
  2025
  9/25/2019

  Takes an array of 64 unsigned chars as an input for the 
  permutation function perm152 and outputs an invertible cypgertext
*/

#include <stdio.h>
#include <inttypes.h>
#include <string.h>

//function headers
static void update(uint32_t * w, uint32_t * x, uint32_t * y, uint32_t * z);
static uint32_t rotl(uint32_t x, int i);
void perm152(unsigned char * in, unsigned char * out);

//input: array of 64 unsigned chars
//output: permutation of input
void perm152(unsigned char * in, unsigned char * out)
{
    int i;
    uint32_t a[16];
    memcpy(a, in, sizeof(a));
    for(i = 0; i < 10; i++)
    {
        update(&a[0], &a[4], &a[8], &a[12]);
        update(&a[1], &a[5], &a[9], &a[13]);
        update(&a[2], &a[6], &a[10], &a[14]);
        update(&a[3], &a[7], &a[11], &a[15]);
        update(&a[0], &a[5], &a[10], &a[15]);
        update(&a[1], &a[6], &a[11], &a[12]);
        update(&a[2], &a[7], &a[8], &a[13]);
        update(&a[3], &a[4], &a[9], &a[14]);
    }
	//copy resulting array into out
    memcpy(out, a, sizeof(a));
}

//update function as described in homework
static void update(uint32_t *w, uint32_t *x, uint32_t *y, uint32_t* z)
{

    *w = *w + *x;
    *z = *z ^ *w;
    *z =  rotl(*z, 16);

    *y = *y + *z;
    *x = *x ^ *y;
    *x = rotl(*x, 12);

    *w = *w + *x;
    *z = *z ^ *w;
    *z = rotl(*z,8);

    *y = *y + *z;
    *x = *x ^ *y;
    *x = rotl(*x, 7);
}

//rotate a 32 bit unsigned integer x left by i
static uint32_t rotl(uint32_t x, int i)
{
    return (x << i) | (x >> (32 - i));
}
 
