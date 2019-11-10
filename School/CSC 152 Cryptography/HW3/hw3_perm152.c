/*Warren Quattrocchi
  CSC 152
  2025
  10/8/2019

  Takes an array of 64 unsigned chars as an input for the 
  permutation function perm152 and outputs an invertible ciphertext
  using SSE registers
*/

#include <stdio.h>
#include <inttypes.h>
#include <string.h>
#include <emmintrin.h>

//function headers
static void update(__m128i * w, __m128i * x, __m128i * y, __m128i * z);
static __m128i rotl(__m128i x, int i);
void perm152(unsigned char * in, unsigned char * out);

//input: array of 64 unsigned chars
//output: permutation of input
void perm152(unsigned char * in, unsigned char * out)
{
    int i;
    //load in into 4 sse registers    
    __m128i x1 = _mm_loadu_si128((__m128i *)(in));
    __m128i x2 = _mm_loadu_si128((__m128i *)(in + 16));
    __m128i x3 = _mm_loadu_si128((__m128i *)(in + 32));
    __m128i x4 = _mm_loadu_si128((__m128i *)(in + 48));

    for(i = 0; i < 10; i++)
    {
        //call update once for the 4 calls in hw2
        update(&x1, &x2, &x3, &x4);
        
        //shuffle to mix diagonally
        x2 = _mm_shuffle_epi32(x2, 0b00111001);
        x3 = _mm_shuffle_epi32(x3, 0b01001110);
        x4 = _mm_shuffle_epi32(x4, 0b10010011);
        update(&x1, &x2, &x3, &x4);
        
        //shuffle integers back to orignal position
        x2 = _mm_shuffle_epi32(x2, 0b10010011);
        x3 = _mm_shuffle_epi32(x3, 0b01001110);
        x4 = _mm_shuffle_epi32(x4, 0b00111001);
    }
    
    //store each vector into out
    _mm_storeu_si128((__m128i *)(out), x1);
    _mm_storeu_si128((__m128i *)(out + 16), x2);
    _mm_storeu_si128((__m128i *)(out + 32), x3);
    _mm_storeu_si128((__m128i *)(out + 48), x4); 
    
}

//update function as described in homework using SSE equivalents
static void update(__m128i  *w, __m128i *x, __m128i *y, __m128i * z)
{
    *w = _mm_add_epi32 (*w, *x);
    *z = _mm_xor_si128(*z, *w);   
    *z =  rotl(*z, 16);

    *y = _mm_add_epi32(*y, *z);
    *x = _mm_xor_si128(*x, *y);
    *x = rotl(*x, 12);

    *w = _mm_add_epi32(*w, *x);
    *z = _mm_xor_si128(*z, *w);
    *z = rotl(*z, 8);

    *y = _mm_add_epi32(*y, *z);
    *x = _mm_xor_si128(*x, *y);
    *x = rotl(*x, 7);
   
}

//rotate each 32 bit int in __m128i left by i
static __m128i rotl(__m128i x, int i)
{
    //take the left shift of x
    __m128i tempL = _mm_slli_epi32(x, i);
    
    //take the right shift of x
    __m128i tempR = _mm_srli_epi32(x, 32 - i);

    //return the 'or' of both shifts
    return _mm_or_si128(tempL, tempR);
}
 
