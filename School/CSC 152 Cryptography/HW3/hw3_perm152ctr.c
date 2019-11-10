/*Warren Quattrocchi
  CSC 152
  2025
  10/8/2019

  Performs ctr encrption using a passed in plaintext, block, output buffer,
  key, key size, and # of bytes to be processed
*/

#include <string.h>

// in and out are pointers to 64-byte buffers
// implementation is in a separate file
void perm152(unsigned char *in, unsigned char *out);

// in and out are pointers to 64-byte buffers
// key points to 0-to-64-byte buffer, kbytes indicates it's length
// Computes: (perm152(in xor key) xor key) and writes 64-bytes to out
//           key has (64 - kbytes) zero bytes appended to its end
static void perm152_bc(unsigned char *in, unsigned char *out,
                       unsigned char *key, int kbytes)
{
    unsigned char perm_in[64];
    unsigned char perm_out[64];

    // copy in to perm_in
    memcpy(perm_in, in, 64);
    
    // xor key to perm_in
    for(int i = 0;  i < kbytes; i++)
        perm_in[i] = perm_in[i] ^ key[i];

    // perm152 perm_in to perm_out
    perm152(perm_in, perm_out);
    
    // xor key to perm_out
    for(int i = 0; i < kbytes; i++)
        perm_out[i] = perm_out[i] ^ key[i];

    // copy perm_out to out
    memcpy(out, perm_out, 64);

}

void perm152ctr(unsigned char *in,    // Input buffer
                unsigned char *out,   // Output buffer
                int nbytes,           // Number of bytes to process
                unsigned char *block, // A 64-byte buffer holding IV+CTR
                unsigned char *key,   // Key to use. 16-32 bytes recommended
                int kbytes)           // Number of key bytes <= 64 to use.
{
    unsigned char buf[64];
    while (nbytes > 0) {
        // perm152_bc block to buf
        perm152_bc(block, buf, key, kbytes);
    
        // len = min(nbytes, 64)
        int len;
        if (nbytes < 64)
            len = nbytes;
        else
            len = 64;
        // xor len bytes of buf with in to out
        for(int i = 0; i < len; i++)
            out[i] = buf[i] ^ in[i];

        // increment block
        int  i = 63;
        do {
            block[i]++;
            i--;
        }while (block[i+1] == 0);

        in = in + len;
        out = out + len;
        nbytes = nbytes - len;
    }
}
