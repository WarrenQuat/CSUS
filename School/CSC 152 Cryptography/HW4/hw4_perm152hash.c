/*Warren Quattrocchi
  CSC 152
  2025
  10/23/2019

  A hash function using sponge contruction and perm152 as the permutation function.
	inputs message, bytes to be processed, and a buffer, returns buffer as a b bit hash of m
*/

#include <string.h>
#include <stdio.h>


static void xor(unsigned char * dst, unsigned char * src, int num_bytes);
void perm152(unsigned char* in, unsigned char* out);
void perm152hash(unsigned char *m, int mbytes, unsigned char *res);


void perm152hash(unsigned char *m, int mbytes, unsigned char * res)
{
    int r, c, b;
    r = b = c = 32;

    //create final padded block array
    unsigned char mPadded[r];

    //create block
    unsigned char block[r + c];
    for(int i = 0; i < (r + c); i++)
        block[i] = (unsigned char)(i+1);   
    //process head of m
    while(mbytes >= r)
    {
		//xoe r bytes of m into block
        xor(block,m,r);
		
		//run block through perm152
        perm152(block, block);
		
		//decrement # of bytes remaining
        mbytes-=r;

		//increment m pointer
        m += r;        
    }
    //process tail
    memcpy(mPadded, m,(size_t)mbytes); 

    //10* padding
    mPadded[mbytes] = 0b10000000;
    for(int i = mbytes + 1; i < r; i++)
        mPadded[i] = 0b00000000;
    
    //run tail through permutation
    xor(block,mPadded,r);
    perm152(block,block);

    //copy min(r,b)  bits of block into res
    int min = (r <= b ? r : b);
    memcpy(res, block,(size_t)min);

	//incrmment output buffer pointer
    res +=r;
    
	//if # of output bytes > r
    if (b > r)
    {
		//run block through perm152
        perm152(block,block);
		
		//copy b-r remaining bytes into output buffer
        memcpy(res, block, (size_t)(b - r));
    }
}

static void xor(unsigned char * dst, unsigned char * src, int num_bytes)
{
    for(int i = 0; i < num_bytes; i++)
        dst[i] ^= src[i];
}
