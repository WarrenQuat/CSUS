/*Warren Quattrocchi
CSC152
2025
9/11/2019
Decrypts a file using the same technique proved in the encryption program
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <openssl/evp.h>

#define BLK_LEN 16
#define BUF_LEN 1024

int main(int argc, const char* argv[]) {
    int len;
    size_t bytes_read;
    unsigned char key[32]; // Receive SHA-2-256 hash of user pass phrase
    unsigned char iv[BLK_LEN];
    unsigned char in_buf[BUF_LEN];
    unsigned char out_buf[BUF_LEN+16];
    EVP_MD_CTX *mdctx;
    
    if (argc != 4) {
        printf("Error: wrong number of arguments\n");
        return EXIT_FAILURE;
    }
        
    // Hash pass phrase
    mdctx = EVP_MD_CTX_create();
    EVP_DigestInit_ex(mdctx, EVP_sha256(), NULL);
    EVP_DigestUpdate(mdctx, argv[1], strlen(argv[1]));
    EVP_DigestFinal_ex(mdctx, key, NULL);
    EVP_MD_CTX_destroy(mdctx);
        
    // Create new file, write a random iv to it
    FILE *src = fopen(argv[2], "rb");
    FILE *dst = fopen(argv[3], "wb");
    if (src==NULL || dst==NULL) {
        printf("Error: could not open file\n");
        return EXIT_FAILURE;
    }
    //read in IV
    fread(iv, 1, BLK_LEN, src);

    // Set up context with alg, key and iv
    EVP_CIPHER_CTX *ctx = EVP_CIPHER_CTX_new();
    EVP_DecryptInit_ex(ctx, EVP_aes_256_cbc(), NULL, key, iv);

    do {
        bytes_read = fread(in_buf, 1, BUF_LEN, src);  
        if (bytes_read > 0) {
            EVP_DecryptUpdate(ctx, out_buf, &len, in_buf, (int)bytes_read);
            fwrite(out_buf, 1, (size_t)len, dst);
        }
    } while (bytes_read == BUF_LEN);
    EVP_DecryptFinal_ex(ctx, out_buf, &len);
    fwrite(out_buf, 1, (size_t)len, dst);
        
    // Clean up
    EVP_CIPHER_CTX_free(ctx);
    OPENSSL_cleanse(key, sizeof(key));
    OPENSSL_cleanse(in_buf, sizeof(in_buf));
    
    return EXIT_SUCCESS;
}
