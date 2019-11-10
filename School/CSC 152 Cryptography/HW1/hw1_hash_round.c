/*Warren Quattrocchi
  CSC 152
  2025
  9/11/2019

  takes 4 64 bit unsigned integers and runs them 
  through a hash function
*/

#include <stdint.h>
#include <stdio.h>
#include <math.h>
#include <inttypes.h>

void hash_round(uint64_t *v);
static uint64_t reverse_bytes(uint64_t v);
static uint64_t rotate_left(uint64_t v, int i); 

void hash_round(uint64_t *v)
{
    //reverse bytes for big endian
    uint64_t v0 = reverse_bytes(v[0]);
    uint64_t v1 = reverse_bytes(v[1]);
    uint64_t v2 = reverse_bytes(v[2]);
    uint64_t v3 = reverse_bytes(v[3]);
    
    v0 = (v1 + v0);
    v1 = rotate_left(v1, 13);
    v1 = v1 ^ v0;
    v0 = rotate_left(v0,32);
    
    v2 = (v2 + v3);
    v3 = rotate_left(v3, 16);
    v3 = v2 ^ v3;
    
    v0 = (v3 + v0);
    v3 = rotate_left(v3,21);
    v2 = (v2 + v1);
    v1 = rotate_left(v1,17);
    v1 = v1 ^ v2;

    v2 = rotate_left(v2,32);
    v3 = v0 ^ v3;
    
    //reverse bytes again
    v[0] = reverse_bytes(v0);
    v[1] = reverse_bytes(v1);
    v[2] = reverse_bytes(v2);
    v[3] = reverse_bytes(v3);
}

static uint64_t reverse_bytes(uint64_t v)
{
    uint64_t a = (v & 0x00000000000000ff) << 56;
    uint64_t b = (v & 0x000000000000ff00) << 40;
    uint64_t c = (v & 0x0000000000ff0000) << 24;
    uint64_t d = (v & 0x00000000ff000000) << 8;
    
    uint64_t e = (v & 0x000000ff00000000) >> 8;
    uint64_t f = (v & 0x0000ff0000000000) >> 24;
    uint64_t g = (v & 0x00ff000000000000) >> 40;
    uint64_t h = (v & 0xff00000000000000) >> 56;

    return (a | b | c | d| e | f | g | h);
}

//rotate a 64 bit value left
static uint64_t rotate_left(uint64_t v, int i)
{
    v = (v << i) | (v >> (64 - i));
    return v;
}

