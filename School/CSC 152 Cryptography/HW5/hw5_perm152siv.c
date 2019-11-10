/*Warren Quattrocchi
  CSC 152
  2025
  11/6/2019

  Performs siv authenticated encryption on pbytes using perm152hash and perm152ctr
  using a nonce and a key
*/


#include <string.h>
#include <stdlib.h>

//function prototypes
void perm152hash(unsigned char *m, int mbytes, unsigned char * res);
void perm152ctr(unsigned char *in, unsigned char *out, int nbytes, unsigned char*block, unsigned char *key, int kbytes);

void perm152siv_encrypt(unsigned char *k, int kbytes,
			unsigned char *n, int nbytes,
			unsigned char *p, int pbytes,
			unsigned char *siv, unsigned char *c);

int perm152siv_decrypt(unsigned char *k, int kbytes,
			unsigned char *n, int nbytes,
			unsigned char *c, int cbytes,
			unsigned char *siv, unsigned char *p);


//siv encrypt
void perm152siv_encrypt(unsigned char *k, int kbytes,
			unsigned char *n, int nbytes,
			unsigned char *p, int pbytes,
			unsigned char *siv, unsigned char *c)
{
	//total bytes to hash
	int tempk = kbytes;
	int hbytes = 48+pbytes;

	//allocate to_hash on the heap for large p
    unsigned char *to_hash = malloc(hbytes * sizeof(unsigned char));
	
	//copy of to_hash ptr to help with padding
    unsigned char * ptr = &to_hash[0];

	//copy the key into to_hash and increment ptr
    memcpy(ptr, k, kbytes);
    ptr +=kbytes;

	//pad key with 0's to 32 bytes
    while (tempk < 32)
    {
       *ptr = 0b00000000;
       ptr++;
       tempk++;
    }

	//copy the nonce into to_hash and increment ptr
    memcpy(ptr, n, nbytes);
    ptr+=nbytes;

	//pad nonce to 16 bytes
    while(nbytes < 16)
    {
        *ptr = 0b00000000;
        ptr++;
        nbytes++;
    }

	//copy p to the end of to_hash
    memcpy(ptr, p, pbytes);

	//hash to_hash using perm152hash and copy the first
	//16 bytes to siv
	//clear and free to_hash
    perm152hash(to_hash, hbytes,to_hash);
	memcpy(siv,to_hash, 16);
	memset(to_hash,0,hbytes);
	free(to_hash);

	//initialize block and set the first 16 bytes to siv
    unsigned char block[64];
    memcpy(block, siv, 16);

	//initialize counter to 0
    for(int i = 16; i < 64; i++)
       block[i] = 0b00000000;

	//perform ctr encryption on plaintext, return c
    perm152ctr(p, c, pbytes, block, k, kbytes); 
}

//siv decrypt
int perm152siv_decrypt(unsigned char *k, int kbytes,
			           unsigned char *n, int nbytes,
			           unsigned char *c, int cbytes,
			           unsigned char *siv, unsigned char *p)
{
	//create a temporary buffer for the plaintext
	int tempk = kbytes;
	unsigned char *temp = malloc(cbytes * sizeof(unsigned char));

	//initialize block with siv and initialize counter to 0
    unsigned char block[64];
    memcpy(block, siv, 16);
    for(int i = 16; i < 64; i++)
        block[i] = 0b00000000;

	//perform ctr encryption on ciphertext, return p to temp
    perm152ctr(c, temp, cbytes,block,k,kbytes);

	//allocate space for to_hash buffer
	int hbytes = 48 + cbytes;
    unsigned char * to_hash = malloc(hbytes * sizeof(unsigned char));
    unsigned char * ptr = &to_hash[0];	

	//copy k to hash and pad to 32 bytes
    memcpy(ptr, k, kbytes);
    ptr += kbytes;

    while (tempk < 32)
    {
       *ptr = 0b00000000;
       ptr++;
       tempk++;
    }

	//copy n to hash and pad to 16 bytes
    memcpy(ptr, n, nbytes);
    ptr+=nbytes;
    while(nbytes < 16)
    {
        *ptr = 0b00000000;
        ptr++;
        nbytes++;
    }

	//copy temporary plaintext result to to_hash
    memcpy(ptr, temp, cbytes);
	
	//hash the key,nonce, and temporary p
    perm152hash(to_hash, hbytes, to_hash);

	//compare the hash with the given siv
    int res = memcmp(to_hash, siv, 16);
	
	//clear and free the to_hash buffer
	memset(to_hash,0,hbytes);
	free(to_hash);

	//if the siv and hash are equal
	//copy temp into p, clear and free temp
	//return 0
    if(res == 0)
	{
		memcpy(p,temp,cbytes);
		memset(temp,0,cbytes);
		free(temp);
        return 0;
	}

	//else clear and free temp
	//return -1
    else
	{
		memset(temp,0,cbytes);
		free(temp);
        return -1;

	}

}
